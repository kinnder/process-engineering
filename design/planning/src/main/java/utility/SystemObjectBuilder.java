package utility;

import model.SystemAttribute;
import model.SystemObject;

/** ����������� �������� */
public class SystemObjectBuilder {

	/** ��������� ���������� */
	private SystemObject construction;

	/**
	 * ���������
	 *
	 * @return ����������������� ������
	 */
	public SystemObject build() {
		return construction;
	}

	/** ����������� */
	public SystemObjectBuilder() {
		construction = new SystemObject();
	}

	/**
	 * �����������
	 *
	 * @param construction - ����������� ������
	 */
	public SystemObjectBuilder(SystemObject construction) {
		this.construction = construction;
	}

	/**
	 * �������� �������
	 *
	 * @param name  - �������� ��������
	 * @param value - �������� ��������
	 * @return ��������� ��������
	 */
	public SystemObjectBuilder addAttribute(String name, String value) {
		construction.addAttribute(new SystemAttribute(name, value));
		return this;
	}
}
