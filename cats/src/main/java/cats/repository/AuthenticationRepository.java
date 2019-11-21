package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cats.entity.AuthenticationEntity;

@Repository
public interface AuthenticationRepository
extends JpaRepository<AuthenticationEntity,Integer>{

	@Query("select a from AuthenticationEntity a where student_id = :student_id")
	public AuthenticationEntity getPass(@Param("student_id")Integer student_id);


}
