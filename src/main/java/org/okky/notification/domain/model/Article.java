package org.okky.notification.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
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
}
