package com.valeo.task.service;

import com.valeo.task.entity.Task;
import com.valeo.task.exception.RequestBodyMissingException;
import com.valeo.task.exception.RequestParamMissingException;
import com.valeo.task.exception.TaskNotFoundException;
import com.valeo.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class TaskService {


    private TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Page<Task> listTasks(Pageable pageable)
    {
        Page<Task> tasks =this.taskRepository.findAll(pageable);
        return tasks;
    }
    @Transactional
    public Task addNewTask(String title, String description, String status, Date dueDate)
    {
        if (title == null || title.isBlank())
        {
            throw new RequestBodyMissingException("title");
        }
        if (dueDate == null)
        {
            throw new RequestBodyMissingException("dueDate");
        }
        Task newTask = new Task();
        newTask.setTitle(title);
        newTask.setDescription(description);
        if (status == null || status.isEmpty() || status.isBlank())
        {
            status = "Pending";
        }
        newTask.setStatus(status);
        newTask.setDueDate(dueDate);
        this.taskRepository.save(newTask);
        return newTask;
    }
    public Task getTaskById(Integer id)
    {
        if (id == null) {
            throw new RequestParamMissingException("id");
        }

        Task task = this.taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException("task id not found -id: " + id));
        return task;


    }
    public Task updateTaskStatus(Integer id, String newStatus)
    {
        if (id == null) {
            throw new RequestParamMissingException("id");
        }

        if (newStatus == null || newStatus.isBlank())
        {
            throw new RequestParamMissingException("newStatus");
        }

        Task task = this.taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException("task id not found -id: " + id));
        task.setStatus(newStatus);
        this.taskRepository.save(task);
        return task;
    }

    public Task removeTask(Integer id)
    {
        if (id == null) {
            throw new RequestParamMissingException("id");
        }
        Task task = this.taskRepository.findById(id).orElseThrow(()-> new TaskNotFoundException("task id not found -id: " + id));
        this.taskRepository.deleteById(id);
        return task;
    }
}
