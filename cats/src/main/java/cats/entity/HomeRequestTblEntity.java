package cats.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="request")
public class HomeRequestTblEntity {


	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer requestId;

	private Integer studentIdSent;

	private Integer approval;

	@OneToOne
	@JoinColumn(name="studentIdReceive",insertable=true ,updatable=true)
	private StudentTblEntity studentIdTbl;







}
