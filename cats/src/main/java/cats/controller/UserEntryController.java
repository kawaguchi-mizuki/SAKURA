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


@RestController
@RequestMapping(value= {"/User"})
public class UserEntryController {


	@Autowired
	HobbyService hobbyService;

	@Autowired
	SchoolService schoolService;

//	@Autowired
//	UserService userService;

	@Autowired
	HttpSession session;



	@RequestMapping(value= {"/Entry"},method=RequestMethod.GET)
	public ModelAndView TwoConf( ModelAndView mav) {



		List<HobbyDto> hobbylist = hobbyService.getAllList();
		List<SchoolDto> schoollist = schoolService.getAllList();


		//空のデータを作る
		CreateUserDto dto = new CreateUserDto();

		UserBeans userbeans = new UserBeans();

		mav.addObject("userbeans",userbeans);
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

			String errMsg = "パスワードが一致していません";

			mav.setViewName("UserEntry");
			mav.addObject("userbeans",userbeans);
			mav.addObject("hobbylist", hobbylist);
			mav.addObject("schoollist",schoollist);
			mav.addObject("msg",errMsg);

		}else {




			mav.setViewName("login");

		}


		return mav;
	}

}
