package cats.service;

import static cats.repository.MessageSpecification.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import cats.dto.MessageDto;
import cats.entity.MessageTblEntity;
import cats.repository.MessageRepository;

@Service
public class MessageService {

	@Autowired
	HttpSession session;

	@Autowired
	MessageRepository messageRepository;


	public void insert(MessageDto dto) {

		//dto -> entity
		MessageTblEntity entity = createMessageDto(dto);

		messageRepository.saveAndFlush(entity);
	}


	private MessageTblEntity createMessageDto(MessageDto dto) {

		MessageTblEntity entity = new MessageTblEntity();

		entity.setTalkId(dto.getTalkId());
		entity.setMessage(dto.getMessage());
		entity.setSendTime(dto.getSendTime());
		entity.setStudentIdSend(dto.getStudentIdSend());

		return entity;
	}


	public List<MessageDto> getAllList(Integer talkId) {

		List<MessageDto> list = new ArrayList<MessageDto>();

		List<MessageTblEntity> messageList = messageRepository.findAll(Specification.where(IdEqules(talkId)));


		//entity -> DTO
		for(MessageTblEntity entity : messageList) {
			MessageDto dto = new MessageDto();

			dto.setTalkId(entity.getTalkId());
			dto.setMessageId(entity.getMessageId());
			dto.setMessage(entity.getMessage());
			dto.setSendTime(entity.getSendTime());
			dto.setStudentIdSend(entity.getStudentIdSend());

			list.add(dto);
		}


		return list;
	}

}
