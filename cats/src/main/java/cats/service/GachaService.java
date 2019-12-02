package cats.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cats.dto.CreateUserDto;
import cats.dto.GachaDto;
import cats.dto.LoginInfoDto;
import cats.dto.RequestDto;
import cats.entity.RequestTblEntity;
import cats.entity.StudentTblEntity;
import cats.param.SessionConst;
import cats.repository.GachaRepository;
import cats.repository.RequestRepository;
import cats.repository.StudentRepository;

@Service
public class GachaService {
	
	@Autowired
	private GachaRepository gachaRepository;
	
	@Autowired
	private RequestRepository requestRepository;
	
	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	HttpSession session;
		
	
	/**
	 * ユーザー情報を取得
	 * 
	 * @param number
	 * @param password
	 * @return 
	 * @throws Exception
	 * 
	 */
	public GachaDto getGacha(Integer studentId,Integer hobbyId)throws Exception {
		GachaDto dto = null;
		
		StudentTblEntity entity = gachaRepository.getGachaUser(studentId);
		
			//entity -> dto
			dto = new GachaDto();
			
			dto.setStudentId(entity.getStudentId());
			dto.setStudentSex(entity.getStudentSex());
			dto.setHobbyId(entity.getHobbyId());
			//	選択した趣味Id
			dto.setHobbyIdSearch(hobbyId);
			
			System.out.println(dto.getStudentId());
			System.out.println(dto.getStudentSex());
			System.out.println(hobbyId);
			
		return dto;
	}
	/**
	 * 趣味・性別で学生番号リストを取得
	 * 
	 * @param dto
	 * @return 
	 * @throws Exception
	 * 
	 */
	public RequestDto getGachaList(Integer studentId,Integer hobbyIdSearch,String sex) throws Exception{
		
		int value = 0;
		int index = 0;
		RequestDto dto = null;
		dto = new RequestDto();
		Random random = new Random();
		
		List<CreateUserDto> list = new ArrayList<CreateUserDto>();
		
		
		List<StudentTblEntity> userlist = gachaRepository.getAllList(studentId,hobbyIdSearch,sex);
	
			for(StudentTblEntity entity : userlist) {	
				CreateUserDto set = new CreateUserDto();
				
				set.setStudentId(entity.getStudentId());
				set.setHobbyId(entity.getHobbyId());
				set.setStudentSex(entity.getStudentSex());
				list.add(set);
			}
		System.out.println(list.size());
		if(list.size() != 0) {
		
			index = random.nextInt(list.size());
			CreateUserDto dro = list.get(index);
		
			dto.setStudentIdSent(studentId);
			dto.setStudentIdReceive(dro.getStudentId());
			dto.setApproval(value);
			return dto;
		}else {
			
			return dto;
		}
	}
	
	/**
	 * 趣味と一致する学籍番号をリクエストへ
	 * 
	 * @param dto
	 * @return 
	 * @throws Exception
	 * 
	 */
	public void gachaInsert(RequestDto dto) throws Exception{
		
		RequestTblEntity entity = new RequestTblEntity();

		entity.setStudentIdSent(dto.getStudentIdSent());
		entity.setStudentIdReceive(dto.getStudentIdReceive());
		entity.setApproval(dto.getApproval());

		requestRepository.save(entity);
	}
	
	/**
	 * ガチャポイント処理
	 *
	 * @throws Exception
	 * 
	 */
	
	public LoginInfoDto gachaPoint(Integer point){
		//	ガチャ消費ポイント
		int gachapoint = 100;
		//	ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);
		StudentTblEntity entity = gachaRepository.getGachaUser(loginInfo.getStudentId());
		
		point = point-gachapoint;
		
		entity.setPoint(point);
		studentRepository.save(entity);
		
		LoginInfoDto dto = new LoginInfoDto();

		dto.setLastLog(entity.getLastLog());
		dto.setPoint(entity.getPoint());
		dto.setStudentId(entity.getStudentId());

		//session.invalidate();

		return dto;
	}
		
	public int gachaMissPoint(Integer point){
		Random random = new Random();
		int index = 0;
		int hako = 0;
		//	リスト0 ガチャテーブル
		int[] gachapoint = {0,0,0,0,0,0,50,50,50,300};
		//	ユーザー情報をセッションから取得
		LoginInfoDto loginInfo = (LoginInfoDto)session.getAttribute(SessionConst.LOGININFO);
		StudentTblEntity entity = gachaRepository.getGachaUser(loginInfo.getStudentId());
		
		index = random.nextInt(gachapoint.length);
		hako = gachapoint[index];
		point = point+hako;
		
		entity.setPoint(point);
		studentRepository.save(entity);
		
		LoginInfoDto dto = new LoginInfoDto();

		dto.setLastLog(entity.getLastLog());
		dto.setPoint(entity.getPoint());
		dto.setStudentId(entity.getStudentId());

		return hako;
	}
}
