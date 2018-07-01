package org.okky.notification.domain.model.emotion;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.model.Article;
import org.okky.notification.domain.model.Emoter;
import org.okky.notification.domain.model.IdGenerator;
import org.okky.notification.domain.model.Notification;
import org.okky.share.event.Emoted;

import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.util.JsonUtil.toPrettyJson;

/**
 * target은 게시글, 프로필, 답글 등 모든 것이 될 수 있는데 이렇게 일반적이다보니 모델이 구체적이지 않고
 * 보편언어를 정확하게 반영하지 못하는 것으로 보인다. 추후 더 정교하게 리팩토링해야 할 필요가 있다.
 *
 * @author coding8282
 */
@NoArgsConstructor(access = PRIVATE)
@FieldDefaults(level = PRIVATE)
@Getter
public class EmotedNoti extends Notification {
    String targetId;
    String targetOwnerId;//ex) 게시글 작성자
    String targetOwnerName;
    String emoterId;//공감한 사람
    String emoterName;
    String emotionType;
    long emotedOn;

    public EmotedNoti(Emoted event, Article article, Emoter emoter) {
        super(IdGenerator.nextReplyPinnedNotiId(), article.getWriterId());
        this.targetId = article.getId();
        this.targetOwnerId = article.getWriterId();
        this.targetOwnerName = article.getWriterName();
        this.emoterId = event.getEmoterId();
        this.emoterName = emoter.getNickName();
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
        Emoter emoter = Emoter.sample();
        return new EmotedNoti(event, article, emoter);
    }

    /**
     * 다른 사람이 해당 게시글을 공감했는지 여부.
     * 자기 자신의 게시글을 공감했다면 굳이 알림을 줄 필요가 없다.
     */
    public boolean didEmotedByOthers() {
        return !targetOwnerId.equals(emoterId);
    }
}
