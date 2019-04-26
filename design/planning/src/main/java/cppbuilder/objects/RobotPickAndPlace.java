package cppbuilder.objects;

import cppbuilder.Attribute;
import cppbuilder.Element;
import cppbuilder.SystemObject;

/** ����� ����������� */
public class RobotPickAndPlace extends SystemObject {

	public final static String DEFAULT_NAME = "�����-�����������";

	/** ����������� */
	public RobotPickAndPlace() {
		super(DEFAULT_NAME);
		attributes.add(new Attribute(Attribute.ATTRIBUTE_POSITION, Attribute.VALUE_SHUTTLE));
		attributes.shuffle();
	}

	/** ������� ������� - ����������� ���� � ����� �� ��������� ������ 1 */
	public static Element moveBoxFromShuttleToTable1() {
		// TODO: ���������� �������� ��������
		return new Element();
	}
}
