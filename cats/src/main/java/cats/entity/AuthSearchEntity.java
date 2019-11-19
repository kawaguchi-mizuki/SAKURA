package cats.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;



@Data
@Entity
@Table(name="student")
public class AuthSearchEntity {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private Integer student_id;

	public Integer getStudent_id() {
		return student_id;
	}

	public void setStudent_id(Integer student_id) {
		this.student_id = student_id;
	}
	
	
	
}
