package cats.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cats.dto.CourseDto;
import cats.entity.CourseTblEntity;
import cats.repository.CourseRepository;


@Service
public class CourseService {

	@Autowired
	CourseRepository courseRepository;

	public List<CourseDto> getList(Integer schoolId) {
		List<CourseDto> list = new ArrayList<CourseDto>();



		List<CourseTblEntity> courseList = courseRepository.getCourse(
				schoolId);

		//entity -> DTO
				for( CourseTblEntity entity : courseList ) {
					CourseDto dto = new CourseDto();

					dto.setCourseId(entity.getcourseId());
					dto.setSchoolId(entity.getschoolId());
					dto.setCourseName(entity.getcourseName());

					list.add(dto);
				}

				return list;
	}

}
