package cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.LoginInfoDto;
import cats.dto.ProfileDto;
import cats.entity.StudentTblEntity;
import cats.repository.ProfileRepository;

@Service
public class ProfileService {

	@Autowired
	ProfileRepository profileRepository;

	public ProfileDto getDisplayBoard(LoginInfoDto loginInfo) {

		ProfileDto dto = new ProfileDto();

		StudentTblEntity studentTblEntity;

		studentTblEntity = profileRepository.getProfile(loginInfo.getStudentId());

		dto.setStudentName(studentTblEntity.getStudentName());
		dto.setStudentSex(studentTblEntity.getStudentSex());


		dto.setGrade(studentTblEntity.getGrade());
		dto.setAge(studentTblEntity.getAge());
		dto.setBirthplace(studentTblEntity.getBirthplace());
		dto.setSelfIntroduction(studentTblEntity.getSelfIntroduction());
		dto.setPassword(studentTblEntity.getPassword());



		return dto;

	}



}
