package com.miguelhenrique.todosimple.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.miguelhenrique.todosimple.models.Task;
import com.miguelhenrique.todosimple.models.User;
import com.miguelhenrique.todosimple.models.enums.ProfileEnum;
import com.miguelhenrique.todosimple.repositories.TaskRepository;
import com.miguelhenrique.todosimple.security.UserSpringSecurity;
import com.miguelhenrique.todosimple.services.exceptions.AuthorizationException;
import com.miguelhenrique.todosimple.services.exceptions.DataBindingViolationException;
import com.miguelhenrique.todosimple.services.exceptions.ObjectNotFoundException;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserSevice userSevice;

    public Task findById(Long id) {
        Task task = this.taskRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException(
                "Tarefa não encontrada! ID: " + id + ", Tipo: " + Task.class.getName()));

        UserSpringSecurity userSpringSecurity = UserSevice.authenticated();

        if (Objects.isNull(userSpringSecurity)
                || !userSpringSecurity.hasRole(ProfileEnum.ADMIN) && !userHasTask(userSpringSecurity, task))
            throw new AuthorizationException("Acesso negado!");

        return task;
    }

    public List<Task> findAllByUser() {
        UserSpringSecurity userSpringSecurity = UserSevice.authenticated();

        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
        
        List<Task> tasks = this.taskRepository.findByUser_Id(userSpringSecurity.getId());
        return tasks;
    }

    public List<Task> findAll() {
        UserSpringSecurity userSpringSecurity = UserSevice.authenticated();
        if (Objects.isNull(userSpringSecurity) || !userSpringSecurity.hasRole(ProfileEnum.ADMIN))
            throw new AuthorizationException("Acesso negado!");
        
        List<Task> tasks = this.taskRepository.findAll();
        return tasks;
    }

    @Transactional
    public Task create(Task obj) {
        UserSpringSecurity userSpringSecurity = UserSevice.authenticated();

        if (Objects.isNull(userSpringSecurity))
            throw new AuthorizationException("Acesso negado!");
        
        User user = userSevice.findById(userSpringSecurity.getId());
        obj.setId(null);
        obj.setUser(user);
        obj = this.taskRepository.save(obj);
        return obj;
    }

    @Transactional
    public Task update(Task obj) {
        Task newObj = findById(obj.getId());
        newObj.setDescription(obj.getDescription());
        return this.taskRepository.save(newObj);
    }

    public void delete(Long id) {
        findById(id);

        try {
            this.taskRepository.deleteById(id);
        } catch (Exception e) {
            throw new DataBindingViolationException("Não é possível excluir pois há entidades relacionadas!");
        }
    }

    private boolean userHasTask(UserSpringSecurity userSpringSecurity, Task task) {
        return task.getUser().getId().equals(userSpringSecurity.getId());
    }

}
