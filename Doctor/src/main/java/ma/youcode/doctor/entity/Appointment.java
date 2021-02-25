package ma.youcode.doctor.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="appointments")
public class Appointment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id")
	private int ID;
	
	@Column(name="pets_number")
	private int petsNumber;
	
	@Column(name="pet_type")
	private String petType;
	
	@Column(name="user_id")
	private int userID;
	
	@Column(name="appointment_date")
	private String appointmentDate;
	
	@Column(name="appointment_time")
	private String appointmentTime;
	
	
	// Default Constructor
	public Appointment() {
		super();
	}


	public Appointment(int petsNumber, String petType, int userID, String appointmentDate, String appointmentTime) {
		super();
		this.petsNumber = petsNumber;
		this.petType = petType;
		this.userID = userID;
		this.appointmentDate = appointmentDate;
		this.appointmentTime = appointmentTime;
	}


	public int getID() {
		return ID;
	}


	public int getPetsNumber() {
		return petsNumber;
	}


	public String getPetType() {
		return petType;
	}


	public int getUserID() {
		return userID;
	}


	public String getAppointmentDate() {
		return appointmentDate;
	}


	public String getAppointmentTime() {
		return appointmentTime;
	}
	
	
}
