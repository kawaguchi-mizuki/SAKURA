package cats.dto;

import java.util.Date;

import lombok.Data;

@Data
public class LoginDayDto {
	private Date lastLog;
	private Integer continuousLogin;

}
