package cats.repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import cats.entity.HomeRequestTblEntity;
import cats.entity.TalkTblEntity;

public class RequestSpecification {

	public static Specification<HomeRequestTblEntity> studentIdEqules(Integer studentId){

		return studentId == null?null:new Specification<HomeRequestTblEntity>() {

			@Override
			public Predicate toPredicate(Root<HomeRequestTblEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO 自動生成されたメソッド・スタブ
				return cb.equal(root.get("studentIdReceive"),studentId);
			}
		};

	}

	public static Specification<HomeRequestTblEntity> ApprovalEqules(){

		return new Specification<HomeRequestTblEntity>(){
			@Override
			public Predicate toPredicate(Root<HomeRequestTblEntity> root, CriteriaQuery<?> query,
					CriteriaBuilder cb) {
				// TODO 自動生成されたメソッド・スタブ
				return cb.equal(root.get("approval"),0);
			}
		};

	}

	public static Specification<TalkTblEntity> approvelIdEqules(Integer studentId){

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
