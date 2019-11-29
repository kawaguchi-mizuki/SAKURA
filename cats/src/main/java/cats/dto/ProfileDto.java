package cats.dto;

import java.util.Date;

import lombok.Data;

@Data
public class ProfileDto {
	private Integer studentId;

	private String studentName;

	private String studentSex;

	private Integer hobbyId;

	private String hobbyName;

	private Integer schoolId;

	private String schoolName;

	private Integer courseId;

	private String courseName;

	private Integer grade;

	private Integer age;

	private String birthplace;

	private String selfIntroduction ;

	private String password ;

	private Integer point;

	private Date lastlog;

	private Integer continuouslogin;

	private String imagePath;

}
