package at.htlklu.spring.controller;

import at.htlklu.spring.api.LogUtils;
import at.htlklu.spring.model.Absence;
import at.htlklu.spring.model.Address;
import at.htlklu.spring.repository.AbsenceRepository;
import at.htlklu.spring.repository.AddressRepository;
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
@RequestMapping(value ="/mvc/addresses")
//localhost: 8083/mvc/addresses/
public class AddressController
{
	//region Properties
	private static Logger logger = LogManager.getLogger(AddressController.class);

	private static final String CLASS_NAME = "AddressController";
	public static final String FORM_NAME_SINGLE = "AddressSingle";
	public static final String FORM_NAME_LIST = "AddressList";

	@Autowired
	AddressRepository addressRepository;


	//localhost:8083/mvc/addresses/get/1
	 @GetMapping("/get/{addressId}")
	 @ResponseBody
	 public Address get(@PathVariable int addressId)
	 {
		 logger.info(LogUtils.info(CLASS_NAME, "get",String.format("%d",addressId)));
		 Optional<Address> optionalAddress = addressRepository.findById(addressId);
		 Address address = null;

		 if (optionalAddress.isPresent())
		 {
            address = optionalAddress.get();
		 }
		 else
		 {

		 }
		 return address;
	 }

	 //localhost:8083/mvc/addresses/show
	 @GetMapping("/show")
	public ModelAndView show()
	 {
		 logger.info(LogUtils.info(CLASS_NAME,"show"));
		 ModelAndView mv = new ModelAndView(FORM_NAME_LIST);

		 List<Address> addresses = addressRepository.findAll();
		 mv.addObject("addresses",addresses);
		 return mv;
	 }
}
