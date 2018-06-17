package org.okky.notification.domain.event;

import com.jayway.jsonpath.JsonPath;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import static lombok.AccessLevel.PRIVATE;
import static org.okky.share.util.JsonUtil.fromJson;
import static org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Component
@AllArgsConstructor
@FieldDefaults(level = PRIVATE)
class SQSConsumer {
    ApplicationEventPublisher publisher;

    @SqsListener(value = "${app.queue.notification}", deletionPolicy = ON_SUCCESS)
    @SneakyThrows
    void consume(String json) {
        String message = JsonPath.read(json, "$.Message").toString();
        String eventName = JsonPath.read(message, "$.eventName");
        Object event = fromJson(message, eventName);
        publisher.publishEvent(event);
    }
}
