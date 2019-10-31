package cats.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cats.dto.CreateUserDto;
import cats.dto.LoginDayDto;
import cats.dto.LoginInfoDto;
import cats.from.LoginFrom;
import cats.param.SessionConst;
import cats.service.LoginService;


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


	/*@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(
			@ModelAttribute("msg")String msg,
			@ModelAttribute("studentId")int studentId,
			ModelAndView mv,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
	    //	エラーメッセージ
		if(msg != null && msg.length() > 0) {
			mv.addObject("msg",msg);
		}else {
			mv.addObject("msg","");
		}
		LoginInfoBeans beans =  new LoginInfoBeans();
		beans.setStudentId(studentId);
		mv.addObject("loginInfoBeans", beans);
		mv.setViewName("login");
		
		return mv;
	}*/
	
	@RequestMapping(value = {"/Login"}, method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mav) {

		mav.setViewName("Login");

		return mav;
	}
	
	@RequestMapping(value = {"/home"}, method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mav) {

		mav.setViewName("home");

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
	
	@RequestMapping(value = {"/auth"}, method = RequestMethod.POST)
	public String auth(
			RedirectAttributes redirectAttributes,
			@ModelAttribute("studentId")int studentId,
			@ModelAttribute("password")String pass,
			ModelAndView mav,
			HttpServletRequest request,
			HttpServletResponse response
			)throws Exception {
		
		String url = "";
		LoginInfoDto loginInfo= null;
		
		//	ログイン処理
		loginInfo = loginService.Login(studentId, pass);
		
		if(loginInfo != null) {
			//Date date = new Date();	
			//loginService.InsertDate(studentId,date);
			//	セッションにログイン情報を保存
			session.setAttribute(SessionConst.LOGININFO, loginInfo);
			url = "redirect:DayCheck";
			
		}else {
			url = "redirect:Login";
		}
		return url;
	}
	
	/**
	 * 連続ログイン判定
	 * 
	 * 
	 
	
	@RequestMapping(value = {"/DayCheck"}, method = RequestMethod.POST)
	public ModelAndView DayCheck(
			ModelAndView mav,
			LoginDayDto dto,
			HttpServletRequest request,
			HttpServletResponse response
			)throws Exception {
		
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);
		
		LoginDayDto dto = loginService.LastDay(loginInfo.getStudentId());
		
		Date date = new Date();
		
		//	連続日付の判定
		if(date.after(loginInfo.getLastLog())) {
			if() {
			loginService.UpdateDate(loginInfo.getStudentId(),date);
			mav.setViewName("home");
			}
			
		}else if(date.before(loginInfo.getLastLog())){
			//	エラー文
			mav.setViewName("login");
		}
		
		//	日付の更新
		if(date.after(loginInfo.getLastLog())) {
			loginService.UpdateDate(loginInfo.getStudentId(),date);
			mav.setViewName("home");
			
		}else if(date.before(loginInfo.getLastLog())){
			mav.setViewName("login");
		}
		
	
	return mav;
	}
	
	*/
	
	
	/**
	 * ログアウト処理
	 * @param redirectAttributes
	 * @return
	 * @throws AsoBbsSystemErrException
	 */
	@RequestMapping(value= {"/logout"}, method=RequestMethod.GET)
	public String logout(HttpServletRequest request,RedirectAttributes redirectAttributes) throws Exception {
				
		//	セッション破棄
		session.invalidate();
		
		//	ログイン画面へリダイレクト
		//	ログアウトの時はauto=falseをつけて自動ログインを防ぐ
		return "redirect:Login";
	}
	
	
	/**
	 * エラーメッセージ
	 * 
	 * 
	 * 
	 */
	
	/*
 	@RequestMapping(value = {"/login-error"}, method = RequestMethod.GET)
	public ModelAndView UserEntry(Model model) {
		 model.addAttribute("error",true);
		 return "Login";
	}
	*/

	
	
}
