package cats.dto;

import java.sql.Date;

import lombok.Data;

@Data
public class CreateUserDto {
	private Integer studentId;
	private String studentName;
	private String studentSex;
	private Integer hobbyId;
	private String schoolName;
	private String course;
	private Integer grade;
	private Integer age;
	private String birthplace;
	private String selfIntroduction;
	private String password;
	private String imagePass;
	private Integer point;
	private Date lastLog;
	private Integer continuousLogin;

}
