package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.Subject;
import at.htlklu.spring.repository.AbsenceRepository;
import at.htlklu.spring.repository.SubjectRepository;
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
@RequestMapping(value ="/mvc/subjects")
//localhost: 8083/mvc/subjects/
public class SubjectController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(SubjectController.class);

	private static final String CLASS_NAME = "SubjectController";
	public static final String FORM_NAME_SINGLE = "SubjectSingle";
	public static final String FORM_NAME_LIST = "SubjectList";

	@Autowired
	SubjectRepository subjectRepository;


	//localhost:8083/mvc/subjects/get/1
	 @GetMapping("/get/{subjectId}")
	 @ResponseBody
	 public Subject get(@PathVariable int subjectId)
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "get",String.format("%d",subjectId)));

		 //lade Subject gib Subject zurück
		 Optional<Subject> optionalSubject = subjectRepository.findById(subjectId);
		 Subject subject = null;

		 if (optionalSubject.isPresent())
		 {
            subject = optionalSubject.get();
		 }
		 else
		 {

		 }
		 return subject;
	 }

	 //localhost:8083/mvc/subjects/show
	 @GetMapping("/show")
	 public ModelAndView show()
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "show"));
		 ModelAndView mv = new ModelAndView(FORM_NAME_LIST);


		 //übergebe die Liste der Subjects an die View
		 //"Subjects" muss exakt mit dem Namen der Liste in der View übereinstimmen


		 List<Subject> subjects =subjectRepository.findAll();

		 mv.addObject("subjects", subjects);
		 return mv;
	 }

}
