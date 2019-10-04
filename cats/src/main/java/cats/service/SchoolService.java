package cats.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.SchoolDto;
import cats.entity.SchoolTblEntity;
import cats.repository.SchoolRepository;

@Service
public class SchoolService {

	@Autowired
	SchoolRepository schoolRepository;

	/**
	 * 学校一覧を取得する
	 * @return
	 */
	public List<SchoolDto> getAllList(){


		List<SchoolDto> list = new ArrayList<SchoolDto>();

		List<SchoolTblEntity> schoolList = schoolRepository.findAll();

		//entity -> DTO
		for( SchoolTblEntity entity : schoolList ) {
			SchoolDto dto = new SchoolDto();

			dto.setSchoolId(entity.getschoolId());
			dto.setSchoolName(entity.getschoolName());

			list.add(dto);
		}

		return list;
	}

}