package cats.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cats.entity.BoardCommentTblEntity;

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
}
