* IBM Domino 9.0.1 Plugins to develop web applications using Spring Framework.

Domino only supports servlet 2.4 which needs Spring 3.2. So, we're stuck to this version...
And because of a buggy IBM code, I had to patch the spring-web module. You can find the (trivial) Spring 3.2.18 patch on my repositories.
The patched jar (along with all the other needed jars) are provided in one of the plugins.

* How to use

For the moment there is no update site. I will post one as soon as the code will stabilize.

Once the plugins are installed on your IBM Domino Designer and IBM Domino Server, you can create your own plugin, and declare a simple servlet 
(see this web site : https://www.openntf.org/main.nsf/project.xsp?r=project/Servlet%20Sample).

Your new servlet have to :

- Extend the OsgiDispatchServlet provided by the main plugin
- Declare two initialization parameters (for Spring) : 
	- contextClass : MUST BE org.springframework.web.context.support.AnnotationConfigWebApplicationContext. Xml based configuration is NOT supported.
	- contextLocation : Full name of your config class (use spaces or comma to add multiple).

See the sample app for more information. 

A NotesContext bean can be @AutoWired, and will give you access to the Notes Session, the current Notes Database, etc...

* Difficulties 

- The Spring plugin uses org.eclipse.runtime as a dependency. Without it, Spring is not able to access ressources in an OSGI environment.
- The base servlet overrides the init method to change the current thread classloader (and set it back once the init is done). Without it, Spring is not able to load your beans.
- I had to patch the Spring 3.2.18 code so that it won't crash when an IBM buggy ServletContext returns null (instead of an EmptyCollection) for the getInitParameterNames method.
