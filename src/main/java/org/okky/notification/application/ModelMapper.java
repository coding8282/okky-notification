package org.okky.notification.application;

import org.okky.notification.application.command.WriteChangeLogCommand;
import org.okky.notification.domain.model.changelog.ChangeLog;
import org.okky.notification.domain.model.changelog.ChangeType;
import org.springframework.stereotype.Service;

@Service
class ModelMapper {
    ChangeLog toModel(WriteChangeLogCommand cmd) {
        return new ChangeLog(
                cmd.getBody(),
                ChangeType.parse(cmd.getType())
        );
    }
}