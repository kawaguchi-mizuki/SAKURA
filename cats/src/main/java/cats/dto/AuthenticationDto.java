package cats.dto;

import lombok.Data;


@Data
public class AuthenticationDto {
	private String pass;
	private int strudentId;
}
