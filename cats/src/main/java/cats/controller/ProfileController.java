package cats.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.beans.StudentBeans;
import cats.dto.HobbyDto;
import cats.dto.LoginInfoDto;
import cats.dto.ProfileDto;
import cats.dto.SchoolDto;
import cats.service.HobbyService;
import cats.service.ProfileService;
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
	ProfileService profileService;

	@Autowired
	HttpSession session;


	/**マイページ表示
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/View" }, method = RequestMethod.GET)
	public ModelAndView ProfileView(ModelAndView mav) {

		//ログイン情報を取得する
		LoginInfoDto loginInfo = new LoginInfoDto();

		loginInfo.setStudentId(1701129);

		ProfileDto dto = profileService.getDisplayBoard(loginInfo);


		mav.addObject("ProfileDto", dto);
		mav.setViewName("ProfileView");
		return mav;
	}

	/**マイページ修正画面表示
	 * @param studentbeans
	 * @param password
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Update" }, method = RequestMethod.POST)
	public ModelAndView ProfileUpdate(@Valid StudentBeans studentbeans,@RequestParam("password")String password,ModelAndView mav) {

		//趣味一覧を取得
		List<HobbyDto> hobbylist = hobbyService.getAllList();
		//学校一覧を取得
		List<SchoolDto> schoollist = schoolService.getAllList();

		ProfileDto dto = new ProfileDto();

		mav.addObject("ProfileDto", dto);
		mav.addObject("studentbeans",studentbeans);
		mav.addObject("password",password);
		mav.setViewName("ProfileUpdate");
		mav.addObject("hobbylist", hobbylist);
		mav.addObject("schoollist", schoollist);


		return mav;
	}

	/**マイページ更新処理
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Correction" }, method = RequestMethod.GET)
	public ModelAndView ProfileCorrection(ModelAndView mav) {





		return mav;
	}

	/**退会処理
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Delete" }, method = RequestMethod.GET)
	public ModelAndView Profile(ModelAndView mav) {

		LoginInfoDto loginInfo = new LoginInfoDto();
		loginInfo.setStudentId(1701129);

		profileService.ProfileDelete(loginInfo.getStudentId());

		session.invalidate();

		mav.setViewName("login");

		return mav;
	}



}
