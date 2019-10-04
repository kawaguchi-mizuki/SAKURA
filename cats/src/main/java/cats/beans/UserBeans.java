package cats.beans;

import java.io.Serializable;

import lombok.Data;
@Data
public class UserBeans implements Serializable {
	private String name;
	private String sex;
	private String hobby;
	private String school;
	private String subject;
	private int grade;
	private int age;
	private String birthplace;
	private String introduction;

}
