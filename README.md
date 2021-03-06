**[Table of Contents](http://tableofcontent.eu)**
<!-- Table of contents generated generated by http://tableofcontent.eu -->
- [IBM Domino 9.0.1 Plugins to develop web applications using Spring Framework.](#ibm-domino-901-plugins-to-develop-web-applications-using-spring-framework)
- [Installation on production environments](#installation-on-production-environments)
  - [Download the update site](#download-the-update-site)
  - [Install on Domino Server](#install-on-domino-server)
- [Generating the update site yourself](#generating-the-update-site-yourself)
  - [Using IBM Domino Designer](#using-ibm-domino-designer)
    - [Import the projects into IBM Domino Designer](#import-the-projects-into-ibm-domino-designer)
    - [Compile](#compile)
  - [Using Maven](#using-maven)
    - [Install a JDK](#install-a-jdk)
    - [Install maven](#install-maven)
    - [Install IBM Domino Update Site for Build Management](#install-ibm-domino-update-site-for-build-management)
    - [Compile](#compile)
  - [Using Eclipse JEE (Oxygen)](#using-eclipse-jee-oxygen)
    - [Install the XPages SDK](#install-the-xpages-sdk)
    - [Configure the JVM Runtime, and the target platform](#configure-the-jvm-runtime-and-the-target-platform)
    - [Import the projects](#import-the-projects)
    - [Generate the update site](#generate-the-update-site)
- [Deploy plugins on a local Domino Server for development purpose](#deploy-plugins-on-a-local-domino-server-for-development-purpose)
- [Creating an application using Spring](#creating-an-application-using-spring)
  - [Create a new plugin, and a new servlet](#create-a-new-plugin-and-a-new-servlet)
  - [Code "the Spring way"](#code-the-spring-way)
  - [Logging](#logging)
  - [Inject properties from notes.ini](#inject-properties-from-notesini)
  - [Inject your own properties](#inject-your-own-properties)
  - [Inject properties from the first document of a view](#inject-properties-from-the-first-document-of-a-view)
  - [Inject properties from a profile document](#inject-properties-from-a-profile-document)
  - [Deploy your plugins to the Domino Server](#deploy-your-plugins-to-the-domino-server)
- [How to play with the sample application](#how-to-play-with-the-sample-application)
  - [Settings on Domino Server](#settings-on-domino-server)
  - [Import dom-spring update site into Domino Designer](#import-dom-spring-update-site-into-domino-designer)
  - [Import the sample app code](#import-the-sample-app-code)
  - [Generate the update site](#generate-the-update-site)
  - [Deploy to your Domino Server](#deploy-to-your-domino-server)
  - [Sample application endpoints](#sample-application-endpoints)
    - [The "/hello" endpoint](#the-hello-endpoint)
      - [Notes.ini variables](#notesini-variables)
      - [Static property](#static-property)
      - [Property from a Notes document (ie configuration document)](#property-from-a-notes-document-ie-configuration-document)
    - [The "/exception" endpoint](#the-exception-endpoint)
    - [The "/message.html" endpoint](#the-messagehtml-endpoint)
    - [The "/redirect" endpoint](#the-redirect-endpoint)
    - [The "/add" and "/list" endpoints](#the-add-and-list-endpoints)
    - [The logging aspect](#the-logging-aspect)
- [Implementation difficulties](#implementation-difficulties)
- [TODO](#todo)

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

You can now point you browser to the sample app (if you have not disabled the feature)

	http://<your server>/spring-sample/hello

It should repond with a small JSON message. You will probably be logged in as "Anonymous".

You can also access the sample servlet in the context of a domino database :

	http://<your server>/<database>.nsf/spring-sample/hello

Log into the database, and you should see a message with your name in the JSON.

# Generating the update site yourself

## Using IBM Domino Designer

### Import the projects into IBM Domino Designer

- Clone or download the source code from github into a local folder.
- Open Domino Designer
- Open the "package explorer" view, right click in the blank part, and select Import.
- In the "General" section, select "Existing projets into workspace"
- Click "Browse" and select the "domino-osgi" folder. You can also import the sample if you want.
- Select all projects and click "Import"

The code is now imported in your IBM Domino Designer.

### Compile

Open the file "site.xml" in the "com.github.lhervier.spring.update" project.
Click the "Build All" button.

The result is in the "com.github.lhervier.spring.update" folder. This is a "standard" update site composed of :

- the "site.xml" file
- the "plugins" folder
- and the "features" folder

The update site is now ready to be zipped...

## Using Maven

### Install a JDK

Download it from Oracle official web site, and install it. Choose the same version as your Domino Server :

- Domino 9.0.1 FP8 or more, download the latest 1.8 version.
- Less than this version, download the latest 1.6 version.

### Install maven

Download it from http://maven.apache.org, and unzip it in c:\maven (for example).

Add environment variables :

- Add MAVEN_HOME so it points to C:\maven
- Add JAVA_HOME so it points to the folder where your JDK was installed
- Adapt PATH to add C:\maven\bin

Check that it work by opening a CMD shell, and typing :

	mvn --version

It should answer something like this :

	Apache Maven 3.5.0 (ff8f5e7444045639af65f6095c62210b5713f426; 2017-04-03T21:39:06+02:00)
	Maven home: C:\maven\bin\..
	Java version: 1.8.0_102, vendor: Oracle Corporation
	Java home: C:\Program Files\Java\jdk1.8.0_102\jre
	Default locale: fr_FR, platform encoding: Cp1252
	OS name: "windows 10", version: "10.0", arch: "amd64", family: "windows"

### Install IBM Domino Update Site for Build Management

This is a standard Eclipse update site that contains all the plugins for XPages Core and ExtLib.

Download the zip file :

	https://www.openntf.org/main.nsf/project.xsp?r=project/IBM%20Domino%20Update%20Site%20for%20Build%20Management/summary

In the zip is another zip file named "Updatesite.zip". Unzip it to 

	C:\UpdateSite
	
### Compile

Clone this repository in a local folder, open a CMD shell, and run 

	cd domino-osgi
	mvn install -Dnotes-platform=file:///C:/UpdateSite

The update site will be made available in the file :

	/domino-osgi/com.github.lhervier.domino.spring.update/target/com.github.lhervier.domino.spring.update-<version>.zip

## Using Eclipse JEE (Oxygen)
	
### Install the XPages SDK

Download the zip file from :

	https://www.openntf.org/main.nsf/project.xsp?r=project/XPages%20SDK%20for%20Eclipse%20RCP

Unzip the file to the folder of your choice. Then, in Eclipse, go to the menu "Help / Install new software".

In the dialog box, click the "add" button in from of the "Work with" combo :

- Name = XPages sdk
- Click the "Folder" button, and go find the folder named "org.openntf.xsp.sdk.updatesite" in the extracted zip file.

Select all features, accept licences, "Install anyway" when prompted to, and restart Eclipse.

### Configure the JVM Runtime, and the target platform

Open Eclipse preferences, and go to the section "XPages SDK" :

- Enable using IBM DOMINO on this computer
- Enter the path to the notes.ini, the installation folder of your Domino Server, and the path to your data folder.
- Click "Automatically create JRE for domino"
- And "Apply".

This will create a new JRE Runtime, and a new Target platform. Select them :

- Go to the section "Java / Installed JREs", and select the newly created JRE (XPages Domino JRE)
- Go to the section "Plugins Development / Target platform", and select the newly created target platform (Domino Target).

### Import the projects 

In the package explorer, right click :

- Select "Import", then choose "General / Existing project into workspace". 
- Go search for the folder "domino-osgi". You can also import the sample if you want.
- And import the projects.

The code should compile fine.

### Generate the update site

Open the site.xml file present in the project "com.github.lhervier.domino.update", and click the "Build All" button.

This will build the update site

# Deploy plugins on a local Domino Server for development purpose

Once the plugins source has been imported in your workspace (be it Domino Designer, or Eclipse), you can use the "IBM Domino Debug Plugin" to make a local Domino Server use them. 
Please note that you will have to have a LOCAL Domino Server that runs on the same file system as your Designer.

Download the "IBM Domino Debug plugin" file from 

	https://www.openntf.org/main.nsf/project.xsp?r=project/IBM%20Lotus%20Domino%20Debug%20Plugin

It is a zip file. Unzip it anywhere, and :
- In Eclipse, go to the menu "Help / Install New Software", and click the "Add button" :
	- Name = IBM Domino debug Plugin
	- Click the "Archive" button, and go find the zip file named "com.ibm.domino.osgi.debug.site.zip" present in the main zip file.
	- Click next, and install all the available plugins. Accept licence, and "Install anyway".
	- Restart Eclipse.
- In Domino Designer, 
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
- Eclipse Only : In the plugin list, UNCHECK the "target platform" plugins section.
- And in the "Workspace" section, select all the imported plugins.
- Uncheck "Validate bundles prior to launching"
- Click "Debug"
- You will have to enter the installation path of your local Domino Server
- Once done, you will have to restart the http task (using "restart task http")

As for production environment, you can check that the server has loaded your plugins by sending the following console command :

	tell http osgi ss spring

If you update the source code of the project, and want to check the result, simply restart the http task.

# Creating an application using Spring

Once the plugins sources are imported in your Eclipse or Domino Designer, you can create your own plugins, and start using Spring. 

## Create a new plugin, and a new servlet

You will have to create a new OSGI Plug-in. The "com.github.lhervier.spring.sample" plugin is a good starting point, but you can create your own easily. 
Simply add a dependency on the "com.github.lhervier.spring" plugin.

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

## Logging

By default, Spring uses Apache commons-logging, and so does the osgi layer of the http task. To implement a logger, use something like 

	public class MyService {
		private static final Log LOG = LogFactory.getLogger(MyService.class);
		...
	}

So this question is not related to this project, but to the development of any OSGi plugin on Domino. By default, only messages higher than "ERROR" will be logged to the Domino console.
To change this, edit the file

	${DOMINO_DATA}/domino/workspace/.config/rcpinstall.properties

For example, if your classes are in the package named "com.acme.myapp", just add the line 

	com.acme.myapp.level=FINEST
	
See https://stackoverflow.com/questions/22709056/osgi-bundle-logging-on-domino-server for more details.
	
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

# How to play with the sample application

## Settings on Domino Server

As a standard Spring application, the sample app needs properties that are normally defined in the "application.properties" file. 
But of course, with Domino, you don't have such a file.

To define the properties, you can implement your own Spring PropertySource (See this document). Or you can use the NotesIniPropertySource that is installed by default 
with the main spring plugin.

Long story short : You can simply define your properties in your server's notes.ini file.

The awaited properties are :

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

## Import dom-spring update site into Domino Designer

If you haven't imported the dom-spring source (contained in the "domino-osgi" folder), you must import the update site into Domino Designer :

- Go to File/Preferences menu, in the "Domino Designer" section, and check "Enable Eclipse plug-in install"
- Now, go to File/Application/Install menu.
- Select "Search for new features"
- Add a "Zip/jar location" and go find the zip that correspond to the dom-spring update site (the same file you use to install on your server).
- Click Finish, and accept licences.
- Designer will ask to restart.

## Import the sample app code

Import the projects stored in the "sample" folder.

## Generate the update site

Open the site.xml file, and click "build All".

## Deploy to your Domino Server

Deploy the generated update site using an Eclipse Update Site Database, or use the Domino Debug Plugin to plug you Domino Server to your Designer workspace.

## Sample application endpoints

The sample app endpoints are accessible using URLs like :

	http://<domino server>/spring-sample/<endpoint>
	
Or, if you want to execute them in the context of a notes database (you will have to authenticate, based on the database ACL)

	http://<domino server>/<any NSF>.nsf/spring-sample/<endpoint>

All endpoints are implemented in the SampleController class file.
	
### The "/hello" endpoint

This endpoint will return you information from the current user, the current database (if you have a NSF in the URL), etc... It will also extract values of properties made available from different Spring PropertySources.

Just call it, and have a look at the JSON result.

#### Notes.ini variables

Look at the "directory" property. Its value is what is defined in the DIRECTORY notes.ini variable.

#### Static property

This endpoint will extract a property named "spring.sample.static.property" that is defined by the StaticPropertySource object. Look at its implementation, and note that this class 
is used in the plugin.xml to add an extension to the main spring plugin.

#### Property from a Notes document (ie configuration document)

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

### The "/exception" endpoint

This endpoint will raise an exception. It is used to show you how to implement @ControllerAdvice to catch exceptions, and display a custom error page.

Look at the code of the ExceptionController class.

Nothing specific to Domino environnement. This endpoint is just here to show you that @ControllerAdvice, @ErrorHandler, etc... are working as expected.

### The "/message.html" endpoint

This endpoint is here to show you how to use Spring MVC with freemarker templates.

As for "/exception", there is nothing specific to Domino. Note that the Freemarkers templates are defined in the "/templates" folder, and that this folder is 
exported in the "build" part of the plugin.xml/MANIFEST.MF/build.properties eclipse editor.

### The "/redirect" endpoint

This endpoint will redirect the browser (using a 302 http response code) to the "/message.html" endpoint.

Nothing specific to Domino here. Just to show you that it is working.

### The "/add" and "/list" endpoints

Those endpoints will show you how to use Hibernate and JPA with Domino and Spring.

For the sample, we are using a h2 in-memory database. But if you deploy your own JDBC driver, you will be able to access any database.
Also note that the H2 JDBC driver is deployed with the main spring plugin. This is a bad thing, I know. Maybe I will fix this later.

The jpa and hibernate properties must be defined as standard Spring Properties. As explained in introduction of this chapter, the simplest way to define them is to create notes.ini variables :

Once the notes.ini have been updated, you can use the two endpoints to add a new "ToDo", and retrieving the list of the previously added values.
As the database is in-memory only, restarting the http task will reset the list of values.

### The logging aspect

Note that every access to any of the SampleController methods will be logged at the server console. This is implemented using an Aspect.

Again, nothing specific to Domino here. Just have a look at the LoggingAspect class.

# Implementation difficulties 

- The "com.github.lhervier.domino.spring.external.spring_framework" plugin had to uses "org.eclipse.runtime" as a dependency. Without it, Spring is not able to access ressources in an OSGI environment.
- The OsgiDispatcherServlet overrides the main init method to change the current thread's classloader (and set it back once the init is done). Without it, Spring is not able to load your beans.
- I had to patch the Spring 3.2.18 code so that it won't crash when an IBM buggy ServletContext returns null (instead of an EmptyEnumeration) when calling the getInitParameterNames method.

# TODO

- Check deployment using apache wink which is now natively included in Domino. Maybe we can support a higher servlet version (and so, a higher Spring version)
- Split the main spring plugin into multiple plugins (especially the h2 jdbc driver)

