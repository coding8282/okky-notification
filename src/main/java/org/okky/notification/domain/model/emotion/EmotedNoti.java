package org.okky.notification.domain.model.emotion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.IdGenerator;
import org.okky.notification.domain.model.Notification;
import org.okky.share.event.Emoted;

import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.util.JsonUtil.toPrettyJson;

@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
@Getter
public class EmotedNoti extends Notification {
    String targetId;
    String targetOwnerId;
    String targetOwnerName;
    String emoterId;//공감한 사람
    String emotionType;
    long emotedOn;

    public EmotedNoti(Emoted event, Article article) {
        super(IdGenerator.nextReplyPinnedNotiId(), article.getWriterId());
        this.targetId = article.getId();
        this.targetOwnerId = article.getWriterId();
        this.targetOwnerName = article.getWriterName();
        this.emoterId = event.getEmoterId();
        this.emotionType = event.getType();
        this.emotedOn = event.getEmotedOn();
    }

    // ---------------------------------------------------
    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static EmotedNoti sample() {
        Emoted event = Emoted.sample();
        Article article = Article.sample();
        return new EmotedNoti(event, article);
    }

    /**
     * 게시글 작성자가 다른 사람의 게시글을 공감했는지 여부
     */
    public boolean didEmotedByOthers() {
        return !targetOwnerId.equals(emoterId);
    }
}
