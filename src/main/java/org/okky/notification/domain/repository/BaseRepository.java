package org.okky.notification.domain.repository;

import org.okky.notification.domain.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.util.List;

@NoRepositoryBean
public interface BaseRepository<T extends Notification> extends Repository<T, String> {
    long countByOwnerIdAndReadIsFalse(String ownerId);
    void save(T noti);
    void saveAll(Iterable<T> notis);
    Page<T> findByOwnerId(String ownerId, Pageable pageable);
    List<T> findByOwnerId(String ownerId);
    List<T> findByIdIn(List<String> ids);
    void deleteByIdIn(List<String> ids);
}
