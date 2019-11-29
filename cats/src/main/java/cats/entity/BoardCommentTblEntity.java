package cats.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="board_comment")
public class BoardCommentTblEntity {

	/** serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private  Integer boardCommentId;

	private Integer boardId;

	private String boardComment;

	private String boardCommentName;




}
