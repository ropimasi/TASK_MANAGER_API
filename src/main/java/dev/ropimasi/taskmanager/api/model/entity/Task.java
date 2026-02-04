package dev.ropimasi.taskmanager.api.model.entity;

import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
//@Table(name = "task")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 128)
	private String title;

	@Column(columnDefinition = "TEXT")
	private String description;

	@Column(nullable = false, columnDefinition = "boolean default false")
	private boolean completed = false;

	@Column(nullable = false, updatable = false, columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP")
	@CreationTimestamp
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP")
	@UpdateTimestamp
	private LocalDateTime updatedAt;

	// --- Construtores ---
	public Task() {
	}


	public Task(String title, String description) {
		this.title = title;
		this.description = description;
	}


	// --- Getters e Setters ---
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public boolean isCompleted() {
		return completed;
	}


	public void setCompleted(boolean completed) {
		this.completed = completed;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
}
