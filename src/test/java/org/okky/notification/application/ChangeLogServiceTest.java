package org.okky.notification.application;

import lombok.experimental.FieldDefaults;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.okky.notification.TestMother;
import org.okky.notification.application.command.UpdateChangeLogCommand;
import org.okky.notification.application.command.WriteChangeLogCommand;
import org.okky.notification.domain.model.changelog.ChangeLog;
import org.okky.notification.domain.model.changelog.ChangeType;
import org.okky.notification.domain.repository.ChangeLogRepository;
import org.okky.notification.domain.service.ChangeLogConstraint;
import org.powermock.modules.junit4.PowerMockRunner;

import static lombok.AccessLevel.PRIVATE;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@FieldDefaults(level = PRIVATE)
public class ChangeLogServiceTest extends TestMother {
    @InjectMocks
    ChangeLogService service;
    @Mock
    ChangeLogRepository repository;
    @Mock
    ChangeLogConstraint constraint;
    @Mock
    ModelMapper mapper;
    @Mock
    ChangeLog log;
    @Captor
    ArgumentCaptor<ChangeType> captor;

    @Test
    public void write() {
        WriteChangeLogCommand cmd = new WriteChangeLogCommand("b", "FEATURE");
        when(mapper.toModel(cmd)).thenReturn(log);

        service.write(cmd);

        InOrder o = inOrder(mapper, repository);
        o.verify(mapper).toModel(cmd);
        o.verify(repository).save(log);
        o.verifyNoMoreInteractions();
    }

    @Test
    public void update() {
        UpdateChangeLogCommand cmd = new UpdateChangeLogCommand("l", "b", "FEATURE");
        when(constraint.checkExistsAndGet("l")).thenReturn(log);

        service.update(cmd);

        InOrder o = inOrder(constraint, log);
        o.verify(constraint).checkExistsAndGet("l");
        o.verify(log).update(eq("b"), captor.capture());
        o.verifyNoMoreInteractions();

        assertThat("인자로는 FEATURE가 넘어가야 한다.", captor.getValue(), is(ChangeType.FEATURE));
    }

    @Test
    public void delete() {
        service.delete("l");

        InOrder o = inOrder(repository);
        o.verify(repository).deleteById("l");
        o.verifyNoMoreInteractions();
    }
}