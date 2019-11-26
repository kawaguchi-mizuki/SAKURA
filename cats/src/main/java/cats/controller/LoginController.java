package cats.controller;



import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cats.beans.StudentBeans;
import cats.config.AppSettingProperty;
import cats.dto.CreateUserDto;
import cats.dto.HobbyDto;
import cats.dto.HomeRequestDto;
import cats.dto.LoginDayDto;
import cats.dto.LoginInfoDto;
import cats.dto.SchoolDto;
import cats.param.SessionConst;
import cats.service.CourseService;
import cats.service.HobbyService;
import cats.service.HomeService;
import cats.service.LoginService;
import cats.service.SchoolService;
import cats.service.StudentService;
import cats.utils.FileUtils;


/** sift + alt + j
 * @author 栗山和大
 *
 */
/**
 * @author 栗山和大
 *
 */
@Controller
public class LoginController {

	@Autowired
	LoginService loginService;

	@Autowired
	HttpSession session;

	@Autowired
	HomeService homeService;

	@Autowired
	HobbyService hobbyService;

	@Autowired
	CourseService courseService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	StudentService studentService;





	@RequestMapping(value = {"/Login"}, method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mav)throws Exception{

		mav.setViewName("Login");

		return mav;
	}

	@RequestMapping(value = { "/Config" }, method = RequestMethod.POST)
	public ModelAndView UserCheck(@Valid StudentBeans studentbeans,
			@RequestParam("password") String password, @RequestParam("rpassword") String r_password,
			BindingResult bindingResult, ModelAndView mav) throws Exception {

		List<HobbyDto> hobbylist = hobbyService.getAllList();
		List<SchoolDto> schoollist = schoolService.getAllList();

		CreateUserDto dto = new CreateUserDto();

		String ErrMsg;



		//画像アップロード
		uploadFiles(studentbeans);

		dto = getCreateUserDto(studentbeans, password);

		//初期ポイント追加
		dto = getCreatePoint(dto);

		studentService.insert(dto);

		mav.setViewName("Login");

		return mav;
	}

