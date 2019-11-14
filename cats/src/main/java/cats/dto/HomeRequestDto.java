package cats.dto;

import cats.entity.StudentTblEntity;
import lombok.Data;

@Data
public class HomeRequestDto {

	private Integer requestId;

	private Integer sentId;

	private StudentTblEntity receiveId;

	private Integer approval;

	private String studentName;

//	private Integer requestCount;



}
