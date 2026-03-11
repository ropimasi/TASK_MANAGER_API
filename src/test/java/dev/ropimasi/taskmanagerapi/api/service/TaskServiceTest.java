package dev.ropimasi.taskmanagerapi.api.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Sort;
import dev.ropimasi.taskmanagerapi.api.core.TaskMapper;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskCreatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskPatchingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskPatchingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskRecoveringResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingRequestDto;
import dev.ropimasi.taskmanagerapi.api.model.dto.TaskUpdatingResponseDto;
import dev.ropimasi.taskmanagerapi.api.model.entity.Task;
import dev.ropimasi.taskmanagerapi.api.model.repository.TaskRepository;
import dev.ropimasi.taskmanagerapi.api.service.exception.ResourceNotFoundException;


@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {

	@Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;
    
    @Mock
    private TaskMapper taskMapper;
	
	@Test
	public void Given_valid_id_When_findTaskById_Then_return_TaskRecoveringResponseDto() {
		// GIVEN
        Long taskId = 1L;
        Task taskEntity = new Task(taskId, "Study JUnit", "Complete the tests", false, null, null);
        TaskRecoveringResponseDto expectedDto = new TaskRecoveringResponseDto(taskId, "Study JUnit", "Complete the tests", false, null);

        // CONFIG
        when(taskRepository.findById(taskId)).thenReturn(Optional.of(taskEntity));
        when(taskMapper.toRecoveringResponseDto(taskEntity)).thenReturn(expectedDto);

        // ACTION
        TaskRecoveringResponseDto resultDto = taskService.findTaskById(taskId);

        // ASSERTIONS
        Assertions.assertNotNull(resultDto);
        Assertions.assertEquals(expectedDto.id(), resultDto.id());
        Assertions.assertEquals(expectedDto.title(), resultDto.title());
        Assertions.assertEquals(expectedDto.description(), resultDto.description());
        Mockito.verify(taskRepository, Mockito.times(1)).findById(taskId);
        Mockito.verify(taskMapper, Mockito.times(1)).toRecoveringResponseDto(taskEntity);
	}
	
	
	@Test
	public void Given_nonexistent_id_When_findTaskById_Then_throw_ResourceNotFoundException() {
		// GIVEN
		Long nonExistentTaskId = 999L;
		
		// CONFIG
		when(taskRepository.findById(nonExistentTaskId)).thenReturn(Optional.empty());
		
		// ACTION & ASSERTIONS
		ResourceNotFoundException exception = Assertions.assertThrows(
				ResourceNotFoundException.class,
				() -> taskService.findTaskById(nonExistentTaskId));
		Assertions.assertEquals("Task not found with id: 999", exception.getMessage());
		Mockito.verify(taskRepository, Mockito.times(1)).findById(nonExistentTaskId);
		Mockito.verifyNoInteractions(taskMapper);		
	}
	
	
	@Test
	public void Given_valid_TaskCreatingRequestDto_When_saveNewTask_Then_return_TaskCreatingResponseDto() {
	    // GIVEN
		TaskCreatingRequestDto requestDto = new TaskCreatingRequestDto("New Task", "Task description");
		Task taskToSave = new Task(requestDto.title(), requestDto.description());
		Task savedTask = new Task(1L, requestDto.title(), requestDto.description(), false, null, null);
		TaskCreatingResponseDto expectedResponseDto = new TaskCreatingResponseDto(1L, requestDto.title(), requestDto.description(), false);
		
		// CONFIG
		when(taskMapper.toEntity(requestDto)).thenReturn(taskToSave);
		when(taskRepository.save(taskToSave)).thenReturn(savedTask);
        when(taskMapper.toCreatingResponseDto(savedTask)).thenReturn(expectedResponseDto);

        // ACTION
        TaskCreatingResponseDto resultDto = taskService.saveNewTask(requestDto);
        
        // ASSERTIONS
        Assertions.assertNotNull(resultDto);
        Assertions.assertEquals(expectedResponseDto.id(), resultDto.id());
        Assertions.assertEquals(expectedResponseDto.title(), resultDto.title());
        Assertions.assertEquals(expectedResponseDto.description(), resultDto.description());
        Assertions.assertEquals(expectedResponseDto.completed(), resultDto.completed());
        Mockito.verify(taskMapper, Mockito.times(1)).toEntity(requestDto);
        Mockito.verify(taskRepository, Mockito.times(1)).save(taskToSave);
        Mockito.verify(taskMapper, Mockito.times(1)).toCreatingResponseDto(savedTask);
	}
	

	@Test
	public void Given_existent_id_When_deleteTask_Then_call_repository_deleteById() {
	    // GIVEN
	    Long taskId = 1L;
	    
	    // CONFIG
	    when(taskRepository.existsById(taskId)).thenReturn(true);

	    // ACTION
	    taskService.deleteTask(taskId);

	    // ASSERTIONS
	    Mockito.verify(taskRepository, Mockito.times(1)).existsById(taskId);
	    Mockito.verify(taskRepository, Mockito.times(1)).deleteById(taskId);
	    Mockito.verifyNoInteractions(taskMapper);
	}
	
	
	@Test
	public void Given_nonexistent_id_When_deleteTask_Then_throw_ResourceNotFoundException() {
		// GIVEN
		Long nonExistentTaskId = 999L;
		
		// CONFIG
		when(taskRepository.existsById(nonExistentTaskId)).thenReturn(false);
		
		// ACTION & ASSERTIONS
		ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.deleteTask(nonExistentTaskId));
		Assertions.assertEquals("Cannot delete. Task not found with id: 999", exception.getMessage());
		Mockito.verify(taskRepository, Mockito.times(1)).existsById(nonExistentTaskId);
		Mockito.verify(taskRepository, Mockito.never()).deleteById(nonExistentTaskId);
		Mockito.verifyNoInteractions(taskMapper);
	}
	
	
	@Test
	public void Given_tasks_When_findAllTasks_Then_return_list_of_DTOs() {
	    // GIVEN
	    Sort sort = Sort.by("createdAt").ascending();
	    Task task1 = new Task(1L, "Task 1", "Description 1", false, LocalDateTime.now(), null);
	    Task task2 = new Task(2L, "Task 2", "Description 2", true, LocalDateTime.now(), null);
	    
	    List<Task> taskList = List.of(task1, task2);
	    
	    TaskRecoveringResponseDto dto1 = new TaskRecoveringResponseDto(1L, "Task 1", "Description 1", false, LocalDateTime.now());
	    TaskRecoveringResponseDto dto2 = new TaskRecoveringResponseDto(2L, "Task 2", "Description 2", true, LocalDateTime.now());

	    // CONFIG
	    when(taskRepository.findAll(sort)).thenReturn(taskList);
	    when(taskMapper.toRecoveringResponseDto(task1)).thenReturn(dto1);
	    when(taskMapper.toRecoveringResponseDto(task2)).thenReturn(dto2);

	    // ACTION
	    List<TaskRecoveringResponseDto> result = taskService.findAllTasks(sort);

	    // ASSERTIONS
	    Assertions.assertNotNull(result);
	    Assertions.assertEquals(2, result.size());
	    Assertions.assertEquals("Task 1", result.get(0).title());
	    Assertions.assertEquals("Description 1", result.get(0).description());
	    Assertions.assertEquals("Task 2", result.get(1).title());
	    Assertions.assertEquals("Description 2", result.get(1).description());
	    Mockito.verify(taskRepository, Mockito.times(1)).findAll(sort);
	    Mockito.verify(taskMapper, Mockito.times(2)).toRecoveringResponseDto(Mockito.any(Task.class));
	}
	
	
	@Test
	public void Given_no_tasks_When_findAllTasks_Then_return_empty_list() {
	    // GIVEN
	    Sort sort = Sort.by("createdAt").ascending();
	    
	    // CONFIG
	    when(taskRepository.findAll(sort)).thenReturn(List.of());

	    // ACTION
	    List<TaskRecoveringResponseDto> result = taskService.findAllTasks(sort);

	    // ASSERTIONS
	    Assertions.assertNotNull(result);
	    Assertions.assertTrue(result.isEmpty());
	    Mockito.verify(taskRepository, Mockito.times(1)).findAll(sort);
	    // Mapper must never be called if the List is empty.
	    Mockito.verifyNoInteractions(taskMapper);
	}
	
	
	@Test
	public void Given_matching_title_When_findTasksByTitleContainingIgnoreCase_Then_return_filtered_list() {
	    // GIVEN
	    String searchTerm = "Java";
	    Sort sort = Sort.by("title").ascending();
	    
	    Task task1 = new Task(1L, "Learn Java", "Spring Boot studies", false, LocalDateTime.now(), null);
	    Task task2 = new Task(2L, "Estuding Postgres", "Some PostgreSQL studies", false, LocalDateTime.now(), null);
	    Task task3 = new Task(3L, "Java Advanced", "Internal details", true, LocalDateTime.now(), null);
	    
	    // "Java" was searched, so only task1 and task3 should be returned.
	    List<Task> foundTasks = List.of(task1, task3);
	    
	    TaskRecoveringResponseDto dto1 = new TaskRecoveringResponseDto(1L, "Learn Java", "Spring Boot studies", false, LocalDateTime.now());
	    TaskRecoveringResponseDto dto2 = new TaskRecoveringResponseDto(2L, "Estuding Postgres", "Some PostgreSQL studies", true, LocalDateTime.now());
	    TaskRecoveringResponseDto dto3 = new TaskRecoveringResponseDto(3L, "Java Advanced", "Internal details", true, LocalDateTime.now());

	    // CONFIG
	    when(taskRepository.findByTitleContainingIgnoreCase(searchTerm, sort)).thenReturn(foundTasks);
	    when(taskMapper.toRecoveringResponseDto(task1)).thenReturn(dto1);
	    // "Java" was searched, so task2 should not be mapped to a DTO, as it won't be in the result list.
	    //when(taskMapper.toRecoveringResponseDto(task2)).thenReturn(dto2);
	    when(taskMapper.toRecoveringResponseDto(task3)).thenReturn(dto3);

	    // ACTION
	    List<TaskRecoveringResponseDto> result = taskService.findTasksByTitleContainingIgnoreCase(searchTerm, sort);

	    // ASSERTIONS
	    Assertions.assertNotNull(result);
	    Assertions.assertEquals(2, result.size());
	    Assertions.assertTrue(result.get(0).title().contains(searchTerm));
	    Assertions.assertTrue(result.get(1).title().contains(searchTerm));
	    Mockito.verify(taskRepository, Mockito.times(1)).findByTitleContainingIgnoreCase(searchTerm, sort);
	    // "Java" was searched, so only task1 and task3 should be mapped to DTOs.
	    Mockito.verify(taskMapper, Mockito.times(2)).toRecoveringResponseDto(Mockito.any(Task.class));
	}
	
	
	@Test
	public void Given_valid_id_and_TaskUpdatingRequestDto_When_updateTask_Then_return_TaskUpdatingResponseDto() {
	    // GIVEN
	    Long taskId = 1L;
	    TaskUpdatingRequestDto requestDto = new TaskUpdatingRequestDto("Updated Title", "Updated Description", true);
	    Task existingTask = new Task(taskId, "Old Title", "Old Description", false, LocalDateTime.now(), null);
	    TaskUpdatingResponseDto expectedResponse = new TaskUpdatingResponseDto(taskId, "Updated Title", "Updated Description", true, LocalDateTime.now());

	    // CONFIG
	    when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
	    doNothing().when(taskMapper).updateEntityFromDto(existingTask, requestDto);
	    when(taskRepository.save(existingTask)).thenReturn(existingTask);
	    when(taskMapper.toUpdatingResponseDto(existingTask)).thenReturn(expectedResponse);

	    // ACTION
	    TaskUpdatingResponseDto result = taskService.updateTask(taskId, requestDto);

	    // ASSERTIONS
	    Assertions.assertNotNull(result);
	    Assertions.assertEquals("Updated Title", result.title());
	    Assertions.assertEquals("Updated Description", result.description());
	    Assertions.assertTrue(result.completed());
	    Mockito.verify(taskRepository, Mockito.times(1)).findById(taskId);
	    Mockito.verify(taskMapper, Mockito.times(1)).updateEntityFromDto(existingTask, requestDto);
	    Mockito.verify(taskRepository, Mockito.times(1)).save(existingTask);
	}
	
	
	@Test
	public void Given_nonexistent_id_When_updateTask_Then_throw_ResourceNotFoundException() {
		// GIVEN
        Long nonExistentTaskId = 999L;
        TaskUpdatingRequestDto requestDto = new TaskUpdatingRequestDto("Updated Title", "Updated Description", true);

        // CONFIG
        when(taskRepository.findById(nonExistentTaskId)).thenReturn(Optional.empty());
        
        // ACTION & ASSERTIONS
        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.updateTask(nonExistentTaskId, requestDto));
        Assertions.assertEquals("Cannot update. Task not found with id: 999", exception.getMessage());
        Mockito.verify(taskRepository, Mockito.times(1)).findById(nonExistentTaskId);
		Mockito.verifyNoInteractions(taskMapper);
		Mockito.verify(taskRepository, Mockito.never()).save(Mockito.any());
	}
	
	
	@Test
	public void Given_valid_id_and_partial_dto_When_patchTask_Then_update_only_provided_fields() {
	    // GIVEN
	    Long taskId = 1L;

	    TaskPatchingRequestDto patchDto = new TaskPatchingRequestDto("New Title", null, null);
	    
	    Task existingTask = new Task(taskId, "Old Title", "Old Description", false, LocalDateTime.now(), null);
	    
	    TaskPatchingResponseDto expectedResponseDto =
	    		new TaskPatchingResponseDto(taskId, "New Title", "Old Description", false, LocalDateTime.now());

	    // CONFIG
	    when(taskRepository.findById(taskId)).thenReturn(Optional.of(existingTask));
	    
	    // Simulate the behavior of the mapper.
	    Mockito.doAnswer(invocation -> {
	        Task task = invocation.getArgument(0);
	        TaskPatchingRequestDto dto = invocation.getArgument(1);
	        if (dto.title() != null) task.setTitle(dto.title());
	        if (dto.description() != null) task.setDescription(dto.description());
	        if (dto.completed() != null) task.setCompleted(dto.completed());
	        return null;
	    }).when(taskMapper).updateEntityFromPatchDto(Mockito.any(Task.class), Mockito.any(TaskPatchingRequestDto.class));

	    when(taskRepository.save(existingTask)).thenReturn(existingTask);
	    when(taskMapper.toPatchingResponseDto(existingTask)).thenReturn(expectedResponseDto);

	    // ACTION
	    TaskPatchingResponseDto result = taskService.patchTask(taskId, patchDto);

	    // ASSERTIONS & VERIFICATIONS
	    Assertions.assertNotNull(result);
	    Assertions.assertEquals("New Title", result.title());
	    Assertions.assertEquals("Old Description", result.description());
	    Assertions.assertFalse(result.completed());
	    Mockito.verify(taskRepository, Mockito.times(1)).findById(taskId);
	    Mockito.verify(taskMapper, Mockito.times(1)).updateEntityFromPatchDto(existingTask, patchDto);
	    Mockito.verify(taskRepository, Mockito.times(1)).save(existingTask);
	}
	
	
	@Test
	public void Given_nonexistent_id_and_partial_dto_When_patchTask_Then_throw_ResourceNotFoundException() {
		// GIVEN
        Long nonExistentTaskId = 999L;
        TaskPatchingRequestDto patchDto = new TaskPatchingRequestDto("New Title", null, null);
        

        // CONFIG
        when(taskRepository.findById(nonExistentTaskId)).thenReturn(Optional.empty());
        
        // ACTION & ASSERTIONS
        ResourceNotFoundException exception = Assertions.assertThrows(
                ResourceNotFoundException.class,
                () -> taskService.patchTask(nonExistentTaskId, patchDto));
        Assertions.assertEquals("Cannot patch. Task not found with id: 999", exception.getMessage());
        Mockito.verify(taskRepository, Mockito.times(1)).findById(nonExistentTaskId);
		Mockito.verifyNoInteractions(taskMapper);
		Mockito.verify(taskRepository, Mockito.never()).save(Mockito.any());
	}
	
}
