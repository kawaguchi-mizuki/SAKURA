package cats.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class TwoConfServlet {
	@RequestMapping(value= {"/TwoConf"},method=RequestMethod.GET)
	public ModelAndView TwoConf( ModelAndView mav) {

		mav.setViewName("UserEntry");

		return mav;
	}

}
