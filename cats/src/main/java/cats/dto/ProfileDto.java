package cats.dto;

import lombok.Data;

@Data
public class ProfileDto {

	private String studentName = "川口";

	private String studentSex = "男";

	private String hobbyName = "カラオケ";

	private String schoolName = "麻生情報ビジネス専門学校";

	private String course = "情報システム専攻科";

	private Integer grade = 1;

	private Integer age = 19;

	private String birthplace = "鹿児島";

	private String selfIntroduction = "よろしくお願いいたします。";

	private String password = "abccabcc";

	private String imagePass;

}
