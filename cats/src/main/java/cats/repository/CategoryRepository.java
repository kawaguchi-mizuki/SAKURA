package cats.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cats.entity.CategoryTblEntity;

public interface CategoryRepository extends  JpaRepository<CategoryTblEntity,Integer>,
JpaSpecificationExecutor<CategoryTblEntity>{

}
