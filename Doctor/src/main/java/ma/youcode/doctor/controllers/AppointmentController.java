package ma.youcode.doctor.controllers;

import java.io.IOException;

import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ma.youcode.doctor.config.HybernateConfig;
import ma.youcode.doctor.entity.Appointment;
import ma.youcode.doctor.entity.User;

/**
 * Servlet implementation class Appointment
 */
@WebServlet("/appointment")
public class AppointmentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HybernateConfig hybernateConfig = null;
	private Appointment appointment = null;
	private SessionFactory factory = null;
	private Session session = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppointmentController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // redirect to home page
        response.sendRedirect("/Doctor/");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Get data from user
		String petsNumber = request.getParameter("petsNumber");
		String petType = request.getParameter("petType");
		String dateAppointment = request.getParameter("dateAppointment");
		String timeAppointment = request.getParameter("timeAppointment");
//		int dateID =  request.getParameter("dateID") != null ? Integer.parseInt(request.getParameter("dateID")) : 0;
		
		String[] dateSplit = timeAppointment.split(";");
	
		int dateID = Integer.parseInt(dateSplit[1]);
		String tApointment = dateSplit[0];
		
		// Instantiate HybernateConfig
		hybernateConfig = new HybernateConfig();
		
		// Get session factory
		factory = hybernateConfig.getAppointmentSessionFactory();
		
		// Create session
		session = factory.getCurrentSession();
		
		// Create http session
		HttpSession httpSession =  request.getSession();
		
		try {
			// Begin transaction
			session.beginTransaction();
			
			// Get user
			User user = (User) httpSession.getAttribute("user");
			
			if (user != null && petsNumber != null && petType != null  && dateAppointment != null && timeAppointment != null && dateID != 0 ) {
				// Create Appointment Object
				appointment = new Appointment(Integer.parseInt(petsNumber), petType, user.getID(), dateAppointment, tApointment);	
				
				// Save user object to database
				int isBooked = (int) session.save(appointment);
				
				// check if data saved to database
				if (isBooked > 0) {				
					// send comment to jsp
					httpSession.setAttribute("isBooked", true);
					
					// commit transaction
					session.getTransaction().commit();
					
					// Update date 
					this.updateDateAsTaken(dateID);
					
			        // redirect to home page
			        response.sendRedirect("/Doctor/");
				}
			}else {
				// send comment to jsp
				httpSession.setAttribute("isBooked", false);
				
		        // redirect to home page
		        response.sendRedirect("/Doctor/");
			}

			
		} finally {
			factory.close();
		}
	}
	
	// Update date as taken 
	private int updateDateAsTaken(int id) {
		// Instantiate HybernateConfig
		hybernateConfig = new HybernateConfig();
		
		// Get session factory
		factory = hybernateConfig.getDateSessionFactory();
		
		// Create session
		session = factory.getCurrentSession();
		
		try {
			// Start transaction
			session.beginTransaction();
			
			// Query users and get user
			Query hql = session.createQuery("UPDATE Date d SET d.isTaken=:isTaken WHERE d.ID=:ID");
			hql.setParameter("isTaken", true);
			hql.setParameter("ID", id);
			int status = hql.executeUpdate();
			
			// Commit transaction
			session.getTransaction().commit();
			
		} finally {
			// Close factory
			factory.close();
		}
		
		// return true
		return 1;
	}
}