package cats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import cats.dto.AuthenticationDto;
import cats.form.authForm;
import cats.service.AuthenticationService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.RandomStringUtils;

@Controller

@SessionAttributes(names="authStudent")

public class AuthenticationCodeController {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	AuthenticationService authentication;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value = {"/start"},method=RequestMethod.GET)
	
	public ModelAndView start(ModelAndView mav)  {
		mav.setViewName("send");
		return mav;
	}
	
	@RequestMapping(value = {"/send"},method=RequestMethod.POST)
	public ModelAndView send(
			ModelAndView mav,
			@RequestParam("studentId") Integer studentId){
		
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
		
        if(1700000<=studentId&&studentId<=1799999) {
			mail = studentId + "@st.asojuku.ac.jp";
			sendMail(mail,pass);
		}else if(1800000<=studentId&&studentId<=1999999){
			mail = studentId + "@s.asojuku.ac.jp";
			sendMail(mail,pass);
		}else {
			String errMsg = "学籍番号が正しくありません";
			mav.addObject("errMsg",errMsg);
			
			return mav;
		}
	
		
		authentication.insert(studentId, pass, NowDate);
		
		
		mav.setViewName("get");
		
				return mav;
	}

	@Autowired
    private MailSender sender;
	
	
	public void sendMail(String mail,String pass) {
		
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(mail);
        msg.setSubject("認証コード送信");
        msg.setText("下記の認証コードを入力欄に入力してください。\n\n\n"+pass);
        this.sender.send(msg);
    }
	
	@RequestMapping(value = {"/approval"},method=RequestMethod.POST)
	public ModelAndView approval(
			ModelAndView mav,
			@RequestParam("passauth") String passauth
			){
		
		AuthenticationDto dto = new AuthenticationDto();
		
		Integer authStudent = (Integer)session.getAttribute("authStudent");
		
		dto = authentication.apptova(authStudent);
		
		if(dto.getPass().equals(passauth) ) {
			//セッションの破棄
			session.invalidate();
			
			//認証コードの破棄
			authentication.delete(authStudent);
			
			//認証成功
			mav.setViewName("keikou");
			
		}else {
			//認証失敗
			String errMsg = "認証コードが正しくありません";
			mav.addObject("errMsg",errMsg);
		}
		
		
		return mav;
		
	}
	
}

