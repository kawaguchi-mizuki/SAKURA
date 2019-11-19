package cats.dto;


import java.sql.Timestamp;

import lombok.Data;

@Data
public class MessageDto {

	private Integer talkId;

	private Integer messageId;

	private String message;

	private Timestamp sendTime;

	private Integer studentIdSend;

}
