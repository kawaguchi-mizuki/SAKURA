package cats.dto;

import cats.entity.StudentTblEntity;
import lombok.Data;

@Data
public class HomeRequestDto {

	private Integer requestId;

	private StudentTblEntity sentId;

	private Integer receiveId;

	private Integer approval;

	private String studentName;





}
