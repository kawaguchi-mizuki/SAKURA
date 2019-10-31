package cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.LoginInfoDto;
import cats.dto.ProfileDto;
import cats.entity.CourseTblEntity;
import cats.entity.HobbyTblEntity;
import cats.entity.SchoolTblEntity;
import cats.entity.StudentTblEntity;
import cats.repository.CourseRepository;
import cats.repository.HobbyRepository;
import cats.repository.SchoolRepository;
import cats.repository.StudentRepository;


@Service
public class ProfileService {


	@Autowired
	StudentRepository studentRepository;

	@Autowired
	HobbyRepository hobbyRepository;

	@Autowired
	SchoolRepository schoolRepository;

	@Autowired
	CourseRepository courseRepository;

	public ProfileDto getDisplayBoard(LoginInfoDto loginInfo) {

		ProfileDto dto = new ProfileDto();

		StudentTblEntity studentTblEntity;
		HobbyTblEntity hobbyTblEntity;
		SchoolTblEntity schoolTblEntity;
		CourseTblEntity courseTblEntity;


		studentTblEntity = studentRepository.getProfile(loginInfo.getStudentId());

		dto.setHobbyId(studentTblEntity.getHobbyId());
		dto.setSchoolId(studentTblEntity.getSchoolId());
		dto.setCourseId(studentTblEntity.getCourseId());


		hobbyTblEntity = hobbyRepository.getHobby(dto.getHobbyId());
		schoolTblEntity = schoolRepository.getSchool(dto.getSchoolId());
		courseTblEntity = courseRepository.getCourseSelect(dto.getCourseId());

		dto.setStudentName(studentTblEntity.getStudentName());
		dto.setStudentSex(studentTblEntity.getStudentSex());
		dto.setHobbyName(hobbyTblEntity.gethobbyName());
		dto.setSchoolName(schoolTblEntity.getschoolName());
		dto.setCourseName(courseTblEntity.getcourseName());
		dto.setGrade(studentTblEntity.getGrade());
		dto.setAge(studentTblEntity.getAge());
		dto.setBirthplace(studentTblEntity.getBirthplace());
		dto.setSelfIntroduction(studentTblEntity.getSelfIntroduction());
		dto.setPassword(studentTblEntity.getPassword());



		return dto;

	}



}
