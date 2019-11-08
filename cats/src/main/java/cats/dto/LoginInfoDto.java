
package cats.dto;



import java.util.Date;

import lombok.Data;

/**
 * LoginInfoBeans
 *
 * DBからのデータを格納する
 *
 * @author
 *
 */

@Data
public class LoginInfoDto {
	private int studentId;
	private Integer point;
	private Date lastLog;

}
