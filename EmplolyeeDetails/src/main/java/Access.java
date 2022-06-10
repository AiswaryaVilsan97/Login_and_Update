import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;



import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession; 
@WebServlet("/Access")
public class Access extends HttpServlet{
	private static final long serialVersionUID = 1L;
	
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException,ServletException {
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		
		String empl_name=req.getParameter("username");
		String role= req.getParameter("password");
		String empl_id= req.getParameter("empl_id");
		String place= req.getParameter("place");
		String phone_number = req.getParameter("phone_number");
		
		PrintWriter out= res.getWriter();
		Employee emp= new Employee();
		Connection c=null;
	    PreparedStatement ps=null;
	    Statement st=null;
	    ResultSet r=null;
		
		 try {
			 if(emp.validate(empl_name, role, phone_number, place)) {
				Class.forName("com.mysql.jdbc.Driver");
			 c=DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "Current-Root-Password");
			 
			  HttpSession s=req.getSession();  
		        s.setAttribute("username",empl_name);
		        
			if(role.equals("admin")) {
				System.out.println("CONNECTION SUCCESSFUL");
				st= c.createStatement();
     			r = st.executeQuery("SELECT * FROM employee.employee");
			 while (r.next()) {
		            JSONArray jsonr= new JSONArray();
			        JSONObject jo= new JSONObject();
			        jo.put("empl_id", r.getLong("empl_id"));
					jo.put("id", r.getLong("id"));
					jo.put("empl_name", r.getString("empl_name"));
					jo.put("phone_number", r.getLong("phone_number"));
					jo.put("place", r.getString("place"));
					jo.put("role", r.getString("role"));
					
			        jsonr.add(jo);
			        out.println(jsonr);
			}
			 RequestDispatcher r1 = (RequestDispatcher) req.getRequestDispatcher("Update.java");
				//r1.include(req, res);
				}
			else {
				 ps= c.prepareStatement("SELECT * FROM employee.employee  where empl_name=?");   
		         ps.setString(1,empl_name);
		         ResultSet rs = ps.executeQuery();
		         while (rs.next()) {
		            JSONArray jsonr= new JSONArray();
			        JSONObject jo= new JSONObject();
			        jo.put("empl_id", rs.getLong("empl_id"));
				    jo.put("id", rs.getLong("id"));
					jo.put("empl_name", rs.getString("empl_name"));
					jo.put("phone_number", rs.getLong("phone_number"));
					jo.put("place", rs.getString("place"));
					jo.put("role", rs.getString("role"));
					
			        jsonr.add(jo);
			        out.println(jsonr);
		         }}}
			    RequestDispatcher r1 = (RequestDispatcher) req.getRequestDispatcher("Update.java");
				//r1.include(req, res);
			 }
		     catch(Exception e) {			
		    	 e.printStackTrace();
		     		}}}
