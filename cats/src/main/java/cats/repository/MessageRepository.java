package cats.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cats.entity.MessageTblEntity;

public interface MessageRepository extends JpaRepository<MessageTblEntity,Integer>,JpaSpecificationExecutor<MessageTblEntity> {

	@Query("select message from MessageTblEntity m where talkId = :talkId order by messageId desc")
	List<String> getLastMessage(Integer talkId);






}
