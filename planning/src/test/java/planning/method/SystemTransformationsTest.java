package planning.method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.SystemTransformation;

public class SystemTransformationsTest {

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

	SystemTransformations testable;

	@BeforeEach
	public void setup() {
		testable = new SystemTransformations();
	}

	@Test
	public void getElement() {
		assertNull(testable.getElement("not-found"));
	}

	@Test
	public void addElement() {
		final SystemTransformation element_1_mock = context.mock(SystemTransformation.class, "element-1");
		final SystemTransformation element_2_mock = context.mock(SystemTransformation.class, "element-2");

		testable.addElement(element_1_mock);
		testable.addElement(element_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(element_1_mock).getName();
				will(returnValue("element-1"));

				oneOf(element_2_mock).getName();
				will(returnValue("element-2"));
			}
		});
		assertEquals(element_2_mock, testable.getElement("element-2"));
	}

	@Test
	public void getElements() {
		assertEquals(0, testable.getElements().length);
	}

	@Test
	public void addElements() {
		final SystemTransformation element_1_mock = context.mock(SystemTransformation.class, "element-1");
		final SystemTransformation element_2_mock = context.mock(SystemTransformation.class, "element-2");

		SystemTransformation[] elements = new SystemTransformation[] { element_1_mock, element_2_mock };

		testable.addElements(elements);
		assertEquals(2, testable.getElements().length);
	}
}