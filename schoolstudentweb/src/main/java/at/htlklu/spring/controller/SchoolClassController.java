package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.*;
import at.htlklu.spring.repository.DepartmentRepository;
import at.htlklu.spring.repository.SchoolClassRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping(value ="/mvc/schoolClasses")

//localhost: 8083/mvc/schoolClasses/
public class SchoolClassController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(SchoolClassController.class);

	private static final String CLASS_NAME = "SchoolClassController";
	public static final String FORM_NAME_SINGLE = "SchoolClassSingle";
	public static final String FORM_NAME_LIST = "SchoolClassList";

	@Autowired
	SchoolClassRepository schoolClassRepository;

	//localhost:8083/mvc/schoolClasses/show
	@GetMapping("/show")
	public ModelAndView show()
	{
		logger.info(LogUtils.info(CLASS_NAME, "show"));
		ModelAndView mv = new ModelAndView(FORM_NAME_LIST);

		//Lade Liste aller Schulklassen
		//List<SchoolClass> schoolClasses = schoolClassRepository.findAll().stream()
				                                              // .sorted(SchoolClass.BY_LEVEL)
						                                     //  .collect(Collectors.toList());

		List<SchoolClass> schoolClasses = schoolClassRepository.findByOrderByLevelAscNameAscDepartmentAsc();

		mv.addObject("schoolClasses",schoolClasses);
		return mv;
	}

	//localhost:8083/mvc/schoolClasses/show/{schoolClassId}/students
	@GetMapping("/show/{schoolClassId}/students")
	public ModelAndView showStudents(@PathVariable int schoolClassId)
	{
		logger.info(LogUtils.info(CLASS_NAME, "showStudents"));
		ModelAndView mv = new ModelAndView(StudentController.FORM_NAME_LIST);

		//Lade alle Abteilungen die zum Lehrer gehören mit oben gegebener Id
		Optional<SchoolClass> optionalSchoolClass = schoolClassRepository.findById(schoolClassId);

		if (optionalSchoolClass.isPresent())
		{
			SchoolClass schoolClass = optionalSchoolClass.get();
			System.out.println(schoolClass);
			mv.addObject("students", schoolClass.getStudents());

		}
		else
		{

		}

		return mv;
	}
}