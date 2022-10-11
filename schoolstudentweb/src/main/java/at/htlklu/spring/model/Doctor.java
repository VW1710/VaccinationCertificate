package at.htlklu.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Comparator;
import java.util.Objects;

@Entity
@Table(name = "DOCTOR")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Doctor extends RepresentationModel<Doctor> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -7790802681322726971L;
	public static final Comparator<Doctor> BY_SURNAME = Comparator.comparing(Doctor::getSurname);
	public static final Comparator<Doctor> BY_FIRSTNAME = Comparator.comparing(Doctor::getFirstname);
	public static final Comparator<Doctor> BY_SURNAME_FIRSTNAME = BY_SURNAME.thenComparing(BY_FIRSTNAME);

	//endregion


	//region Properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DOCTOR_ID")
	private Integer doctorId;

	@NotBlank
	private String surname;
	@NotBlank
	private String firstname;

	@NotNull
	private String title;
	private Character sex;
	//endregion


	//region Constructors
	public Doctor()
	{
//		logger.info(LogUtils.info(className, "Doctor()"));
	}


	public Doctor(String surname,
                  String firstname,
                  String title,
                  char sex)
	{
		super();
//		logger.info(LogUtils.info(className, "Doctor(sfsstep)"));

		this.surname = surname;
		this.firstname = firstname;
		this.title = title;
		this.sex = sex;

	}


	public Doctor(String surname,
                  String firstname,
                  Character sex)
	{
		this(surname, firstname, null, sex);
//		logger.info(LogUtils.info(className, "Doctor(sfss)"));
	}
	//endregion


	//region Methods
//	@JsonGetter("toString")
	@Override
	public String toString()
	{
		return String.format("%s %s", this.surname, this.firstname);
	}



	//region Getter and Setter
	public Integer getDoctorId()
	{
		return doctorId;
	}
	public void setDoctorId(Integer doctorId)
	{
		this.doctorId= doctorId;
	}

	public String getSurname()
	{
		return surname;
	}
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public String getFirstname()
	{
		return firstname;
	}
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getTitle()
	{
		return title;
	}
	public void setTitle(String title)
	{
		this.title = title;
	}

	public Character getSex()
	{
		return sex;
	}
	public void setSex(Character sex)
	{
		this.sex = sex;
	}


	//endregion


	//region HashCode and Equals
	@Override
	public int hashCode() {
	    return getClass().hashCode();
	}
	
	@Override 
	public boolean equals(Object obj)
	{
		boolean equal;
		Doctor doctor1 = this;
		if (doctor1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Doctor)))
		{
			equal = false;
		}
		else
		{
			Doctor doctor2 = (Doctor)obj;
			equal = doctor1.doctorId != null && Objects.equals(doctor1.doctorId, doctor2.getDoctorId());
		}
		return equal;
	}
	//endregion
	
}
