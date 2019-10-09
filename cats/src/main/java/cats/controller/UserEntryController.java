package cats.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.beans.UserBeans;
import cats.dto.CreateUserDto;
import cats.dto.HobbyDto;
import cats.dto.SchoolDto;
import cats.service.HobbyService;
import cats.service.SchoolService;
import cats.service.UserService;


@RestController
@RequestMapping(value= {"/User"})
public class UserEntryController {


	@Autowired
	HobbyService hobbyService;

	@Autowired
	SchoolService schoolService;

	@Autowired
	UserService userService;

	@Autowired
	HttpSession session;



	@RequestMapping(value= {"/Entry"},method=RequestMethod.GET)
	public ModelAndView TwoConf( ModelAndView mav) {


		//趣味一覧を取得
		List<HobbyDto> hobbylist = hobbyService.getAllList();

		//学校一覧を取得
		List<SchoolDto> schoollist = schoolService.getAllList();

		//空のデータを作る
		CreateUserDto dto = new CreateUserDto();

		UserBeans userbeans = new UserBeans();

		mav.addObject("userbeans",userbeans);
		mav.addObject("createUserDto",dto);
		mav.setViewName("UserEntry");
		mav.addObject("hobbylist", hobbylist);
		mav.addObject("schoollist",schoollist);

		return mav;
	}


	@RequestMapping(value= {"/Check"}, method=RequestMethod.POST)
	public ModelAndView UserEntry(@Valid UserBeans userbeans,
			@RequestParam("password") String password,@RequestParam("r_password") String r_password,
			BindingResult bindingResult,ModelAndView mav) {

		List<HobbyDto> hobbylist = hobbyService.getAllList();
		List<SchoolDto> schoollist = schoolService.getAllList();







		//	パスワードとパスワード再入力値が一致しない場合
		if(!(password.equals(r_password))) {

			CreateUserDto dto = new CreateUserDto();

			String errMsg = "パスワードが一致していません";

			mav.setViewName("UserEntry");
			mav.addObject("userbeans",userbeans);
			mav.addObject("createUserDto",dto);
			mav.addObject("hobbylist", hobbylist);
			mav.addObject("schoollist",schoollist);
			mav.addObject("msg",errMsg);

		}else {

			CreateUserDto dto = getCreateUserDto(userbeans,password);

			//ポイントを付与
			dto.setPoint(500);

			userService.insert(dto);


			mav.setViewName("login");

		}


		return mav;
	}


	private CreateUserDto getCreateUserDto(@Valid UserBeans userbeans,String password) {

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
