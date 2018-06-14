package org.okky.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import static java.lang.System.currentTimeMillis;
import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor
@FieldDefaults(level = PROTECTED)
@Getter
public abstract class Notification {
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
}
