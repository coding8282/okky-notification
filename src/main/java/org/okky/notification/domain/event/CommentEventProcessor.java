package org.okky.notification.domain.event;

import org.okky.notification.domain.model.Reply;
import org.okky.notification.domain.model.noti.ReplyCommentedNoti;
import org.okky.share.event.ReplyCommented;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
class CommentEventProcessor extends EventProcessor {
    @EventListener
    void when(ReplyCommented event) {
        Reply reply = proxy.fetchReply(event.getReplyId());
        ReplyCommentedNoti noti = new ReplyCommentedNoti(event, reply);
        if (noti.wasCommentedByOthers())
            repository.save(noti);
    }
}
