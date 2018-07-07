package org.okky.notification.domain.model.changelog;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.domain.Aggregate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

import static java.lang.String.format;
import static java.lang.System.currentTimeMillis;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;
import static org.okky.notification.domain.model.changelog.ChangeType.FEATURE;
import static org.okky.share.domain.AssertionConcern.*;
import static org.okky.share.util.JsonUtil.toPrettyJson;

@NoArgsConstructor(access = PROTECTED)
@EqualsAndHashCode(of = "id")
@FieldDefaults(level = PRIVATE)
@Getter
@Document
public class ChangeLog implements Aggregate {
    @Id
    String id;
    String body;
    ChangeType type;
    long logedOn;

    public ChangeLog(String body, ChangeType type) {
        setId("n-" + UUID.randomUUID().toString().replaceAll("-", "").substring(0, 15));
        setBody(body);
        setType(type);
        setLogedOn(currentTimeMillis());
    }

    public static ChangeLog sample() {
        String body = "새로운 기능이 추가되었습니다.";
        ChangeType type = FEATURE;
        return new ChangeLog(body, type);
    }

    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public void update(String newBody, ChangeType newType) {
        setBody(newBody);
        setType(newType);
    }

    // ---------------------------------
    private void setId(String id) {
        assertArgNotNull(id, "id는 필수입니다.");
        this.id = id;
    }

    private void setBody(String body) {
        assertArgNotEmpty(body, "바디는 필수입니다.");
        String trimed = body.trim();
        assertArgLength(trimed, 200, format("내용은 %d자까지만 가능합니다: 현재 %d자", 200, trimed.length()));
        this.body = trimed;
    }

    private void setLogedOn(long logedOn) {
        this.logedOn = logedOn;
    }

    private void setType(ChangeType type) {
        assertArgNotNull(type, "변경 유형은 필수입니다.");
        this.type = type;
    }
}
