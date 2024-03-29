package application.storage.xml;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.ActionFunction;
import planning.model.LuaScriptLine;

public class ActionFunctionXMLSchemaTest {

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

	ActionFunctionXMLSchema testable;

	LuaScriptLineXMLSchema luaScriptLineXMLSchema_mock;

	@BeforeEach
	public void setup() {
		luaScriptLineXMLSchema_mock = context.mock(LuaScriptLineXMLSchema.class);

		testable = new ActionFunctionXMLSchema(luaScriptLineXMLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new ActionFunctionXMLSchema();
	}

	@Test
	public void parse() throws DataConversionException {
		final Element root_mock = context.mock(Element.class, "root");
		final List<Element> luaScriptLineElements = new ArrayList<>();
		final Element luaScriptLineElement_mock = context.mock(Element.class);
		luaScriptLineElements.add(luaScriptLineElement_mock);
		final LuaScriptLine luaScriptLine = new LuaScriptLine(1, "local systemVariant = ...");

		context.checking(new Expectations() {
			{
				oneOf(luaScriptLineXMLSchema_mock).getSchemaName();
				will(returnValue("luaScriptLine"));

				oneOf(root_mock).getChildren("luaScriptLine");
				will(returnValue(luaScriptLineElements));

				oneOf(luaScriptLineXMLSchema_mock).parse(luaScriptLineElement_mock);
				will(returnValue(luaScriptLine));
			}
		});

		assertTrue(testable.parse(root_mock) instanceof ActionFunction);
	}

	@Test
	public void combine() {
		final ActionFunction actionFunction_mock = context.mock(ActionFunction.class);
		final List<LuaScriptLine> luaScriptLines = new ArrayList<LuaScriptLine>();
		final LuaScriptLine luaScriptLine = new LuaScriptLine(1, "local systemVariant = ...");
		luaScriptLines.add(luaScriptLine);
		final Element luaScriptLineElement = new Element("luaScriptLine");

		context.checking(new Expectations() {
			{
				oneOf(actionFunction_mock).getScriptLines();
				will(returnValue(luaScriptLines));

				oneOf(luaScriptLineXMLSchema_mock).combine(luaScriptLine);
				will(returnValue(luaScriptLineElement));
			}
		});

		Element element = testable.combine(actionFunction_mock);
		List<Element> lines = element.getChildren("luaScriptLine");
		assertEquals(1, lines.size());
		assertEquals(luaScriptLineElement, lines.get(0));
	}

	@Test
	public void getSchemaName() {
		assertEquals("actionFunction", testable.getSchemaName());
	}
}
