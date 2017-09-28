# IBM Domino 9.0.1 Plugins to develop web applications using Spring Framework.

Domino only supports servlet 2.4. The latest Spring version that support that servlet level is 3.2. So, we're stuck to this version...
And because of a buggy IBM code, I had to patch the spring-web module. You can have a look at the (trivial) change on my fork of the Spring repository.

The patched jar (along with all the other jars needed by spring-webmvc) are provided in the "com.github.lhervier.domino.spring.external.spring_framework" plugin.
Sorry, I haven't modularized the jars, each in his plugin...

Bundles dependencies are :

- Spring Framework 3.2.18.RELEASE + Patched version of spring-web module
- Spring Data JPA 1.6.6.RELEASE
- FreeMarker 2.3.20
- AspectJ 1.6.12
- H2 Database Driver 1.4.191
- HikariCP 2.3.13
- Hibernate JPA provider 1.3.11.FINAL

# Installation on production environments

## Download the update site

You can download the update site from the release screen on github. It is a simple zip file that contains the needed OSGI plugins and features.

## Install on Domino Server

Create a new database named "SpringUpdateSite.nsf" (you can name it the way you want) on your server, using the "Eclipse Update Site" advanced template.

Open the database with your Notes client, and click on the "Import local update site" button. Go select the "site.xml" in the unzipped version of the update site.

Using the "Main features" view, you can disable the "com.github.lhervier.domino.spring.sample.feature" feature, and so, not deplting the sample app.

Add (or update) the notes.ini variable "OSGI_HTTP_DYNAMIC_BUNDLES". It must point to your "SpringUpdateSite.nsf" database. 
Use a comma to separate multiple values if you already have another entry (for the extlib for example).

Restart the http task (console command):

	restart task http

Check that plugins are deployed : 

	tell http osgi ss spring
	
You should see something like this :

	> tell http osgi ss spring
	[010C:0002-1DC8] 07/07/2017 20:01:20   Framework is launched.
	[010C:0002-1DC8] 07/07/2017 20:01:20   id       State       Bundle
	[010C:0002-1DC8] 07/07/2017 20:01:20   92       RESOLVED    com.github.lhervier.domino.spring.external.spring_framework_3.2.18
	[010C:0002-1DC8] 07/07/2017 20:01:20   93       ACTIVE      com.github.lhervier.domino.spring.sample_1.0.0.qualifier
	[010C:0002-1DC8] 07/07/2017 20:01:20   94       ACTIVE      com.github.lhervier.domino.spring_1.0.0.qualifier

You can now point you browser to the sample app 

	http://<your server>/spring-sample/hello

It should repond with a small JSON message. You will probably be logged in as "Anonymous".

You can also access the sample servlet in the context of a domino database :

	http://<your server>/<database>.nsf/spring-sample/hello

Log into the database, and you should see a message with your name in the JSON.

# Generating the update site yourself

## Import the projects into IBM Domino Designer

- Clone or download the source code from github into a local folder.
- Open Domino Designer
- Open the "package explorer" view, right click in the blank part, and select Import.
- In the "General" section, select "Existing projets into workspace"
- Click "Browse" and select the folder that contains this project's sources.
- Select all projects and click "Import"

The code is now imported in your IBM Domino Designer.

## Compile

Open the file "site.xml" in the "com.github.lhervier.spring.update" project.
Click the "Build All" button.

The result is in the "com.github.lhervier.spring.update" folder. This is a "standard" update site composed of :

- the "site.xml" file
- the "plugins" folder
- and the "features" folder

The update site is now ready to be zipped...

# Deploy spring plugins on IBM Domino Server (development case)

Of course, you can deploy the plugins on your development server the same way you deployed them on the production servers. But there is another way.

Once the plugins source has been imported in your workspace, you can use the "IBM Domino Debug Plugin" to make a local Domino Server use them. 
Please note that you will have to have a LOCAL Domino Server that runs on the same file system as your Designer.

First, install the "IBM Domino Debug Plugin" :

