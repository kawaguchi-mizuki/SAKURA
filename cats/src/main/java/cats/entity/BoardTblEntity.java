package cats.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="board")
public class BoardTblEntity implements Serializable{

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** BOARD_ID.
	 *
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer boardId;

	private Integer studentId;

	private String boardTitle;

	private Integer categoryId;

	private Date boardDate;


}
