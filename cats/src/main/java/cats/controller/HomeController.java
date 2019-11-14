package cats.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = { "/Home" })
public class HomeController {

	@Autowired
	HttpSession session;

	/**ホーム画面表示
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Read" }, method = RequestMethod.GET)
	public ModelAndView HomeRead(ModelAndView mav) {





		mav.setViewName("Home");

		return mav;
	}

}
