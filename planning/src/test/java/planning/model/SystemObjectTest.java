package planning.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Set;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

public class SystemObjectTest {

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

	SystemObject testable;

	@BeforeEach
	public void setup() {
		testable = new SystemObject("object");
	}

	@Test
	public void getName() {
		assertEquals("object", testable.getName());
	}

	@Test
	public void clone_test() throws CloneNotSupportedException {
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");
		final Attribute clonedAttribute_mock = context.mock(Attribute.class, "attribute-clone");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));

				oneOf(attribute_mock).clone();
				will(returnValue(clonedAttribute_mock));

				oneOf(clonedAttribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		testable.addAttribute(attribute_mock);

		assertNotEquals(testable, testable.clone());
	}

	@Test
	public void equals() {
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addAttribute(attribute_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		final SystemObject systemObject = new SystemObject("object");
		systemObject.addAttribute(attribute_mock);

		assertTrue(testable.equals(systemObject));
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
	public void equals_differentName() {
		assertFalse(testable.equals(new SystemObject("object 2")));
	}

	@Test
	public void equals_differentAttribute() {
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addAttribute(attribute_1_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_2_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		final SystemObject systemObject = new SystemObject("object");
		systemObject.addAttribute(attribute_2_mock);

		assertFalse(testable.equals(systemObject));
	}

	@Test
	public void addAttribute() {
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		testable.addAttribute(attribute_mock);
	}

	@Test
	public void addAttribute_with_parameters() {
		testable.addAttribute("attributeName", true);
	}

	@Test
	public void getId() {
		testable = new SystemObject("object", "id");
		assertEquals("id", testable.getId());
	}

	@Test
	public void getAttribute() {
		final Attribute attribute_mock = context.mock(Attribute.class, "attribute");

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addAttribute(attribute_mock);

		assertEquals(attribute_mock, testable.getAttribute("attribute-name"));
	}

	@Test
	public void getIds() {
		Set<String> ids = testable.getIds();
		assertEquals(1, ids.size());
	}

	@Test
	public void getAttributes() {
		assertTrue(testable.getAttributes() instanceof Collection);
	}

	@Test
	public void createTemplate() {
		final Attribute attribute_mock = context.mock(Attribute.class);
		final AttributeTemplate attributeTemplate_mock = context.mock(AttributeTemplate.class);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).getName();
				will(returnValue("attribute-name"));
			}
		});
		testable.addAttribute(attribute_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_mock).createTemplate();
				will(returnValue(attributeTemplate_mock));

				oneOf(attributeTemplate_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		SystemObjectTemplate objectTemplate = testable.createTemplate();
		assertEquals(1, objectTemplate.getAttributeTemplates().size());
	}
}
