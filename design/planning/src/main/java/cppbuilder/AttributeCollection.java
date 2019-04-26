package cppbuilder;

import cppbuilder.utility.Collection;

/** ��������� ��������� */
public class AttributeCollection extends Collection<Attribute> {

	/** ����������� */
	public AttributeCollection() {
		xmlName = "attributes";
	}

	/** ����� �������� */
	public Attribute find(String name) {
		return super.find(attribute -> attribute.name.equals(name));
	}
}
