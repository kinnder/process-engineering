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

import planning.model.LinkTransformation;

public class LinkTransformationOWLSchemaTest {

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

	LinkTransformationOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);

		testable = new LinkTransformationOWLSchema(owlModel_mock);
	}

	@Test
	public void combine() {
		final LinkTransformation linkTransformation = new LinkTransformation("link-id", "link-name", "link-id-old", "link-id-new");
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final DatatypeProperty dp_id2Old_mock = context.mock(DatatypeProperty.class, "dp-id2Old");
		final DatatypeProperty dp_id2New_mock = context.mock(DatatypeProperty.class, "dp-id2New");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_LinkTransformation();
				will(returnValue(i_linkTransformation_mock));

				oneOf(i_linkTransformation_mock).addLabel("Link transformation \"link-name\"", "en");

				oneOf(i_linkTransformation_mock).addLabel("Трансформация связи \"link-name\"", "ru");

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_id_mock, "link-id");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_name_mock, "link-name");

				oneOf(owlModel_mock).getDataProperty_id2Old();
				will(returnValue(dp_id2Old_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_id2Old_mock, "link-id-old");

				oneOf(owlModel_mock).getDataProperty_id2New();
				will(returnValue(dp_id2New_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_id2New_mock, "link-id-new");
			}
		});

		assertEquals(i_linkTransformation_mock, testable.combine(linkTransformation));
	}

	@Test
	public void combine_null_ids() {
		final LinkTransformation linkTransformation = new LinkTransformation("link-id", "link-name", null, null);
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_LinkTransformation();
				will(returnValue(i_linkTransformation_mock));

				oneOf(i_linkTransformation_mock).addLabel("Link transformation \"link-name\"", "en");

				oneOf(i_linkTransformation_mock).addLabel("Трансформация связи \"link-name\"", "ru");

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_id_mock, "link-id");

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTransformation_mock).addProperty(dp_name_mock, "link-name");
			}
		});

		assertEquals(i_linkTransformation_mock, testable.combine(linkTransformation));
	}

	@Test
	public void parse() {
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final DatatypeProperty dp_id2Old_mock = context.mock(DatatypeProperty.class, "dp-id2Old");
		final DatatypeProperty dp_id2New_mock = context.mock(DatatypeProperty.class, "dp-id2New");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_id_mock = context.mock(Statement.class, "st-id");
		final Statement st_id2Old_mock = context.mock(Statement.class, "st-id2Old");
		final Statement st_id2New_mock = context.mock(Statement.class, "st-id2New");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_id_mock);
				will(returnValue(st_id_mock));

				oneOf(st_id_mock).getString();
				will(returnValue("link-id"));

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("link-name"));

				oneOf(owlModel_mock).getDataProperty_id2Old();
				will(returnValue(dp_id2Old_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_id2Old_mock);
				will(returnValue(st_id2Old_mock));

				oneOf(st_id2Old_mock).getString();
				will(returnValue("link-id-old"));

				oneOf(owlModel_mock).getDataProperty_id2New();
				will(returnValue(dp_id2New_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_id2New_mock);
				will(returnValue(st_id2New_mock));

				oneOf(st_id2New_mock).getString();
				will(returnValue("link-id-new"));
			}
		});

		LinkTransformation result = testable.parse(i_linkTransformation_mock);
		assertEquals("link-name", result.getLinkName());
		assertEquals("link-id", result.getId());
		assertEquals("link-id-old", result.getId2Old());
		assertEquals("link-id-new", result.getId2New());
	}

	@Test
	public void parse_null_ids() {
		final Individual i_linkTransformation_mock = context.mock(Individual.class, "i-linkTransformation");
		final DatatypeProperty dp_name_mock = context.mock(DatatypeProperty.class, "dp-name");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final DatatypeProperty dp_id2Old_mock = context.mock(DatatypeProperty.class, "dp-id2Old");
		final DatatypeProperty dp_id2New_mock = context.mock(DatatypeProperty.class, "dp-id2New");
		final Statement st_name_mock = context.mock(Statement.class, "st-name");
		final Statement st_id_mock = context.mock(Statement.class, "st-id");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_id_mock);
				will(returnValue(st_id_mock));

				oneOf(st_id_mock).getString();
				will(returnValue("link-id"));

				oneOf(owlModel_mock).getDataProperty_name();
				will(returnValue(dp_name_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_name_mock);
				will(returnValue(st_name_mock));

				oneOf(st_name_mock).getString();
				will(returnValue("link-name"));

				oneOf(owlModel_mock).getDataProperty_id2Old();
				will(returnValue(dp_id2Old_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_id2Old_mock);
				will(returnValue(null));

				oneOf(owlModel_mock).getDataProperty_id2New();
				will(returnValue(dp_id2New_mock));

				oneOf(i_linkTransformation_mock).getProperty(dp_id2New_mock);
				will(returnValue(null));
			}
		});

		LinkTransformation result = testable.parse(i_linkTransformation_mock);
		assertEquals("link-name", result.getLinkName());
		assertEquals("link-id", result.getId());
		assertNull(result.getId2Old());
		assertNull(result.getId2New());
	}
}
