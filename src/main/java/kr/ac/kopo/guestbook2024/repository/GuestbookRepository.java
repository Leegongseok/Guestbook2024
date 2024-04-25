package kr.ac.kopo.guestbook2024.repository;

import kr.ac.kopo.guestbook2024.entity.Guestbook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface GuestbookRepository extends JpaRepository <Guestbook,Long>, QuerydslPredicateExecutor<Guestbook> {
}
