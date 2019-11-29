package cats.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cats.entity.TalkTblEntity;

public class TalkSpecification {

	public static Specification<TalkTblEntity> IdEqules(Integer studentId){

		return studentId == null?null:new Specification<TalkTblEntity>() {


			@Override
			public Predicate toPredicate(Root<TalkTblEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO 自動生成されたメソッド・スタブ
				return cb.equal(root.get("studentIdSent"),studentId);
			}
		};
	}

	public static Specification<TalkTblEntity> RIdEqules(Integer studentId){

		return studentId == null?null:new Specification<TalkTblEntity>() {


			@Override
			public Predicate toPredicate(Root<TalkTblEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO 自動生成されたメソッド・スタブ
				return cb.equal(root.get("studentIdReceive"),studentId);
			}
		};
	}

}
