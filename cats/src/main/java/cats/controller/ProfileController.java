package cats.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.dto.HobbyDto;
import cats.dto.ProfileDto;
import cats.dto.SchoolDto;
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
	@RequestMapping(value = { "/View" }, method = RequestMethod.GET)
	public ModelAndView ProfileView(ModelAndView mav) {

		ProfileDto dto = new ProfileDto();


		mav.addObject("ProfileDto", dto);
		mav.setViewName("ProfileView");
		return mav;
	}

	@RequestMapping(value = { "/Update" }, method = RequestMethod.POST)
	public ModelAndView ProfileUpdate(ModelAndView mav) {

		//趣味一覧を取得
		List<HobbyDto> hobbylist = hobbyService.getAllList();
		//学校一覧を取得
		List<SchoolDto> schoollist = schoolService.getAllList();

		ProfileDto dto = new ProfileDto();

		mav.addObject("ProfileDto", dto);
		mav.setViewName("ProfileUpdate");
		mav.addObject("hobbylist", hobbylist);
		mav.addObject("schoollist", schoollist);


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

	/**退会処理
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Delete" }, method = RequestMethod.DELETE)
	public ModelAndView Profile(ModelAndView mav) {




		mav.setViewName("login");

		return mav;
	}



}
