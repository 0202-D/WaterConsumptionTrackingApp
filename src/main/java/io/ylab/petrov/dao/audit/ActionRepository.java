package io.ylab.petrov.dao.audit;

import io.ylab.petrov.model.audit.Action;

import java.util.List;

public interface ActionRepository {

    void addAction(Action action);

    List<Action> getAllByUserName(String userName);
}
