package cats.service;

import static cats.repository.RequestSpecification.*;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

	/**リクエスト取得
	 * @param loginInfo
	 * @return
	 */
	public List<HomeRequestDto> getAllList(LoginInfoDto loginInfo) {

		List<HomeRequestDto> list = new ArrayList<HomeRequestDto>();

		List<HomeRequestTblEntity> requestList = homeRequestRepository.findAll(Specification.where(studentIdEqules(loginInfo.getStudentId()).and(ApprovalEqules())),new Sort(Sort.Direction.DESC,"requestId"));

		//entity -> DTO
		for( HomeRequestTblEntity entity : requestList ) {

			HomeRequestDto dto = new HomeRequestDto();

			dto.setRequestId(entity.getRequestId());
			dto.setSentId(entity.getStudentIdTbl());
			dto.setReceiveId(entity.getStudentIdReceive());
			dto.setApproval(entity.getApproval());
			dto.setStudentName(entity.getStudentIdTbl().getStudentName());

			list.add(dto);
		}


		return list;
	}

	/**リクエスト承認処理
	 * @param requestId
	 */
	public void approvalId(Integer requestId) {

		HomeRequestTblEntity entity = homeRequestRepository.getOne(requestId);

		entity.setApproval(1);

		homeRequestRepository.save(entity);
	}

	/**リクエスト削除
	 * @param requestId
	 */
	public void delete(Integer requestId) {

		this.homeRequestRepository.deleteById(requestId);

	}



}
