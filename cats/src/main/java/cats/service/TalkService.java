package cats.service;

import static cats.repository.TalkSpecification.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cats.config.AppSettingProperty;
import cats.dto.LoginInfoDto;
import cats.dto.TalkSelectDto;
import cats.entity.MessageTblEntity;
import cats.entity.StudentTblEntity;
import cats.entity.TalkTblEntity;
import cats.param.SessionConst;
import cats.repository.HomeRequestRepository;
import cats.repository.MessageRepository;
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
	MessageRepository messageRepository;

	@Autowired
	HttpSession session;

	/**トーク一覧を取得する
	 * @return
	 * @throws Exception
	 */
	public List<TalkSelectDto> getAllList() throws Exception {

		List<TalkSelectDto> list = new ArrayList<TalkSelectDto>();

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);



		List<TalkTblEntity> talkList = talkRepository.findAll(Specification.where(IdEqules(loginInfo.getStudentId()).or(RIdEqules(loginInfo.getStudentId()))),new Sort(Sort.Direction.DESC,"TalkId"));

		String imagepath = AppSettingProperty.getInstance().getCatsProfileImgPrefix();

		//entity -> DTO
				for(TalkTblEntity entity : talkList) {
					TalkSelectDto dto = new TalkSelectDto();

					dto.setTalkId(entity.getTalkId());
					dto.setStudentIdSend(entity.getStudentIdSent());
					dto.setStudentIdReceive(entity.getStudentIdReceive());

					StudentTblEntity studentEntity;
					StudentTblEntity sendstudentEntity;
					MessageTblEntity messageEntity = new MessageTblEntity();


					studentEntity = studentRepository.getOne(dto.getStudentIdReceive());

					dto.setStudentReceiveName(studentEntity.getStudentName());
					dto.setReceiveImagePath(imagepath+"/"+studentEntity.getImagePass());

					sendstudentEntity = studentRepository.getOne(dto.getStudentIdSend());

					dto.setStudentSendName(sendstudentEntity.getStudentName());
					dto.setSendImagePath(imagepath+"/"+sendstudentEntity.getImagePass());

					String LastMessage = messageRepository.getLastMessage(dto.getTalkId());

					System.out.println(dto.getTalkId());

					dto.setLastMessage(LastMessage);

					System.out.println(LastMessage);


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

	public TalkSelectDto getTalk(Integer talkId) {

		TalkSelectDto dto = new TalkSelectDto();

		TalkTblEntity entity = talkRepository.getOne(talkId);

		dto.setTalkId(entity.getTalkId());
		dto.setStudentIdSend(entity.getStudentIdSent());
		dto.setStudentIdReceive(entity.getStudentIdReceive());

		StudentTblEntity studentEntity;
		StudentTblEntity sendstudentEntity;


		studentEntity = studentRepository.getOne(dto.getStudentIdReceive());

		dto.setStudentReceiveName(studentEntity.getStudentName());

		sendstudentEntity = studentRepository.getOne(dto.getStudentIdSend());

		dto.setStudentSendName(sendstudentEntity.getStudentName());

		return dto;
	}

	public void deleteTalk(Integer talkId) {

		talkRepository.deleteById(talkId);

	}




}
