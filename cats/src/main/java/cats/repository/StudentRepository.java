package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cats.entity.StudentTblEntity;
@Repository
public interface StudentRepository  extends JpaRepository<StudentTblEntity,Integer>,JpaSpecificationExecutor<StudentTblEntity> {


	@Query("select s from StudentTblEntity s where studentId = :studentId")
	public StudentTblEntity getProfile(@Param("studentId")Integer studentId);






}
