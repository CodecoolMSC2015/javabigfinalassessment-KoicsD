package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import connection.ConnectionParameters;
import datatypes.Person;
import searching.SearchType;

public class SearchServlet extends HttpServlet {

	// POSTer:
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setCharacterEncoding("UTF-8");
		resp.setContentType("text/html");
		try (PrintWriter writer = resp.getWriter()) {  // PrintWrinter auto close
			try (SocketClient socketClient = new SocketClient(	getInitParameter("host"),  // socket connection
																ConnectionParameters.getPortNumber())) {
				SearchType searchType = getSearchType(req);
				String searchCriteria = getSearchCriteria(req);
				Set<Person> persons = socketClient.getPersons(searchCriteria, searchType);
				listMatches(writer);
			} catch (IOException e) {  // socket connection
				reportException(e, writer);
			}
		}  // if something is wrong with PrintWriter, I don't want to deal with it, let TomCat catch the exception
	}
	
	private SearchType getSearchType(HttpServletRequest req) {
		SearchType searchType = SearchType.MANDATORY; // a default value is necessary for compilation
		switch (req.getParameter("type")) {
		case "mandatory":
			searchType = SearchType.MANDATORY;
			break;
		case "optional":
			searchType = SearchType.OPTIONAL;
			break;
		}
		return searchType;
	}
	
	private String getSearchCriteria(HttpServletRequest req) {
		return req.getParameter("skills");
	}
	
	private void listMatches(PrintWriter writer) {
		// TODO here to list data to browser
	}
	
	private void reportException(IOException e, PrintWriter writer) {
		writer.println(generateHTML("IOException Occurred",
				"<h1>An IOException has Occurred.</h1>"
				+ "<p>Type: " + e.getClass().getName() + "</p>"
				+ "<p>Message: " + e.getMessage() + "</p>"));
	}
	
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
