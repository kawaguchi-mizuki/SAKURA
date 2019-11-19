package cats.service;

import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cats.dto.AuthSearchDto;
import cats.dto.AuthenticationDto;
import cats.entity.AuthSearchEntity;
import cats.entity.AuthenticationEntity;
import cats.repository.AuthSearchRepository;
import cats.repository.AuthenticationRepository;

@Service
@Transactional
public class AuthenticationService {
	
	@Autowired
	AuthenticationRepository authRepository;
	
	@Autowired
	AuthSearchRepository authSearchRepository;
	
	
	
	public void insert(int studentId,String pass,Date NowDate) {
		
		//登録
		
		AuthenticationEntity entity = new AuthenticationEntity();
		
		entity.setStudent_id(studentId);
		entity.setOne_pass(pass);
		entity.setTime_limit(NowDate);
		
		 authRepository.save(entity);
		
		
		
	}

	public  AuthenticationDto apptova(Integer student_id) {
		
		
		AuthenticationDto dto = new AuthenticationDto();
		
		AuthenticationEntity authEntity;
		
		authEntity = authRepository.getPass(student_id);
		
		dto.setStudentId(authEntity.getStudent_id());
		dto.setPass(authEntity.getOne_pass());
		dto.setNowDate(authEntity.getTime_limit());
		
		
		return dto;	 
	}

	public AuthSearchDto search(Integer studentId) {
		// TODO Auto-generated method stub
		
		AuthSearchEntity authEntity;
		
		AuthSearchDto dto = null;
		
		authEntity = authSearchRepository.searchstudentId(studentId);
		
		if(authEntity != null ) {
			dto = new AuthSearchDto();
			dto.setStudentId(authEntity.getStudent_id());
		}
		
		return dto;
	}
	

}
