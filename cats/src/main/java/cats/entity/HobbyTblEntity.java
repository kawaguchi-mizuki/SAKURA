package cats.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="hobby")
public class HobbyTblEntity implements Serializable {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** HOBBY_ID. */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer hobbyId;

	/** HOBBY_NAME. */
	private String hobbyName;

	/**
	 * COURSE_ID を設定します.
	 *
	 * @param hobbyId
	 *            COURSE_ID
	 */
	public void sethobbyId(Integer hobbyId) {
		this.hobbyId = hobbyId;
	}

	/**
	 * COURSE_ID を取得します.
	 *
	 * @return COURSE_ID
	 */
	public Integer gethobbyId() {
		return this.hobbyId;
	}

	/**
	 * COURSE_NAME を設定します.
	 *
	 * @param hobbyName
	 *            COURSE_NAME
	 */
	public void sethobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	/**
	 * COURSE_NAME を取得します.
	 *
	 * @return COURSE_NAME
	 */
	public String gethobbyName() {
		return this.hobbyName;
	}







}
