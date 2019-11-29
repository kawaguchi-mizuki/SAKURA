package cats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cats.entity.StudentTblEntity;

@Repository
public interface GachaRepository extends  JpaRepository<StudentTblEntity,Integer>,
JpaSpecificationExecutor<StudentTblEntity>{
	
	@Query("select distinct u from StudentTblEntity u "
			+"where studentId not in(select studentIdReceive from RequestTblEntity r where studentIdSent = :studentId)"
			+"and studentId not in(select studentIdSent from RequestTblEntity r where studentIdReceive = :studentId)"
			+"and hobbyId = :hobbyIdSearch and studentSex != :sex")
	public List<StudentTblEntity> getAllList(int studentId,int hobbyIdSearch,String sex);
	
	@Query("select u from StudentTblEntity u where studentId = :studentId")
	public StudentTblEntity getGachaUser(@Param("studentId")int studentId);
	
}


