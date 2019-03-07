package model;

/**
 * �������
 * <p>
 * ������� �� ��������������
 * </p>
 */
public class DesignTask {

	/** ��������� ��������� ������� */
	SystemState initialState;

	/** �������� ��������� ������� */
	SystemState targetState;

	/** ���� ��������� */
	ElementsDataBase elements;

	/**
	 * �����������
	 *
	 * @param initialState - ��������� ��������� �������
	 * @param targetState  - �������� ��������� �������
	 * @param elements     - ���� ���������
	 */
	public DesignTask(SystemState initialState, SystemState targetState, ElementsDataBase elements) {
		this.initialState = initialState;
		this.targetState = targetState;
		this.elements = elements;
	}

	/**
	 * �������� �������� ���������
	 *
	 * @return �������� ��������� ������� ��� �������� �������
	 */
	public SystemState getTargetState() {
		return targetState;
	}

	/**
	 * ������� ��������������� �������
	 *
	 * @param systemState - ��������� ��������� ������� ��� ���������������� �������
	 * @return ������� �� ��������������
	 */
	public DesignTask createSubTask(SystemState systemState) {
		return new DesignTask(systemState, targetState, elements);
	}

	/**
	 * �������� ���� ���������
	 *
	 * @return ���� ��������� ��� �������� �������
	 */
	public ElementsDataBase getElements() {
		return elements;
	}

	/**
	 * �������� ��������� ���������
	 *
	 * @return ��������� ��������� ������� ��� �������� �������
	 */
	public SystemState getInitialState() {
		return initialState;
	}

	/**
	 * �������� - �������� ����������
	 *
	 * @return true - ���� ������� �������� ����������;<br>
	 *         false - ���� ������� �������� �� ����������
	 */
	public boolean isValid() {
		return (initialState != null) && (targetState != null) && (elements != null)
				&& elements.haveElementMatchingState(initialState);
	}
}
