package planning.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class IdsMatchingTest {

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

	IdsMatching testable;

	@BeforeEach
	public void setup() {
		testable = new IdsMatching();
	}

	@Test
	public void get() {
		assertNull(testable.get("template-id"));
	}

	@Test
	public void add() {
		testable.add("template-id", "object-id");

		assertEquals("object-id", testable.get("template-id"));
	}

	@Test
	public void isChecked() {
		assertFalse(testable.isChecked());
	}

	@Test
	public void check() {
		testable.check();

		assertTrue(testable.isChecked());
	}

	@Test
	public void areKeysAndValuesTheSame() {
		testable.add("id-1", "id-1");
		testable.add("id-2", "id-2");

		assertTrue(testable.areKeysAndValuesTheSame());
	}

	@Test
	public void areKeysAndValuesTheSame_different() {
		testable.add("id-1", "id-1");
		testable.add("id-2", "id-3");

		assertFalse(testable.areKeysAndValuesTheSame());
	}
}
