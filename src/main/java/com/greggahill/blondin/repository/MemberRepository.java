package com.greggahill.blondin.repository;

import com.greggahill.blondin.model.Member;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Long> {
}
