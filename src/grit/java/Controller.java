package grit.java;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConnection.connect();
		//DBConnection.searchDB(request.getParameter("search"));
		
		//response.setCharacterEncoding("text/html");
		PrintWriter writer = response.getWriter();
		
		writer.print("<html><style>div:nth-child(odd) {background-color:lightgray;}</style><body><h1>Dumble's Decision</h1>");
		writer.print(DBConnection.searchDB(request.getParameter("search")));
		writer.print("</body></html>");
	}
}
