package cats.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.beans.BoardBeans;
import cats.dto.BoardListDto;
import cats.dto.CategoryDto;
import cats.dto.CreateBoardDto;
import cats.dto.LoginInfoDto;
import cats.param.SessionConst;
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


	/**掲示板投稿画面表示
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Create" }, method = RequestMethod.GET)
	public ModelAndView BoardCreate(ModelAndView mav) {

		//カテゴリ一覧を取得
		List<CategoryDto> categorylist = categoryService.getAllList();

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();



		mav.addObject("point",point);
		mav.setViewName("BordCreate");
		mav.addObject("categorylist", categorylist);

		return mav;
	}

	private String PointCheck(int point) {

		String ErrMsg = "";

		if(point<50) {

			ErrMsg = "ポイントが足りていません";

		}

		return ErrMsg;

	}

	/**掲示板表示
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Read" }, method = RequestMethod.GET)
	public ModelAndView BoardRead(ModelAndView mav) {


		//カテゴリ一覧を取得
		List<CategoryDto> categorylist = categoryService.getAllList();

		//掲示板のリストを取得
		List<BoardListDto> boardlist = new ArrayList<BoardListDto>();
		try {
			boardlist = boardService.getAllList();
		} catch (Exception e) {

		}

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("studentId",loginInfo.getStudentId());
		mav.addObject("categorylist", categorylist);
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

		dto = getCreateBoardDto(boardbeans);

		//掲示板作成処理
		boardService.insert(dto);

		List<BoardListDto> boardlist = new ArrayList<BoardListDto>();
		try {
			boardlist = boardService.getAllList();
		} catch (Exception e) {

		}

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		String ErrMsg;

		 ErrMsg = PointCheck(point);

		 //ポイントチェック
		 if(!(ErrMsg.equals(""))){


				//カテゴリ一覧を取得
				List<CategoryDto> categorylist = categoryService.getAllList();

				mav.addObject("ErrMsg",ErrMsg);
				mav.addObject("point",point);
				mav.setViewName("BordCreate");
				mav.addObject("categorylist", categorylist);

				return mav;

		 }





		loginInfo = boardService.boardPoint(point);

		session.setAttribute(SessionConst.LOGININFO, loginInfo);





		mav.addObject("point",loginInfo.getPoint());
		mav.addObject("studentId",loginInfo.getStudentId());
		mav.addObject("boardlist",boardlist);
		mav.setViewName("Bord");

		return mav;
	}

	/**掲示板削除
	 * @param boardId
	 * @param mav
	 * @return
	 */
	@RequestMapping(value = { "/Delete" }, method = RequestMethod.GET)
	public ModelAndView BoardDelete(@RequestParam("boardId") Integer boardId, ModelAndView mav) {


		boardService.BoardDelete(boardId);

		List<BoardListDto> boardlist = new ArrayList<BoardListDto>();

		//カテゴリ一覧を取得
		List<CategoryDto> categorylist = categoryService.getAllList();

		try {
			boardlist = boardService.getAllList();
		} catch (Exception e) {

		}
		mav.addObject("boardlist",boardlist);

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();



		mav.addObject("categorylist", categorylist);
		mav.addObject("point",point);
		mav.addObject("studentId",loginInfo.getStudentId());
		mav.setViewName("Bord");

		return mav;
	}

	@RequestMapping(value = { "/Select" }, method = RequestMethod.GET)
	public ModelAndView BoardSelect(@RequestParam Integer categoryId,@RequestParam String sex, ModelAndView mav) {


		//掲示板のリストを取得
		List<BoardListDto> boardlist = new ArrayList<BoardListDto>();

		//カテゴリ一覧を取得
		List<CategoryDto> categorylist = categoryService.getAllList();

		int flag;

		if(sex.equals("男"))
			flag = 1;
		else if(sex.equals("女"))
			flag = 2;
		else
			flag = 0;


		//全て表示
		if(categoryId==0&&sex.equals("全て")) {

			try {
				boardlist = boardService.getAllList();
			} catch (Exception e) {

			}

		}else if(categoryId!=0&&sex.equals("全て")) {

			try {
				boardlist = boardService.getCategorySelect(categoryId);
				mav.addObject("categoryId",categoryId);
			} catch (Exception e) {

			}

		}else if(categoryId==0&&(!(sex.equals("全て")))){

			try {
				boardlist = boardService.getSexSelect(sex);
				mav.addObject("flag",flag);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}else {

			try {
				boardlist = boardService.getSelectList(categoryId,sex);
				mav.addObject("categoryId",categoryId);
				mav.addObject("flag",flag);
			} catch (Exception e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			}

		}


		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		//ポイント反映
		int point = loginInfo.getPoint();

		mav.addObject("point",point);
		mav.addObject("studentId",loginInfo.getStudentId());
		mav.addObject("categorylist", categorylist);
		mav.addObject("boardlist",boardlist);
		mav.setViewName("Bord");

		return mav;
	}


	private CreateBoardDto getCreateBoardDto(@Valid BoardBeans boardbeans) {

		CreateBoardDto dto = new CreateBoardDto();

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		dto.setStudentId(loginInfo.getStudentId());
		dto.setBoardTitle(boardbeans.getBoardTitle());
		dto.setCategoryId(boardbeans.getCategoryId());
		Date date = new Date();

		dto.setBoardDate(date);

		return dto;
	}



}
