package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cats.entity.StudentTblEntity;

@Repository
public interface StudentRepository  extends JpaRepository<StudentTblEntity,Integer>,JpaSpecificationExecutor<StudentTblEntity> {



}
