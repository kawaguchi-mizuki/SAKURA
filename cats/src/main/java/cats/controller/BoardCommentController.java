package cats.controller;

import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.beans.BoardCommentBeans;
import cats.dto.BoardCommentDto;
import cats.service.BoardCommentService;

//掲示板
@RestController
@RequestMapping(value = { "/BoardComment" })
public class BoardCommentController {

	@Autowired
	BoardCommentService boardcommnetService;

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


		BoardCommentBeans boardcommentbeans = new BoardCommentBeans();

		List<BoardCommentDto> commentlist = boardcommnetService.getAllList(boardId);


		mav.addObject("commentlist",commentlist);
		mav.addObject("boardcommentbeans",boardcommentbeans);
		mav.addObject("boardId",boardId);
		mav.addObject("boardTitle",boardTitle);
		mav.setViewName("BordComment");


		return mav;
	}

	@RequestMapping(value = { "/Insert" }, method = RequestMethod.POST)
	public ModelAndView BoardCommentInsert(@Valid BoardCommentBeans boardcommetbeans,@RequestParam String boardTitle, ModelAndView mav) {

		BoardCommentDto dto = new BoardCommentDto();

		dto = getCreateComment(boardcommetbeans);


		dto = boardcommnetService.insert(dto);


		List<BoardCommentDto> commentlist = boardcommnetService.getAllList(dto.getBoardId());


		mav.addObject("commentlist",commentlist);

		int boardId = dto.getBoardId();



		mav.addObject("boardId",boardId);
		mav.addObject("boardTitle",boardTitle);
		mav.setViewName("BordComment");


		return mav;
	}

	private BoardCommentDto getCreateComment( @Valid BoardCommentBeans boardcommetbeans) {

		BoardCommentDto dto = new BoardCommentDto();

		dto.setBoardId(boardcommetbeans.getBoardId());
		dto.setComment(boardcommetbeans.getBoardComment());

		return dto;
	}




}
