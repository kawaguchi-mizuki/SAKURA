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
	 * HOBBY_ID を設定します.
	 *
	 * @param hobbyId
	 *            HOBBY_ID
	 */
	public void sethobbyId(Integer hobbyId) {
		this.hobbyId = hobbyId;
	}

	/**
	 * HOBBY_ID を取得します.
	 *
	 * @return HOBBY_ID
	 */
	public Integer gethobbyId() {
		return this.hobbyId;
	}

	/**
	 * HOBBY_NAME を設定します.
	 *
	 * @param hobbyName
	 *            HOBBY_NAME
	 */
	public void sethobbyName(String hobbyName) {
		this.hobbyName = hobbyName;
	}

	/**
	 * HOBBY_NAME を取得します.
	 *
	 * @return HOBBY_NAME
	 */
	public String gethobbyName() {
		return this.hobbyName;
	}







}
