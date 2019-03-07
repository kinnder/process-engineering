package model;

import utility.Collection;
import utility.CollectionItem;

/** ���������� ��������� */
public class TransitionalState implements CollectionItem {

	/** ���������� ������� */
	public Collection modifiingObjects;

	/** ���������� ������� */
	public Collection permanentObjects;

	/** ����������� */
	public TransitionalState() {
		modifiingObjects = new Collection();
		permanentObjects = new Collection();
	}

	/**
	 * ���������� ��������� ����� ��������
	 *
	 * @param transition - �������
	 * @return ��������� �������, ����� ���������� �������� ����������� ���������
	 */
	public SystemState getStateAfterTransition(SystemTransition transition) {
		SystemState state = new SystemState();
		Collection modifiedObjects = transition.getModifiedObjects(modifiingObjects);
		state.objects.addRange(modifiedObjects);
		state.objects.addRange((Collection) permanentObjects.clone());
		return state;
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int compareTo(CollectionItem o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean matches(Object pattern) {
		// TODO Auto-generated method stub
		return false;
	}
}
