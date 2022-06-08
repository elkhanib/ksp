package com.iodigital.ksp.api.tedtalk;

import com.iodigital.ksp.domain.TedTalk;
import com.iodigital.ksp.service.TedTalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ted-talks")
@Tag(name = "query", description = "Endpoints for searching or getting TedTalk")
public class QueryController {

    private final TedTalkService tedTalkService;

    @GetMapping("/{id}")
    @Operation(summary = "Get a Ted-talk by its id")
    public ResponseEntity<TedTalk> findById(@PathVariable Long id) {
        return ResponseEntity.ok(tedTalkService.findById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "Getting all Ted talks with pagination")
    public ResponseEntity<Slice<TedTalk>> getAllTalks(Pageable pageable) {
        log.debug("Get list of talks: {}", pageable);
        return ResponseEntity.ok(tedTalkService.getTalkList(pageable));
    }

    @GetMapping("/by-author")
    @Operation(summary = "Getting all Ted talks by author")
    public ResponseEntity<Slice<TedTalk>> findTalkByAuthor(@RequestParam("author") String author,
                                                           Pageable pageable) {
        log.debug("Get list of talks by author: {}", author);
        return ResponseEntity.ok(tedTalkService.findTalkByAuthor(author, pageable));
    }

    @GetMapping("/by-title")
    @Operation(summary = "Getting all Ted talks by title")
    public ResponseEntity<List<TedTalk>> findTalkByTitle(@RequestParam("title") String title) {
        log.debug("Get list of talks by title: {}", title);
        return ResponseEntity.ok(tedTalkService.findTalkByTitle(title));
    }

    @GetMapping("/by-views")
    @Operation(summary = "Get list of talks by view counts")
    public ResponseEntity<Slice<TedTalk>> findByViewCounts(@RequestParam("from") Long from,
                                                           @RequestParam("until") Long until,
                                                           Pageable pageable) {
        log.debug("Get list of talks by view counts between '{}' and '{}'", from, until);
        return ResponseEntity.ok(tedTalkService.findByViewCounts(from, until, pageable));
    }

    @GetMapping("/by-likes")
    @Operation(summary = "Get list of talks by like counts")
    public ResponseEntity<Slice<TedTalk>> findByLikeCounts(@RequestParam("from") Long from,
                                                           @RequestParam("until") Long until,
                                                           Pageable pageable) {
        log.debug("Get list of talks by like counts between '{}' and '{}'", from, until);
        return ResponseEntity.ok(tedTalkService.findByLikeCounts(from, until, pageable));
    }

}
