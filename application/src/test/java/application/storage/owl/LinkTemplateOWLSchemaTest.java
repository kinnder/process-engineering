package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.rdf.model.Statement;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.LinkTemplate;

public class LinkTemplateOWLSchemaTest {

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

	LinkTemplateOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);

		testable = new LinkTemplateOWLSchema(owlModel_mock);
	}

	@Test
	public void combine() {
		final LinkTemplate linkTemplate = new LinkTemplate("link-name", "link-id-1", "link-id-2");
		final Individual i_linkTemplate_mock = context.mock(Individual.class, "i-linkTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId1_mock = context.mock(DatatypeProperty.class, "dp-objectId1");
		final DatatypeProperty dp_objectId2_mock = context.mock(DatatypeProperty.class, "dp-objectId2");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_LinkTemplate();
				will(returnValue(i_linkTemplate_mock));

				oneOf(i_linkTemplate_mock).addLabel("Link template \"link-name\"", "en");

				oneOf(i_linkTemplate_mock).addLabel("Шаблон связи \"link-name\"", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTemplate_mock).addProperty(dp_name_mock, "link-name");

				oneOf(owlModel_mock).getDataProperty_objectId1();
				will(returnValue(dp_objectId1_mock));

				oneOf(i_linkTemplate_mock).addProperty(dp_objectId1_mock, "link-id-1");

				oneOf(owlModel_mock).getDataProperty_objectId2();
				will(returnValue(dp_objectId2_mock));

				oneOf(i_linkTemplate_mock).addProperty(dp_objectId2_mock, "link-id-2");
			}
		});

		assertEquals(i_linkTemplate_mock, testable.combine(linkTemplate));
	}

	@Test
	public void combine_null_ids() {
		final LinkTemplate linkTemplate = new LinkTemplate("link-name", null, null);
		final Individual i_linkTemplate_mock = context.mock(Individual.class, "i-linkTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_LinkTemplate();
				will(returnValue(i_linkTemplate_mock));

				oneOf(i_linkTemplate_mock).addLabel("Link template \"link-name\"", "en");

				oneOf(i_linkTemplate_mock).addLabel("Шаблон связи \"link-name\"", "ru");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTemplate_mock).addProperty(dp_name_mock, "link-name");
			}
		});

		assertEquals(i_linkTemplate_mock, testable.combine(linkTemplate));
	}

	@Test
	public void parse() {
		final Individual i_linkTemplate_mock = context.mock(Individual.class, "i-linkTemplate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId1_mock = context.mock(DatatypeProperty.class, "dp-objectId1");
		final DatatypeProperty dp_objectId2_mock = context.mock(DatatypeProperty.class, "dp-objectId2");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_objectId1_mock = context.mock(Statement.class, "st-objectId1");
		final Statement st_objectId2_mock = context.mock(Statement.class, "st-objectId2");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTemplate_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("link-name"));

				oneOf(owlModel_mock).getDataProperty_objectId1();
				will(returnValue(dp_objectId1_mock));

				oneOf(i_linkTemplate_mock).getProperty(dp_objectId1_mock);
				will(returnValue(st_objectId1_mock));

				oneOf(st_objectId1_mock).getString();
				will(returnValue("link-id-1"));

				oneOf(owlModel_mock).getDataProperty_objectId2();
				will(returnValue(dp_objectId2_mock));

				oneOf(i_linkTemplate_mock).getProperty(dp_objectId2_mock);
				will(returnValue(st_objectId2_mock));

				oneOf(st_objectId2_mock).getString();
				will(returnValue("link-id-2"));
			}
		});

		LinkTemplate result = testable.parse(i_linkTemplate_mock);
		assertEquals("link-name", result.getName());
		assertEquals("link-id-1", result.getObjectId1());
		assertEquals("link-id-2", result.getObjectId2());
	}

	@Test
	public void parse_null_ids() {
		final Individual i_linkTemplate_mock = context.mock(Individual.class, "i-linkTemlate");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_objectId1_mock = context.mock(DatatypeProperty.class, "dp-objectId1");
		final DatatypeProperty dp_objectId2_mock = context.mock(DatatypeProperty.class, "dp-objectId2");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTemplate_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("link-name"));

				oneOf(owlModel_mock).getDataProperty_objectId1();
				will(returnValue(dp_objectId1_mock));

				oneOf(i_linkTemplate_mock).getProperty(dp_objectId1_mock);
				will(returnValue(null));

				oneOf(owlModel_mock).getDataProperty_objectId2();
				will(returnValue(dp_objectId2_mock));

				oneOf(i_linkTemplate_mock).getProperty(dp_objectId2_mock);
				will(returnValue(null));
			}
		});

		LinkTemplate result = testable.parse(i_linkTemplate_mock);
		assertEquals("link-name", result.getName());
		assertNull(result.getObjectId1());
		assertNull(result.getObjectId2());
	}
}
