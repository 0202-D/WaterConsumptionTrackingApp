package io.ylab.petrov.service.audit;

import io.ylab.petrov.model.audit.Action;

import java.util.List;

public interface AuditService {
    void addAction(Action action);

    List<Action> getAllByUserName(String userName);
}
