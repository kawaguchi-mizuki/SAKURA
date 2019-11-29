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
import cats.service.TalkService;

@RestController
@RequestMapping(value = { "/Request" })
public class RequestController {

	@Autowired
	HttpSession session;

	@Autowired
	HomeService homeService;

	@Autowired
	TalkService talkService;



	/**
	 * @param mav
	 * @return
	 */

	@RequestMapping(value = {"/Approval"}, method = RequestMethod.POST)
	public ModelAndView RequestApproval(@RequestParam Integer requestId,ModelAndView mav)throws Exception{


			//リクエスト承認フラグ更新処理
			Integer approvalId = homeService.approvalId(requestId);

			//承認したユーザーとのトークを作成
			 talkService.getCreateTalk(approvalId);

			//リクエスト承認したユーザーの名前を取得
			String approvalName = homeService.getName(requestId);

			//ユーザー情報をセッションから取得
			LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

			//受け取ったリクエスト一覧を取得
			List<HomeRequestDto> requestlist = homeService.getAllList(loginInfo);

			//受け取ったリクエスト数を取得
			int requestcount = requestlist.size();

			//ポイント反映
			int point = loginInfo.getPoint();

			mav.addObject("point",point);
			mav.addObject("count",requestcount);
			mav.addObject("requestlist", requestlist);
			mav.addObject("approvalName",approvalName);
			mav.setViewName("Home");

		return mav;
	}

	/**リクエスト破棄
	 * @param requestId
	 * @param mav
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = {"/Delete"}, method = RequestMethod.POST)
	public ModelAndView RequestDelete(@RequestParam Integer requestId,ModelAndView mav)throws Exception{


		homeService.delete(requestId);


		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//受け取ったリクエスト一覧を取得
		List<HomeRequestDto> requestlist = homeService.getAllList(loginInfo);

		//受け取ったリクエスト数を取得
		int requestcount = requestlist.size();

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("count",requestcount);
		mav.addObject("requestlist", requestlist);
		mav.setViewName("Home");
		return mav;
	}

}
