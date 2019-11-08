package cats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cats.service.AuthenticationService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;

@Controller

@RequestMapping(value = {"/auth"})
public class AuthenticationCodeController {
	
	@Autowired
    private JavaMailSender mailSender;
	
	@Autowired
	AuthenticationService authentication;
	
	@RequestMapping(value = {"/start"})
	
	public void start(ModelAndView mav) {
		mav.setViewName("send");
	}
	
	@RequestMapping(value = {"/send"},method=RequestMethod.POST)
	public ModelAndView send(
			ModelAndView mav,
			@RequestParam("studentId") int studentId
			) {
		
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
		
		
		mav.setViewName("send");
		
				return mav;
	}

	@Autowired
    private MailSender sender;
	
	
	public void sendMail(String mail,String pass) {
		
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setTo(mail);
        msg.setSubject("認証コード送信");
        msg.setText("下記の認証コードを入力欄に入力してください。/n"+pass);

        this.sender.send(msg);
    }
	
	@RequestMapping(value = {"/approval"},method=RequestMethod.POST)
	public ModelAndView approval(
			ModelAndView mav,
			@RequestParam("passapp") String passapp
			){
		
		authentication.apptova(passapp);
		
		
		
		return mav;
		
	}
	
}

