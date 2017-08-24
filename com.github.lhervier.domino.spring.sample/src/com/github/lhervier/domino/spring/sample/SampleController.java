package com.github.lhervier.domino.spring.sample;

import javax.servlet.http.HttpServletRequest;

import lotus.domino.Database;
import lotus.domino.NotesException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.lhervier.domino.spring.servlet.NotesContext;

@Controller
public class SampleController {

	/**
	 * Injecting a service
	 */
	@Autowired
	private SampleService service;
	
	/**
	 * NotesContext object gives you access to the local
	 * Domino server informations.
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
	 * The response class
	 */
	public static class SampleResponse {
		private String user;
		private String server;
		private String database;
		private String directory;
		private String uri;
		private String message;
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
	}
	
	/**
	 * Sample rest method
	 * @return the response
	 * @throws NotesException
	 */
	@RequestMapping(value="/hello", method = RequestMethod.GET, produces = "application/json")
    @ResponseBody SampleResponse sayHello() throws NotesException {
		SampleResponse ret = new SampleResponse();
		
		ret.setMessage(this.service.getMessage());
		
		ret.setServer(this.notesContext.getServerSession().getEffectiveUserName());
		ret.setUser(this.notesContext.getUserSession().getEffectiveUserName());
		Database database = this.notesContext.getUserDatabase();
		if( database != null )
			ret.setDatabase(database.getFilePath());
		
		ret.setDirectory(this.directory);
		
		ret.setUri(this.request.getRequestURI());
		
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
}
