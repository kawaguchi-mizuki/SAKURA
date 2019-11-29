package cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.CreateUserDto;
import cats.entity.StudentTblEntity;
import cats.repository.StudentRepository;



@Service
public class StudentService {
	@Autowired
	StudentRepository studentRepository;

	/**
	 * 登録処理
	 *
	 * @param userdto
	 * @throws AsoBbsSystemErrException
	 */
	public void insert(CreateUserDto userdto) {

		//dto -> entity
				StudentTblEntity entity = createUserTblEntityFromDto(userdto);

				studentRepository.saveAndFlush(entity);

	}

	private StudentTblEntity createUserTblEntityFromDto(CreateUserDto userdto) {

		StudentTblEntity entity = new StudentTblEntity();

		entity.setStudentId(userdto.getStudentId());
		entity.setStudentName(userdto.getStudentName());
		entity.setStudentSex(userdto.getStudentSex());
		entity.setHobbyId(userdto.getHobbyId());
		entity.setSchoolId(userdto.getSchoolId());
		entity.setCourseId(userdto.getCourseId());
		entity.setGrade(userdto.getGrade());
		entity.setAge(userdto.getAge());
		entity.setBirthplace(userdto.getBirthplace());
		entity.setSelfIntroduction(userdto.getSelfIntroduction());
		entity.setPassword(userdto.getPassword());
		entity.setImagePass(userdto.getImagePass());
		entity.setPoint(userdto.getPoint());
		entity.setLastLog(userdto.getLastLog());
		entity.setContinuousLogin(userdto.getContinuousLogin());


		return entity;
	}


}
