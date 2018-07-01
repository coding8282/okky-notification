package org.okky.notification.domain.event;

import lombok.experimental.FieldDefaults;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.okky.notification.TestMother;
import org.okky.notification.domain.repository.NotiRepository;
import org.okky.notification.domain.service.NotiAssembler;
import org.okky.notification.domain.service.NotiProxy;

import static lombok.AccessLevel.PROTECTED;

@RunWith(MockitoJUnitRunner.class)
@FieldDefaults(level = PROTECTED)
public abstract class EventProcessorTestMother extends TestMother {
    @Mock
    NotiRepository repository;
    @Mock
    NotiAssembler assembler;
    @Mock
    NotiProxy proxy;
}