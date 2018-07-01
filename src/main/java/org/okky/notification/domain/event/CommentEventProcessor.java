package org.okky.notification.domain.event;

import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Reply;
import org.okky.notification.domain.model.noti.ReplyCommentedNoti;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.NotiProxy;
import org.okky.share.event.ReplyCommented;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class CommentEventProcessor {
    NotiRepository repository;
    NotiProxy proxy;

    @EventListener
    void when(ReplyCommented event) {
        Reply reply = proxy.fetchReply(event.getReplyId());
        ReplyCommentedNoti noti = new ReplyCommentedNoti(event, reply);
        if (noti.wasCommentedByOthers())
            repository.save(noti);
    }
}
