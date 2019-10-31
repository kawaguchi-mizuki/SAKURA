package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cats.entity.SchoolTblEntity;

public interface SchoolRepository extends  JpaRepository<SchoolTblEntity,Integer>,
JpaSpecificationExecutor<SchoolTblEntity>{

	@Query("select s from SchoolTblEntity s where schoolId = :schoolId")
	public SchoolTblEntity  getSchool(@Param("schoolId")Integer schoolId);

}
