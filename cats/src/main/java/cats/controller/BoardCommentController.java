package cats.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.service.BoardService;

//掲示板
@RestController
@RequestMapping(value = { "/BoardComment" })
public class BoardCommentController {

	@Autowired
	BoardService boardService;

	@Autowired
	HttpSession session;


	/**掲示板コメント画面表示
	 * @param boardId
	 * @param boardTitle
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Read" }, method = RequestMethod.GET)
	public ModelAndView BoardCommentRead(@RequestParam Integer boardId,@RequestParam String boardTitle, ModelAndView mav) {




		mav.addObject("boardId",boardId);
		mav.addObject("boardTitle",boardTitle);
		mav.setViewName("BordComment");


		return mav;
	}

	@RequestMapping(value = { "/Insert" }, method = RequestMethod.POST)
	public ModelAndView BoardCommentInsert(@RequestParam Integer boardId,@RequestParam String boardTitle,@RequestParam String boardComment,ModelAndView mav) {





		System.out.println(boardComment);


		mav.addObject("boardId",boardId);
		mav.addObject("boardTitle",boardTitle);
		mav.setViewName("BordComment");


		return mav;
	}


}
