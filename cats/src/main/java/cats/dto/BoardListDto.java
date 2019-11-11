package cats.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BoardListDto {

	private Integer boardId;

	private Integer studentId;

	private String studentName;

	private String boardTitle;

	private Integer categoryId;

	private String categoryName;

	private Date boardDate;
}
