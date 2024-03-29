package application.storage.owl;

import org.apache.jena.ontology.Individual;
import planning.method.TaskDescription;
import planning.model.System;

public class TaskDescriptionOWLSchema implements OWLSchema<TaskDescription> {

	private PlanningOWLModel owlModel;

	private SystemOWLSchema systemOWLSchema;

	public TaskDescriptionOWLSchema(PlanningOWLModel owlModel) {
		this(owlModel, new SystemOWLSchema(owlModel));
	}

	TaskDescriptionOWLSchema(PlanningOWLModel owlModel, SystemOWLSchema systemOWLSchema) {
		this.owlModel = owlModel;
		this.systemOWLSchema = systemOWLSchema;
	}

	@Override
	public Individual combine(TaskDescription taskDescription) {
		final Individual ind_taskDescription = owlModel.newIndividual_TaskDescription();
		ind_taskDescription.addLabel("Task Description", "en");
		ind_taskDescription.addLabel("Описание задания", "ru");

		final Individual ind_initialSystem = systemOWLSchema.combine(taskDescription.getInitialSystem());
		ind_initialSystem.setOntClass(owlModel.getClass_InitialSystem());
		ind_initialSystem.addLabel("Initial system", "en");
		ind_initialSystem.addLabel("Начальная система", "ru");
		ind_taskDescription.addProperty(owlModel.getObjectProperty_hasInitialSystem(), ind_initialSystem);

		final Individual ind_finalSystem = systemOWLSchema.combine(taskDescription.getFinalSystem());
		ind_finalSystem.setOntClass(owlModel.getClass_FinalSystem());
		ind_finalSystem.addLabel("Final system", "en");
		ind_finalSystem.addLabel("Конечная система", "ru");
		ind_taskDescription.addProperty(owlModel.getObjectProperty_hasFinalSystem(), ind_finalSystem);

		return ind_taskDescription;
	}

	@Override
	public TaskDescription parse(Individual individual) {
		final TaskDescription taskDescription = new TaskDescription();

		owlModel.getClass_TaskDescription().listInstances().forEachRemaining((ind_taskDescription) -> {
			owlModel.getClass_InitialSystem().listInstances().filterKeep((ind_initialSystem) -> {
				return ind_taskDescription.hasProperty(owlModel.getObjectProperty_hasInitialSystem(), ind_initialSystem);
			}).forEachRemaining((ind_initialSystem) -> {
				final System initialSystem = systemOWLSchema.parse(ind_initialSystem.asIndividual());
				taskDescription.setInitialSystem(initialSystem);
			});
			owlModel.getClass_FinalSystem().listInstances().filterKeep((ind_finalSystem) -> {
				return ind_taskDescription.hasProperty(owlModel.getObjectProperty_hasFinalSystem(), ind_finalSystem);
			}).forEachRemaining((ind_finalSystem) -> {
				final System finalSystem = systemOWLSchema.parse(ind_finalSystem.asIndividual());
				taskDescription.setFinalSystem(finalSystem);
			});
		});

		return taskDescription;
	}
}
