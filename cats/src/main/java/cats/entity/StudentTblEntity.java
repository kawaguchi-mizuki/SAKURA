
package cats.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="student")
public class StudentTblEntity implements Serializable{
	
	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** 	学籍番号 **/
	@Id
	private int studentId;
	
	/** 	名前 **/
	private String studentName;
	
	/** 	性別 **/
	private String studentSex;
	
	/**		 趣味id **/
	private int hobbyId;
	
	/**		 学校名 **/
	private Integer schoolId;
	
	/**		 学科 **/
	private Integer courseId;
	
	/** 	学年 **/
	private Integer grade;
	
	/** 	年齢 **/
	private Integer age;
	
	/**		 出身地 **/
	private String birthplace;
	
	/** 	自己紹介 **/
	private String selfIntroduction;
	
	/** 	パスワード **/
	private String password;
	
	/** 	画像パス **/
	private String imagePass;
	
	/** 	ポイント **/
	private Integer point;
	
	/**		 最終ログイン **/
	private Date lastLog;
	
	/** 	ログインカウント **/
	private Integer continuousLogin;
	
	
	

}
