package io.github.playeriv65.todobackend;

import io.github.playeriv65.todobackend.entity.Todo;
import io.github.playeriv65.todobackend.exception.ResourceNotFoundException;
import io.github.playeriv65.todobackend.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PatchMapping("/{id}")
    public Todo update(@PathVariable long id, @RequestBody Todo todoUpdate) {
        Todo todoExisting = todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Update failed with id: " + id));

        if (todoUpdate.getName() != null) {
            todoExisting.setName(todoUpdate.getName());
        }
        if (todoUpdate.getFinished() != null) {
            todoExisting.setFinished(todoUpdate.getFinished());
        }

        return todoRepository.save(todoExisting);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        if (!todoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Delete failed with id: " + id);
        }
        todoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public Todo getById(@PathVariable long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Get failed with id: " + id));
    }
}
