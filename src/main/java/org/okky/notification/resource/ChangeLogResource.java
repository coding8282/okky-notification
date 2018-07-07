package org.okky.notification.resource;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.application.ChangeLogService;
import org.okky.notification.application.command.UpdateChangeLogCommand;
import org.okky.notification.application.command.WriteChangeLogCommand;
import org.okky.notification.domain.model.changelog.ChangeLog;
import org.okky.notification.domain.repository.ChangeLogRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class ChangeLogResource {
    ChangeLogService service;
    ChangeLogRepository repository;

    @GetMapping(value = "/changelogs", produces = APPLICATION_JSON_VALUE)
    PagingEnvelop findAll(@PageableDefault(size = 20, sort = "logedOn", direction = DESC) Pageable pageable) {
        Page<ChangeLog> page = repository.findAll(pageable);
        return new PagingEnvelop(page);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping(value = "/changelogs", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    void write(@RequestBody WriteChangeLogCommand command) {
        service.write(command);
    }

    @Secured("ROLE_ADMIN")
    @PutMapping(value = "/changelogs/{logId}", consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(NO_CONTENT)
    void update(@RequestBody UpdateChangeLogCommand command) {
        service.update(command);
    }

    @Secured("ROLE_ADMIN")
    @DeleteMapping(value = "/changelogs/{logId}")
    @ResponseStatus(NO_CONTENT)
    void remove(@PathVariable String logId) {
        service.delete(logId);
    }
}
