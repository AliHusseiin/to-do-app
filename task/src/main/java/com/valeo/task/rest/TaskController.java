package com.valeo.task.rest;

import com.valeo.task.dto.ApiResponseDTO;
import com.valeo.task.dto.TaskRequestDTO;
import com.valeo.task.entity.Task;
import com.valeo.task.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class TaskController {
    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {

        this.taskService = taskService;
    }

    @GetMapping("/tasks")
    public ResponseEntity<ApiResponseDTO<Page<Task>>> listTasks(@RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "5") int size,
                                                                @RequestParam(defaultValue = "id") String sortBy)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, sortBy));
        Page<Task> tasks = taskService.listTasks(pageable);
        return ResponseEntity.ok(new ApiResponseDTO<> (200, "Success", tasks));
    }

    @PostMapping("/new-task")
    public ResponseEntity<ApiResponseDTO<Task>> addNewTask(@RequestBody(required = false) TaskRequestDTO request)
    {
        Task task = this.taskService.addNewTask(request.getTitle(), request.getDescription(), request.getStatus(), request.getDueDate());
        ApiResponseDTO<Task> response = new ApiResponseDTO<>(HttpStatus.CREATED.value(), "Task created successfully", task);

        return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);
    }

    @GetMapping("/task")
    public ResponseEntity<ApiResponseDTO<Task>> getTaskById(@RequestParam(required = false) Integer id)
    {
        Task task = this.taskService.getTaskById(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Success", task));
    }
    @PutMapping("/task-status")
    public ResponseEntity<ApiResponseDTO<Task>> updateTaskStatus(@RequestParam(required = false) Integer id, @RequestParam(required = false) String newStatus)
    {
        Task task = this.taskService.updateTaskStatus(id, newStatus);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Task updated successfully", task));

    }
    @DeleteMapping("/end-task")
    public ResponseEntity<ApiResponseDTO<Task>> removeTask(@RequestParam(required = false) Integer id)
    {
        Task task = this.taskService.removeTask(id);
        return ResponseEntity.ok(new ApiResponseDTO<>(200, "Task removed successfully", task));
    }


}
