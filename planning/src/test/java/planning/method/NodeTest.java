package planning.method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.System;

public class NodeTest {

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

	Node testable;

	System system_mock;

	@BeforeEach
	public void setup() {
		system_mock = context.mock(System.class, "system");

		testable = new Node(system_mock);
	}

	@Test
	public void equals() {
		final Node node = new Node(testable.getId(), testable.getSystem());
		assertTrue(testable.equals(node));
	}

	@Test
	public void equals_null() {
		assertFalse(testable.equals(null));
	}

	@Test
	public void equals_self() {
		assertTrue(testable.equals(testable));
	}

	@Test
	public void equals_type() {
		assertFalse(testable.equals(new Object()));
	}

	@Test
	public void equals_differentId() {
		final Node node = new Node("differentId", system_mock);
		assertFalse(testable.equals(node));
	}

	@Test
	public void getId() {
		testable = new Node("id", system_mock);
		assertEquals("id", testable.getId());
	}

	@Test
	public void setId() {
		testable.setId("new-id");
		assertEquals("new-id", testable.getId());
	}

	@Test
	public void getSystem() {
		assertEquals(system_mock, testable.getSystem());
	}

	@Test
	public void setSystem() {
		final System new_system_mock = context.mock(System.class, "new-system");
		testable.setSystem(new_system_mock);
		assertEquals(new_system_mock, testable.getSystem());
	}

	@Test
	public void hashCode_test() {
		testable = new Node("id", system_mock);
		assertEquals(3355, testable.hashCode());
	}

	@Test
	public void isUnchecked() {
		assertTrue(testable.isUnchecked());
	}

	@Test
	public void setChecked() {
		assertTrue(testable.isUnchecked());

		testable.setChecked(true);

		assertFalse(testable.isUnchecked());
	}

	@Test
	public void getChecked() {
		assertFalse(testable.getChecked());
	}

	@Test
	public void toString_test() {
		testable = new Node("id123", system_mock);
		assertEquals("node-id123", testable.toString());
	}
}
