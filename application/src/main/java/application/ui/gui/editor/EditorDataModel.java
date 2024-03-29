package application.ui.gui.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import planning.method.Edge;
import planning.method.Node;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.Action;
import planning.model.ActionFunction;
import planning.model.System;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemProcess;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;
import planning.model.Transformations;

// TODO (2022-11-16 #72): saveXXX и loadXXX методы переименовать в getXXX и setXXX
public class EditorDataModel extends DefaultTreeModel {

	private static final long serialVersionUID = 2748742512319035267L;

	private DefaultMutableTreeNode projectNode;

	private DefaultMutableTreeNode taskDescriptionNode;

	private DefaultMutableTreeNode systemTransformationsNode;

	private DefaultMutableTreeNode nodeNetworkNode;

	private DefaultMutableTreeNode systemProcessNode;

	public EditorDataModel() {
		super(null);

		projectNode = new DefaultMutableTreeNode("Project");
		taskDescriptionNode = new DefaultMutableTreeNode("Task Description");
		systemTransformationsNode = new DefaultMutableTreeNode("System Transformations");
		nodeNetworkNode = new DefaultMutableTreeNode("Node Network");
		systemProcessNode = new DefaultMutableTreeNode("Process");

		projectNode.add(taskDescriptionNode);
		projectNode.add(systemTransformationsNode);
		projectNode.add(nodeNetworkNode);
		projectNode.add(systemProcessNode);

		this.root = projectNode;
	}

	public void loadTaskDescription(TaskDescription taskDescription) {
		System system;
		DefaultMutableTreeNode systemNode;

		// TODO (2022-09-22 #72): удалить
		// taskDescription = AssemblyLine.getTaskDescription();

		taskDescriptionNode.removeAllChildren();
		taskDescriptionNode.setUserObject(taskDescription);

		system = taskDescription.getInitialSystem();
		system.setName("initialSystem");
		systemNode = createSystemNode(system);
		taskDescriptionNode.add(systemNode);

		system = taskDescription.getFinalSystem();
		system.setName("finalSystem");
		systemNode = createSystemNode(system);
		taskDescriptionNode.add(systemNode);

		reload();
	}

	public TaskDescription saveTaskDescription() {
		return (TaskDescription) taskDescriptionNode.getUserObject();
	}

	public DefaultMutableTreeNode createSystemNode(System system) {
		final DefaultMutableTreeNode systemNode = new DefaultMutableTreeNode(system);
		final Collection<SystemObject> objects = system.getObjects();
		for (SystemObject object : objects) {
			final DefaultMutableTreeNode objectNode = createObjectNode(object);
			systemNode.add(objectNode);
		}
		return systemNode;
	}

	public DefaultMutableTreeNode createObjectNode(SystemObject object) {
		return new DefaultMutableTreeNode(object);
	}

	public DefaultMutableTreeNode createSystemTransformationNode(SystemTransformation systemTransformation) {
		DefaultMutableTreeNode systemTransformationNode;
		DefaultMutableTreeNode systemTemplateNode;
		DefaultMutableTreeNode transformationsNode;
		DefaultMutableTreeNode actionNode;

		systemTransformationNode = new DefaultMutableTreeNode(systemTransformation);

		systemTemplateNode = createSystemTemplateNode(systemTransformation.getSystemTemplate());
		systemTransformationNode.add(systemTemplateNode);

		transformationsNode = createTransformationsNode(systemTransformation.getTransformations());
		systemTransformationNode.add(transformationsNode);

		actionNode = createActionNode(systemTransformation.getAction());
		systemTransformationNode.add(actionNode);

		return systemTransformationNode;
	}

	public DefaultMutableTreeNode createActionNode(Action action) {
		final DefaultMutableTreeNode actionNode = new DefaultMutableTreeNode(action);
		// TODO (2022-12-07 #73): все функции должны обрабатываться в одной коллекции
		final Collection<ActionFunction> parameterUpdaters = action.getParameterUpdaters();
		for (ActionFunction actionFunction : parameterUpdaters) {
			final DefaultMutableTreeNode functionNode = createActionFunctionNode(actionFunction);
			actionNode.add(functionNode);
		}
		final Collection<ActionFunction> preConditionCheckers = action.getPreConditionCheckers();
		for (ActionFunction actionFunction : preConditionCheckers) {
			final DefaultMutableTreeNode functionNode = createActionFunctionNode(actionFunction);
			actionNode.add(functionNode);
		}
		return actionNode;
	}

	public DefaultMutableTreeNode createActionFunctionNode(ActionFunction actionFunction) {
		return new DefaultMutableTreeNode(actionFunction);
	}

