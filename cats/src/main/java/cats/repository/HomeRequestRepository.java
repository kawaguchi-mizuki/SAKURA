package cats.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cats.entity.HomeRequestTblEntity;
@Transactional
@Repository
public interface HomeRequestRepository extends JpaRepository<HomeRequestTblEntity,Integer>,JpaSpecificationExecutor<HomeRequestTblEntity>{



}
