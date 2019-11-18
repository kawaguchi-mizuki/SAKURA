package cats.dto;

import lombok.Data;

@Data
public class TalkSelectDto {

	private Integer talkId;

	private Integer studentIdSent;

	private Integer studentIdReceive;

	private String studentName;


}
