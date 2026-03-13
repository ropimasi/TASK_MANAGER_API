package dev.ropimasi.taskmanagerapi.api.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskPatchingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.entity.Task;



public class TaskMapperTest {

	private final TaskMapper taskMapper = new TaskMapper();


	@Test
	void Given_TaskCreatingRequestDto_When_toEntity_Then_return_a_properly_fulfilled_Task() {
		// GIVEN
		// Default: completed = false.
		TaskCreatingRequestDto dto = new TaskCreatingRequestDto("Study TDD", "Unit tests are important");

		// ACTION
		Task task = taskMapper.toEntity(dto);

		// ASSERTIONS
		Assertions.assertNotNull(task);
		Assertions.assertEquals(dto.title(), task.getTitle());
		Assertions.assertEquals(dto.description(), task.getDescription());
		Assertions.assertFalse(task.getCompleted());
	}
	
	
	@Test
	void Given_a_Task_and_a_TaskUpdatingRequestDto_When_updateEntityFromDto_return_a_properly_fulfilled_Task() {
	    // GIVEN
	    Task task = new Task();
	    task.setId(1L);
	    task.setTitle("Old Title");
	    task.setDescription("Old Description");
	    task.setCompleted(false);

	    TaskUpdatingRequestDto updateDto = new TaskUpdatingRequestDto(
	            "New Professional Title", 
	            "New Detailed Description", 
	            true
	    );

	    // ACTION
	    taskMapper.updateEntityFromDto(task, updateDto);

	    // ASSERTIONS
	    Assertions.assertEquals("New Professional Title", task.getTitle(), "Title should be updated");
	    Assertions.assertEquals("New Detailed Description", task.getDescription(), "Description should be updated");
	    Assertions.assertTrue(task.getCompleted(), "Status should be updated to true");
	}
	
	
	@Test
	void Given_a_Task_and_a_TaskPatchingRequestDto_When_updateEntityFromPatchDto_return_a_properly_updated_Task() {
	    // GIVEN
	    Task originalTask = new Task(1L, "Original Title", "Original Description", false, null, null);
	    TaskPatchingRequestDto patchDto = new TaskPatchingRequestDto(null, null, true);

	    // ACTION
	    taskMapper.updateEntityFromPatchDto(originalTask, patchDto);

	    // ASSERTIONS
	    Assertions.assertEquals("Original Title", originalTask.getTitle()); // Should not be changed to null!
	    Assertions.assertEquals("Original Description", originalTask.getDescription()); // Should not be changed to null!
	    Assertions.assertTrue(originalTask.getCompleted()); // This one should be updated.
	}
	

}
