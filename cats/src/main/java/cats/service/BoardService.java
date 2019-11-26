package cats.service;

import static cats.repository.BoardSpecification.*;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cats.config.AppSettingProperty;
import cats.dto.BoardListDto;
import cats.dto.CreateBoardDto;
import cats.dto.LoginInfoDto;
import cats.entity.BoardTblEntity;
import cats.entity.CategoryTblEntity;
import cats.entity.StudentTblEntity;
import cats.param.SessionConst;
import cats.repository.BoardRepository;
import cats.repository.CategoryRepository;
import cats.repository.StudentRepository;


@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	HttpSession session;

	public void insert(CreateBoardDto dto) {

		//dto -> entity
		BoardTblEntity entity = createBoardTblEntityFromDto(dto);


		boardRepository.saveAndFlush(entity);

	}

	private BoardTblEntity createBoardTblEntityFromDto(CreateBoardDto dto) {

		BoardTblEntity entity = new BoardTblEntity();

		entity.setStudentId(dto.getStudentId());
		entity.setCategoryId(dto.getCategoryId());
		entity.setBoardTitle(dto.getBoardTitle());
		entity.setBoardDate(dto.getBoardDate());


		return entity;
	}

	public List<BoardListDto> getAllList() throws Exception{

		List<BoardListDto> list = new ArrayList<BoardListDto>();

		List<BoardTblEntity> boardList = boardRepository.findAll(new Sort(Sort.Direction.DESC,"boardId"));

		String imagepath = AppSettingProperty.getInstance().getCatsProfileImgPrefix();

		//entity -> DTO
		for( BoardTblEntity entity : boardList ) {
			BoardListDto dto = new BoardListDto();

			dto.setBoardId(entity.getBoardId());
			dto.setStudentId(entity.getStudentId());
			dto.setBoardTitle(entity.getBoardTitle());

			StudentTblEntity studentTblEntity;
			studentTblEntity = studentRepository.getId(dto.getStudentId());
			dto.setStudentName(studentTblEntity.getStudentName());
			dto.setSex(studentTblEntity.getStudentSex());

			dto.setImagePath(imagepath+"/"+studentTblEntity.getImagePass());

			dto.setCategoryId(entity.getCategoryId());
			CategoryTblEntity categoryTblEntity;
			categoryTblEntity = categoryRepository.getId(dto.getCategoryId());
			dto.setCategoryName(categoryTblEntity.getcategoryName());

			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			dto.setBoardDate(sdf.format(entity.getBoardDate()));

			list.add(dto);
		}
		return list;
	}

	public void BoardDelete(Integer boardId) {

		this.boardRepository.deleteById(boardId);

	}

	public LoginInfoDto boardPoint(Integer point) {


		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);


		StudentTblEntity entity = studentRepository.getOne(loginInfo.getStudentId());


		point = point - 50;

		entity.setPoint(point);

		studentRepository.save(entity);

		LoginInfoDto dto = new LoginInfoDto();

		dto.setLastLog(entity.getLastLog());
		dto.setPoint(entity.getPoint());
		dto.setStudentId(entity.getStudentId());

		session.invalidate();

		return dto;

	}

	public List<BoardListDto> getCategorySelect(Integer categoryId) throws Exception {

		List<BoardListDto> list = new ArrayList<BoardListDto>();

		List<BoardTblEntity> boardList = boardRepository.findAll(Specification.where(CategorySelect(categoryId)),new Sort(Sort.Direction.DESC,"boardId"));

		String imagepath = AppSettingProperty.getInstance().getCatsProfileImgPrefix();

		//entity -> DTO
		for( BoardTblEntity entity : boardList ) {
			BoardListDto dto = new BoardListDto();

			dto.setBoardId(entity.getBoardId());
			dto.setStudentId(entity.getStudentId());
			dto.setBoardTitle(entity.getBoardTitle());

			StudentTblEntity studentTblEntity;
			studentTblEntity = studentRepository.getId(dto.getStudentId());
			dto.setStudentName(studentTblEntity.getStudentName());
			dto.setSex(studentTblEntity.getStudentSex());

			dto.setImagePath(imagepath+"/"+studentTblEntity.getImagePass());

			dto.setCategoryId(entity.getCategoryId());
			CategoryTblEntity categoryTblEntity;
			categoryTblEntity = categoryRepository.getId(dto.getCategoryId());
			dto.setCategoryName(categoryTblEntity.getcategoryName());

			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

			dto.setBoardDate(sdf.format(entity.getBoardDate()));

			list.add(dto);
		}
		return list;
	}



}
