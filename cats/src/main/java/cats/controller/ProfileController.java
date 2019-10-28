package cats.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.dto.ProfileDto;
import cats.service.HobbyService;
import cats.service.SchoolService;
import cats.service.StudentService;

@RestController
@RequestMapping(value = { "/Profile" })
public class ProfileController {
	@Autowired
	HobbyService hobbyService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	StudentService studentService;

	@Autowired
	HttpSession session;


	/**プロフィール更新画面表示
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/UpdateStart" }, method = RequestMethod.GET)
	public ModelAndView ProfileUpdate(ModelAndView mav) {

		ProfileDto dto = new ProfileDto();


		mav.addObject("ProfileDto", dto);
		mav.setViewName("ProfileUpdate");
		return mav;
	}

	/**プロフィール閲覧画面表示
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Reading" }, method = RequestMethod.GET)
	public ModelAndView ProfileReading(ModelAndView mav) {



		return mav;
	}

	@RequestMapping(value = { "/Delete" }, method = RequestMethod.GET)
	public ModelAndView Profile(ModelAndView mav) {

		return mav;
	}



}
