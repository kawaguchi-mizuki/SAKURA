package cats.controller;

import java.util.List;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import cats.dto.GachaDto;
import cats.dto.HobbyDto;
import cats.dto.LoginInfoDto;
import cats.dto.RequestDto;
import cats.param.SessionConst;

import cats.service.HobbyService;
import cats.service.GachaService;

//	ガチャ
@RestController
@RequestMapping(value = { "/Gacha" })
public class GachaController {
	
	@Autowired
	GachaService gachaService;
	
	@Autowired
	HobbyService hobbyService;
	
	@Autowired
	HttpSession session;
	
	@RequestMapping(value = {"/Top"}, method = RequestMethod.GET)
	public ModelAndView Matching(ModelAndView mav)throws Exception{
		
		//	ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);
		
		//	趣味一覧を取得
		List<HobbyDto> hobbylist = hobbyService.getAllList();
		
		//	ポイント反映
		int point = loginInfo.getPoint();
		
		mav.addObject("point",point);
		mav.addObject("hobbylist", hobbylist);
		mav.setViewName("Gacha");

		return mav;
	}
	
	@RequestMapping(value = {"/Matching"}, method = RequestMethod.GET)
	public ModelAndView Gacha(
			RedirectAttributes redirectAttributes,
			@ModelAttribute("hobbyId")int hobbyId,
			ModelAndView mav,
			HttpServletRequest request,
			HttpServletResponse response)throws Exception{
		
			String ErrMsg ="";
			String mode = null;

			//	ユーザー情報をセッションから取得
			LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);
			System.out.println(loginInfo.getPoint());
			
			if(loginInfo.getPoint() > 100) {						
				GachaDto dto = gachaService.getGacha(loginInfo.getStudentId(),hobbyId);
				RequestDto set = gachaService.getGachaList(dto.getStudentId(),dto.getHobbyIdSearch(),dto.getStudentSex());
				loginInfo = gachaService.gachaPoint(loginInfo.getPoint());

				if(set.getStudentIdSent() != null) {
					//	えら
					gachaService.gachaInsert(set);
					mode = "update";
					
				}else {
					ErrMsg = "マッチング失敗！";
					loginInfo = gachaService.gachaMissPoint(loginInfo.getPoint());
				}
			  }else if(hobbyId == 0){
				ErrMsg = "趣味を選択してください！";
			  }else {
				ErrMsg = "ポイントが足りません！"; 
			  }
		//	リダイレクト時に取得したい
		List<HobbyDto> hobbylist = hobbyService.getAllList();
		session.setAttribute(SessionConst.LOGININFO, loginInfo);

		mav.addObject("hobbylist", hobbylist);
		mav.addObject("point",loginInfo.getPoint());
		mav.addObject("msg", ErrMsg);
		mav.addObject("mode", mode);
		mav.setViewName("Gacha");
		
		return mav;
	}

}
