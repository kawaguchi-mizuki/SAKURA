package cats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cats.entity.CourseTblEntity;

public interface CourseRepository extends  JpaRepository<CourseTblEntity,Integer>,
JpaSpecificationExecutor<CourseTblEntity>{

	@Query("select c from CourseTblEntity c where schoolId = :schoolId")
	public List<CourseTblEntity>getCourse(@Param("schoolId")Integer schoolId);

}
