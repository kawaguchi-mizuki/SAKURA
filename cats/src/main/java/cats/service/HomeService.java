package cats.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.HomeRequestDto;
import cats.dto.LoginInfoDto;
import cats.entity.HomeRequestTblEntity;
import cats.repository.HomeRequestRepository;

@Service
public class HomeService {
	@Autowired
	HomeRequestRepository homeRequestRepository;

	@Autowired
	HttpSession session;

	public List<HomeRequestDto> getAllList(LoginInfoDto loginInfo) {

		List<HomeRequestDto> list = new ArrayList<HomeRequestDto>();

		List<HomeRequestTblEntity> requestList = homeRequestRepository.findAll();

		//entity -> DTO
		for( HomeRequestTblEntity entity : requestList ) {

			HomeRequestDto dto = new HomeRequestDto();

			dto.setRequestId(entity.getRequestId());
			dto.setSentId(entity.getStudentIdSent());
			dto.setReceiveId(entity.getStudentIdTbl());
			dto.setApproval(entity.getApproval());
			dto.setStudentName(entity.getStudentIdTbl().getStudentName());

			list.add(dto);
		}


		return list;
	}



}
