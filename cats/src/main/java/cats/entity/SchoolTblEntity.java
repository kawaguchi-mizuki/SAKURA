package cats.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="school")
public class SchoolTblEntity implements Serializable{

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** SCHOOL_ID. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer schoolId;

	/** SCHOOL_NAME. */
	private String schoolName;

	/**
	 * SCHOOL_ID を設定します.
	 *
	 * @paramschoolId
	 *            SCHOOL_ID
	 */
	public void setschoolId(Integer schoolId) {
		this.schoolId = schoolId;
	}

	/**
	 * SCHOOL_ID を取得します.
	 *
	 * @return COURSE_ID
	 */
	public Integer getschoolId() {
		return this.schoolId;
	}

	/**
	 * SCHOOL_NAME を設定します.
	 *
	 * @param schoolName
	 *            SCHOOL_NAME
	 */
	public void setschoolName(String schoolName) {
		this.schoolName = schoolName;
	}

	/**
	 * SCHOOL_NAME を取得します.
	 *
	 * @return SCHOOL_NAME
	 */
	public String getschoolName() {
		return this.schoolName;
	}


}
