package cats.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = { "/Message" })
public class MessageController {

	@Autowired
	HttpSession session;

	@RequestMapping(value = { "/View" }, method = RequestMethod.GET)
	public ModelAndView MessageView(@RequestParam Integer talkId,@RequestParam String receiveName, ModelAndView mav) {


		mav.addObject("receiveName",receiveName);
		mav.setViewName("Message");

		return mav;

	}

}
