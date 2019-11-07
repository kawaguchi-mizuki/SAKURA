package cats.service;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.beans.StudentBeans;
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

	public void ProfileDelete(Integer studentId) {

		this.studentRepository.deleteById(studentId);

	}

	public ProfileDto updateProfile(@Valid StudentBeans studentbeans, String password) {

		ProfileDto dto = new ProfileDto();

		StudentTblEntity studentTblEntity;
		HobbyTblEntity hobbyTblEntity;
		SchoolTblEntity schoolTblEntity;
		CourseTblEntity courseTblEntity;

		hobbyTblEntity = hobbyRepository.getHobby(studentbeans.getHobbyId());
		schoolTblEntity = schoolRepository.getSchool(studentbeans.getSchoolId());
		courseTblEntity = courseRepository.getCourseSelect(studentbeans.getCourseId());

		//本来は、ログインインフォから取得する
		dto.setStudentId(1701129);

		dto.setStudentName(studentbeans.getName());
		dto.setStudentSex(studentbeans.getSex());
		dto.setHobbyId(studentbeans.getHobbyId());
		dto.setHobbyName(hobbyTblEntity.gethobbyName());
		dto.setSchoolId(studentbeans.getSchoolId());
		dto.setSchoolName(schoolTblEntity.getschoolName());
		dto.setCourseId(studentbeans.getCourseId());
		dto.setCourseName(courseTblEntity.getcourseName());
		dto.setGrade(studentbeans.getGrade());
		dto.setAge(studentbeans.getAge());
		dto.setBirthplace(studentbeans.getBirthplace());
		dto.setSelfIntroduction(studentbeans.getIntroduction());
		dto.setPassword(password);

		studentTblEntity = updateUserTblEntityFromDto(dto);

		studentRepository.saveAndFlush(studentTblEntity);


		return dto;
	}

	private StudentTblEntity updateUserTblEntityFromDto(ProfileDto dto) {
		StudentTblEntity entity = new StudentTblEntity();

		entity.setStudentId(dto.getStudentId());
		entity.setStudentName(dto.getStudentName());
		entity.setStudentSex(dto.getStudentSex());
		entity.setHobbyId(dto.getHobbyId());
		entity.setSchoolId(dto.getSchoolId());
		entity.setCourseId(dto.getCourseId());
		entity.setGrade(dto.getGrade());
		entity.setAge(dto.getAge());
		entity.setBirthplace(dto.getBirthplace());
		entity.setSelfIntroduction(dto.getSelfIntroduction());
		entity.setPassword(dto.getPassword());
		return entity;
	}




}
