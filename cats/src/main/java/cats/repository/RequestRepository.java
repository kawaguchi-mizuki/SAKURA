package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import cats.entity.RequestTblEntity;


@Repository
public interface RequestRepository extends  JpaRepository<RequestTblEntity,Integer>,
JpaSpecificationExecutor<RequestTblEntity>{
	
	//@Query("select r from RequestTblEntity r, StudentTblEntitiy u where  studentIdReceive = r.studentIdTbl.studentId")
	//public List<StudentTblEntity> getSeachList();
	
	@Query("select u from RequestTblEntity u where studentIdReceive = :studentId")
	public RequestTblEntity getRequest(@Param("studentId")int studentId);
}


