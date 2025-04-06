package com.example.community.domain.member.repository;

import com.example.community.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {

    boolean existsByEmail(String email); // email이 있는지 없는지

    boolean existsByPassword(String password);

    Optional<Member> findByEmail(String email);

}
