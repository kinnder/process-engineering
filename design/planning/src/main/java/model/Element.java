package model;

import utility.Collection;
import utility.CollectionItem;

/**
 * ������� �������
 * <p>
 * ������� �������, ������������ ��� �������������� ���������
 * </p>
 */
public class Element {

	/**
	 * ������ ���������
	 * <p>
	 * ������ ��������� �������, ������������ ����������� ���������� ��������
	 * </p>
	 */
	SystemState statePattern;

	/**
	 * �������
	 * <p>
	 * ��������� ��������� ������� � ���������� ���������� ��������
	 * </p>
	 */
	SystemTransition transition;

	/**
	 * �����������
	 *
	 * @param statePattern - ������ ���������
	 * @param transition   - �������
	 */
	public Element(SystemState statePattern, SystemTransition transition) {
		this.statePattern = statePattern;
		this.transition = transition;
	}

	/**
	 * �������� ������ ���������
	 *
	 * @return ������ ��������� ������� ��� �������� ��������
	 */
	public SystemState getStatePattern() {
		return statePattern;
	}

	/**
	 * �������� �������
	 *
	 * @return ������� ��� �������� ��������
	 */
	public SystemTransition getTransition() {
		return transition;
	}

	/**
	 * ���������� ��������� ������� � ���������� ��������
	 * <p>
	 * ���������� ��������� ������� � ���������� ��������� �� ��������� ���������
	 * </p>
	 *
	 * @param state - �������� ���������
	 * @return ��������� �������
	 */
	public Collection getStatesAfterTransitionFromState(SystemState state) {
		Collection transitions = new Collection();
		Collection transitionalStates = state.prepareTransitionalStates(statePattern);
		for (CollectionItem ci : transitionalStates) {
			TransitionalState transitionalState = (TransitionalState) ci;
			SystemState transitionResult = transitionalState.getStateAfterTransition(transition);
			transitions.add(transitionResult);
		}
		return transitions;
	}
}
