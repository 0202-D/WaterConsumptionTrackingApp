package io.ylab.petrov.dao.audit;

import io.ylab.petrov.model.audit.Action;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InMemoryActionRepositoryImpl implements ActionRepository {
    private final List<Action> actions = new ArrayList<>();

    @Override
    public void addAction(Action action) {
        actions.add(action);
    }

    @Override
    public List<Action> getAllByUserName(String userName) {
        return actions
                .stream().filter(el -> el.getUser().getUserName().equals(userName))
                .collect(Collectors.toList());
    }
}

