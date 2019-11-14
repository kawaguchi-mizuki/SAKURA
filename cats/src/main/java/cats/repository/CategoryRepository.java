package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import cats.entity.CategoryTblEntity;

public interface CategoryRepository extends  JpaRepository<CategoryTblEntity,Integer>,
JpaSpecificationExecutor<CategoryTblEntity>{

	@Query("select c from CategoryTblEntity c where categoryId = :categoryId")
	CategoryTblEntity getId(Integer categoryId);

}
