package cats.controller;


import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.service.HomeService;

@RestController
@RequestMapping(value = { "/Request" })
public class RequestController {

	@Autowired
	HttpSession session;

	@Autowired
	HomeService homeService;

	/**
	 * @param mav
	 * @return
	 */

	@RequestMapping(value = {"/Approval"}, method = RequestMethod.GET)
	public ModelAndView RequestApproval(ModelAndView mav)throws Exception{

		mav.setViewName("Login");

		return mav;
	}

	@RequestMapping(value = {"/Delete"}, method = RequestMethod.GET)
	public ModelAndView RequestDelete(ModelAndView mav)throws Exception{

		mav.setViewName("Login");

		return mav;
	}

}
