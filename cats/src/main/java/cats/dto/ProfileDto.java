package cats.dto;

import lombok.Data;

@Data
public class ProfileDto {

	private String studentName;

	private String studentSex;

	private String hobbyName = "カラオケ";

	private String schoolName = "麻生情報ビジネス専門学校";

	private String courseName = "情報システム専攻科";

	private Integer grade;

	private Integer age;

	private String birthplace;

	private String selfIntroduction ;

	private String password ;

	private String imagePass;

}
