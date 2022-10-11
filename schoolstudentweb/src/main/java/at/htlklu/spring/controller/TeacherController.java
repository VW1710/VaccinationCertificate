package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Department;
import at.htlklu.spring.model.Teacher;
import at.htlklu.spring.repository.TeacherRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value ="/mvc/teachers")
//localhost: 8083/mvc/teachers/
public class TeacherController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(TeacherController.class);

	private static final String CLASS_NAME = "TeacherController";
	public static final String FORM_NAME_SINGLE = "DepartmentSingle";
	public static final String FORM_NAME_LIST = "TeacherList";

	@Autowired
	TeacherRepository teacherRepository;


	//localhost:8083/mvc/teachers/get/1
	 @GetMapping("/get/{teacherId}")
	 @ResponseBody
	 public Teacher get(@PathVariable int teacherId)
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "get",String.format("%d",teacherId)));

		 //lade Teacher gib Teacher zurück
		 Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);
		 Teacher teacher = null;

		 if (optTeacher.isPresent())
		 {
             teacher = optTeacher.get();
		 }
		 else
		 {

		 }
		 return teacher;
	 }

	 //localhost:8083/mvc/teachers/show
	 @GetMapping("/show")
	 public ModelAndView show()
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "show"));
		 ModelAndView mv = new ModelAndView(FORM_NAME_LIST);
		 //lade Liste aller LehrerInnen
		 //Variante 1: Sortierung wird clientseitig durchgeführt
		 List<Teacher> teachers = teacherRepository.findAll().stream()
				                                             .sorted(Teacher.BY_SURNAME)
				                                             .collect(Collectors.toList());

		 //übergebe die Liste der LehrerInnen an die View
		 //"Teachers" muss exakt mit dem Namen der Liste in der View übereinstimmen

		 //Variante 2
		 //List<Teacher> teachers = teacherRepository.findByOrderBySurnameAscFirstnameAsc();

		 mv.addObject("teachers",teachers);
		 return mv;
	 }

	//localhost:8083/mvc/teachers/show/{teacherId}/departments
	@GetMapping("/show/{teacherId}/departments")
	public ModelAndView showDepartments(@PathVariable int teacherId)
	{
		logger.info(LogUtils.info(CLASS_NAME, "showDepartments"));
		ModelAndView mv = new ModelAndView(DepartmentController.FORM_NAME_LIST);

		//Lade alle Abteilungen die zum Lehrer gehören mit oben gegebener Id
		Optional<Teacher> optTeacher = teacherRepository.findById(teacherId);

		if (optTeacher.isPresent())
		{
			Teacher teacher = optTeacher.get();
			System.out.println(teacher);
			mv.addObject("departments", teacher.getDepartments());

		}
		else
		{

		}

		return mv;
	}

}
