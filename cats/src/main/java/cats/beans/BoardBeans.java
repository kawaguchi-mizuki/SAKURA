package cats.beans;

import java.io.Serializable;

import lombok.Data;
@Data
public class BoardBeans implements Serializable{

	private int studentId;
	private String boardTitle;
	private int categoryId;


}
