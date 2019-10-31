package cats.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import cats.dto.CategoryDto;
import cats.service.CategoryService;

@RestController
@RequestMapping(value = { "/Board" })
public class BoardController {

	@Autowired
	CategoryService categoryService;

	@Autowired
	HttpSession session;

	@RequestMapping(value = { "/Create" }, method = RequestMethod.GET)
	public ModelAndView UserEntry(ModelAndView mav) {

		//カテゴリ一覧を取得
		List<CategoryDto> categorylist = categoryService.getAllList();

		mav.setViewName("BordCreate");

		mav.addObject("categorylist", categorylist);


		return mav;
	}


}
