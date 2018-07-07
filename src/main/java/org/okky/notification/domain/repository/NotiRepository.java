package org.okky.notification.domain.repository;

import org.okky.notification.domain.model.noti.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface NotiRepository extends Repository<Notification, String> {
    long countByOwnerIdAndReadIsFalse(String ownerId);
    void save(Notification noti);
    void saveAll(Iterable<Notification> notis);
    Page<Notification> findByOwnerId(String ownerId, Pageable pageable);
    List<Notification> findByOwnerId(String ownerId);
    List<Notification> findByIdIn(List<String> ids);
    void deleteByIdIn(List<String> ids);
}
