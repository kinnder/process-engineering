package cppbuilder;

import cppbuilder.utility.LogicOperators;
import cppbuilder.utility.XMLTagBuilder;

/** ������ */
public class SystemObject extends LogicOperators<SystemObject> {

	/** �������� */
	public String name;

	/** �������� */
	public AttributeCollection attributes;

	/** ������������� */
	public int id;

	/** �������� ���������� �������������� */
	private static int nextId = 1;

	/** ����������� */
	public SystemObject() {
		this("undefined");
	}

	/** ����������� � ����������� */
	public SystemObject(String name) {
		this.name = name;
		this.id = nextId++;
		this.attributes = new AttributeCollection();
	}

	@Override
	public String toXMLString() {
		XMLTagBuilder objectTag = new XMLTagBuilder("object");
		XMLTagBuilder nameTag = new XMLTagBuilder("name");
		return objectTag.startTag() + nameTag.print(name) + attributes.toXMLString() + objectTag.endTag();
	}

	@Override
	protected boolean allFieldsAreEqual(SystemObject systemObject) {
		return name.equals(systemObject.name) && attributes.operator_equality(systemObject.attributes);
	}

	@Override
	protected boolean haveMatchingFields(SystemObject systemObject) {
		return name.equals(systemObject.name) && attributes.matches(systemObject.attributes);
	}

	@Override
	protected boolean haveSubset(SystemObject systemObject) {
		return name.equals(systemObject.name) && attributes.includes(systemObject.attributes);
	}
}
