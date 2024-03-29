package application.event;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class EventQueueTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	EventQueue testable;

	@BeforeEach
	public void setup() {
		testable = new EventQueue() {
			@Override
			protected void processEvent(CommandEvent event) {
			}

			@Override
			protected void processEvent(UserEvent event) {
			}
		};
	}

	@Test
	public void newInstance() {
	}

	@Test
	public void pushEvent() {
		final Event event_mock = context.mock(Event.class);

		testable.pushEvent(event_mock);
	}

	@Test
	public void stop() {
		testable.stop();
	}

	@Test
	public void run() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(testable);
		testable.stop();
		executor.shutdown();
	}

	@Test
	public void run_CommandEvent() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(testable);

		CommandEvent event = new CommandEvent("test-event");
		testable.pushEvent(event);

		testable.stop();
		executor.shutdown();
	}

	@Test
	public void run_UserEvent() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(testable);

		UserEvent event = new UserEvent("test-event");
		testable.pushEvent(event);

		testable.stop();
		executor.shutdown();
	}

	@Test
	public void run_Event() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(testable);

		Event event = new Event("test-event");
		testable.pushEvent(event);

		testable.stop();
		executor.shutdown();
	}

	@Test
	public void run_InterruptedException() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(testable);
		executor.shutdownNow();
	}

	@Test
	public void isRunning() throws InterruptedException {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(testable);

		assertTrue(testable.isRunning());

		testable.stop();
		executor.shutdown();
		executor.awaitTermination(500, TimeUnit.MILLISECONDS);

		assertFalse(testable.isRunning());
	}
}
