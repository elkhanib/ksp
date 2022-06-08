package com.iodigital.ksp.api.tedtalk;

import com.iodigital.ksp.domain.CreateTedTalkRequest;
import com.iodigital.ksp.domain.TedTalk;
import com.iodigital.ksp.service.TedTalkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/ted-talks")
@Validated
@Tag(name = "command", description = "Endpoints for create, update and delete TedTalk")
public class CommandController {

    private final TedTalkService tedTalkService;

    @PostMapping("/")
    @Operation(summary = "Adds a new TedTalk")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<TedTalk> create(@RequestBody @Valid CreateTedTalkRequest request) {
        return new ResponseEntity<>(tedTalkService.create(request), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    @Operation(summary = "Partially updates TedTalk by id")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<TedTalk> patch(@PathVariable Long id,
                                         @RequestBody Map<String, Object> fields) {
        return ResponseEntity.ok(tedTalkService.partiallyUpdate(id, fields));
    }
}
