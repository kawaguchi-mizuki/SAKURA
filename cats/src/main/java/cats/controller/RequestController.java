package cats.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.dto.HomeRequestDto;
import cats.dto.LoginInfoDto;
import cats.param.SessionConst;
import cats.service.HomeService;

@RestController
@RequestMapping(value = { "/Request" })
public class RequestController {

	@Autowired
	HttpSession session;

	@Autowired
	HomeService homeService;



	/**
	 * @param mav
	 * @return
	 */

	@RequestMapping(value = {"/Approval"}, method = RequestMethod.POST)
	public ModelAndView RequestApproval(@RequestParam Integer requestId,ModelAndView mav)throws Exception{

			System.out.println(requestId);

			//リクエスト承認フラグ更新処理
			homeService.approvalId(requestId);



			//ユーザー情報をセッションから取得
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

	@RequestMapping(value = {"/Delete"}, method = RequestMethod.POST)
	public ModelAndView RequestDelete(@RequestParam Integer requestId,ModelAndView mav)throws Exception{

		System.out.println(requestId);



		mav.setViewName("Home");

		return mav;
	}

}
