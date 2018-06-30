package org.okky.notification.domain.model.reply;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Notification;
import org.okky.share.event.ReplyPinned;

import static lombok.AccessLevel.PRIVATE;
import static org.okky.notification.domain.model.IdGenerator.nextReplyPinnedNotiId;
import static org.okky.share.util.JsonUtil.toPrettyJson;

@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
@Getter
public class ReplyPinnedNoti extends Notification {
    String articleId;
    String articleWriterId;
    String articleWriterName;
    String replierId;
    String replierName;
    String pinMemo;
    long pinnedOn;

    public ReplyPinnedNoti(ReplyPinned event, Article article) {
        super(nextReplyPinnedNotiId(), event.getReplierId());
        this.articleId = article.getId();
        this.articleWriterId = article.getWriterId();
        this.articleWriterName = article.getWriterName();
        this.replierId = event.getReplierId();
        this.replierName = event.getReplierName();
        this.pinMemo = event.getPinMemo();
        this.pinnedOn = event.getPinnedOn();
    }

    // ---------------------------------------------------
    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static ReplyPinnedNoti sample() {
        ReplyPinned event = ReplyPinned.sample();
        Article article = Article.sample();
        return new ReplyPinnedNoti(event, article);
    }

    /**
     * 게시글 작성자가 자신의 답글을 고정했는지 여부
     *
     * @return 게시글 작성자 === 답글 작성자
     */
    public boolean didFixedYourself() {
        return articleWriterId.equals(replierId);
    }

    /**
     * 게시글 작성자가 다른 사람의 답글을 고정했는지 여부
     */
    public boolean didFixedOthers() {
        return !didFixedYourself();
    }
}
