package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cats.entity.HomeRequestTblEntity;

@Repository
public interface HomeRequestRepository extends JpaRepository<HomeRequestTblEntity,Integer>,JpaSpecificationExecutor<HomeRequestTblEntity>{

}
