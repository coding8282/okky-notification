package org.okky.notification.domain.service;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.changelog.ChangeLog;
import org.okky.notification.domain.repository.ChangeLogRepository;
import org.okky.share.execption.ModelNotExists;
import org.springframework.stereotype.Service;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ChangeLogConstraint {
    ChangeLogRepository repository;

    public ChangeLog checkExistsAndGet(String id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new ModelNotExists(format("해당 변경 이력은 존재하지 않습니다: '%s'", id)));
    }
}
