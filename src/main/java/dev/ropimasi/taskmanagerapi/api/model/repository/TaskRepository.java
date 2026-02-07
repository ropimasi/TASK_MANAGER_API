package dev.ropimasi.taskmanagerapi.api.model.repository;

import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.ropimasi.taskmanagerapi.api.model.entity.Task;



@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
	
	// I do usage of JpaRepository query-methods.
	List<Task> findByCompletedTrue();
	List<Task> findByCompletedFalse();
	List<Task> findByTitleContainingIgnoreCase(String title, Sort sort);
}
