package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;

import org.apache.jena.ontology.DatatypeProperty;
import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.AttributeTemplate;
import planning.model.SystemObjectTemplate;

public class SystemObjectTemplateOWLSchemaTest {

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

	SystemObjectTemplateOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	AttributeTemplateOWLSchema attributeTemplateOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);
		attributeTemplateOWLSchema_mock = context.mock(AttributeTemplateOWLSchema.class);

		testable = new SystemObjectTemplateOWLSchema(owlModel_mock, attributeTemplateOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemObjectTemplateOWLSchema(new PlanningOWLModel());
	}

	@Test
	public void combine() {
		final SystemObjectTemplate systemObjectTemplate = new SystemObjectTemplate("template-id");
		final AttributeTemplate attributeTemplate = new AttributeTemplate("attribute-name");
		systemObjectTemplate.addAttributeTemplate(attributeTemplate);
		final Individual i_systemObjectTemplate_mock = context.mock(Individual.class, "i-systemObjectTemplate");
		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final ObjectProperty op_hasAttributeTemplate_mock = context.mock(ObjectProperty.class, "op-hasAttributeTemplate");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_ObjectTemplate();
				will(returnValue(i_systemObjectTemplate_mock));

				oneOf(i_systemObjectTemplate_mock).addLabel("Шаблон объекта \"template-id\"", "ru");

				oneOf(i_systemObjectTemplate_mock).addLabel("Object template \"template-id\"", "en");

				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_systemObjectTemplate_mock).addProperty(dp_id_mock, "template-id");

				oneOf(attributeTemplateOWLSchema_mock).combine(attributeTemplate);
				will(returnValue(i_attributeTemplate_mock));

				oneOf(owlModel_mock).getObjectProperty_hasAttributeTemplate();
				will(returnValue(op_hasAttributeTemplate_mock));

				oneOf(i_systemObjectTemplate_mock).addProperty(op_hasAttributeTemplate_mock, i_attributeTemplate_mock);
			}
		});

		assertEquals(i_systemObjectTemplate_mock, testable.combine(systemObjectTemplate));
	}

	@Test
	public void parse() {
		final Individual i_systemObjectTemplate_mock = context.mock(Individual.class, "i-systemObjectTemplate");
		final AttributeTemplate attributeTemplate_mock = context.mock(AttributeTemplate.class);
		final DatatypeProperty dp_id_mock = context.mock(DatatypeProperty.class, "dp-id");
		final Statement st_id_mock = context.mock(Statement.class, "st-id");
		final OntClass oc_attributeTemplate_mock = context.mock(OntClass.class, "oc-attributeTemplate");
		final ObjectProperty op_hasAttributeTemplate_mock = context.mock(ObjectProperty.class,
				"op-hasAttributeTemplate");

		final Individual i_attributeTemplate_mock = context.mock(Individual.class, "i-attributeTemplate");
		final ExtendedIterator<Individual> attributeTemplateIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_attributeTemplate_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getDataProperty_id();
				will(returnValue(dp_id_mock));

				oneOf(i_systemObjectTemplate_mock).getProperty(dp_id_mock);
				will(returnValue(st_id_mock));

				oneOf(st_id_mock).getString();
				will(returnValue("template-id"));

				oneOf(owlModel_mock).getClass_AttributeTemplate();
				will(returnValue(oc_attributeTemplate_mock));

				oneOf(oc_attributeTemplate_mock).listInstances();
				will(returnValue(attributeTemplateIterator));

				oneOf(owlModel_mock).getObjectProperty_hasAttributeTemplate();
				will(returnValue(op_hasAttributeTemplate_mock));

				oneOf(i_systemObjectTemplate_mock).hasProperty(op_hasAttributeTemplate_mock, i_attributeTemplate_mock);
				will(returnValue(true));

				oneOf(i_attributeTemplate_mock).asIndividual();
				will(returnValue(i_attributeTemplate_mock));

				oneOf(attributeTemplateOWLSchema_mock).parse(i_attributeTemplate_mock);
				will(returnValue(attributeTemplate_mock));

				oneOf(attributeTemplate_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		SystemObjectTemplate result = testable.parse(i_systemObjectTemplate_mock);
		assertEquals("template-id", result.getId());
		assertTrue(result.getAttributeTemplates().contains(attributeTemplate_mock));
	}
}