- Download the zip file from https://www.openntf.org/main.nsf/project.xsp?r=project/IBM%20Lotus%20Domino%20Debug%20Plugin
- In your IBM Domino Designer :
	- Go to File/Preferences menu, in the "Domino Designer" section, and check "Enable Eclipse plug-in install"
	- Now, go to File/Application/Install menu.
	- Select "Search for new features"
	- Add a "Zip/jar location" and go find the zip you just downloaded.
	- Click Finish, and accept licences.
	- Designer will ask to restart.
	
Once restarted :

- Open "package explorer" view
- Click on the arrow next to the green bug icon bar, and select "Debug Configurations"
- Right click on the "OSGI Framework section", and choose "New"
- Name it the way you want
- Select "Domino Osgi Framework" in the drop down list
- Set "default auto start" to "false"
- In the plugin list, UNCHECK the "target platform" plugins section.
- And in the "Workspace" section, select all the imported plugins.
- Click "Debug"
- You will have to enter the installation path of your local Domino Server
- Once done, you will have to restart the http task (using "restart task http")

As for production environment, you can check that the server has loaded your plugins by sending the following console command :

	tell http osgi ss spring

# Creating an application using Spring :

Once the plugins sources are imported in your Domino Designer and into your IBM Domino Server, you can create your own plugins, and start using Spring. 

## Create a new plugin, and a new servlet

You will have to create a new OSGI Plug-in. The "com.github.lhervier.spring.sample" plugin is a good starting point, but you can create your own easily. Simply add a dependency on the "com.github.lhervier.spring" plugin.

Then, you will have to create a new servlet class that extends the "com.github.lhervier.spring.servlet.OsgiDispatchServlet" provided by the main plugin.
For obscure class loading reasons, this main servlet have to be declared into YOUR plugin, even if it's implementation is empty.

Create a standard Spring Configuration class that you will annotate with @Configuration, @ComponentScan, @EnableWebMvc, etc...

Deploy your servlet to Domino : 

- In your plugin.xml, add an extension to "org.eclipse.equinox.http.registry.servlets" :
- Alias : Root of your servlet. It is "/spring-sample" in the sample app.
- Class : Name of the Servlet class you just created.
- Add two initialization parameters (right click on the servlet entry) : 
	- contextClass : MUST BE set to "org.springframework.web.context.support.AnnotationConfigWebApplicationContext". Xml based configuration is NOT supported.
	- contextLocation : Full name of your config class (use spaces or comma to add multiple).

For more details on developping servlets on Domino, see this web site : https://www.openntf.org/main.nsf/project.xsp?r=project/Servlet%20Sample).

## Code "the Spring way"

Then, create your Spring beans using the @Bean, @Service, or @Controller annotations... 
You can @Autowire beans to private properties as usual and map requests using @RequestMapping. See sample plugin for example.

