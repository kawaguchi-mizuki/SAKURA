package cats.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cats.beans.StudentBeans;
import cats.config.AppSettingProperty;
import cats.dto.CourseDto;
import cats.dto.CreateUserDto;
import cats.dto.HobbyDto;
import cats.dto.SchoolDto;
import cats.service.CourseService;
import cats.service.HobbyService;
import cats.service.SchoolService;
import cats.service.StudentService;
import cats.utils.FileUtils;

//会員登録
@RestController
@RequestMapping(value = { "/User" })
public class UserEntryController {

	@Autowired
	HobbyService hobbyService;

	@Autowired
	CourseService courseService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	StudentService studentService;

	@Autowired
	HttpSession session;

	@RequestMapping(value = { "/Entry" }, method = RequestMethod.GET)
	public ModelAndView UserEntry(ModelAndView mav) {

		//趣味一覧を取得
		List<HobbyDto> hobbylist = hobbyService.getAllList();
		//学校一覧を取得
		List<SchoolDto> schoollist = schoolService.getAllList();

		//空のデータを作る
		CreateUserDto dto = new CreateUserDto();


		StudentBeans studentbeans = new StudentBeans();

		mav.addObject("studentbeans", studentbeans);
		mav.addObject("createUserDto", dto);
		mav.setViewName("UserEntry");
		mav.addObject("hobbylist", hobbylist);
		mav.addObject("schoollist", schoollist);


		return mav;
	}

	/**
	 * @param studentbeans
	 * @param password
	 * @param r_password
	 * @param bindingResult
	 * @param mav
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = { "/Check" }, method = RequestMethod.POST)
	public ModelAndView UserCheck(@Valid StudentBeans studentbeans,
			@RequestParam("password") String password, @RequestParam("r_password") String r_password,
			BindingResult bindingResult, ModelAndView mav) throws Exception {

		List<HobbyDto> hobbylist = hobbyService.getAllList();
		List<SchoolDto> schoollist = schoolService.getAllList();

		CreateUserDto dto = new CreateUserDto();

		String ErrMsg;

		//入力値チェック
		ErrMsg = ValidationCheck(password,r_password);

		//エラーメッセージがあればリダイレクト
		if(!(ErrMsg.equals(""))){
			mav.addObject("hobbylist", hobbylist);
			mav.addObject("schoollist", schoollist);
			mav.setViewName("UserEntry");
			mav.addObject("studentbeans", studentbeans);
			mav.addObject("createUserDto", dto);
			mav.addObject("msg", ErrMsg);

			return mav;
		}

		//画像アップロード
		uploadFiles(studentbeans);

		dto = getCreateUserDto(studentbeans, password);

		//初期ポイント追加
		dto = getCreatePoint(dto);

		studentService.insert(dto);

		mav.setViewName("login");

		return mav;
	}

	/**
	 * @return
	 */
	@RequestMapping("/GetCourseList")
    public List<CourseDto> getucourselist(
    		@RequestParam("schoolId")Integer schoolId) {
        return courseService.getList(schoolId);
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

	/**ファイルのコピー
	 * @param studentbeans
	 * @throws Exception
	 */
	private void uploadFiles(@Valid StudentBeans studentbeans) throws Exception {

		MultipartFile uploadFile = studentbeans.getMultipartFile();

		File uploadDir = null;

		//ファイルがあれば保存して、パスを覚えておく

		if( !uploadFile.isEmpty() ) {
			//アップロードディレクトリを取得する
			uploadDir = (uploadDir == null ? mkdirs() : uploadDir);
			//出力ファイル名を決定する
			File uploadFilePath = new File(uploadDir.getPath() + "/" + uploadFile.getOriginalFilename());
			//ファイルコピー
			uploadFile.transferTo(uploadFilePath);
			//アップロードしたファイル名を覚えておく
			studentbeans.addUploadFilePath(uploadFilePath.toString(),uploadFile.getSize());
		}

	}


	private File mkdirs() throws Exception{

		//アップロードディレクトリを取得する
		StringBuffer filePath = new StringBuffer(AppSettingProperty.getInstance().getCatsUploadWorkDirectory());

		Date now = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		File uploadDir = new File(filePath.toString(), sdf.format(now));
		// 既に存在する場合はプレフィックスをつける
		int prefix = 0;
		while(uploadDir.exists()){
			prefix++;
			uploadDir =
					new File(filePath.toString() + sdf.format(now) + "-" + String.valueOf(prefix));
		}

		// フォルダ作成
		FileUtils.makeDir( uploadDir.toString());

		return uploadDir;
	}



	/**
	 * @param studentbeans
	 * @param password
	 * @return
	 */
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
		dto.setImagePass("aaa");


		return dto;

	}

}
