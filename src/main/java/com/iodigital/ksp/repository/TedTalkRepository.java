package com.iodigital.ksp.repository;

import com.iodigital.ksp.domain.TedTalk;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TedTalkRepository extends JpaRepository<TedTalk, Long>{

    Optional<TedTalk> findById(Long id);

    Page<TedTalk> findByAuthorIgnoreCaseContaining(String authorName, Pageable pageable);

    List<TedTalk> findByTitleIgnoreCaseContaining(String title);

    Page<TedTalk> findByViewCountBetween(Long from, Long until, Pageable pageable);

    Page<TedTalk> findByLikeCountBetween(Long from, Long until, Pageable pageable);
}
