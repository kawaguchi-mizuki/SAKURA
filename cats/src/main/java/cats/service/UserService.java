package cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.CreateUserDto;
import cats.entity.StudentTblEntity;
import cats.repository.UserRepository;



@Service
public class UserService {
	@Autowired
	UserRepository userRepository;

	/**
	 * 登録処理
	 *
	 * @param userdto
	 * @throws AsoBbsSystemErrException
	 */
	public void insert(CreateUserDto userdto) {

		//dto -> entity
				StudentTblEntity entity = createUserTblEntityFromDto(userdto);

				System.out.println(entity);

				userRepository.saveAndFlush(entity);




	}

	private StudentTblEntity createUserTblEntityFromDto(CreateUserDto userdto) {

		StudentTblEntity entity = new StudentTblEntity();

		entity.setStudentId(userdto.getStudentId());
		entity.setStudentName(userdto.getStudentName());
		entity.setStudentSex(userdto.getStudentSex());
		entity.setHobbyId(userdto.getHobbyId());
		entity.setSchoolName(userdto.getSchoolName());
		entity.setCourse(userdto.getCourse());
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
