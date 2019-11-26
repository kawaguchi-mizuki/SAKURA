package cats.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cats.beans.StudentBeans;
import cats.config.AppSettingProperty;
import cats.dto.CourseDto;
import cats.dto.HobbyDto;
import cats.dto.LoginInfoDto;
import cats.dto.ProfileDto;
import cats.dto.SchoolDto;
import cats.param.SessionConst;
import cats.service.CourseService;
import cats.service.HobbyService;
import cats.service.ProfileService;
import cats.service.SchoolService;
import cats.service.StudentService;
import cats.utils.FileUtils;




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
	CourseService courseService;

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

		ProfileDto dto = new ProfileDto();
		try {
			dto = profileService.getDisplayBoard(loginInfo);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}



		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("ProfileDto", dto);
		mav.setViewName("ProfileView");
		return mav;
	}

	@RequestMapping("/GetCourseList")
    public List<CourseDto> getucourselist(
    		@RequestParam("schoolId")Integer schoolId) {



        return courseService.getList(schoolId);
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





		try {

			if (!(studentbeans.getMultipartFile().getOriginalFilename().equals(""))) {

				uploadFiles(studentbeans);

			}

			dto = profileService.updateProfile(studentbeans,password);

		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}






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
	@RequestMapping(value = { "/Browse" }, method = RequestMethod.GET)
	public ModelAndView ProfileBrowse(@RequestParam Integer studentId,@RequestParam Integer talkId, ModelAndView mav) {




		ProfileDto dto = profileService.getDisplayBoardBrowse(studentId);

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("talkId",talkId);
		mav.addObject("point",point);
		mav.addObject("ProfileDto", dto);
		mav.setViewName("ProfileBrowse");
		return mav;
	}
	private void uploadFiles(@Valid StudentBeans studentbeans) throws Exception {

		MultipartFile uploadFile = studentbeans.getMultipartFile();

		File uploadDir = null;

		//ファイルがあれば保存して、パスを覚えておく

		if( !uploadFile.isEmpty() ) {
			//アップロードディレクトリを取得する
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
			String profileImage = sdf.format(now);
			uploadDir = mkdirs(profileImage);
			//出力ファイル名を決定する
			File uploadFilePath = new File(uploadDir.getPath() + "/" + uploadFile.getOriginalFilename());
			//ファイルコピー
			uploadFile.transferTo(uploadFilePath);
			//アップロードしたファイル名を覚えておく
			studentbeans.addUploadFilePath(profileImage+"/" + uploadFile.getOriginalFilename(),uploadFile.getSize());
		}

	}


	private File mkdirs(String profileImage) throws Exception{

		//アップロードディレクトリを取得する
		StringBuffer filePath = new StringBuffer(AppSettingProperty.getInstance().getCatsUploadWorkDirectory());


		File uploadDir = new File(filePath.toString(), profileImage);


		// 既に存在する場合はプレフィックスをつける
		int prefix = 0;
		while(uploadDir.exists()){
			prefix++;
			uploadDir =
					new File(filePath.toString() + profileImage + "-" + String.valueOf(prefix));
		}

		// フォルダ作成
		FileUtils.makeDir( uploadDir.toString());

		return uploadDir;
	}



}
