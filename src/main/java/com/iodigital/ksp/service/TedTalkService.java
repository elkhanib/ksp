package com.iodigital.ksp.service;

import com.iodigital.ksp.domain.CreateTedTalkRequest;
import com.iodigital.ksp.domain.PartiallyUpdateRequest;
import com.iodigital.ksp.domain.TedTalk;
import java.util.List;
import java.util.Map;
import javax.json.JsonMergePatch;
import javax.json.JsonPatch;
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

    TedTalk partiallyUpdate(Long id, Map<String, Object> fields);
}
