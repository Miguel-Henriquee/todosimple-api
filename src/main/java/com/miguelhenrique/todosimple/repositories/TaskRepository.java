package com.miguelhenrique.todosimple.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miguelhenrique.todosimple.models.Task;
import com.miguelhenrique.todosimple.models.projection.TaskProjection;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{

    List<TaskProjection> findByUser_Id(Long id);
    
}
