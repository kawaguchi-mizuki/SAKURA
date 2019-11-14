package cats.controller;



import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cats.dto.HomeRequestDto;
import cats.dto.LoginDayDto;
import cats.dto.LoginInfoDto;
import cats.param.SessionConst;
import cats.service.HomeService;
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

	@Autowired
	HomeService homeService;




	@RequestMapping(value = {"/Login"}, method = RequestMethod.GET)
	public ModelAndView login(ModelAndView mav)throws Exception{

		mav.setViewName("Login");

		return mav;
	}

	@RequestMapping(value = {"/Home"}, method = RequestMethod.GET)
	public ModelAndView home(ModelAndView mav, HttpServletResponse response) {


		//	ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//受け取ったリクエスト一覧を取得
		List<HomeRequestDto> requestlist = homeService.getAllList(loginInfo);

		//受け取ったリクエスト数を取得
		int requestcount = requestlist.size();


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

}
