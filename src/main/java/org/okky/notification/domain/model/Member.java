package org.okky.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.domain.ValueObject;

import static java.lang.System.currentTimeMillis;
import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.JsonUtil.toPrettyJson;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Getter
public class Member implements ValueObject {
    String id;
    String type;
    String nickName;
    String motto;
    String description;
    boolean blocked;
    Long joinedOn;

    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static Member sample() {
        String id = "a-1";
        String type = "m-3";
        String nickName = "coding8282";
        String motto = "coding8282";
        String description = "coding8282";
        boolean blocked = false;
        Long joinedOn = currentTimeMillis();
        return new Member(id, type, nickName, motto, description, blocked, joinedOn);
    }
}
