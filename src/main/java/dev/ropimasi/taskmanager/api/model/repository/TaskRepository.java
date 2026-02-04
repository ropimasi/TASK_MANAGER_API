package dev.ropimasi.taskmanager.api.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.ropimasi.taskmanager.api.model.entity.Task;



@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
