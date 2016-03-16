package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.UnknownHostException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SearchServlet extends HttpServlet {

	// POSTer:
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text.html");
		try (	PrintWriter writer = resp.getWriter();
				SocketClient socketClient = new SocketClient("localhost", 9999)) {
			// TODO here to process request, invoke socketClient.getPersons and compose response
		} catch (IOException e) {
			// TODO handle IOException 
		}
	}
	
}
