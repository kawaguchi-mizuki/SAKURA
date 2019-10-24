package cats.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
@Controller

@RequestMapping("/auth")
public class AuthenticationCodeController {
	@RequestMapping(value = "send",method=RequestMethod.GET)
	public ModelAndView send(
			ModelAndView mav,
			HttpServletRequest request,
    		HttpServletResponse response
			) {
		mav.setViewName("send");
		
				return mav;
	}
}

