package jpabook.jpashop.repository;

import java.util.List;

import jpabook.jpashop.domain.Member;

public interface MemberRepositoryCustom {
	public List<Member> findMemberCustom();
}
