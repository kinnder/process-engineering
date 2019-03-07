package solution;

import model.DesignTask;
import solution.node.Node;
import solution.node.NodeTree;
import solution.node.NodeTreeBuilder;

/** �������� */
public class Solver {

	/** ����������� ������ ��������� */
	NodeTreeBuilder transitionTreeBuilder;

	/**
	 * �����������
	 *
	 * @param transitionTreeBuilder - ����������� ������ ���������
	 */
	public Solver(NodeTreeBuilder transitionTreeBuilder) {
		this.transitionTreeBuilder = transitionTreeBuilder;
	}

	/**
	 * ������
	 *
	 * @param task - �������
	 * @return �������
	 */
	public SolutionRoutes solve(DesignTask task) {
		NodeTree nodeTree = transitionTreeBuilder.buildNodeTree(null, task);
		while (!nodeTree.haveNodeMatchingTarget() && nodeTree.haveUncheckedNodes()) {
			Node node = nodeTree.getEffectiveNode(task.getTargetState());
			DesignTask subTask = task.createSubTask(node.getState());
			NodeTree nodeSubTree = transitionTreeBuilder.buildNodeTree(node, subTask);
			nodeTree.integrateWith(nodeSubTree);
		}
		return nodeTree.getSolutionRoutes();
	}
}
