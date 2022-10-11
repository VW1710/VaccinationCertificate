package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.StudentSubject;
import at.htlklu.spring.repository.AbsenceRepository;
import at.htlklu.spring.repository.StudentSubjectRepository;
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

@Controller
@RequestMapping(value ="/mvc/studentSubjects")
//localhost: 8083/mvc/studentSubjects/
public class StudentSubjectController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(StudentSubjectController.class);

	private static final String CLASS_NAME = "StudentSubjectController";
	public static final String FORM_NAME_SINGLE = "StudentSubjectSingle";
	public static final String FORM_NAME_LIST = "StudentSubjectList";

	@Autowired
	StudentSubjectRepository studentSubjectRepository;


	//localhost:8083/mvc/studentSubjects/get/1
	 @GetMapping("/get/{studentSubjectId}")
	 @ResponseBody
	 public StudentSubject get(@PathVariable int studentSubjectId)
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "get",String.format("%d",studentSubjectId)));

		 //lade studentSubjects gib studentSubjects zurück
		 Optional<StudentSubject> optionalStudentSubject = studentSubjectRepository.findById(studentSubjectId);
		 StudentSubject studentSubject = null;

		 if (optionalStudentSubject.isPresent())
		 {
			 studentSubject= optionalStudentSubject.get();
		 }
		 else
		 {

		 }
		 return studentSubject;
	 }

	 //localhost:8083/mvc/studentSubjects/show
	 @GetMapping("/show")
	 public ModelAndView show()
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "show"));
		 ModelAndView mv = new ModelAndView(FORM_NAME_LIST);


		 //übergebe die Liste der studentSubjects an die View
		 //"studentSubjects" muss exakt mit dem Namen der Liste in der View übereinstimmen


		 List<StudentSubject> studentSubjects = studentSubjectRepository.findAll();

		 mv.addObject("studentSubjects",studentSubjects);
		 return mv;
	 }

}
