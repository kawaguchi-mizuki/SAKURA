package cats.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.entity.AuthenticationEntity;
import cats.repository.AuthenticationRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	AuthenticationRepository authrepository;
	
	public void insert(int studentId,String pass) {
		
		
		
		AuthenticationEntity entity = authInsert(studentId,pass);
		
		authrepository.saveAndFlush(entity);
		
	}
	
	private AuthenticationEntity authInsert(int studentId, String pass) {
		
		AuthenticationEntity entity = new AuthenticationEntity();
		
		entity.setStudentId(studentId);
		entity.setPass(pass);
		
		
		return entity;
	}
	

}
