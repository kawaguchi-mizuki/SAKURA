package cats.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cats.entity.BoardCommentTblEntity;
import cats.entity.BoardTblEntity;

public class BoardSpecification {

	public static Specification<BoardCommentTblEntity> IdEqules(Integer boardId){

		return boardId == null?null:new Specification<BoardCommentTblEntity>() {

			@Override
			public Predicate toPredicate(Root<BoardCommentTblEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO 自動生成されたメソッド・スタブ
				return cb.equal(root.get("boardId"),boardId);
			}
		};

	}
	public static Specification<BoardTblEntity> CategorySelect(Integer categoryId){

		return categoryId == null?null:new Specification<BoardTblEntity>() {

			@Override
			public Predicate toPredicate(Root<BoardTblEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO 自動生成されたメソッド・スタブ
				return cb.equal(root.get("categoryId"),categoryId);
			}
		};

	}


}
