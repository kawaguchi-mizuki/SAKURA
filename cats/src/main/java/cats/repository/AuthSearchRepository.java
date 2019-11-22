package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cats.entity.StudentTblEntity;

@Repository
public interface AuthSearchRepository
extends JpaRepository<StudentTblEntity,Integer>{

	@Query("select s from StudentTblEntity s where studentId = :studentId")
	public StudentTblEntity searchstudentId(@Param("studentId")Integer studentId);

}
