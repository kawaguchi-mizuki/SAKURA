package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import cats.entity.HobbyTblEntity;


public interface HobbyRepository extends  JpaRepository<HobbyTblEntity,Integer>,
JpaSpecificationExecutor<HobbyTblEntity>{


	@Query("select h from HobbyTblEntity h where hobbyId = :hobbyId")
	public HobbyTblEntity  getHobby(@Param("hobbyId")Integer hobbyId);

}

