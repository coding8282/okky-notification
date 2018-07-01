package org.okky.notification.domain.model.noti;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.IdGenerator;
import org.okky.notification.domain.model.Notification;
import org.okky.notification.domain.model.Reply;
import org.okky.share.event.ReplyCommented;

import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.util.JsonUtil.toPrettyJson;

@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
@Getter
public class ReplyCommentedNoti extends Notification {
    String replyId;
    String articleId;
    String commentBody;
    String commenterId;
    String commenterName;
    String replierId;
    String replierName;
    long commentedOn;
    Long updatedOn;

    public ReplyCommentedNoti(ReplyCommented event, Reply reply) {
        super(IdGenerator.nextReplyCommentedNotiId(), reply.getReplierId());
        this.replyId = reply.getId();
        this.articleId = reply.getArticleId();
        this.commentBody = event.getBody();
        this.commenterId = event.getCommenterId();
        this.commenterName = event.getCommenterName();
        this.replierId = reply.getReplierId();
        this.replierName = reply.getReplierName();
        this.commentedOn = event.getCommentedOn();
        this.updatedOn = event.getUpdatedOn();
    }

    // ---------------------------------------------------
    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static ReplyCommentedNoti sample() {
        ReplyCommented event = ReplyCommented.sample();
        Reply reply = Reply.sample();
        return new ReplyCommentedNoti(event, reply);
    }

    /**
     * 답글자가 아닌 제 3자가 코멘트를 달았는지 여부.
     * 답글자가 자신의 글에 코멘트를 달면 굳이 알림을 줄 필요는 없다.
     */
    public boolean wasCommentedByOthers() {
        return !replierId.equals(commenterId);
    }
}
