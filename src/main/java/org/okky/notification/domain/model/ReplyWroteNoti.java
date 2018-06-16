package org.okky.notification.domain.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.event.ReplyWrote;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static java.lang.String.format;
import static lombok.AccessLevel.PRIVATE;
import static org.okky.notification.domain.model.IdPrefixGenerator.replyWroteNotiId;
import static org.okky.notification.domain.model.ReplyWroteNotiContext.*;
import static org.okky.share.JsonUtil.toPrettyJson;

@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
@Getter
@Document
public class ReplyWroteNoti extends Notification {
    @Id
    String id;
    String articleId;
    String articleWriterId;
    String articleWriterName;
    String replierId;
    String replierName;
    long repliedOn;
    String replyBody;
    ReplyWroteNotiContext context;

    @Builder
    ReplyWroteNoti(String ownerId, ReplyWrote event, Article article) {
        super(ownerId);
        this.id = format("%s-%s-%d", replyWroteNotiId(), ownerId, event.getRepliedOn());
        this.articleId = article.getId();
        this.articleWriterId = article.getWriterId();
        this.articleWriterName = article.getWriterName();
        this.replierId = event.getReplierId();
        this.replierName = event.getReplierName();
        this.repliedOn = event.getRepliedOn();
        this.replyBody = format("%1.100s", event.getBody());
        this.context = calcMyContext();
    }

    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static ReplyWroteNoti sample() {
        ReplyWrote event = ReplyWrote.sample();
        Article article = Article.sample();
        ReplyWroteNoti noti = new ReplyWroteNoti("o-1", event, article);
        return noti;
    }

    /**
     * 알림을 제공하기에 적절한지 여부. 예를 들어, 자기가 쓴 게시글에 자기가 답변을 단 경우 굳이 알림을 줄 필요 없다.
     */
    public boolean isEligible() {
        return context != SELF;
    }

    // ----------------------------------------
    private ReplyWroteNotiContext calcMyContext() {
        if (replierId.equals(ownerId))
            return SELF;
        if (!articleWriterId.equals(replierId) && articleWriterId.equals(ownerId))
            return YOURS;
        if (!articleWriterId.equals(replierId))
            return EATCH_OTHER;
        else
            return ADVISORY;
    }
}
