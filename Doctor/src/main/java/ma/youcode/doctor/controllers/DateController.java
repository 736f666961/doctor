package ma.youcode.doctor.controllers;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ma.youcode.doctor.config.HybernateConfig;
import ma.youcode.doctor.entity.Date;

/**
 * Servlet implementation class DateController
 */
@WebServlet("/date")
public class DateController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HybernateConfig hybernateConfig = null;
	private SessionFactory factory = null;
	private Session session = null;
	private HttpSession httpSession = null;
	private Date dateObj = null;
	
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DateController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String date = request.getParameter("date").toString();
		String time = request.getParameter("time").toString();
		
		PrintWriter out = response.getWriter();
		
//		out.println(date);
//		out.println(time);
//		
		// Instantiate HybernateConfig
		hybernateConfig = new HybernateConfig();
		
		// Get session factory
		factory = hybernateConfig.getDateSessionFactory();
		
		// Create session
		session = factory.getCurrentSession();
		
		// Create http session
		httpSession =  request.getSession();
		
		try {
			// Begin transaction
			session.beginTransaction();
			
			// Create Date Object
			dateObj = new Date(date, time, false);
			out.println(dateObj.test());
			
			int isAdded = (int) session.save(dateObj);
			
			if (isAdded > 0) {
				
				session.getTransaction().commit();
				
				httpSession.setAttribute("isAdded", true);
				
				// redirect 
				response.sendRedirect("/Doctor/admin.jsp");
				
			}else {
				
				httpSession.setAttribute("isAdded", false);
				
				// redirect 
				response.sendRedirect("/Doctor/admin.jsp");
			}

			
			
		} catch(Exception e) {
			out.println(e.getMessage());
			factory.close();
		}
		
		
	}

}