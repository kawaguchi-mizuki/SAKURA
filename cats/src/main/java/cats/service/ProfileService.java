package cats.service;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.beans.StudentBeans;
import cats.config.AppSettingProperty;
import cats.dto.LoginInfoDto;
import cats.dto.ProfileDto;
import cats.entity.CourseTblEntity;
import cats.entity.HobbyTblEntity;
import cats.entity.SchoolTblEntity;
import cats.entity.StudentTblEntity;
import cats.param.SessionConst;
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

	@Autowired
	HttpSession session;

	public ProfileDto getDisplayBoard(LoginInfoDto loginInfo) throws Exception {

		ProfileDto dto = new ProfileDto();

		String imagepath = AppSettingProperty.getInstance().getCatsProfileImgPrefix();

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
		dto.setImagePath(imagepath+"/"+studentTblEntity.getImagePass());



		return dto;

	}

	public void ProfileDelete(Integer studentId) {

		this.studentRepository.deleteById(studentId);

	}

	public ProfileDto updateProfile(@Valid StudentBeans studentbeans, String password) throws Exception {

		ProfileDto dto = new ProfileDto();

		String imagepath = AppSettingProperty.getInstance().getCatsProfileImgPrefix();

		StudentTblEntity studentTblEntity;
		HobbyTblEntity hobbyTblEntity;
		SchoolTblEntity schoolTblEntity;
		CourseTblEntity courseTblEntity;

		hobbyTblEntity = hobbyRepository.getHobby(studentbeans.getHobbyId());
		schoolTblEntity = schoolRepository.getSchool(studentbeans.getSchoolId());
		courseTblEntity = courseRepository.getCourseSelect(studentbeans.getCourseId());

		//ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);

		studentTblEntity = studentRepository.getstatus(loginInfo.getStudentId());




		dto.setStudentId(loginInfo.getStudentId());

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
		dto.setPoint(studentTblEntity.getPoint());
		dto.setLastlog(studentTblEntity.getLastLog());
		dto.setContinuouslogin(studentTblEntity.getContinuousLogin());
		studentTblEntity = updateUserTblEntityFromDto(dto);





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
		entity.setPoint(dto.getPoint());
		entity.setLastLog(dto.getLastlog());
		entity.setContinuousLogin(dto.getContinuouslogin());
		return entity;
	}

	public ProfileDto getDisplayBoardBrowse(Integer studentId) {
		ProfileDto dto = new ProfileDto();

		StudentTblEntity studentTblEntity;
		HobbyTblEntity hobbyTblEntity;
		SchoolTblEntity schoolTblEntity;
		CourseTblEntity courseTblEntity;


		studentTblEntity = studentRepository.getProfile(studentId);

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
