package cats.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.entity.AuthenticationEntity;
import cats.repository.AuthenticationRepository;

@Service
public class AuthenticationService {
	
	@Autowired
	AuthenticationRepository authrepository;
	
	public void insert(int studentId,String pass,Date NowDate) {
		
		
		
		AuthenticationEntity entity = authInsert(studentId,pass,NowDate);
		
		authrepository.saveAndFlush(entity);
		
	}
	
	private AuthenticationEntity authInsert(int studentId, String pass, Date NowDate) {
		
		AuthenticationEntity entity = new AuthenticationEntity();
		
		entity.setStudentId(studentId);
		entity.setPass(pass);
		entity.setNowDate(NowDate);
		
		
		return entity;
	}

	public void apptova(String pass2) {
		
		 
		
		
	}
	

}
