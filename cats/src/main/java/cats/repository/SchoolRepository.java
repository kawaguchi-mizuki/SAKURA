package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cats.entity.SchoolTblEntity;

public interface SchoolRepository extends  JpaRepository<SchoolTblEntity,Integer>,
JpaSpecificationExecutor<SchoolTblEntity>{

}
