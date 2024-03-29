package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.model.Action;
import planning.model.ActionFunction;

public class ActionOWLSchema implements OWLSchema<Action> {

	private PlanningOWLModel owlModel;

	private ActionFunctionOWLSchema actionFunctionOWLSchema;

	public ActionOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new ActionFunctionOWLSchema(owlModel));
	}

	ActionOWLSchema(PlanningOWLModel owlModel, ActionFunctionOWLSchema actionFunctionOWLSchema) {
		this.owlModel = owlModel;
		this.actionFunctionOWLSchema = actionFunctionOWLSchema;
	}

	@Override
	public Individual combine(Action action) {
		final Individual ind_action = owlModel.newIndividual_Action();
		final String name = action.getName();
		ind_action.addLabel(String.format("Действие \"%s\"", name), "ru");
		ind_action.addLabel(String.format("Action \"%s\"", name), "en");
		ind_action.addProperty(owlModel.getDataProperty_name(), name);
		for (ActionFunction preConditionChecker : action.getPreConditionCheckers()) {
			final Individual ind_preConditionChecker = actionFunctionOWLSchema.combine(preConditionChecker);
			ind_action.addProperty(owlModel.getObjectProperty_hasPreConditionChecker(), ind_preConditionChecker);
		}
		for (ActionFunction parameterUpdater : action.getParameterUpdaters()) {
			final Individual ind_parameterUpdater = actionFunctionOWLSchema.combine(parameterUpdater);
			ind_action.addProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
		}
		return ind_action;
	}

	@Override
	public Action parse(Individual ind_action) {
		final String name = ind_action.getProperty(owlModel.getDataProperty_name()).getString();
		final Action action = new Action(name);
		owlModel.getClass_ActionFunction().listInstances().filterKeep((ind_preConditionChecker) -> {
			return ind_action.hasProperty(owlModel.getObjectProperty_hasPreConditionChecker(), ind_preConditionChecker);
		}).forEachRemaining((ind_preConditionChecker) -> {
			final ActionFunction preConditionChecker = actionFunctionOWLSchema.parse(ind_preConditionChecker.asIndividual());
			action.registerPreConditionChecker(preConditionChecker);
		});
		owlModel.getClass_ActionFunction().listInstances().filterKeep((ind_parameterUpdater) -> {
			return ind_action.hasProperty(owlModel.getObjectProperty_hasParameterUpdater(), ind_parameterUpdater);
		}).forEachRemaining((ind_parameterUpdater) -> {
			final ActionFunction parameterUpdater = actionFunctionOWLSchema.parse(ind_parameterUpdater.asIndividual());
			action.registerParameterUpdater(parameterUpdater);
		});
		return action;
	}
}
