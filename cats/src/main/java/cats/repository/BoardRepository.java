package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import cats.entity.BoardTblEntity;

@Repository
public interface BoardRepository  extends JpaRepository<BoardTblEntity,Integer>,JpaSpecificationExecutor<BoardTblEntity> {




}
