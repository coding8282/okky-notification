package org.okky.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.okky.share.JsonUtil;
import org.okky.share.domain.ValueObject;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
@Getter
public class Article implements ValueObject {
    String id;
    String writerId;
    String writerName;

    public static void main(String[] args) {
        System.out.println(JsonUtil.toPrettyJson(sample()));
    }

    public static Article sample() {
        String id = "a-1";
        String writerId = "m-3";
        String writerName = "coding8282";
        return new Article(id, writerId, writerName);
    }
}
