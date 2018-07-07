package org.okky.notification.application;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.application.command.UpdateChangeLogCommand;
import org.okky.notification.application.command.WriteChangeLogCommand;
import org.okky.notification.domain.model.changelog.ChangeLog;
import org.okky.notification.domain.model.changelog.ChangeType;
import org.okky.notification.domain.repository.ChangeLogRepository;
import org.okky.notification.domain.service.ChangeLogConstraint;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PRIVATE;

@Service
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
public class ChangeLogService {
    ChangeLogRepository repository;
    ChangeLogConstraint constraint;
    ModelMapper mapper;

    public void write(WriteChangeLogCommand cmd) {
        ChangeLog log = mapper.toModel(cmd);
        repository.save(log);
    }

    public void update(UpdateChangeLogCommand cmd) {
        ChangeType type = ChangeType.parse(cmd.getType());

        ChangeLog log = constraint.checkExistsAndGet(cmd.getId());
        log.update(cmd.getBody(), type);
    }

    public void delete(String logId) {
        repository.deleteById(logId);
    }
}
