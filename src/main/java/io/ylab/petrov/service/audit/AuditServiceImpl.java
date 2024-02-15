package io.ylab.petrov.service.audit;

import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.model.audit.Action;

import java.util.List;

public class AuditServiceImpl implements AuditService{
    private ActionRepository actionRepository;
   public void addAction(Action action){
       actionRepository = new JdbcActionRepository();
       actionRepository.addAction(action);
    }

    public List<Action> getAllByUserName(String userName){
        actionRepository = new JdbcActionRepository();
       return actionRepository.getAllByUserName(userName);

    }
}
