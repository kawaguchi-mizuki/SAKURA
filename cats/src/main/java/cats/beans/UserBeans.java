package cats.beans;

import java.io.Serializable;

import lombok.Data;
@Data
public class UserBeans implements Serializable {
	private int studentId = 1701120;
	private String name;
	private String sex;
	private int hobbyId;
	private String school;
	private String course;
	private int grade;
	private int age;
	private String birthplace;
	private String introduction;

}
