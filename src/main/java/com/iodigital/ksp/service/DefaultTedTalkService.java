package com.iodigital.ksp.service;

import com.iodigital.ksp.domain.CreateTedTalkRequest;
import com.iodigital.ksp.domain.TedTalk;
import com.iodigital.ksp.exception.TalkNotFoundException;
import com.iodigital.ksp.repository.TedTalkRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultTedTalkService implements TedTalkService {

    private final TedTalkRepository talkRepository;
    private final ModelMapper mapper;

    @Override
    public TedTalk create(CreateTedTalkRequest request) {
        var entity = mapper.map(request, TedTalk.class);
        return talkRepository.save(entity);
    }

    @Override
    @Cacheable(value = "tedTalk", key = "#id")
    public TedTalk findById(Long id) {
        return talkRepository.findById(id).orElseThrow(TalkNotFoundException::new);
    }

    @Override
    public Page<TedTalk> getTalkList(Pageable pageable) {
        return talkRepository.findAll(pageable);
    }

    @Override
    public Page<TedTalk> findTalkByAuthor(String authorName, Pageable pageable) {
        return talkRepository.findByAuthorIgnoreCaseContaining(authorName, pageable);
    }

    @Override
    @Cacheable(value = "ted-talks", key = "#title")
    public List<TedTalk> findTalkByTitle(String title) {
        return talkRepository.findByTitleIgnoreCaseContaining(title);
    }

    @Override
    public Page<TedTalk> findByViewCounts(Long from, Long until, Pageable pageable) {
        return talkRepository.findByViewCountBetween(from, until, pageable);
    }

    @Override
    public Page<TedTalk> findByLikeCounts(Long from, Long until, Pageable pageable) {
        return talkRepository.findByLikeCountBetween(from, until, pageable);
    }

}
