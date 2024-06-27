package com.miguelhenrique.todosimple.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.miguelhenrique.todosimple.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{}
