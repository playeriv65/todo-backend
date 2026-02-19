package io.github.playeriv65.todobackend.repository;

import io.github.playeriv65.todobackend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{

}
