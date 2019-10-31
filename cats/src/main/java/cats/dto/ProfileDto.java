package cats.dto;

import lombok.Data;

@Data
public class ProfileDto {

	private String studentName;

	private String studentSex;

	private Integer hobbyId;

	private String hobbyName;

	private Integer schoolId;

	private String schoolName;

	private Integer courseId;

	private String courseName = "情報システム専攻科";

	private Integer grade;

	private Integer age;

	private String birthplace;

	private String selfIntroduction ;

	private String password ;

	private String imagePass;

}
