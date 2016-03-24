package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datatypes.Person;
import searching.SearchParameters;
import searching.SearchType;
import tools.ConnectionParameters;

public class SearchServlet extends HttpServlet {

	// POSTer:
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		try (PrintWriter writer = resp.getWriter()) {  // PrintWrinter auto close
			try {
				SearchType searchType = getSearchType(req);
				String searchCriteria = getSearchCriteria(req);
				List<Person> persons = getPersons(searchCriteria, searchType, req.getSession());  // socket connection here
				printMatchesAsHtml(persons, writer);
			} catch (IOException | ClassNotFoundException | ClassCastException | InvalidFormException e) {  // socket-related exceptions
				e.printStackTrace();
				reportException(e, writer);
			}
		}  // if something is wrong with PrintWriter, I don't want to deal with it, let TomCat catch the exception
	}
	
	// if we use HTTPSession, we have to enable invalidating -- let's do it by GET:
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		try (PrintWriter writer = resp.getWriter()) {
			req.getSession().invalidate();
			writer.print(generateHTML("Session Invalidated", "<h1>Your session has been invalidated.</h1>"));
		}
	}
	
	// regaining data from User:
	private SearchType getSearchType(HttpServletRequest req) throws InvalidFormException {
		switch (req.getParameter("type")) {
		case "mandatory":
			return SearchType.MANDATORY;
		case "optional":
			return SearchType.OPTIONAL;
		}
		throw new InvalidFormException("The form you have filled is invalid.");
	}
	
	private String getSearchCriteria(HttpServletRequest req) {
		return req.getParameter("skills");
	}
	
	// intelligent searcher, asks SocketServer only if necessary:
	private List<Person> getPersons(String searchCriteria, SearchType searchType, HttpSession session) throws IOException, ClassNotFoundException, ClassCastException {
		if (session.getAttribute("searchHistory") == null) {
			session.setAttribute("searchHistory", new HashMap<SearchParameters, List<Person>>());  // TODO not serializable! TomCat gets angry when shutting down.
		}
		Map<SearchParameters, List<Person>> searchHistory = (Map<SearchParameters, List<Person>>)session.getAttribute("searchHistory");
		SearchParameters parameters = new SearchParameters(searchCriteria, searchType);
		if (searchHistory.containsKey(parameters))
			return searchHistory.get(parameters);
		try (SocketClient socketClient = new SocketClient(	getInitParameter("host"),  // socket connection auto close
				ConnectionParameters.getPortNumber())) {
			List<Person> personsReceived = socketClient.getPersons(searchCriteria, searchType);
			searchHistory.put(parameters, personsReceived);
			return personsReceived;
		}
	}
	
	// match-list printer:
	private void printMatchesAsHtml(List<Person> persons, PrintWriter writer) {
		String title = "Match List";
		String body = "<h1>List of Persons Found:</h1>\n";
		if (persons.size() == 0) {
			body += "<p>No persons found.</p>\n";
		}
		else {
			for (Person person: persons) {
				body += "<p>" + person.toHtmlString() + "</p>\n";
			}
		}
		body += "<p><a href=\"index.html\">back to main page</a></p>\n";
		writer.print(generateHTML(title, body));
		writer.flush();
	}
	
	// error-reporter for socket-related exceptions:
	private void reportException(Exception e, PrintWriter writer) {
		writer.print(generateHTML("IOException Occurred",
				"<h1>An Exception has Occurred.</h1>\n"
				+ "<p>Type: " + e.getClass().getName() + "</p>\n"
				+ "<p>Message: " + e.getMessage() + "</p>\n"
				+ "<p><a href=\"index.html\">back to main page</a></p>\n"));
	}
	
	// assistant function for html formula:
	private String generateHTML(String title, String body) {
		return "<html>\n"
				+ "<head>\n"
				+ "<title>" + title + "</title>\n"
				+ "</head>\n"
				+ "<body>\n"
				+ body
				+ "</body>\n"
				+ "</html>\n";
	}
	
}
