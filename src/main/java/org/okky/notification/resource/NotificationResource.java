package org.okky.notification.resource;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.application.NotificationApplicationService;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.repository.NotiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static lombok.AccessLevel.PRIVATE;
import static org.springframework.data.domain.Sort.Direction.DESC;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class NotificationResource {
    NotificationApplicationService service;
    NotiRepository repository;

    @GetMapping("/owners/{ownerId}/notifications/count")
    Long count(@PathVariable String ownerId) {
        return repository.countByOwnerIdAndReadIsFalse(ownerId);
    }

    @GetMapping(value = "/owners/{ownerId}/notifications", produces = APPLICATION_JSON_VALUE)
    PagingEnvelop findByOwnerId(
            @PathVariable String ownerId,
            @PageableDefault(size = 20, sort = "notifiedOn", direction = DESC) Pageable pageable) {
        Page<Notification> page = repository.findByOwnerId(ownerId, pageable);
        return new PagingEnvelop(page);
    }

    @PutMapping("/owners/{ownerId}/notifications/read-all")
    @ResponseStatus(NO_CONTENT)
    void markReadAll(@PathVariable String ownerId) {
        service.markReadAll(ownerId);
    }

    @PutMapping("/notifications/{notificationIds}/read")
    @ResponseStatus(NO_CONTENT)
    void markRead(@PathVariable List<String> notificationIds) {
        service.markRead(notificationIds);
    }

    @DeleteMapping("/notifications/{notificationIds}/read")
    @ResponseStatus(NO_CONTENT)
    void markUnread(@PathVariable List<String> notificationIds) {
        service.markUnread(notificationIds);
    }

    @PutMapping("/notifications/{notificationIds}/read/toggle")
    @ResponseStatus(NO_CONTENT)
    void toggleRead(@PathVariable List<String> notificationIds) {
        service.toggleRead(notificationIds);
    }

    @DeleteMapping("/notifications/{notificationIds}")
    @ResponseStatus(NO_CONTENT)
    void remove(@PathVariable List<String> notificationIds) {
        service.remove(notificationIds);
    }
}
