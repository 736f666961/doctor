package ma.youcode.doctor.dao.impl;

import java.util.List;

import javax.persistence.Query;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import ma.youcode.doctor.config.HybernateConfig;
import ma.youcode.doctor.dao.AppointmentDao;

public class AppointmentDaoImpl implements AppointmentDao {
	private List<Object[]> appointments;
	private HybernateConfig hybernateConfig;
	private SessionFactory factory ;
	private Session session;

	@Override
	public List<Object[]> getAllAppointments() {
		// Instantiate HybernateConfig
		hybernateConfig = new HybernateConfig();
		
		// Create Session factory
		factory = hybernateConfig.getAppointmentSessionFactory();
		
		// Create session
		session = factory.getCurrentSession();
		
		try {
			// Start transaction
			session.beginTransaction();
		
			// Query comment and get comments
			Query hql = session.createNativeQuery("SELECT first_name, last_name, email, pets_number, pet_type, appointment_date, appointment_time FROM users u, appointments a WHERE a.user_id = u.id");
			appointments = hql.getResultList();
			
			// commit transaction
			session.getTransaction().commit();
			
			
		} finally {
			// close factory
			factory.close();
		}
		
		return appointments;
	}
	
}
