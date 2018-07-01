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
public class Reply implements ValueObject {
    String id;
    String articleId;
    String replierId;
    String replierName;

    public static void main(String[] args) {
        System.out.println(toPrettyJson(sample()));
    }

    public static Reply sample() {
        String id = "a-1";
        String articleId = "m-3";
        String replierId = "coding8282";
        String replierName = "coding8282";
        return new Reply(id, articleId, replierId, replierName);
    }
}
