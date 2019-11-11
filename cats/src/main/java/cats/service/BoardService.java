package cats.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.BoardListDto;
import cats.dto.CreateBoardDto;
import cats.entity.BoardTblEntity;
import cats.entity.CategoryTblEntity;
import cats.entity.StudentTblEntity;
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

	public void insert(CreateBoardDto dto) {

		//dto -> entity
		BoardTblEntity entity = createBoardTblEntityFromDto(dto);

		System.out.println(entity);

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

	public List<BoardListDto> getAllList() {

		List<BoardListDto> list = new ArrayList<BoardListDto>();

		List<BoardTblEntity> boardList = boardRepository.findAll();

		//entity -> DTO
		for( BoardTblEntity entity : boardList ) {
			BoardListDto dto = new BoardListDto();

			dto.setBoardId(entity.getBoardId());
			dto.setStudentId(entity.getStudentId());
			dto.setBoardTitle(entity.getBoardTitle());

			StudentTblEntity studentTblEntity;
			studentTblEntity = studentRepository.getId(dto.getStudentId());
			dto.setStudentName(studentTblEntity.getStudentName());

			dto.setCategoryId(entity.getCategoryId());
			CategoryTblEntity categoryTblEntity;
			categoryTblEntity = categoryRepository.getId(dto.getCategoryId());
			dto.setCategoryName(categoryTblEntity.getcategoryName());
			dto.setBoardDate(entity.getBoardDate());

			list.add(dto);
		}
		return list;
	}



}
