package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Department;
import at.htlklu.spring.model.Teacher;
import at.htlklu.spring.repository.DepartmentRepository;
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
@RequestMapping(value ="/mvc/departments")
//localhost: 8083/mvc/departments/
public class DepartmentController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(DepartmentController.class);

	private static final String CLASS_NAME = "DepartmentController";
	public static final String FORM_NAME_SINGLE = "DepartmentSingle";
	public static final String FORM_NAME_LIST = "DepartmentList";

	@Autowired
	DepartmentRepository departmentRepository;

	//localhost:8083/mvc/departments/show
	@GetMapping("/show")
	public ModelAndView show()
	{
		logger.info(LogUtils.info(CLASS_NAME, "show"));
		ModelAndView mv = new ModelAndView(FORM_NAME_LIST);

		//List<Department> departments = departmentRepository.findAll().stream()
		                                                  // .sorted(Department.BY_NAME)
		                                                  // .collect(Collectors.toList());

		List<Department> departments = departmentRepository.findByOrderByName();

		mv.addObject("departments", departments);
		return mv;

	}

	//localhost:8083/mvc/departments/show/{departmentId}/schoolClasses
	@GetMapping("/show/{departmentId}/schoolClasses")
	public ModelAndView showSchoolClasses(@PathVariable int departmentId)
	{
		logger.info(LogUtils.info(CLASS_NAME,"showSchoolClasses"));
		ModelAndView mv = new ModelAndView(SchoolClassController.FORM_NAME_LIST);

		Optional<Department> optionalDepartment = departmentRepository.findById(departmentId);

		if(optionalDepartment.isPresent())
		{
			Department department = optionalDepartment.get();
			System.out.println(department);
			mv.addObject("schoolClasses",department.getSchoolClasses());
		}
		else
		{

		}
		return mv;
	}

}