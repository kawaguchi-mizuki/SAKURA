package cats.dto;

import java.util.Date;

import lombok.Data;


@Data
public class AuthenticationDto {
	
	private String pass;
	private int studentId;
	private Date NowDate;
	public String getPass() {
		return pass;
	}
	public void setPass(String pass) {
		this.pass = pass;
	}
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public Date getNowDate() {
		return NowDate;
	}
	public void setNowDate(Date nowDate) {
		NowDate = nowDate;
	}
	
	
	
}
