package cats.controller;

import java.sql.Timestamp;
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
import cats.dto.MessageDto;
import cats.dto.TalkSelectDto;
import cats.param.SessionConst;
import cats.service.MessageService;
import cats.service.TalkService;

@RestController
@RequestMapping(value = { "/Message" })
public class MessageController {

	@Autowired
	HttpSession session;

	@Autowired
	MessageService messageService;

	@Autowired
	TalkService talkSerivce;

	/**トーク画面表示
	 * @param talkId
	 * @param receiveName
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/View" }, method = RequestMethod.GET)
	public ModelAndView MessageView(@RequestParam Integer talkId,@RequestParam String receiveName,@RequestParam String sendName, ModelAndView mav) {


		List<MessageDto> messagelist = new ArrayList<MessageDto>();
		try {
			messagelist = messageService.getAllList(talkId);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}


		TalkSelectDto dto = talkSerivce.getTalk(talkId);

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("dto",dto);
		mav.addObject("studentId",loginInfo.getStudentId());
		mav.addObject("messagelist",messagelist);
		mav.addObject("talkId",talkId);
		mav.addObject("receiveName",receiveName);
		mav.addObject("sendName",sendName);
		mav.setViewName("Message");

		return mav;

	}
	@RequestMapping(value = { "/Insert" }, method = RequestMethod.POST)
	public ModelAndView MessageInsert(@RequestParam Integer talkId,@RequestParam String talk,@RequestParam String receiveName,@RequestParam String sendName,ModelAndView mav) {


		MessageDto dto = new MessageDto();

		dto = getCreateMessage(talk,talkId);

		messageService.insert(dto);

		TalkSelectDto talkdto = talkSerivce.getTalk(talkId);

		List<MessageDto> messagelist = new ArrayList<MessageDto>();
		try {
			messagelist = messageService.getAllList(talkId);
		} catch (Exception e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("dto",talkdto);
		mav.addObject("studentId",loginInfo.getStudentId());
		mav.addObject("messagelist",messagelist);
		mav.addObject("talkId",talkId);
		mav.addObject("receiveName",receiveName);
		mav.addObject("sendName",sendName);
		mav.setViewName("Message");

		return mav;
	}
	private MessageDto getCreateMessage(String talk,Integer talkId) {

		MessageDto dto = new MessageDto();


		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);


		dto.setTalkId(talkId);
		dto.setMessage(talk);
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		dto.setSendTime(timestamp);
		dto.setStudentIdSend(loginInfo.getStudentId());

		return dto;
	}
}
