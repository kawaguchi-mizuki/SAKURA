package cats.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cats.entity.MessageTblEntity;

public class MessageSpecification {

	public static Specification<MessageTblEntity> IdEqules(Integer talkId){

		return talkId == null?null:new Specification<MessageTblEntity>() {


			@Override
			public Predicate toPredicate(Root<MessageTblEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO 自動生成されたメソッド・スタブ
				return cb.equal(root.get("talkId"),talkId);
			}
		};
	}


}
