package cats.dto;

import lombok.Data;

@Data
public class AuthSearchDto {
	
	private Integer studentId ;

	public Integer getStudentId() {
		return studentId;
	}

	public void setStudentId(Integer studentId) {
		this.studentId = studentId;
	}
	

}
