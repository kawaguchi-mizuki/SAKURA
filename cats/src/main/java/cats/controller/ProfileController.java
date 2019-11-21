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
import cats.param.SessionConst;
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

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		ProfileDto dto = profileService.getDisplayBoard(loginInfo);



		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
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
	public ModelAndView ProfileUpdate(@Valid StudentBeans studentbeans,@RequestParam("password")String password,@RequestParam("hobbyname") String hobbyName,ModelAndView mav) {

		//趣味一覧を取得
		List<HobbyDto> hobbylist = hobbyService.getAllList();
		//学校一覧を取得
		List<SchoolDto> schoollist = schoolService.getAllList();

		ProfileDto dto = new ProfileDto();

		int hobbyId = hobbyService.getHobbyId(hobbyName);

		studentbeans.setHobbyId(hobbyId);

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
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
	@RequestMapping(value = { "/Correction" }, method = RequestMethod.POST)
	public ModelAndView ProfileCorrection(@Valid StudentBeans studentbeans,@RequestParam("password") String password,
			@RequestParam("r_password") String r_password,ModelAndView mav) {

		ProfileDto dto = new ProfileDto();
		String ErrMsg;


		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);


		//入力値チェック
		ErrMsg = ValidationCheck(password,r_password);

		//エラーメッセージがあればリダイレクト
		if(!(ErrMsg.equals(""))){

			//趣味一覧を取得
			List<HobbyDto> hobbylist = hobbyService.getAllList();
			//学校一覧を取得
			List<SchoolDto> schoollist = schoolService.getAllList();

			//ポイント反映
			int point = loginInfo.getPoint();

			mav.addObject("point",point);
			mav.addObject("hobbylist", hobbylist);
			mav.addObject("schoollist", schoollist);
			mav.setViewName("ProfileUpdate");
			mav.addObject("studentbeans", studentbeans);
			mav.addObject("msg", ErrMsg);

			return mav;
		}

		dto = profileService.updateProfile(studentbeans,password);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("ProfileDto", dto);
		mav.setViewName("ProfileView");

		return mav;
	}

	/**入力チェック処理
	 * @param password
	 * @param r_password
	 * @return
	 */
	private String ValidationCheck(String password, String r_password) {

		String errMsg = "";

		if(!(password.equals(r_password))){
			errMsg = "パスワードが一致していません";
		}else if(password.length()<7){
			errMsg = "パスワードが短すぎます";
		}

		return errMsg;
	}

	/**退会処理
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Delete" }, method = RequestMethod.GET)
	public ModelAndView Profile(ModelAndView mav) {


		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);


		profileService.ProfileDelete(loginInfo.getStudentId());

		session.invalidate();

		mav.setViewName("Login");

		return mav;
	}



}
