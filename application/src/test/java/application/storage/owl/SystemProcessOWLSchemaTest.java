package application.storage.owl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.jena.ontology.Individual;
import org.apache.jena.ontology.ObjectProperty;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.util.iterator.ExtendedIterator;
import org.apache.jena.util.iterator.NiceIterator;
import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Action;
import planning.model.SystemOperation;
import planning.model.SystemProcess;

public class SystemProcessOWLSchemaTest {

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

	SystemProcessOWLSchema testable;

	PlanningOWLModel owlModel_mock;

	SystemOperationOWLSchema systemOperationOWLSchema_mock;

	@BeforeEach
	public void setup() {
		owlModel_mock = context.mock(PlanningOWLModel.class);
		systemOperationOWLSchema_mock = context.mock(SystemOperationOWLSchema.class);

		testable = new SystemProcessOWLSchema(owlModel_mock, systemOperationOWLSchema_mock);
	}

	@Test
	public void newInstance() {
		testable = new SystemProcessOWLSchema(new PlanningOWLModel());
	}

	@Test
	public void combine() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class, "systemOperation");
		final SystemProcess systemProcess = new SystemProcess();
		systemProcess.add(systemOperation_mock);
		final Individual i_systemProcess_mock = context.mock(Individual.class, "i-systemProcess");
		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final ObjectProperty op_performsSystemOperation_mock = context.mock(ObjectProperty.class, "op-performsSystemOperation");

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).newIndividual_Process();
				will(returnValue(i_systemProcess_mock));

				oneOf(i_systemProcess_mock).addLabel("Process", "en");

				oneOf(i_systemProcess_mock).addLabel("Процесс", "ru");

				oneOf(systemOperationOWLSchema_mock).combine(systemOperation_mock);
				will(returnValue(i_systemOperation_mock));

				oneOf(owlModel_mock).getObjectProperty_performsSystemOperation();
				will(returnValue(op_performsSystemOperation_mock));

				oneOf(i_systemProcess_mock).addProperty(op_performsSystemOperation_mock, i_systemOperation_mock);
			}
		});

		assertEquals(i_systemProcess_mock, testable.combine(systemProcess));
	}

	@Test
	public void parse() {
		final SystemOperation systemOperation_mock = context.mock(SystemOperation.class, "systemOperation");
		final SystemProcess systemProcess = new SystemProcess();
		systemProcess.add(systemOperation_mock);
		final ObjectProperty op_performsSystemOperation_mock = context.mock(ObjectProperty.class, "op-performsSystemOperation");
		final OntClass oc_process_mock = context.mock(OntClass.class, "oc-process");
		final OntClass oc_systemOperation_mock = context.mock(OntClass.class, "oc-systemOperation");

		final Individual i_systemProcess_mock = context.mock(Individual.class, "i-systemProcess");
		final ExtendedIterator<Individual> systemProcessIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemProcess_mock).iterator());

		final Individual i_systemOperation_mock = context.mock(Individual.class, "i-systemOperation");
		final ExtendedIterator<Individual> systemOperationIterator = new NiceIterator<Individual>()
				.andThen(Arrays.asList(i_systemOperation_mock).iterator());

		context.checking(new Expectations() {
			{
				oneOf(owlModel_mock).getClass_Process();
				will(returnValue(oc_process_mock));

				oneOf(oc_process_mock).listInstances();
				will(returnValue(systemProcessIterator));

				oneOf(owlModel_mock).getClass_SystemOperation();
				will(returnValue(oc_systemOperation_mock));

				oneOf(oc_systemOperation_mock).listInstances();
				will(returnValue(systemOperationIterator));

				oneOf(owlModel_mock).getObjectProperty_performsSystemOperation();
				will(returnValue(op_performsSystemOperation_mock));

				oneOf(i_systemProcess_mock).hasProperty(op_performsSystemOperation_mock, i_systemOperation_mock);
				will(returnValue(true));

				oneOf(i_systemOperation_mock).asIndividual();
				will(returnValue(i_systemOperation_mock));

				oneOf(systemOperationOWLSchema_mock).parse(i_systemOperation_mock);
				will(returnValue(systemOperation_mock));
			}
		});

		SystemProcess result = testable.parse(null);
		assertTrue(result.contains(systemOperation_mock));
	}

	@Test
	public void combine_full() {
		final SystemProcess systemProcess = new SystemProcess();
		final Map<String, String> parameters = new HashMap<>();
		parameters.put("test-parameter-name", "test-parameter-value");
		final Action action = new Action("test-action");
		systemProcess.add(new SystemOperation(action, parameters));

		PlanningOWLModel owlModel = new PlanningOWLModel();
		SystemProcessOWLSchema owlSchema = new SystemProcessOWLSchema(owlModel);

		owlModel.createOntologyModel();
		owlSchema.combine(systemProcess);
		OntModel model = owlModel.getOntologyModel();
		assertNotNull(model);
		assertEquals(332, model.listObjects().toList().size());
		assertEquals(962, model.listStatements().toList().size());
	}

	@Test
	public void parse_full() {
		final SystemProcess systemProcess = new SystemProcess();
		final Map<String, String> parameters = new HashMap<>();
		parameters.put("test-parameter-name", "test-parameter-value");
		final Action action = new Action("test-action");
		systemProcess.add(new SystemOperation(action, parameters));

		PlanningOWLModel owlModel = new PlanningOWLModel();
		SystemProcessOWLSchema owlSchema = new SystemProcessOWLSchema(owlModel);

		owlModel.createOntologyModel();
		Individual ind_systemProcess = owlSchema.combine(systemProcess);

		owlSchema.parse(ind_systemProcess);
	}
}
