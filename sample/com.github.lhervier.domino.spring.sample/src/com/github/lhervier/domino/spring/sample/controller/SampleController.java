package com.github.lhervier.domino.spring.sample.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lotus.domino.Database;
import lotus.domino.NotesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.lhervier.domino.spring.sample.entity.ToDo;
import com.github.lhervier.domino.spring.sample.repository.ToDoRepository;
import com.github.lhervier.domino.spring.sample.service.SampleService;
import com.github.lhervier.domino.spring.servlet.NotesContext;

@Controller
public class SampleController {

	/**
	 * Injecting a service
	 */
	@Autowired
	private SampleService service;
	
	/**
	 * The NotesContext
	 */
	@Autowired
	private NotesContext notesContext;
	
	/**
	 * You can inject variables directly from the notes.ini
	 */
	@Value("${directory}")
	private String directory;
	
	/**
	 * And you can inject the normal HttpServlet objects
	 */
	@Autowired
	private HttpServletRequest request;
	
	/**
	 * The Todo repository
	 */
	@Autowired
	private ToDoRepository todoRepo;
	
	/**
	 * The spring environment
	 */
	@Autowired
	private Environment env;
	
	/**
	 * Sample test property injected from the StaticPropertySource
	 */
	@Value("${spring.sample.static.property}")
	private String staticProperty;
	
	/**
	 * The response class
	 */
	public static class SampleResponse {
		private String user;
		private String server;
		private String database;
		private String directory;
		private String uri;
		private String message;
		private String staticEnvValue;
		private String paramViewEnvValue;
		private List<String> roles;
		public String getUser() { return user; }
		public void setUser(String s) { this.user = s; }
		public String getServer() { return server; }
		public void setServer(String server) { this.server = server; }
		public String getDatabase() { return database; }
		public void setDatabase(String database) { this.database = database; }
		public String getDirectory() { return directory; }
		public void setDirectory(String directory) { this.directory = directory; }
		public String getUri() { return uri; }
		public void setUri(String uri) { this.uri = uri; }
		public String getMessage() { return message; }
		public void setMessage(String message) { this.message = message; }
		public String getStaticEnvValue() {return staticEnvValue;}
		public void setStaticEnvValue(String envValue) {this.staticEnvValue = envValue;}
		public String getParamViewEnvValue() {return paramViewEnvValue;}
		public void setParamViewEnvValue(String paramViewEnvValue) {this.paramViewEnvValue = paramViewEnvValue;}
		public List<String> getRoles() {return roles;}
		public void setRoles(List<String> roles) {this.roles = roles;}
	}
	
	/**
	 * Sample rest method
	 * @return the response
	 * @throws NotesException
	 */
	@RequestMapping(value="/hello", method = RequestMethod.GET, produces = "application/json")
    public @ResponseBody SampleResponse sayHello() throws NotesException {
		SampleResponse ret = new SampleResponse();
		
		// Calling an external service
		ret.setMessage(this.service.getMessage());
		
		// Extracting information from the current notes context
		ret.setServer(this.notesContext.getServerSession().getEffectiveUserName());
		ret.setUser(this.notesContext.getUserSession().getEffectiveUserName());
		if( this.notesContext.getUserDatabase() != null )
			ret.setDatabase(this.notesContext.getUserDatabase().getFilePath());
		ret.setRoles(this.notesContext.getUserRoles());
		
		// Accessing current request properties
		ret.setUri(this.request.getRequestURI());
		
		// Accessing notes.ini variables
		ret.setDirectory(this.directory);
		
		// Accessing a property injected by a custom PropertySource
		ret.setStaticEnvValue(this.staticProperty);
		
		// Accessing a property that correspon to a field in a configuration document
		ret.setParamViewEnvValue(this.env.getProperty("spring.sample.local.ParamField"));
		
		return ret;
	}
	
	/**
	 * Method that throws an error
	 * @return nothing...
	 * @throws NotesException
	 */
	@RequestMapping(value = "/exception", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody SampleResponse throwException() throws NotesException {
		Database db = this.notesContext.getUserSession().getDatabase(null, "names.nsf");
		db.getDocumentByUNID("DOESNOTEXISTS");
		return new SampleResponse();
	}
	
	/**
	 * Method that returns HTML
	 */
	@RequestMapping(value = "/message.html")
	public ModelAndView message() {
		Map<String, Object> model = new HashMap<String, Object>();
		model.put("message", "Hello World !!");
		return new ModelAndView("message", model);
	}
	
	/**
	 * Redirect controller
	 */
	@RequestMapping(value = "/redirect")
	public ModelAndView redirect() {
		return new ModelAndView("redirect:message.html");
	}
	
	/**
	 * Add a todo
	 */
	@RequestMapping(value = "/add")
	@ResponseBody Long addTodo() {
		ToDo todo = new ToDo();
		todo.setDescription("description");
		todo.setTitle("title : " + System.currentTimeMillis());
		todo = this.todoRepo.save(todo);
		return todo.getId();
	}
	
	/**
	 * List todos
	 */
	@RequestMapping(value = "/list")
	@ResponseBody List<ToDo> listTodos() {
		return this.todoRepo.findAll();
	}
}
