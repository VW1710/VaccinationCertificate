package at.htlklu.spring.model;

import java.io.Serializable;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.RepresentationModel;

@Entity																
@Table(name = "STUDENT")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@NamedEntityGraph(name = "student.studentSubjects.subject",
		          attributeNodes = {@NamedAttributeNode(value = "schoolClass", subgraph="g_schoolClass"),
						  			@NamedAttributeNode(value = "studentSubjects", subgraph="g_subject")
				  				   },
        		  subgraphs = {@NamedSubgraph(name = "g_subject",
                        					  attributeNodes = {@NamedAttributeNode(value = "subject")}
				  							 ),
							   @NamedSubgraph(name = "g_schoolClass",
                        					  attributeNodes = {@NamedAttributeNode(value = "teacher")}
				  							 )
							  }
				 )
public class Student extends RepresentationModel<Student> implements Serializable
{
	//region static Properties
	private static final long serialVersionUID = -6574326723164905323L;

	private static final Comparator<Student> BY_SURNAME = Comparator.comparing(Student::getSurname);
	private static final Comparator<Student> BY_FIRSTNAME = Comparator.comparing(Student::getFirstname);
	public static final Comparator<Student> BY_SURNAME_FIRSTNAME = BY_SURNAME.thenComparing(BY_FIRSTNAME);
	//endregion


	//region Properties
	@Id																
	@GeneratedValue(strategy = GenerationType.IDENTITY)				
	@Column(name = "STUDENT_ID")		
	private Integer studentId;

