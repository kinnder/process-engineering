package cppbuilder;

import cppbuilder.objects.Obstacle;

/** ������ ��������� ������� */
public class SystemStateTree {

	/** ��������� ��������� */
	public SystemState startState = null;

	/** �������� ��������� */
	public SystemState finalState = null;

	/** ���� */
	public SystemStateCollection states = new SystemStateCollection();

	/** ���� */
	public RouteCollection routes = new RouteCollection();

	/** ���� ��������� */
	private ElementBase elements = null;

	/** ������ ��������� ������� */
	private void analyzeSystemState(SystemState systemState, Route route) {
		if (!finalState.isMatchingTo(systemState)) {
			if (!systemState.isDeadEndState()) {
				for (int i = 0; i < systemState.links.size(); i++) {
					route.nodes.add(new Node(systemState.links.get(i)));
					analyzeSystemState(systemState.links.get(i).end, route);
				}
			}
		} else {
			routes.add(route);
		}
	}

	/** �������� - ���� ��������� ������������ � �������� */
	private boolean haveStateMatchingFinalState() {
		if (finalState != null) {
			for (int i = 0; i < states.size(); i++) {
				if (finalState.isMatchingTo(states.get(i))) {
					return true;
				}
			}
		}
		return false;
	}

	/** ���������� ��������� ����� �� ��������� */
	private void buildNextStates(SystemState state) {
		ElementCollection l_elements = elements.find(state);
		for (Element element : l_elements) {
			addNewStateToTree(state, (element));
		}
	}

	/** ����� ����� */
	public void prepareRoutes() {
		routes.clear();
		if (haveStateMatchingFinalState()) {
			if (startState != null) {
				Route route = new Route();
				analyzeSystemState(startState, route);
			}
		}
	}

	/** ���������� ������ */
	public void buildTree(SystemState begin, SystemState end, ElementBase elements) {
		this.elements = elements;
		states.clear();
		states.add(begin);
		int stateId = 0;
		int l_limit = 1000;
		while ((stateId < states.size()) && (l_limit > 0)) {
			SystemState l_state = states.get(stateId);
			if (l_state.includes(end)) {
				startState = states.get(0);
				finalState = (l_state);
				break;
			}
			buildNextStates((l_state));
			stateId++;
			l_limit--;

		}
	}

	/** �������� ����� ���� */
	public void addNewStateToTree(SystemState state, Element element) {
		SystemStateCollection l_newStates = element.calculateSystemState(state);
		for (SystemState l_newState : l_newStates) {
			// !\todo refactor
			SystemStateCollection l_result = Obstacle.collision().calculateSystemState(l_newState);
			if (l_result.size() == 0)
//			if (p_element->statePassesRestriction(*l_newState))
			{
				if (!states.exists(l_newState)) {
					states.add(l_newState);
					state.link(l_newState, element.transition);
				}
			}
		}
	}
}
