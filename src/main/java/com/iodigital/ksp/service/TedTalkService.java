package com.iodigital.ksp.service;

import com.iodigital.ksp.domain.CreateTedTalkRequest;
import com.iodigital.ksp.domain.TedTalk;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TedTalkService {

    TedTalk create(CreateTedTalkRequest request);

    TedTalk findById(Long id);
    Page<TedTalk> getTalkList(Pageable pageable);
    Page<TedTalk> findTalkByAuthor(String authorName, Pageable pageable);
    List<TedTalk> findTalkByTitle(String title);
    Page<TedTalk> findByViewCounts(Long from, Long until, Pageable pageable);
    Page<TedTalk> findByLikeCounts(Long from, Long until, Pageable pageable);
}
