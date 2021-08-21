package br.ce.wcaquino.taskbackend.controller;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import br.ce.wcaquino.taskbackend.model.Task;
import br.ce.wcaquino.taskbackend.repo.TaskRepo;
import br.ce.wcaquino.taskbackend.utils.ValidationException;

public class TaskControllerTest {

	@Mock
	private TaskRepo taskRepo;
	
	@InjectMocks
	private TaskController controller;
	
	@Before
	public void setup() {
		// Iniating mocks before each test
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void shouldNotSaveTaskWithoutDescription() {
		// Task without description (only with date)
		Task newTask = new Task();
		newTask.setDueDate(LocalDate.now());

		try {
			controller.save(newTask);
			Assert.fail("Should not coming here");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the task description", e.getMessage());
		}
	}

	@Test
	public void shouldNotSaveTaskWithoutDate() {
		// Task without date (only with description)
		Task newTask = new Task();
		newTask.setTask("Task Description");

		try {
			controller.save(newTask);
			Assert.fail("Should not coming here");
		} catch (ValidationException e) {
			Assert.assertEquals("Fill the due date", e.getMessage());
		}
	}

	@Test
	public void shouldNotSaveTaskWithPastDate() {
		// Task with a past date
		Task newTask = new Task();
		newTask.setTask("Task Description");
		newTask.setDueDate(LocalDate.of(2010, 01, 01));

		try {
			controller.save(newTask);
			Assert.fail("Should not coming here");
		} catch (ValidationException e) {
			Assert.assertEquals("Due date must not be in past", e.getMessage());
		}

	}

	@Test
	public void shouldSaveTaskWithSuccess() throws ValidationException {
		// Task save with success
		Task newTask = new Task();
		newTask.setTask("Task Description");
		newTask.setDueDate(LocalDate.now());

		controller.save(newTask);
	}
}