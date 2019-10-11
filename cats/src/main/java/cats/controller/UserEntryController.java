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
import cats.dto.CreateUserDto;
import cats.dto.HobbyDto;
import cats.dto.SchoolDto;
import cats.service.HobbyService;
import cats.service.SchoolService;
import cats.service.StudentService;
import cats.utils.FileUtils;


@RestController
@RequestMapping(value = { "/User" })
public class UserEntryController {

	@Autowired
	HobbyService hobbyService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	StudentService studentService;

	@Autowired
	HttpSession session;

	@RequestMapping(value = { "/Entry" }, method = RequestMethod.GET)
	public ModelAndView TwoConf(ModelAndView mav) {

		//趣味一覧を取得
		List<HobbyDto> hobbylist = hobbyService.getAllList();

		//学校一覧を取得
		List<SchoolDto> schoollist = schoolService.getAllList();

		//空のデータを作る
		CreateUserDto dto = new CreateUserDto();

		StudentBeans userbeans = new StudentBeans();

		mav.addObject("userbeans", userbeans);
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
	public ModelAndView UserEntry(@Valid StudentBeans studentbeans,
			@RequestParam("password") String password, @RequestParam("r_password") String r_password,
			BindingResult bindingResult, ModelAndView mav) throws Exception {

		List<HobbyDto> hobbylist = hobbyService.getAllList();
		List<SchoolDto> schoollist = schoolService.getAllList();

		CreateUserDto dto = new CreateUserDto();




		//	パスワードとパスワード再入力値が一致しない場合
		if (!(password.equals(r_password))) {



			String errMsg = "パスワードが一致していません";

			mav.setViewName("UserEntry");
			mav.addObject("userbeans", studentbeans);
			mav.addObject("createUserDto", dto);
			mav.addObject("hobbylist", hobbylist);
			mav.addObject("schoollist", schoollist);
			mav.addObject("msg", errMsg);

		} else {

			//画像アップロード
			uploadFiles(studentbeans);

			 dto = getCreateUserDto(studentbeans, password);

			//ポイントを付与
			dto.setPoint(500);

			studentService.insert(dto);

			mav.setViewName("login");

		}

		return mav;
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
	 * @param userbeans
	 * @param password
	 * @return
	 */
	private CreateUserDto getCreateUserDto(@Valid StudentBeans userbeans, String password) {

		CreateUserDto dto = new CreateUserDto();

		dto.setStudentId(userbeans.getStudentId());
		dto.setStudentName(userbeans.getName());
		dto.setStudentSex(userbeans.getSex());
		dto.setHobbyId(userbeans.getHobbyId());
		dto.setSchoolName(userbeans.getSchool());
		dto.setCourse(userbeans.getCourse());
		dto.setGrade(userbeans.getGrade());
		dto.setAge(userbeans.getAge());
		dto.setBirthplace(userbeans.getBirthplace());
		dto.setSelfIntroduction(userbeans.getIntroduction());
		dto.setPassword(password);
		dto.setImagePass("aaa");
		dto.setLastLog(null);
		dto.setContinuousLogin(4);

		return dto;

	}

}
