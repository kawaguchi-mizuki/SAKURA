package cats.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import cats.beans.StudentBeans;
import cats.dto.AuthSearchDto;
import cats.dto.AuthenticationDto;
import cats.dto.CourseDto;
import cats.dto.CreateUserDto;
import cats.dto.HobbyDto;
import cats.dto.SchoolDto;
import cats.service.AuthenticationService;
import cats.service.CourseService;
import cats.service.HobbyService;
import cats.service.SchoolService;
import cats.service.StudentService;

@Controller

@SessionAttributes(names="authStudent")

public class AuthenticationCodeController {


	@Autowired
	AuthenticationService authentication;

	@Autowired
    private JavaMailSender mailSender;

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


	@RequestMapping(value = {"/check"})

	public ModelAndView check(ModelAndView mav)  {
		mav.setViewName("TwoCheck");
		return mav;
	}

	@RequestMapping(value = {"/TwoCheck"},method=RequestMethod.POST)
	public ModelAndView send(
			ModelAndView mav,
			@RequestParam("studentId") Integer studentId
			)
	{

		session.setAttribute("authStudent",studentId);

		String mail;

		String pass = RandomStringUtils.randomAlphanumeric(7);

		//現在時間の取得、フォーマット変換
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();




        //日時計算
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.MINUTE, 30);

        Date NowDate = calendar.getTime();

        //登録確認
        AuthSearchDto asd = authentication.search(studentId);

        if(asd != null) {

        	String errMsg = "この学籍番号は既に登録されています。";
        	mav.addObject("errMsg",errMsg);
        	mav.setViewName("TwoCheck");

        	return mav;

        }


        //メールアドレス設定
        if(1700000<=studentId&&studentId<=1799999) {
			mail = studentId + "@st.asojuku.ac.jp";
			sendMail(mail,pass);
		}else if(1800000<=studentId&&studentId<=1999999){
			mail = studentId + "@s.asojuku.ac.jp";
			sendMail(mail,pass);
		}else {
			String errMsg = "学籍番号が正しくありません";
			mav.addObject("errMsg",errMsg);
			mav.setViewName("TwoCheck");
			return mav;
		}


		authentication.insert(studentId, pass, NowDate);


		mav.setViewName("TwoConf");

				return mav;
	}

	@Autowired
    private MailSender sender;


	public void sendMail(String mail,String pass) {

        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(mail);
        msg.setSubject("認証コード送信");
        msg.setText("下記の認証コードを入力欄に入力してください。\n認証コードの期限は30分です。"
        		+ "期限を超えた場合はお手数ですがもう一度学籍番号の入力を行ってもらいます。\n\n"+pass);
        this.sender.send(msg);
    }

	@RequestMapping(value = {"/TwoConf"},method=RequestMethod.POST)
	public ModelAndView approval(
			ModelAndView mav,
			@RequestParam("passauth") String passauth
			){

		AuthenticationDto dto = new AuthenticationDto();

		Integer authStudent = (Integer)session.getAttribute("authStudent");

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

		dto = authentication.apptova(authStudent);

		Integer time = date.compareTo(dto.getNowDate());

		System.out.println(passauth);
		System.out.println(dto.getPass());


		if(dto.getPass().equals(passauth) && time<=0){



			//認証成功
			//趣味一覧を取得
			List<HobbyDto> hobbylist = hobbyService.getAllList();
			//学校一覧を取得
			List<SchoolDto> schoollist = schoolService.getAllList();

			//空のデータを作る
			CreateUserDto createdto = new CreateUserDto();




			StudentBeans studentbeans = new StudentBeans();



			mav.addObject("studentId",authStudent);
			mav.addObject("studentbeans", studentbeans);
			mav.addObject("createUserDto", createdto);
			mav.setViewName("UserEntry");
			mav.addObject("hobbylist", hobbylist);
			mav.addObject("schoollist", schoollist);

			//セッションの破棄
			session.invalidate();


			return mav;

		}else if(dto.getPass() != passauth){
			//認証失敗
			String errMsg = "認証コードが正しくありません";
			mav.addObject("errMsg",errMsg);
			mav.setViewName("TwoConf");
		}else {
			//認証コードの期限切れ
			String errMsg = "認証コードの期限が切れています。もう一度学籍番号を入力してください。";
			mav.addObject("errMsg",errMsg);
			mav.setViewName("TwoCheck");
		}


		return mav;

	}
	@RequestMapping("/GetCourseList")
    public List<CourseDto> getucourselist(
    		@RequestParam("schoolId")Integer schoolId) {


        return courseService.getList(schoolId);
    }

}

