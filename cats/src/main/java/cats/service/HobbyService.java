package cats.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.HobbyDto;
import cats.entity.HobbyTblEntity;
import cats.repository.HobbyRepository;

@Service
public class HobbyService {

	@Autowired
	HobbyRepository hobbyRepository;

	/**
	 * 趣味一覧を取得する
	 * @return
	 */
	public List<HobbyDto> getAllList(){

		List<HobbyDto> list = new ArrayList<HobbyDto>();

		List<HobbyTblEntity> hobbyList = hobbyRepository.findAll();

		//entity -> DTO
				for( HobbyTblEntity entity : hobbyList ) {
					HobbyDto dto = new HobbyDto();

					dto.setHobbyId(entity.gethobbyId());
					dto.setHobbyName(entity.gethobbyName());

					list.add(dto);
				}

		return list;
	}

	public int getHobbyId(String hobbyName) {

		int hobbyId =  hobbyRepository.getHobbyId(hobbyName);

		return hobbyId;
	}




}
