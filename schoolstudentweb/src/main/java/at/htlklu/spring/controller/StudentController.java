package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Department;
import at.htlklu.spring.model.Student;
import at.htlklu.spring.model.Teacher;
import at.htlklu.spring.repository.StudentRepository;
import at.htlklu.spring.repository.TeacherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value ="/mvc/students")
//localhost: 8083/mvc/students/
public class StudentController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(StudentController.class);

	private static final String CLASS_NAME = "StudentController";
	public static final String FORM_NAME_SINGLE = "StudentSingle";
	public static final String FORM_NAME_LIST = "StudentList";

	@Autowired
	StudentRepository studentRepository;


	//localhost:8083/mvc/students/get/1
	 @GetMapping("/get/{studentId}")
	 @ResponseBody
	 public Student get(@PathVariable int studentId)
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "get",String.format("%d",studentId)));

		 //lade Student gib Student zurück
		 Optional<Student> optionalStudent = studentRepository.findById(studentId);
		 Student student = null;

		 if (optionalStudent.isPresent())
		 {
            student = optionalStudent.get();
		 }
		 else
		 {

		 }
		 return student;
	 }

	 //localhost:8083/mvc/students/show
	 @GetMapping("/show")
	 public ModelAndView show()
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "show"));
		 ModelAndView mv = new ModelAndView(FORM_NAME_LIST);
		 //lade Liste aller SchülerInnen
		 //Variante 1: Sortierung wird clientseitig durchgeführt
		 List<Student> students = studentRepository.findAll().stream()
				                                             .sorted(Student.BY_SURNAME_FIRSTNAME)
				                                             .collect(Collectors.toList());

		 //übergebe die Liste der SchülerInnen an die View
		 //"Students" muss exakt mit dem Namen der Liste in der View übereinstimmen

		 //Variante 2
		 //List<Student> students = studentRepository.findByOrderBySurnameAscFirstnameAsc();

		 mv.addObject("students",students);
		 return mv;
	 }

	/*//localhost:8083/mvc/students/show/{studentId}/absences
	@GetMapping("/show/{studentId}/absences")
	public ModelAndView showAbsences(@PathVariable int studentId)
	{
		logger.info(LogUtils.info(CLASS_NAME, "showAbsences"));
		ModelAndView mv = new ModelAndView(AbsenceController.FORM_NAME_LIST);

		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if (optionalStudent.isPresent())
		{
			Student student= optionalStudent.get();
			System.out.println(student);
			mv.addObject("absences", student.getAbsences());
		}
		else
		{

		}

		return mv;
	}*/


	//localhost:8083/mvc/students/show/{studentId}/studentSubjects
	@GetMapping("/show/{studentId}/studentSubjects")
	public ModelAndView showStudentSubjects(@PathVariable int studentId)
	{
		logger.info(LogUtils.info(CLASS_NAME, "showStudentSubjects"));
		ModelAndView mv = new ModelAndView(StudentSubjectController.FORM_NAME_LIST);

		Optional<Student> optionalStudent = studentRepository.findById(studentId);

		if (optionalStudent.isPresent())
		{
			Student student= optionalStudent.get();
			System.out.println(student);
			mv.addObject("studentSubjects", student.getStudentSubjects());
		}
		else
		{

		}
		return mv;
	}

}
