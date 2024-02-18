package io.ylab.petrov.service.audit;

import io.ylab.petrov.dao.audit.ActionRepository;
import io.ylab.petrov.dao.audit.JdbcActionRepository;
import io.ylab.petrov.model.audit.Action;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AuditServiceImpl implements AuditService{
    private final ActionRepository actionRepository;
   public void addAction(Action action){
       actionRepository.addAction(action);
    }

    public List<Action> getAllByUserName(String userName){
       return actionRepository.getAllByUserName(userName);
    }
}
