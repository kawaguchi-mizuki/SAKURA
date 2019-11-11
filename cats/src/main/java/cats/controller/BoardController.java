package cats.controller;


import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.beans.BoardBeans;
import cats.dto.BoardListDto;
import cats.dto.CategoryDto;
import cats.dto.CreateBoardDto;
import cats.service.BoardService;
import cats.service.CategoryService;

//掲示板
@RestController
@RequestMapping(value = { "/Board" })
public class BoardController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	BoardService boardService;

	@Autowired
	HttpSession session;

	@RequestMapping(value = { "/Create" }, method = RequestMethod.GET)
	public ModelAndView BoardCreate(ModelAndView mav) {

		//カテゴリ一覧を取得
		List<CategoryDto> categorylist = categoryService.getAllList();

		mav.setViewName("BordCreate");
		mav.addObject("categorylist", categorylist);


		return mav;
	}

	@RequestMapping(value = { "/Read" }, method = RequestMethod.GET)
	public ModelAndView BoardRead(ModelAndView mav) {

		List<BoardListDto> boardlist = boardService.getAllList();

		System.out.println(boardlist);

		mav.addObject("boardlist",boardlist);
		mav.setViewName("Bord");

		return mav;
	}


	/**掲示板作成
	 * @param boardbeans
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Insert" }, method = RequestMethod.GET)
	public ModelAndView BoardInsert(@Valid BoardBeans boardbeans,ModelAndView mav) {


		CreateBoardDto dto = new CreateBoardDto();

		//ログイン情報から学籍番号を取得、格納する処理を書く

		dto = getCreateBoardDto(boardbeans);

			//掲示板作成
		boardService.insert(dto);

		List<BoardListDto> boardlist = boardService.getAllList();
		mav.addObject("boardlist",boardlist);
		mav.setViewName("Bord");

		return mav;
	}



	private CreateBoardDto getCreateBoardDto(@Valid BoardBeans boardbeans) {

		CreateBoardDto dto = new CreateBoardDto();

		dto.setStudentId(1701129);
		dto.setBoardTitle(boardbeans.getBoardTitle());
		dto.setCategoryId(boardbeans.getCategoryId());
		Date date = new Date();

		dto.setBoardDate(date);



		return dto;
	}


}
