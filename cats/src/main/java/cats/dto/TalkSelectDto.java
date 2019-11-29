package cats.dto;

import lombok.Data;

@Data
public class TalkSelectDto {

	private Integer talkId;

	private Integer studentIdSend;

	private Integer studentIdReceive;

	private String studentSendName;

	private String studentReceiveName;

	private String SendImagePath;

	private String ReceiveImagePath;

	private String lastMessage;


}
