package cats.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.dto.LoginInfoDto;
import cats.dto.TalkSelectDto;
import cats.param.SessionConst;
import cats.service.ProfileService;
import cats.service.TalkService;

@RestController
@RequestMapping(value = { "/Talk" })
public class TalkSelectController {

	@Autowired
	HttpSession session;

	@Autowired
	TalkService talkService;

	@Autowired
	ProfileService profileService;

	@RequestMapping(value = { "/Select" }, method = RequestMethod.GET)
	public  ModelAndView TalkSelect(ModelAndView mav) {


		//トークリストを取得
		List<TalkSelectDto> talkselectlist = new ArrayList<TalkSelectDto>();
		try {
			talkselectlist = talkService.getAllList();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("selectId",loginInfo.getStudentId());
		mav.addObject("talkselectlist",talkselectlist);
		mav.setViewName("TalkSelect");

		return mav;
	}

	@RequestMapping(value = { "/Delete" }, method = RequestMethod.GET)
	public  ModelAndView TalkDelete(@RequestParam Integer talkId,  ModelAndView mav) {


		talkService.deleteTalk(talkId);


		//トークリストを取得
		List<TalkSelectDto> talkselectlist = new ArrayList<TalkSelectDto>();
		try {
			talkselectlist = talkService.getAllList();
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("selectId",loginInfo.getStudentId());
		mav.addObject("talkselectlist",talkselectlist);
		mav.setViewName("TalkSelect");

		return mav;
	}


}
