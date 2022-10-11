package at.htlklu.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "PATIENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Patient extends RepresentationModel<Patient> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -7790802681322726971L;
	public static final Comparator<Patient> BY_SURNAME = Comparator.comparing(Patient::getSurname);
	public static final Comparator<Patient> BY_FIRSTNAME = Comparator.comparing(Patient::getFirstname);
	public static final Comparator<Patient> BY_SURNAME_FIRSTNAME = BY_SURNAME.thenComparing(BY_FIRSTNAME);

	//endregion


	//region Properties
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PATIENT_ID")
	private Integer patientId;

	@NotBlank
	private String surname;
	@NotBlank
	private String firstname;

	@Column(name = "DATE_OF_BIRTH")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate dateOfBirth;

	@NotBlank
	private String socialSecurityNumber;
	@NotNull
	private Character sex;
	//endregion


	//region Constructors
	public Patient()
	{
//		logger.info(LogUtils.info(className, "Patient()"));
	}


	public Patient(String surname,
                   String firstname,
                   LocalDate dateOfBirth,
				   String socialSecurityNumber,
				   char sex)
	{
		super();
//		logger.info(LogUtils.info(className, "Patient(sfsstep)"));

		this.surname = surname;
		this.firstname = firstname;
		this.dateOfBirth = dateOfBirth;
		this.socialSecurityNumber = socialSecurityNumber;
		this.sex = sex;

	}


	public Patient(String surname,
                   String firstname,
                   Character sex)
	{
		this(surname, firstname, null,null, sex);
//		logger.info(LogUtils.info(className, "Patient(sfss)"));
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
	public Integer getPatientId()
	{
		return patientId;
	}
	public void setPatientId(Integer patientId)
	{
		this.patientId = patientId;
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

	public LocalDate getDateOfBirth()
	{
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth)
	{
		this.dateOfBirth = dateOfBirth;
	}

	public String getSocialSecurityNumber()
	{
		return socialSecurityNumber;
	}
	public void setSocialSecurityNumber(String socialSecurityNumber)
	{
		this.socialSecurityNumber = socialSecurityNumber;
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
		Patient patient1 = this;
		if (patient1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Patient)))
		{
			equal = false;
		}
		else
		{
			Patient patient2 = (Patient)obj;
			equal = patient1.patientId != null && Objects.equals(patient1.patientId, patient2.getPatientId());
		}
		return equal;
	}
	//endregion
	
}
