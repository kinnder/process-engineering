package utility;

import model.SystemObject;
import model.SystemState;

/** ��������� ��������� ������� */
public class SystemStateBuilder {

	/** ��������� ���������� */
	private SystemState construction;

	/** ����������� */
	public SystemStateBuilder() {
		construction = new SystemState();
	}

	/**
	 * �����������
	 *
	 * @param construction - ����������� ���������
	 */
	public SystemStateBuilder(SystemState construction) {
		this.construction = construction;
	}

	/**
	 * �������� ������
	 *
	 * @param object_n - ������
	 * @return ��������� ���������
	 */
	public SystemStateBuilder addObject(SystemObject object_n) {
		construction.addObject(object_n);
		return this;
	}

	/**
	 * �������� �����
	 *
	 * @param object_1 - ������ 1
	 * @param object_2 - ������ 2
	 * @param linkName - �������� �����
	 * @return ��������� ���������
	 */
	public SystemStateBuilder linkObjects(SystemObject object_1, SystemObject object_2, String linkName) {
		try {
			construction.createLink(object_1, object_2, linkName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return this;
	}

	public SystemStateBuilder linkObjects(SystemObject object_1, SystemObject object_2) {
		return linkObjects(object_1, object_2, null);
	}

	/**
	 * ���������
	 *
	 * @return ����������������� ���������
	 */
	public SystemState build() {
		return construction;
	}
}
