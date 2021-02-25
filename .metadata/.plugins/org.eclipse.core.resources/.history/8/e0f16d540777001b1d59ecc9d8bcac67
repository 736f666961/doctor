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
import ma.youcode.doctor.entity.User;
import ma.youcode.doctor.entity.Comment;

/**
 * Servlet implementation class Comment
 */
@WebServlet("/comment")
public class CommentController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private HybernateConfig hybernateConfig = null;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CommentController() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // redirect to home page
        response.sendRedirect("/Doctor/");
	}



	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Instantiate HybernateConfig
		hybernateConfig = new HybernateConfig();
		
		// Get session factory
		SessionFactory factory = hybernateConfig.getCommentSessionFactory();
		
		// Create session
		Session session = factory.getCurrentSession();
		
		// Create http session
		HttpSession httpSession =  request.getSession();
		
		try {
			
			// Get data from user
			String commentContent = request.getParameter("commentContent");
			
			// Begin transaction
			session.beginTransaction();
			
			// Get user
			User user = (User) httpSession.getAttribute("user");
			
			// Create Comment Object
			Comment comment = new Comment(user.getID(), commentContent);
			
			
			// Check that user is logged in
			if (user != null) {
				// Save user object to database
				int isCommented = (int) session.save(comment);
				
		        // When data saved to database Redirect anyway
				if (isCommented > 0) {
					// Commit transaction
					session.getTransaction().commit();
					
					// send comment to jsp
					httpSession.setAttribute("comment", comment);
					
					httpSession.removeAttribute("cantComment");
					
			        // redirect to home page
			        response.sendRedirect("/Doctor/");
			        
				} 
			}else {
				// Create http session
				httpSession.setAttribute("cantComment", true);
				
		        // redirect to home page
		        response.sendRedirect("/Doctor/");
			}
			
		} catch (Exception e) {
			// Create http session
			httpSession.setAttribute("cantComment", true);

	        // redirect to home page
	        response.sendRedirect("/Doctor/");
			
		} finally {
			factory.close();
		}
	}

}
