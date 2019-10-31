package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cats.entity.StudentTblEntity;

public interface ProfileRepository extends JpaRepository<StudentTblEntity,Integer>,
JpaSpecificationExecutor<StudentTblEntity>{

	@Query("select s from StudentTblEntity s where studentId = :studentId")
	public StudentTblEntity getProfile(@Param("studentId")Integer studentId);


}
