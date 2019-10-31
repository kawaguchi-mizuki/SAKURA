package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cats.entity.StudentTblEntity;



@Repository
public interface LoginRepository 
extends JpaRepository<StudentTblEntity, Integer>,JpaSpecificationExecutor<StudentTblEntity>{

	@Query("select u from StudentTblEntity u where studentId = :studentId and password = :password")
	public StudentTblEntity getlogin(@Param("studentId")int studentId,@Param("password")String password);
	
	@Query("select u from StudentTblEntity u where studentId = :studentId")
	public StudentTblEntity getlogout(@Param("studentId")int studentId);
	
	@Query("select u from StudentTblEntity u where studentId = :studentId")
	public StudentTblEntity getloginByStudentId(@Param("studentId")int studentId);

}
