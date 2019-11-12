package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cats.entity.BoardCommentTblEntity;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardCommentTblEntity,Integer>,JpaSpecificationExecutor<BoardCommentTblEntity>{

}
