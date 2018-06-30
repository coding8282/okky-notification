package org.okky.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.JsonUtil;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import static java.lang.System.currentTimeMillis;
import static lombok.AccessLevel.PROTECTED;

/**
 * 기본 정렬은 알림시각(notifiedOn)으로 내림차순.
 *
 * @author coding8282
 */
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PROTECTED)
@Getter
@Document
public class Notification implements Comparable<Notification> {
    @Id
    String id;
    boolean read;
    boolean hidden;
    Long readOn;
    Long hiddenOn;
    long notifiedOn;
    String ownerId;
    String event;

    protected Notification(String ownerId) {
        this.read = false;
        this.hidden = false;
        this.readOn = null;
        this.hiddenOn = null;
        this.notifiedOn = currentTimeMillis();
        this.ownerId = ownerId;
        this.event = getClass().getSimpleName();
    }

    public static void main(String[] args) {
        System.out.println(JsonUtil.toPrettyJson(sample()));
    }

    public static Notification sample() {
        return new Notification("o-1");
    }

    public void markRead() {
        this.read = true;
        this.readOn = currentTimeMillis();
    }

    public void markUnread() {
        this.read = false;
        this.readOn = null;
    }

    public void toggleRead() {
        if (read)
            markUnread();
        else
            markRead();
    }

    @Override
    public int compareTo(Notification o) {
        return -(int) (notifiedOn - o.notifiedOn);
    }
}
