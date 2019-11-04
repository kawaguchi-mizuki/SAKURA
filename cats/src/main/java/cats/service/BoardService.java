package cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.CreateBoardDto;
import cats.entity.BoardTblEntity;
import cats.repository.BoardRepository;


@Service
public class BoardService {
	@Autowired
	BoardRepository boardRepository;

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



}
