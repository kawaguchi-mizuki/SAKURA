package cats.controller;

import java.sql.Timestamp;
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
import cats.param.SessionConst;
import cats.service.MessageService;

@RestController
@RequestMapping(value = { "/Message" })
public class MessageController {

	@Autowired
	HttpSession session;

	@Autowired
	MessageService messageService;

	/**トーク画面表示
	 * @param talkId
	 * @param receiveName
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/View" }, method = RequestMethod.GET)
	public ModelAndView MessageView(@RequestParam Integer talkId,@RequestParam String receiveName, ModelAndView mav) {


		List<MessageDto> messagelist = messageService.getAllList(talkId);



		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		mav.addObject("studentId",loginInfo.getStudentId());
		mav.addObject("messagelist",messagelist);
		mav.addObject("talkId",talkId);
		mav.addObject("receiveName",receiveName);
		mav.setViewName("Message");

		return mav;

	}
	@RequestMapping(value = { "/Insert" }, method = RequestMethod.POST)
	public ModelAndView MessageInsert(@RequestParam Integer talkId,@RequestParam String talk,@RequestParam String receiveName,ModelAndView mav) {


		MessageDto dto = new MessageDto();

		dto = getCreateMessage(talk,talkId);

		messageService.insert(dto);


		List<MessageDto> messagelist = messageService.getAllList(talkId);

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		mav.addObject("studentId",loginInfo.getStudentId());
		mav.addObject("messagelist",messagelist);
		mav.addObject("talkId",talkId);
		mav.addObject("receiveName",receiveName);
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