	@RequestMapping(value = {"/Home"}, method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mav, HttpServletResponse response) {


		//	ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//受け取ったリクエスト一覧を取得
		List<HomeRequestDto> requestlist = new ArrayList<HomeRequestDto>();
		try {
			requestlist = homeService.getAllList(loginInfo);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		//受け取ったリクエスト数を取得
		int requestcount = requestlist.size();

		//ポイント反映
	    int point = loginInfo.getPoint();

	    mav.addObject("point",point);
		mav.addObject("count",requestcount);
		mav.addObject("requestlist", requestlist);
		mav.setViewName("Home");

		return mav;
	}


	/**
	 * ログイン処理
	 * @param redirectAttributes
	 * @param studentId
	 * @param pass
	 * @param from
	 * @param mv
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	//string hennkou
	@RequestMapping(value = {"/auth"}, method = RequestMethod.POST)
	public String auth(
			RedirectAttributes redirectAttributes,
			@ModelAttribute("studentId")int studentId,
			@ModelAttribute("password")String pass,
			ModelAndView mav,
			HttpServletRequest request,
			HttpServletResponse response
			)throws Exception {

		String ErrMsg;
		String url;

		LoginInfoDto loginInfo= null;

		//	ログイン処理
		loginInfo = loginService.Login(studentId, pass);

		//	セッション情報を入れ替えるuserInfo =

		if(loginInfo != null) {
			//	セッションにログイン情報を保存
			session.setAttribute(SessionConst.LOGININFO, loginInfo);
			DayCheck(loginInfo);
			url = "redirect:Home";

		}else{
			ErrMsg = "学籍番号とパスワードが一致しません";
			redirectAttributes.addFlashAttribute("msg",ErrMsg);
			url = "redirect:Login";
		}
		return url;
	}

	/**
	 * 連続ログイン判定
	 * @param loginInfo
	 * @return
	 * @throws Exception
	 */
	private void DayCheck(LoginInfoDto loginInfo)throws Exception {

		LoginDayDto dto = null;
		String cheinday = null;
		String lastday = null;
		String today =null;
		Date chein = null;
		Date last = null;
		int num = 0;
		int point = 0;
		//	ログインボーナス（ポイント）
		int[] bar  = {50,100,150,200,300};


		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();

		dto = loginService.LastDay(loginInfo.getStudentId());

		if(dto.getLastLog() != null) {
		//	Date型の日時をCalendar型に変換
        Calendar calendar = Calendar.getInstance();
        Calendar calenday = Calendar.getInstance();

        calendar.setTime(date);
        calenday.setTime(dto.getLastLog());

        //	日時を減算する
        calendar.add(Calendar.DATE, -1);

        cheinday = sdf.format(calendar.getTime());
        lastday = sdf.format(calenday.getTime());
        today = sdf.format(date);

        //format
        last = sdf.parse(lastday);
        chein = sdf.parse(cheinday);
        date = sdf.parse(today);

        System.out.println(last);
        System.out.println(chein);
        System.out.println(date);

		//	現在の日付を超えているか
		if(last.before(date)) {

			//	最後にログインした値を取得して-1したものと現在日付が一致するか
			if(last.equals(chein)) {
				num = dto.getContinuousLogin();
				if(num < 6) {
					num = num+1;
					//	ログインポイント追加処理 for
					for(int i = 0;i<6;i++) {
						for(int j = 0;j<bar.length;j++) {
							if(num == i) {
								if(i-1 == j) {
									point = loginInfo.getPoint();
									point = point+bar[j];
									loginService.LoginPoint(loginInfo.getStudentId(),point);
								}
							}
						}
					}
				}
				if(num >= 6) {
					num = 0;
				}
				loginService.UpdateCount(loginInfo.getStudentId(),num);
				loginService.UpdateDate(loginInfo.getStudentId(),date);
			}
			else {
			loginService.UpdateCount(loginInfo.getStudentId(),num);
			loginService.UpdateDate(loginInfo.getStudentId(),date);
			}
		}
	}else {
		loginService.UpdateCount(loginInfo.getStudentId(),num);
		loginService.UpdateDate(loginInfo.getStudentId(),date);
	}
}

	/**
	 * ログアウト処理
	 * @param redirectAttributes
	 * @return
	 * @throws AsoBbsSystemErrException
	 */
	@RequestMapping(value= {"/Logout"}, method=RequestMethod.GET)
	public String logout(HttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception {

		//	セッション破棄
		session.invalidate();

		//	ログイン画面へリダイレクト
		return "redirect:Login";
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



	/**追加ポイント処理
	 * @param dto
	 * @return
	 */
	private CreateUserDto getCreatePoint(CreateUserDto dto) {

		//初期ポイントを付与
		dto.setPoint(500);

		//任意項目を入力していたら追加ポイント
		if(dto.getSchoolId()!=1){
			dto.setPoint(dto.getPoint()+100);
		}

		if(dto.getGrade()!=0){
			dto.setPoint(dto.getPoint()+50);
		}

		if(dto.getAge()!=0){
			dto.setPoint(dto.getPoint()+50);
		}

		if(!(dto.getBirthplace().equals("未選択"))){
			dto.setPoint(dto.getPoint()+50);
		}

		if(!(dto.getSelfIntroduction().equals(""))){
			dto.setPoint(dto.getPoint()+50);
		}

		return dto;
	}

	private CreateUserDto getCreateUserDto(@Valid StudentBeans studentbeans, String password) {

		CreateUserDto dto = new CreateUserDto();

		dto.setStudentId(studentbeans.getStudentId());
		dto.setStudentName(studentbeans.getName());
		dto.setStudentSex(studentbeans.getSex());
		dto.setHobbyId(studentbeans.getHobbyId());
		dto.setSchoolId(studentbeans.getSchoolId());
		dto.setCourseId(studentbeans.getCourseId());
		dto.setGrade(studentbeans.getGrade());
		dto.setAge(studentbeans.getAge());
		dto.setBirthplace(studentbeans.getBirthplace());
		dto.setSelfIntroduction(studentbeans.getIntroduction());
		dto.setPassword(password);

		dto.setImagePass(studentbeans.getUploadFilePathList().get(0).getFilePath());


		return dto;

	}


}
