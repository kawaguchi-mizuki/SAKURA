package cats.service;



import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cats.dto.LoginDayDto;
import cats.dto.LoginInfoDto;
import cats.entity.StudentTblEntity;
import cats.repository.LoginRepository;
import cats.repository.StudentRepository;

@Service
@Transactional
public class LoginService {
	
	@Autowired
	private LoginRepository loginRepository;
	
	@Autowired
	private StudentRepository StudentRepository;
	
	/**
	 * ログイン処理
	 * 
	 * @param number
	 * @param password
	 * @return 
	 * @throws Exception
	 * 
	 */
	public LoginInfoDto Login(int number,String password)throws Exception {
		LoginInfoDto dto = null;
		
		//	ログイン検索
		StudentTblEntity entity = loginRepository.getlogin(number,password);
		
		if(entity != null) {
			//entity -> dto
			
			dto = new LoginInfoDto();
			
			dto.setStudentId(entity.getStudentId());
			dto.setPoint(entity.getPoint());
			dto.setLastLog(entity.getLastLog());
		}
		return dto;
	}
	
	/**
	 *　過去の日付・連続ログイン情報の取得
	 *　
	 * @param studentId
	 * @return 
	 * @throws Exception
	 * 
	 */
	
	public LoginDayDto LastDay(Integer studentId) {
		 StudentTblEntity entity = null;
		 	//	ユーザ情報取得
			 entity = loginRepository.getloginByStudentId(studentId);
	
			//entity -> dto
				
			LoginDayDto dto = new LoginDayDto();
				
			dto.setLastLog(entity.getLastLog());
			dto.setContinuousLogin(entity.getContinuousLogin());
			
			//System.out.println(dto);
			return dto;
	}
	
	
	/**
	 * 現在日付　更新
	 * 
	 * @param studentId
	 * @param date
	 * @return
	 * @throws Exception
	 * 
	 */
	public void UpdateDate(Integer studentId,Date date)throws Exception{
		//	ユーザ情報取得
		StudentTblEntity entity = loginRepository.getloginByStudentId(studentId);
		
		UpdateDate(entity,date);
	}
	
	private void UpdateDate(StudentTblEntity entity, Date date)throws Exception{
		
		entity.setLastLog(date);

		StudentRepository.save(entity);
		
	}
	
	/**
	 * 連続ログイン　更新
	 * 
	 * @param studentId
	 * @param num
	 * @return
	 * @throws Exception
	 *
	*/
	public void UpdateCount(Integer studentId,int num)throws Exception{
//		ユーザ情報取得
			StudentTblEntity entity = loginRepository.getloginByStudentId(studentId);
			UpdateCount(entity,num);
	}
	
	private void UpdateCount(StudentTblEntity entity,int num)throws Exception{
		
		entity.setContinuousLogin(num);

		StudentRepository.save(entity);
		
	}
	
	/**
	 * 連続ログインに応じてポイントの取得
	 * 
	 * 
	 */
	public void LoginPoint(Integer studentId,int  point)throws Exception{
//		ユーザ情報取得
			StudentTblEntity entity = loginRepository.getloginByStudentId(studentId);
			LoginPoint(entity,point);
	}
	
	private void LoginPoint(StudentTblEntity entity,int point)throws Exception{
		
		entity.setPoint(point);

		StudentRepository.save(entity);
		
	}
	
	/**
	 * ログインボーナスのポイントを取得
	 * 
	 * 
	 */
	public int HomePoint(Integer num){
		int point = 0;
		//	ログインボーナス（ポイント）
		int[] bar  = {50,100,150,200,300};
		
		//	ログインボーナス表示
		for(int i = 0;i<6;i++) {
			for(int j = 0;j<bar.length;j++) {
				if(num == i) {
					if(i-1 == j) {
						point = bar[j];	
					}
				}
			}
		}
		return point;
	}
	
	/**
	 * ログアウト処理
	 * 
	 * @param token
	 */
	public void logout(Integer studentId)
	{
		StudentTblEntity StudentTblEntity = loginRepository.getlogout(studentId);
		if( StudentTblEntity != null ) {
			loginRepository.delete(StudentTblEntity);
		}
	}
	/**
	 * エラーチェック処理
	 * 
	 * @param token
	 */
	//public String LoginCheack() {
		
		
	//}
	
	
}