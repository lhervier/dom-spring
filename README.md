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

# Generate the update site

## Update for IBM Designer installation

IBM have updated the lotus.domino.Session and lotus.domino.Database interfaces in their latest version. But the Notes.jar stored in the "com.ibm.notes.java.api" haven't been upgraded. 
As Eclipse is using this one to compile, it's ok. But when compiling the update site, ant will use the one from jvm/lib/ext. That will lead to compilation errors.
  
Before generating the update site, you must update this plugin :

- Copy the file jvm/lib/ext/Notes.jar
- In the folder "framework\shared\eclipse\plugins\com.ibm.notes.java.api.win32.linux_9.0.1.20131022-0932" and replace the existing one.

I'm using IBM Domino Designer 9.0.1FP8. Maybe the next version will change this behavior...

## Import the projects into IBM Domino Designer

- Clone or download the source code into a local folder.
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

The update site is now ready to be deployed into your IBM Domino Server.

## Deploy on IBM Domino Server (production case)

Create a new database named "SpringUpdateSite.nsf" (you can name it the way you want) on your server, using the "Eclipse Update Site" advanced template.

Open the database with your Notes client, and click on the "Import local update site" button. Go select the "site.xml" file in the "com.github.lhervier.spring.update" project.

On the server, add (or update) the notes.ini variable "OSGI_HTTP_DYNAMIC_BUNDLES". It must point to your "SpringUpdateSite.nsf" database. 
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

## Deploy on IBM Domino Server (development case)

Once the plugins source has been imported in your workspace, you can use the "IBM Domino Debug Plugin" to make a local Domino Server use them. 
Please note that you will have to have a LOCAL Domino Server that runs on the same host as your Designer.
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

# Create a application using Spring :

Once the plugins sources are imported in your Domino Designer, and the update site is deployed into your IBM Domino Server, you can create your own plugins, and start using Spring. 

## Create a new plugin, and a new servlet

You will have to create a new OSGI Plug-in. The "com.github.lhervier.spring.sample" plugin is a good starting point, but you can create your own easily. Simply add a dependency on the "com.github.lhervier.spring" plugin.

Then, you will have to create a new servlet class that extends the "com.github.lhervier.spring.servlet.OsgiDispatchServlet" provided by the main plugin.
For obscure class loading reasons, this main servlet have to be declared into YOUR plugin, even if it's implementation is empty.

Deploy your servlet to Domino : 

- In your plugin.xml, add an extension to "org.eclipse.equinox.http.registry.servlets" :
- Alias : Root of your servlet. It is "/spring-sample" in the sample app.
- Class : Name of the Servlet class you just created.
- Add two initialization parameters (right click on the servlet entry) : 
	- contextClass : MUST BE set to "org.springframework.web.context.support.AnnotationConfigWebApplicationContext". Xml based configuration is NOT supported.
	- contextLocation : Full name of your config class (use spaces or comma to add multiple).

For more details on developping with servlets, see this web site : https://www.openntf.org/main.nsf/project.xsp?r=project/Servlet%20Sample).

## Code "the Spring way"

Then, create your Spring config classes (beware. You must be coherent with what you declared in your plugin.xml file) using the @Configuration annotation, add you Spring beans using the @Bean annotation and your Spring controllers with @Controller.
You can @Autowire beans to private properties as usual and map requests using @RequestMapping. See sample plugin for example.

I wrote a set of beans to access the notes context. You can @Autowired them as usual :

- UserSession : A lotus.domino.Session object that is opened using the current user credentials.
- UserDatabase : A lotus.domino.Database object that points to the current database. Beware that if you are accessing the servlet outside of a database context, this object will NOT be null. But the "isAvailable()" method will return false.
- ServerSession: Same thing as UserSession, but session is opened as the server.
- ServerDatabase: Same as UserDatabase, but current database is opened as the server.

## Inject properties from notes.ini

I also added a Spring property source that allows you to inject values from notes.ini variables. Simply use the @Value annotation :

	@Value("${directory}")
	private String directory

## Inject your own properties
	
You can also inject your own PropertySource using the "com.github.lhervier.domino.spring.propertysources" extension point. 

Using this extension point, you can inject objects that extends the "BaseNotesPropertySource" object. You will have to implement the following methods :

- an empty construtor that will send to the parent constructor a unique name for your property source (Yes, it MUST be unique).
- init(session) : You will be given a Notes Session, opened as the server, allowing you to load your properties from whatever source you want (notes database probably)
- getProperty(name) : You will have to return a value for the property, or null if you don't know about it.

## Inject properties from the first document a view 

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

Don't forget that your properties will NOT be available at your bean construction time ! You will have to use the Spring Environment object to find the values.

## Inject properties from a profile document 

As I hate profile documents (just a personnal opinion), you will have to implement your own property source for that.

## Deploy your plugins to the Domino Server

- Create a "Feature Plug-in" plugin, and add your plugin into.
- Create an "Update Site Plug-in" plugin, and add your feature into.
- Build the update site, and deploy it on your IBM Domino Server using an update site database (as described earlier in this document)
- Or use the "XPages Domino Debug plugin" to deploy your plugin on your local Domino Server in development environment.

Once http is restarted, you can navigate to your controllers.

Again, look at the sample plugin for an example on how to use them.

# Implementation difficulties 

- The "com.github.lhervier.domino.spring.external.spring_framework" plugin had to uses "org.eclipse.runtime" as a dependency. Without it, Spring is not able to access ressources in an OSGI environment.
- The OsgiDispatcherServlet overrides the main init method to change the current thread's classloader (and set it back once the init is done). Without it, Spring is not able to load your beans.
- I had to patch the Spring 3.2.18 code so that it won't crash when an IBM buggy ServletContext returns null (instead of an EmptyEnumeration) when calling the getInitParameterNames method.

# TODO

Check deployment using apache wink which is now natively included in Domino. Maybe we can support a higher servlet version (and so, a higher Spring version)
