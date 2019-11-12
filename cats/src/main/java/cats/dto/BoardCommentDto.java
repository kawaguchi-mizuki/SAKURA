package cats.dto;

import lombok.Data;

@Data
public class BoardCommentDto {

	private Integer boardId;

	private Integer commentId;

	private String comment;

	private String commentName;

}
