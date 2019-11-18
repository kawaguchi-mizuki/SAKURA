package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cats.entity.TalkTblEntity;

public interface TalkRepository extends JpaRepository<TalkTblEntity,Integer>,JpaSpecificationExecutor<TalkTblEntity> {



}