	public DefaultMutableTreeNode createTransformationsNode(Transformations transformations) {
		final DefaultMutableTreeNode transformationsNode = new DefaultMutableTreeNode(transformations);
		for (Transformation transformation : transformations) {
			final DefaultMutableTreeNode transformationNode = createTransformationNode(transformation);
			transformationsNode.add(transformationNode);
		}
		return transformationsNode;
	}

	public DefaultMutableTreeNode createTransformationNode(Transformation transformation) {
		return new DefaultMutableTreeNode(transformation);
	}

	public DefaultMutableTreeNode createSystemTemplateNode(SystemTemplate systemTemplate) {
		final DefaultMutableTreeNode systemTemplateNode = new DefaultMutableTreeNode(systemTemplate);
		final Collection<SystemObjectTemplate> objectTemplates = systemTemplate.getObjectTemplates();
		for (SystemObjectTemplate objectTemplate : objectTemplates) {
			final DefaultMutableTreeNode objectTemplateNode = createObjectTemplateNode(objectTemplate);
			systemTemplateNode.add(objectTemplateNode);
		}
		return systemTemplateNode;
	}

	public DefaultMutableTreeNode createObjectTemplateNode(SystemObjectTemplate objectTemplate) {
		return new DefaultMutableTreeNode(objectTemplate);
	}

	public void loadSystemTransformations(SystemTransformations systemTransformations) {
		// TODO (2022-11-18 #73): удалить
		// systemTransformations = AssemblyLine.getSystemTransformations();

		systemTransformationsNode.removeAllChildren();
		systemTransformationsNode.setUserObject(systemTransformations);

		for (SystemTransformation systemTransformation : systemTransformations) {
			final DefaultMutableTreeNode systemTransformationNode = createSystemTransformationNode(systemTransformation);
			systemTransformationsNode.add(systemTransformationNode);
		}

		reload();
	}

	public SystemTransformations saveSystemTransformations() {
		return (SystemTransformations) systemTransformationsNode.getUserObject();
	}

	public void loadNodeNetwork(NodeNetwork nodeNetwork) {
		// TODO (2022-12-22 #74): удалить
//		Planner planner = new Planner(AssemblyLine.getTaskDescription(), AssemblyLine.getSystemTransformations(), nodeNetwork);
//		try {
//			planner.plan();
//		} catch (CloneNotSupportedException e) {
//			e.printStackTrace();
//		}

		nodeNetworkNode.removeAllChildren();
		nodeNetworkNode.setUserObject(nodeNetwork);

		final DefaultMutableTreeNode nodesNode = new DefaultMutableTreeNode("Nodes");
		nodeNetworkNode.add(nodesNode);
		for (Node node : nodeNetwork.getNodes()) {
			final DefaultMutableTreeNode nodeNode = createNodeNode(node);
			nodesNode.add(nodeNode);
		}

		final DefaultMutableTreeNode edgesNode = new DefaultMutableTreeNode("Edges");
		nodeNetworkNode.add(edgesNode);
		for (Edge edge : nodeNetwork.getEdges()) {
			final DefaultMutableTreeNode edgeNode = createEdgeNode(edge);
			edgesNode.add(edgeNode);
		}

		reload();
	}

	public DefaultMutableTreeNode createEdgeNode(Edge edge) {
		final DefaultMutableTreeNode edgeNode = new DefaultMutableTreeNode(edge);
		return edgeNode;
	}

	public DefaultMutableTreeNode createNodeNode(Node node) {
		final DefaultMutableTreeNode nodeNode = new DefaultMutableTreeNode(node);
		final DefaultMutableTreeNode systemNode = createSystemNode(node.getSystem());
		nodeNode.add(systemNode);
		return nodeNode;
	}

	public void loadSystemProcess(SystemProcess systemProcess) {
		DefaultMutableTreeNode processNode;
		systemProcessNode.removeAllChildren();

		processNode = new DefaultMutableTreeNode("process-id-0000");
		systemProcessNode.add(processNode);

		reload();
	}

	public List<Edge> selectEdges(Node node) {
		final NodeNetwork nodeNetwork = (NodeNetwork) nodeNetworkNode.getUserObject();
		final List<Edge> edgesWithNode = new ArrayList<Edge>();

		final String nodeId = node.getId();
		for (Edge edge : nodeNetwork.getEdges()) {
			if (edge.getBeginNodeId().equals(nodeId)) {
				edgesWithNode.add(edge);
			}
		}
		return edgesWithNode;
	}
}
