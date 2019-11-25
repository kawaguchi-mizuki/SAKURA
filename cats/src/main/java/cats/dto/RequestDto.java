package cats.dto;


import lombok.Data;

@Data
public class RequestDto {
	private Integer reqestId;
	
	private Integer studentIdSent;
	
	private Integer studentIdReceive;
	
	private Integer approval; 
}
