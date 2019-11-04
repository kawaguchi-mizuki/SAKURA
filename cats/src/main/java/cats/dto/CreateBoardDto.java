package cats.dto;

import java.util.Date;

import lombok.Data;

@Data
public class CreateBoardDto {

	private Integer boardId;
	private Integer studentId;
	private String boardTitle;
	private int categoryId;
	private Date boardDate;
}
