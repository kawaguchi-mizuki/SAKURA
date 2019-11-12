package cats.service;

import static cats.repository.BoardSpecification.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cats.dto.BoardCommentDto;
import cats.dto.LoginInfoDto;
import cats.entity.BoardCommentTblEntity;
import cats.entity.StudentTblEntity;
import cats.param.SessionConst;
import cats.repository.BoardCommentRepository;
import cats.repository.StudentRepository;

@Service
public class BoardCommentService {
	@Autowired
	BoardCommentRepository boardCommentRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	HttpSession session;



	public BoardCommentDto insert(BoardCommentDto dto) {

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		StudentTblEntity studentEntity;

		studentEntity = studentRepository.getId(loginInfo.getStudentId());

		dto.setCommentName(studentEntity.getStudentName());


		BoardCommentTblEntity entity = createBoardComment(dto);

		System.out.println(entity);


		boardCommentRepository.save(entity);


		return dto;
	}



	private BoardCommentTblEntity createBoardComment(BoardCommentDto dto) {

		BoardCommentTblEntity entity = new BoardCommentTblEntity();

		entity.setBoardId(dto.getBoardId());
		entity.setBoardCommentId(dto.getCommentId());
		entity.setBoardComment(dto.getComment());
		entity.setBoardCommentName(dto.getCommentName());

		return entity;
	}



	public List<BoardCommentDto> getAllList(Integer boardId) {

		List<BoardCommentDto> list = new ArrayList<BoardCommentDto>();

		List<BoardCommentTblEntity> commentList = boardCommentRepository.findAll(Specification.where(IdEqules(boardId)) ,new Sort(Sort.Direction.DESC,"boardCommentId"));

		System.out.println(commentList);

		//entity -> DTO
				for( BoardCommentTblEntity entity : commentList ) {

					BoardCommentDto dto = new BoardCommentDto();

					dto.setCommentId(entity.getBoardCommentId());
					dto.setBoardId(entity.getBoardId());
					dto.setComment(entity.getBoardComment());
					dto.setCommentName(entity.getBoardCommentName());

					list.add(dto);
				}


		return list;
	}



}
