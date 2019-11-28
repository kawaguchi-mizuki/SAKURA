package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cats.entity.MessageTblEntity;

public interface MessageRepository extends JpaRepository<MessageTblEntity,Integer>,JpaSpecificationExecutor<MessageTblEntity> {

	@Query("select m from MessageTblEntity m where talkId = :talkId")
	String getLastMessage(Integer talkId);






}
