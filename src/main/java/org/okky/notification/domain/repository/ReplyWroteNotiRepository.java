package org.okky.notification.domain.repository;

import org.okky.notification.domain.model.ReplyWroteNoti;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.List;

@RepositoryDefinition(domainClass = ReplyWroteNoti.class, idClass = String.class)
public interface ReplyWroteNotiRepository {
    Long countByOwnerIdAndReadIsFalse(String ownerId);
    void save(ReplyWroteNoti noti);
    void saveAll(Iterable<ReplyWroteNoti> notis);
    Page<ReplyWroteNoti> findByOwnerId(String ownerId, Pageable pageable);
    List<ReplyWroteNoti> findByOwnerId(String ownerId);
    List<ReplyWroteNoti> findByIdIn(List<String> ids);
    void deleteByIdIn(List<String> ids);
}
