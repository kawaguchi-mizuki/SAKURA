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
	private Integer student_id;
	
	private String one_pass;
	
	private Date time_limit;

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}

	public String getOne_pass() {
		return one_pass;
	}

	public void setOne_pass(String one_pass) {
		this.one_pass = one_pass;
	}

	public Date getTime_limit() {
		return time_limit;
	}

	public void setTime_limit(Date time_limit) {
		this.time_limit = time_limit;
	}

	

	
		
	
}
