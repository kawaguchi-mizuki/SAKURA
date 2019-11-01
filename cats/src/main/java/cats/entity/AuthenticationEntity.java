package cats.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="authentication")
public class AuthenticationEntity implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	
	private Integer studentId;
	
	private String pass;
	
	private Date nowDate;

	public void setStudentId(int studentId) {
		// TODO Auto-generated method stub
		
	}

	public void setPass(String pass) {
		// TODO Auto-generated method stub
		
	}

	public void setNowDate(Date nowDate) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
