package org.okky.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.domain.ValueObject;

import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.JsonUtil.toPrettyJson;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Getter
public class Emoter implements ValueObject {
    String id;
    String nickName;

    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static Emoter sample() {
        String id = "a-1";
        String nickName = "coding8282";
        return new Emoter(id, nickName);
    }
}
