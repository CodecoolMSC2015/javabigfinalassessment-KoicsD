package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import datatypes.Person;
import searchparams.SearchParameters;
import searchparams.SearchType;
import tools.ToHtmlConverter;

public class SearchServlet extends HttpServlet {
	
	// POSTer:
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		try (PrintWriter writer = resp.getWriter()) {  // PrintWrinter auto close
			try {
				ensureInitParams();
				ensureFormContainsFields(req);
				SearchType searchType = getSearchType(req);
				Set<String> searchCriteria = getSearchCriteria(req);
				List<Person> persons = getPersons(searchCriteria, searchType, req.getSession());  // socket connection here
				printMatchesAsHtml(persons, searchCriteria, writer);
			} catch (IOException | ClassNotFoundException | ClassCastException |  // socket-related exceptions
					NumberFormatException | InvalidFormException | InvalidConfigurationException e) {  // web-content related exceptions 
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
			writer.print(generateHTML("Session Invalidated",
			"<h1>Your session has been successfully invalidated.</h1>\n" +
			"<p>This page is an indicator of a service-function which is intended to invalidate HttpSession.</p>"));
		}
	}
	
	// making sure initial parameters are defined correctly in Web-XML:
	private void ensureInitParams() throws InvalidConfigurationException {
		Enumeration<String> parameterNames = getInitParameterNames();
		if(!parameterNames.hasMoreElements() || !parameterNames.nextElement().equals("storeServerHost"))
			throw new InvalidConfigurationException("Web content descriptor must define two initial parameters for servlet.SearchServlet, named 'storeServerHost' and 'storeServerPort', respectively");
		if(!parameterNames.hasMoreElements() || !parameterNames.nextElement().equals("storeServerPort"))
			throw new InvalidConfigurationException("Web content descriptor must define two initial parameters for servlet.SearchServlet, named 'storeServerHost' and 'storeServerPort', respectively");
		if(parameterNames.hasMoreElements())
			throw new InvalidConfigurationException("Web content descriptor must define two initial parameters for servlet.SearchServlet, named 'storeServerHost' and 'storeServerPort', respectively");
	}
	
	// making sure form contains the necessary fields:
	private void ensureFormContainsFields(HttpServletRequest req) throws InvalidFormException {
		Map<String, String[]> parameters = req.getParameterMap();
		if (!parameters.containsKey("searchType") || !parameters.containsKey("searchCriteria"))
			throw new InvalidFormException("The form filled by User does not contain both fields 'searchType' and 'searchCriteria'");
	}
	
	// regaining data from User:
	private SearchType getSearchType(HttpServletRequest req) throws InvalidFormException {
		switch (req.getParameter("searchType")) {
		case "mandatory":
			return SearchType.MANDATORY;
		case "optional":
			return SearchType.OPTIONAL;
		default:
			throw new InvalidFormException("User has not specifyed any valid searchTypes in the form");
		}
	}
	
	private Set<String> getSearchCriteria(HttpServletRequest req) {
		String[] criteriaArray = req.getParameter("searchCriteria").split("\n");
		HashSet<String> criteriaSet = new HashSet<String>();
		for (int i = 0; i < criteriaArray.length; ++i) {
			criteriaSet.add(criteriaArray[i].trim());
		}
		return criteriaSet;
	}
	
	// intelligent searcher, asks SocketServer only if necessary:
	private List<Person> getPersons(Set<String> searchCriteria, SearchType searchType, HttpSession session) throws NumberFormatException, IOException, ClassNotFoundException, ClassCastException {
		if (session.getAttribute("searchHistory") == null) {
			session.setAttribute("searchHistory", new HashMap<SearchParameters, List<Person>>());
		}
		Map<SearchParameters, List<Person>> searchHistory = (Map<SearchParameters, List<Person>>)session.getAttribute("searchHistory");
		SearchParameters searchParameters = new SearchParameters(searchCriteria, searchType);
		if (searchHistory.containsKey(searchParameters))
			return searchHistory.get(searchParameters);
		try (SocketClient socketClient = new SocketClient(	getInitParameter("storeServerHost"),  // socket connection auto close
				Integer.valueOf(getInitParameter("storeServerPort")))) {
			List<Person> personsReceived = socketClient.getPersons(searchParameters);
			searchHistory.put(searchParameters, personsReceived);
			return personsReceived;
		}
	}
	
	// match-list printer:
	private void printMatchesAsHtml(List<Person> persons, Set<String> searchCriteria, PrintWriter writer) {
		String title = "Match List";
		String body = "<h1>List of Persons Found:</h1>\n";
		body += "<p>Number of Persons found: " + persons.size() + "</p>\n";
		body += "<table border=\"1\">\n";
		body += "<theader>" + ToHtmlConverter.getPersonTableHeader() + "</theader>\n";
		body += "<tbody>\n";
		for (Person person: persons)
			body += ToHtmlConverter.convertToTableRecords(person, searchCriteria) + "\n";
		body += "</tbody>\n";
		body += "</table>\n";
		if (persons.size() == 0)
			body += "<h2>No persons found.</h2>\n";
		body += "<p><a href=\"index.html\">back to main page</a></p>\n";
		writer.print(generateHTML(title, body));
		writer.flush();
	}
	
	// error-reporter for socket-related exceptions:
	private void reportException(Exception e, PrintWriter writer) {
		writer.print(generateHTML("IOException Occurred",
				"<h1>An Exception has Occurred.</h1>\n"
				+ "<p>Type: " + e.getClass().getName() + "</p>\n"
				+ (e.getMessage() != null ? ("<p>Message: " + e.getMessage() + "</p>\n") : "")
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
