package cats.service;

import static cats.repository.TalkSpecification.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cats.dto.LoginInfoDto;
import cats.dto.TalkSelectDto;
import cats.entity.StudentTblEntity;
import cats.entity.TalkTblEntity;
import cats.param.SessionConst;
import cats.repository.HomeRequestRepository;
import cats.repository.StudentRepository;
import cats.repository.TalkRepository;



@Service
public class TalkService {

	@Autowired
	TalkRepository talkRepository;

	@Autowired
	HomeRequestRepository requestRepository;

	@Autowired
	StudentRepository studentRepository;

	@Autowired
	HttpSession session;

	/**トーク一覧を取得する
	 * @return
	 */
	public List<TalkSelectDto> getAllList() {

		List<TalkSelectDto> list = new ArrayList<TalkSelectDto>();

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);



		List<TalkTblEntity> talkList = talkRepository.findAll(Specification.where(IdEqules(loginInfo.getStudentId()).or(RIdEqules(loginInfo.getStudentId()))),new Sort(Sort.Direction.DESC,"TalkId"));


		//entity -> DTO
				for(TalkTblEntity entity : talkList) {
					TalkSelectDto dto = new TalkSelectDto();

					dto.setTalkId(entity.getTalkId());
					dto.setStudentIdSent(entity.getStudentIdSent());
					dto.setStudentIdReceive(entity.getStudentIdReceive());

					StudentTblEntity studentEntity;

					studentEntity = studentRepository.getOne(dto.getStudentIdReceive());

					dto.setStudentName(studentEntity.getStudentName());

					list.add(dto);
				}


		return list;
	}

	public void getCreateTalk(Integer approvalId) {

		TalkTblEntity entity = new TalkTblEntity();

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		entity.setStudentIdSent(loginInfo.getStudentId());
		entity.setStudentIdReceive(approvalId);

		talkRepository.save(entity);

	}




}
