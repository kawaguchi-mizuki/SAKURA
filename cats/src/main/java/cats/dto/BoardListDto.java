package cats.dto;

import lombok.Data;

@Data
public class BoardListDto {

	private Integer boardId;

	private Integer studentId;

	private String studentName;

	private String boardTitle;

	private Integer categoryId;

	private String categoryName;

	private String boardDate;
}
