package org.okky.notification.domain.event;

import lombok.experimental.FieldDefaults;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.NotiAssembler;
import org.okky.notification.domain.service.NotiProxy;
import org.springframework.beans.factory.annotation.Autowired;

import static lombok.AccessLevel.PROTECTED;

@FieldDefaults(level = PROTECTED)
abstract class EventProcessor {
    @Autowired
    NotiRepository repository;
    @Autowired
    NotiAssembler assembler;
    @Autowired
    NotiProxy proxy;
}
