package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Absence;
import at.htlklu.spring.repository.AbsenceRepository;
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
@RequestMapping(value ="/mvc/absences")
//localhost: 8083/mvc/absences/
public class AbsenceController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(AbsenceController.class);

	private static final String CLASS_NAME = "AbsenceController";
	public static final String FORM_NAME_SINGLE = "AbsenceSingle";
	public static final String FORM_NAME_LIST = "AbsenceList";

	@Autowired
	AbsenceRepository absenceRepository;


	//localhost:8083/mvc/absences/get/1
	 @GetMapping("/get/{absenceId}")
	 @ResponseBody
	 public Absence get(@PathVariable int absenceId)
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "get",String.format("%d",absenceId)));

		 //lade Absence gib Absence zurück
		 Optional<Absence> optionalAbsence = absenceRepository.findById(absenceId);
		 Absence absence = null;

		 if (optionalAbsence.isPresent())
		 {
            absence = optionalAbsence.get();
		 }
		 else
		 {

		 }
		 return absence;
	 }

	 //localhost:8083/mvc/absences/show
	 @GetMapping("/show")
	 public ModelAndView show()
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "show"));
		 ModelAndView mv = new ModelAndView(FORM_NAME_LIST);

		 List<Absence> absences = absenceRepository.findAll();
		 mv.addObject("absences",absences);

		 return mv;
	 }
}
