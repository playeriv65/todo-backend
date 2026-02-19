package io.github.playeriv65.todobackend;

import io.github.playeriv65.todobackend.entity.Todo;
import io.github.playeriv65.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    @Autowired
    private TodoRepository todoRepository;

    @GetMapping
    public List<Todo> getAll(){
        return todoRepository.findAll();
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo) {
        return todoRepository.save(todo);
    }
}