You can store your FreeMarker templates in a "template" folder, at the root of your plugin (don't forget to export it in the "build" tab of the plugin.xml), and static files
in a "static" folder.

I wrote a NotesContext bean to access the notes context. You can @Autowired it as usual and access its methods :

- getUserSession() : Returns a lotus.domino.Session object that is opened using the current user credentials.
- getUserDatabase() : Returns a lotus.domino.Database object that points to the current database. 
  Beware that if you are accessing the servlet outside of a database context, this object will NOT be null.
- getServerSession(): Same thing as getUserSession(), but session is opened as the server.
- getServerDatabase(): Same as getUserDatabase(), but current database is opened as the server.
- getUserRoles(): This is a simple ArrayList<String> that contains the current user roles on the current database. If current database does not exists, the list will be empty.

## Inject properties from notes.ini

I also added a Spring property source that allows you to inject values from notes.ini variables. Simply use the @Value annotation in your beans :

	@Value("${directory}")
	private String directory

## Inject your own properties
	
You can also inject your own PropertySource using the "com.github.lhervier.domino.spring.propertysources" extension point. 

Using this extension point, you can inject objects that extends the "BaseNotesPropertySource" object. You will have to implement the following methods :

- an empty construtor that will send to the parent constructor a unique name for your property source (Yes, it MUST be unique).
- init(session) : You will be given a Notes Session, opened as the server, allowing you to load your properties from whatever source you want (notes database probably)
- getProperty(name) : You will have to return a value for the property, or null if you don't know about it.

## Inject properties from the first document of a view 

I have also implemented a "BaseParamViewPropertySource" object that you can extend instead of the "BaseNotesPropertySource". 
Such objects will allow you to access properties that correspond to fields present in the first document of a given view. 
And because the document will be accessed using a session opened as the server, you will be able to protect it with a reader field.

Note that such Property Sources DO NOT allow you to inject property values at creation time with @Value annotation. This is because the values themselves will be extracted only when we know about the current database accessed. 
If you want to acces such properties, you will have to @Autowired the standard Spring "Environment" object, and use its "getProperty" method. See sample app for example.

	@Autowired
	private Environment env

	...
	
	public void yourMethod() {
		String value = env.getProperty("my.property");
	}
	
To implement such property sources, you will have to implement :

- An empty constructor. Same as with BaseNotesPropertySource objects.
- getViewName() : This method must return the name of the view in which we will search for the document that contains the parameters.
- checkDb(Database) : This method will have to return "true" if the given database (opened with the server rights) is allowed by your servlet (don't forget that your servlet will be made available on ALL databases). 
  Here, you can check for a given template name, or any other criteria you want (but template name is a good candidate).
- getPrefix() is a non mandatory method. It's return will be added before the field names.

Don't forget that your properties will NOT be available at your beans construction time ! You will have to use the Spring Environment object to find the values.

## Inject properties from a profile document 

As I hate profile documents (just a personal opinion), you will have to implement your own property source for that. Should'nt be that hard...

## Deploy your plugins to the Domino Server

- Create a "Feature Plug-in" plugin, and add your plugin into.
- Create an "Update Site Plug-in" plugin, and add your feature into.
- Build the update site, and deploy it on your IBM Domino Server using an update site database (as described earlier in this document)
- Or use the "XPages Domino Debug plugin" to deploy your plugin on your local Domino Server in development environment.

Once http is restarted, you can navigate to your controllers.

Again, look at the sample plugin for an example on how to use them.

# Deploy the sample application

The sample app is deployed when the "com.github.lhervier.domino.spring.sample.feature" feature is activated in your Eclipse Update Site database. Or, if you are in a development environnement, when you
deploy the "com.github.lhervier.domino.spring.sample" plugin to your local Domino Server using the XPages Debug plugin.

As a standard Spring application, he sample app needs properties that are normally defined in the "application.properties" file. But of course, with Domino, you don't have such a file.

To define the properties, you can implement your own Spring PropertySource (See this document). Or you can use the NotesIniPropertySource that is installed by default with the main spring plugin.

Long story short : You can simply define your properties in your server's notes.ini file.

The sample app endpoints are accessible using URLs like :

	http://<domino server>/spring-sample/<endpoint>
	
Or, if you want to execute them in the context of a notes database (you will have to authenticate, based on the database ACL)

	http://<domino server>/<any NSF>.nsf/spring-sample/<endpoint>

All endpoints are implemented in the SampleController class file.
	
## The "/hello" endpoint

This endpoint will return you information from the current user, the current database (if you have a NSF in the URL), etc... It will also extract values of properties made available from different Spring PropertySources.

Just call it, and have a look at the JSON result.

### Notes.ini variables

Look at the "directory" property. Its value is what is defined in the DIRECTORY notes.ini variable.

### Static property

This endpoint will extract a property named "spring.sample.static.property" that is defined by the StaticPropertySource object. Look at its implementation, and note that this class 
is used in the plugin.xml to add an extension to the main spring plugin.

### Property from a Notes document (ie configuration document)

The same way, it will also extract a property named "spring.sample.local.ParamField". 
This property value is extracted from the field named "ParamField" stored in the first document of the "Params" view (if such a view with such a document exists in the current context).
Look at the code of the "ParamViewPropertySource" :

- It extends "BaseParamViewPropertySource"
- It implements the getViewName and getPrefix methods
- And it is declared in the plugin.xml file

Note that to access such properties, you will have to use the Spring Environment bean. 
You can NOT use @Value annotations because such annotations are evaluated when spring creates the SampleController bean : When the servlet is started.
At this moment, we don't have any notes context (any current notes database), and we are not able to extract values. But if you use the Spring Environment Bean, you ask
Spring to evaluate the property at runtime, when executing the controller's method. And at this moment, we have a current notes database.

So, to define a value for this property, simply create a Notes database with a view named "Params", and add a document in this view with a field named "ParamField".
Save the doc, access the /hello endpoint, and you will get the value of your field in the JSON response.

## The "/exception" endpoint

This endpoint will raise an exception. It is used to show you how to implement @ControllerAdvice to catch exceptions, and display a custom error page.

Look at the code of the ExceptionController class.

Nothing specific to Domino environnement. This endpoint is just here to show you that @ControllerAdvice, @ErrorHandler, etc... are working as expected.

## The "/message.html" endpoint

This endpoint is here to show you how to use Spring MVC with freemarker templates.

As for "/exception", there is nothing specific to Domino. Note that the Freemarkers templates are defined in the "/templates" folder, and that this folder is 
exported in the "build" part of the plugin.xml/MANIFEST.MF/build.properties eclipse editor.

## The "/redirect" endpoint

This endpoint will redirect the browser (using a 302 http response code) to the "/message.html" endpoint.

Nothing specific to Domino here. Just to show you that it is working.

## The "/add" and "/list" endpoints

Those endpoints will show you how to use Hibernate and JPA with Domino and Spring.

For the sample, we are using a h2 in-memory database. But if you deploy your own JDBC driver, you will be able to access any database.
Also note that the H2 JDBC driver is deployed with the main spring plugin. This is a bad thing, I know. Maybe I will fix this later.

The jpa and hibernate properties must be defined as standard Spring Properties. As explained in introduction of this chapter, the simplest way to define them is to create notes.ini variables :

	# Database Configuration
	db.driver=org.h2.Driver
	db.url=jdbc:h2:mem:datajpa
	db.username=sa
	db.password=EMPTY_STRING

	# Hibernate Configuration
	hibernate.dialect=org.hibernate.dialect.H2Dialect
	hibernate.hbm2ddl.auto=create-drop
	hibernate.ejb.naming_strategy=org.hibernate.cfg.ImprovedNamingStrategy
	hibernate.show_sql=false
	hibernate.format_sql=true

Note the use of the "EMPTY_STRING" keyword. This is because you cannot define a notes.ini variable to an empty value...

Once the notes.ini have been updated, you can use the two endpoints to add a new "ToDo", and retrieving the list of the previously added values.
As the database is in-memory only, restarting the http task will reset the list of values.

## The logging aspect

Note that every access to any of the SampleController methods will be logged at the server console. This is implemented using an Aspect.

Again, nothing specific to Domino here. Just have a look at the LoggingAspect class.

# Implementation difficulties 

- The "com.github.lhervier.domino.spring.external.spring_framework" plugin had to uses "org.eclipse.runtime" as a dependency. Without it, Spring is not able to access ressources in an OSGI environment.
- The OsgiDispatcherServlet overrides the main init method to change the current thread's classloader (and set it back once the init is done). Without it, Spring is not able to load your beans.
- I had to patch the Spring 3.2.18 code so that it won't crash when an IBM buggy ServletContext returns null (instead of an EmptyEnumeration) when calling the getInitParameterNames method.

# TODO

- Check deployment using apache wink which is now natively included in Domino. Maybe we can support a higher servlet version (and so, a higher Spring version)
- Split the main spring plugin into multiple plugins (especially the h2 jdbc driver)

