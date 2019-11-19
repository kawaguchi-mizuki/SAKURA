package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cats.entity.AuthSearchEntity;

@Repository
public interface AuthSearchRepository 
extends JpaRepository<AuthSearchEntity,Integer>{
	
	@Query("select student_id  from AuthSearchEntity where student_id = :studentId")
	public AuthSearchEntity searchstudentId(@Param("studentId")Integer studentId);

}
