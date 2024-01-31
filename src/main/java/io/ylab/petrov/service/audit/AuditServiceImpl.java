package io.ylab.petrov.service.audit;

import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.InMemoryActionRepositoryImpl;
import io.ylab.petrov.model.audit.Action;

import java.util.List;

public class AuditServiceImpl implements AuditService{
    private ActionRepository actionRepository = new InMemoryActionRepositoryImpl();
   public void addAction(Action action){
        actionRepository.addAction(action);
    }

    public List<Action> getAllByUserName(String userName){
       return actionRepository.getAllByUserName(userName);

    }
}
