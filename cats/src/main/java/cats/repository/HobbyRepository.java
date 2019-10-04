package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cats.entity.HobbyTblEntity;


public interface HobbyRepository extends  JpaRepository<HobbyTblEntity,Integer>,
JpaSpecificationExecutor<HobbyTblEntity>{

}

