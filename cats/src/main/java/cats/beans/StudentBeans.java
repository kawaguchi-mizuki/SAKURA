package cats.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import cats.dto.AttachedFileDto;
import cats.utils.FileUtils;
import lombok.Data;
@Data
public class StudentBeans implements Serializable {
	private int studentId = 1701127;
	private String name;
	private String sex;
	private int hobbyId;
	private int schoolId;
	private int courseId;
	private int grade;
	private int age;
	private String birthplace;
	private String introduction;
	private MultipartFile multipartFile;

	private List<AttachedFileDto> uploadFilePathList = new ArrayList<AttachedFileDto>();


	public void addUploadFilePath(String filePath,Long size) {
		AttachedFileDto dto = new AttachedFileDto();

		dto.setFileName(FileUtils.getFileNameFromPath(filePath));
		dto.setFilePath(filePath);
		dto.setSize(size);

		uploadFilePathList.add(dto);
	}

}
