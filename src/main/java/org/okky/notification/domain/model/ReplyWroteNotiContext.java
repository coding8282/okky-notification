package org.okky.notification.domain.model;

public enum ReplyWroteNotiContext {
    YOURS, // ex) 당신의 게시글에 coding8282님이 답글을 남겼습니다.
    ADVISORY, // ex) coding8282님이 본인 게시글에 답글을 남겼습니다.
    EACH_OTHER, // ex) coding8282님의 게시글에 올리버님이 답글을 남겼습니다.
    SELF, // ex) 답글자와 알림오너가 같은 경우
}
