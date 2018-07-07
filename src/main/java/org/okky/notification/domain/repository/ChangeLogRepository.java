package org.okky.notification.domain.repository;

import org.okky.notification.domain.model.changelog.ChangeLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface ChangeLogRepository extends Repository<ChangeLog, String> {
    boolean existsById(String id);
    void save(ChangeLog noti);
    Optional<ChangeLog> findById(String id);
    Page<ChangeLog> findAll(Pageable pageable);
    void deleteById(String id);
}
