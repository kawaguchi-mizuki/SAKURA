package cats.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cats.service.AuthenticationService;

import org.apache.commons.lang3.RandomStringUtils;

@Controller

@RequestMapping(value = {"/auth"})
public class AuthenticationCodeController {
	
	@Autowired
	AuthenticationService authentication;
	
	
	@RequestMapping(value = {"/send"},method=RequestMethod.POST)
	public ModelAndView send(
			ModelAndView mav,
			@RequestParam("studentId") int studentId
			) {
		
		String mail;
		
		String pass = RandomStringUtils.randomAlphanumeric(7);
		
		if(1700000<=studentId&&studentId<=1799999) {
			mail = studentId + "@st.asojuku.ac.jp";
			sendMail(mail,pass);
		}else {
			mail = studentId + "@s.asojuku.ac.jp";
			sendMail(mail,pass);
		}
		
		authentication.insert(studentId, pass);
		
		
		mav.setViewName("send");
		
				return mav;
	}
	
	@Autowired
    private MailSender sender;
	public void sendMail(String mail,String pass) {
        SimpleMailMessage msg = new SimpleMailMessage();

        msg.setFrom("guanlicats@gmail.com");
        msg.setTo(mail);
        msg.setSubject("認証コード");
        msg.setText("下記の認証コードを入力欄に入力してください。/n"+pass);

        this.sender.send(msg);
    }
}