	@NotBlank
	private String surname;
	@NotBlank
	private String firstname;
	@NotNull
	private Character sex;
	@Column(name = "BIRTHDATE")
	@DateTimeFormat(pattern = "dd.MM.yyyy")
	private LocalDate birthDate;
	private Integer height;
	private Double weight;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "SCHOOLCLASS_ID")
	private SchoolClass schoolClass;

	@JsonIgnore
	@OneToMany(mappedBy = "student",
			   cascade = CascadeType.MERGE,
			   orphanRemoval = true,
			   fetch = FetchType.LAZY)
	private Set<StudentSubject> studentSubjects = new HashSet<StudentSubject>();

	@JsonIgnore
	@OneToMany(mappedBy = "student", 								
			   cascade = CascadeType.MERGE, 	
			   orphanRemoval = true, 
			   fetch = FetchType.LAZY)
	private Set<Address> addresses = new HashSet<Address>();

	@JsonIgnore
	@OneToMany(mappedBy = "student",
			   cascade = CascadeType.MERGE,
			   orphanRemoval = true,
			   fetch = FetchType.LAZY)
	private Set<Absence> absences = new HashSet<Absence>();
	//endregion


	//region Constructors
	public Student()
	{
	}	
		
	public Student(SchoolClass schoolClass,
				   String surname, 
				   String firstname, 
				   char sex, 
				   LocalDate birthDate, 
				   Integer height, 
				   Double weight)
	{
		super();
		this.surname = surname;
		this.firstname = firstname;
		this.sex = sex;
		this.birthDate = birthDate;
		this.height = height;
		this.weight = weight;
		
		this.schoolClass = schoolClass;					// unidirektionale Verkn??pfung
		this.schoolClass.getStudents().add(this);		// unidirektionale Verkn??pfung 	-> 	bidirektionale Verkn??pfung
	}
	//endregion


	//region Methods
	@JsonGetter("toString")
	@Override
	public String toString()
	{
		return String.format("%1$s %2$s", this.surname, this.firstname);
	}

	public boolean isSmall()
	{
		return this.height != null && this.height < 150;
	}

	public boolean isMale()
	{
		return this.sex == 'f';
	}

	public boolean isFemale()
	{
		return this.sex == 'm';
	}

	// count, sum, average (min, max)
	public int countGrades()
	{
		return this.studentSubjects.size();
	}

	public boolean gradesAvailable()
	{
		return this.countGrades() > 0;
	}

	@JsonGetter("countGM")
	public long countGradesMain()
	{
		return this.studentSubjects.stream()
				.filter(studentSubject -> studentSubject.getSubject().getMain())
				.count();
	}

	// 1 -> Anzahl der 1er, 2 -> Anzahl der 2er, etc.
	public long countGradesA(int grade)
	{
		return this.studentSubjects.stream()
				.filter(studentSubject -> studentSubject.getGrade()== grade)
				.count();
	}

	public double avgGrades()
	{
		//Achtung: nicht bei allen Sch??lerInnen sind Noten eingetragen worden (z.B. 4AHBG, 4BHBG), deswegen orElse notwendig!!
		return this.studentSubjects.stream()
				.mapToDouble(studentSubjects-> studentSubjects.getGrade())
				.average()
				.orElse(-1);

	}

	public long countGrades(int grade)
	{
		return this.getStudentSubjects().stream()
				.filter(ss-> ss.getGrade()== grade)
				.count();
	}

	public long countGrades1()
	{
		return this.countGrades(1);
	}

	public long countGrades5()
	{
		return this.countGrades(5);
	}

	@JsonGetter("bmi")
	public double getBMI()
	{
		double result =0;
		if(this.weight!=0 && this.weight>0 && this.height!=0 && this.height>0)
		{
			double l = (double) this.height/100;
			result = this.weight/Math.pow(l,2);
		}
		return result;
	}

	public int sumAbsencesPeriod()
	{
		return this.absences.stream()
				.mapToInt(absence -> absence.getPeriod())
				.sum();
	}

	//sumAbsencesReason("Krankheit")
	public long sumAbsencesReason(String reason)
	{
		return this.absences.stream()
				.filter(absence -> absence.getReason().equals(reason))
				.mapToInt(absences-> absences.getPeriod())
				.sum();
	}

	public int countAbsencesPeriod()
	{
		return this.getAbsences().size();
	}

	public double avgAbsencesPeriod()
	{
		return this.getAbsences().stream()
				.mapToInt(absences-> absences.getPeriod())
				.average()
				.orElse(-1);
	}

	public int minAbsencesPeriod()
	{
		return this.getAbsences().stream()
				.mapToInt(absences-> absences.getPeriod())
				.min()
				.orElse(-1);
	}

	public int maxAbsencesPeriod()
	{
		return this.getAbsences().stream()
				.mapToInt(absences-> absences.getPeriod())
				.max()
				.orElse(-1);
	}

	public long isSpecialInt()
	{
		return this.getStudentSubjects().stream()
				.filter(ss-> ss.getSubject().getMain())
				.filter(ss-> ss.getGrade() ==1 || ss.getGrade()==2 || ss.getGrade() ==3)
				.count();
	}

	public boolean isSpecial()
	{
		if(this.isSpecialInt()==1 || isSpecialInt()==2 || isSpecialInt()==3)
		{
			System.out.println(true);
		}
		else
		{
			System.out.println(false);
		}
		return isSpecial();
	}

	public int getGermanGrade()
	{
		return this.getStudentSubjects().stream()
				.filter(a -> a.getSubject().getSubjectId() == 1)
				.mapToInt(studentSubjects-> studentSubjects.getGrade())
				.max().orElse(-1);
	}

	public int getEnglishGrade()
	{
		return this.getStudentSubjects().stream()
				.filter(a -> a.getSubject().getSubjectId() == 2)
				.mapToInt(studentSubjects-> studentSubjects.getGrade())
				.max().orElse(-1);
	}

	public int getMathsGrade()
	{
		return this.getStudentSubjects().stream()
				.filter(a -> a.getSubject().getSubjectId() == 3)
				.mapToInt(studentSubjects-> studentSubjects.getGrade())
				.max().orElse(-1);
	}



	//region Getter and Setter
	public Integer getStudentId()
	{
		return studentId;
	}
	public void setStudentId(Integer studentId)
	{
		this.studentId = studentId;
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

	public Character getSex()
	{
		return sex;
	}
	public void setSex(Character sex)
	{
		this.sex = sex;
	}

	public LocalDate getBirthDate()
	{
		return birthDate;
	}
	public void setBirthDate(LocalDate birthDate)
	{
		this.birthDate = birthDate;
	}

	public Integer getHeight()
	{
		return height;
	}
	public void setHeight(Integer height)
	{
		this.height = height;
	}

	public Double getWeight()
	{
		return weight;
	}
	public void setWeight(Double weight)
	{
		this.weight = weight;
	}


	public SchoolClass getSchoolClass()
	{
		return schoolClass;
	}	
	public void setSchoolClass(SchoolClass schoolClass)
	{
		this.schoolClass = schoolClass;
	}

	public Set<StudentSubject> getStudentSubjects()
	{
		return studentSubjects;
	}
	public void setStudentSubjects(Set<StudentSubject> studentSubjects)
	{
		this.studentSubjects = studentSubjects;
	}
	
	public Set<Address> getAddresses()
	{
		return addresses;
	}
	public void setAddresses(Set<Address> addresses)
	{
		this.addresses = addresses;
	}

	public Set<Absence> getAbsences()
	{
		return absences;
	}
	public void setAbsences(Set<Absence> absences)
	{
		this.absences = absences;
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
		Student student1 = this;
		if (student1 == obj)
		{
			equal = true;
		}
		else if ((obj == null) || (!(obj instanceof Student)))
		{
			equal = false;
		}
		else
		{
			Student student2 = (Student)obj;
			equal = student1.studentId != null && Objects.equals(student1.studentId, student2.getStudentId());
		}
		return equal;
	}
	//endregion
}
