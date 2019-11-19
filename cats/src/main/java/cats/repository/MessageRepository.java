package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cats.entity.MessageTblEntity;

public interface MessageRepository extends JpaRepository<MessageTblEntity,Integer>,JpaSpecificationExecutor<MessageTblEntity> {


}
