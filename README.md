# IBM Domino 9.0.1 Plugins to develop web applications using Spring Framework.

Domino only supports servlet 2.4. The latest Spring version that support that servlet level is 3.2. So, we're stuck to this version...
And because of a buggy IBM code, I had to patch the spring-web module. You can find the (trivial) Spring 3.2.18 patch on my repositories.
The patched jar (along with all the other jars needed by spring-webmvc) are provided in the "com.github.lhervier.domino.spring.external.spring_framework" plugin.
Sorry, I haven't modularized the jars each in his plugin...

# How to use

I will post a ready to use update site when the code will be stabilized. For now,it's just an experimentation project.

## Import the projects into IBM Domino Designer

- Open the package explorer, right click in the blank, and select Import.
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

## Install on IBM Domino Server

Create a new database names "SpringUpdateSite.nsf" at the root of your server, using the "Eclipse Update Site" advanced template.

Open the database, and click on the "Import local update site" button, and go select the "site.xml" file in the "com.github.lhervier.spring.update" project.

Add (or update) the notes.ini variable "OSGI_HTTP_DYNAMIC_BUNDLES". It must point to your "SpringUpdateSite.nsf" database. Use a comma to separate multiple values if you already have
another entry (for the extlib for example).

Restart the http taks : "restart task http" at the console.

Check that plugins are deployed : "tell http osgi ss spring". You should see :

	> tell http osgi ss spring
	[010C:0002-1DC8] 07/07/2017 20:01:20   Framework is launched.
	[010C:0002-1DC8] 07/07/2017 20:01:20   id       State       Bundle
	[010C:0002-1DC8] 07/07/2017 20:01:20   92       RESOLVED    com.github.lhervier.domino.spring.external.spring_framework_3.2.18
	[010C:0002-1DC8] 07/07/2017 20:01:20   93       ACTIVE      com.github.lhervier.domino.spring.sample_1.0.0.qualifier
	[010C:0002-1DC8] 07/07/2017 20:01:20   94       ACTIVE      com.github.lhervier.domino.spring_1.0.0.qualifier

You can now point you browser to 

	http://<your server>/spring-sample/hello

It should repond with a small JSON message. You will probably be logged in as "Anonymous".

	http://<your server>/<a database>.nsf/spring-sample/hello

Log into the database, and you should see a message with your name in the JSON.

Once the plugins are imported into your IBM Domino Designer and installed into your IBM Domino Server, you can create your own plugins, and start using Spring. 

# Create a application using Spring :

The "com.github.lhervier.spring.sample" plugin is a good starting point. You can create your own easily. Simply add a dependency on the "com.github.lhervier.spring" plugin.

Then, create your Spring config classes using the @Configuration annotation, add you Spring beans using the @Bean annotation and your Spring controllers with @Controller
You can @Autowire your beans to private properties as usual and map requests using @RequestMapping. See sample plugin for example.

Then, you will have to create a new servlet class that extends the "com.github.lhervier.spring.servlet.OsgiDispatchServlet" provided by the main plugin.
For class loading reasons, this call must be you main servlet, even if it's empty.

Deploy your servlet to Domino : For more details, see this web site : https://www.openntf.org/main.nsf/project.xsp?r=project/Servlet%20Sample).

Add an extension to "org.eclipse.equinox.http.registry.servlets" :

- Alias : Root of your servlet. It is "/spring-sample" in the sample app.
- Class : Name of the Servlet class you just created.
- Add two initialization parameters (right click on the servlet entry) : 
	- contextClass : MUST BE set to "org.springframework.web.context.support.AnnotationConfigWebApplicationContext". Xml based configuration is NOT supported.
	- contextLocation : Full name of your config class (use spaces or comma to add multiple).

Create a "Feature" and an "Update Site" project, compile, and deploy on your IBM Domino Server, or use the "XPages Domino Debug plugin" to make your server
aware of your new plugin. Once http is restarted, you can navigate to your controllers.
	
Note that a "com.github.lhervier.spring.servlet.NotesContext" bean can be @AutoWired, and will give you access to the Notes Session, the current Notes Database, etc...
Again, look at the sample plugin.

# Implementation difficulties 

- The "com.github.lhervier.domino.spring.external.spring_framework" plugin had to uses "org.eclipse.runtime" as a dependency. Without it, Spring is not able to access ressources in an OSGI environment.
- The OsgiDispatcherServlet overrides the main init method to change the current thread's classloader (and set it back once the init is done). Without it, Spring is not able to load your beans.
- I had to patch the Spring 3.2.18 code so that it won't crash when an IBM buggy ServletContext returns null (instead of an EmptyEnumeration) when calling the getInitParameterNames method.

# TODO

Check deployment using apache wink which is now natively included in Domino. Maybe we can support a higher servlet version (and so, a higher Spring version)