package com.iodigital.ksp.service;

import com.iodigital.ksp.domain.CreateTedTalkRequest;
import com.iodigital.ksp.domain.TedTalk;
import com.iodigital.ksp.exception.RecordNotFoundException;
import com.iodigital.ksp.repository.TedTalkRepository;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

@Service
@RequiredArgsConstructor
@Slf4j
public class DefaultTedTalkService implements TedTalkService {

    private final TedTalkRepository talkRepository;
    private final ModelMapper mapper;
    private final Validator validator;

    @Override
    public TedTalk create(CreateTedTalkRequest request) {
        var entity = mapper.map(request, TedTalk.class);
        return talkRepository.save(entity);
    }

    @Override
    @Cacheable(value = "tedTalk", key = "#id")
    public TedTalk findById(Long id) {
        return talkRepository.findById(id).orElseThrow(RecordNotFoundException::new);
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

    @Override
    @Cacheable(value = "tedTalk", key = "#id")
    public TedTalk partiallyUpdate(Long id, Map<String, Object> fields) {
        TedTalk entity = talkRepository.findById(id).orElseThrow(RecordNotFoundException::new);

        // mapping to a dto model to be able to validate against constraints
        CreateTedTalkRequest dto = mapper.map(entity, CreateTedTalkRequest.class);

        // merging request data with dto
        fields.forEach((key, value) -> mapToProperModel(dto, key, value));

        Set<ConstraintViolation<CreateTedTalkRequest>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }

        // updating record
        var mergedEntity = mapper.map(dto, TedTalk.class);
        mergedEntity.setId(id);
        return talkRepository.save(mergedEntity);
    }

    @Override
    @CacheEvict(value = "tedTalk")
    public void delete(Long id) {
        talkRepository.deleteById(id);
    }

    @SuppressWarnings("squid:S3011") // disabling sonar-lint due to field.setAccessible(true)
    private void mapToProperModel(CreateTedTalkRequest dto, String key, Object value) {
        Field field = ReflectionUtils.findField(dto.getClass(), key);
        if (Objects.nonNull(field)) {
            field.setAccessible(true);

            if (field.getType().isAssignableFrom(LocalDate.class)) {
                LocalDate dateValue = LocalDate.parse(value.toString());
                ReflectionUtils.setField(field, dto, dateValue);

            } else if (field.getType().isAssignableFrom(Long.class)) {
                Long longValue = Long.valueOf(value.toString());
                ReflectionUtils.setField(field, dto, longValue);

            } else {
                ReflectionUtils.setField(field, dto, value);
            }
        }
    }

}
