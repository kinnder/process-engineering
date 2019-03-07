package model;

import utility.CollectionItem;

/**
 * ������� �������
 */
public class SystemAttribute implements CollectionItem {

	/** �������� */
	public String name;

	/** �������� */
	public Object value;

	/** ��� �������� */
	public SystemAttributeType type;

	/**
	 * �����������
	 *
	 * @param name  - ��������
	 * @param value - ��������
	 */
	public SystemAttribute(String name, String value) {
		this.name = name;
		this.value = value;
		this.type = SystemAttributeType.String;
	}

	/**
	 * �����������
	 *
	 * @param name  - ��������
	 * @param value - ��������
	 */
	public SystemAttribute(String name, Integer value) {
		this.name = name;
		this.value = value;
		this.type = SystemAttributeType.Integer;
	}

	/**
	 * �����������
	 *
	 * @param name  - ��������
	 * @param value - ��������
	 */
	public SystemAttribute(String name, Float value) {
		this.name = name;
		this.value = value;
		this.type = SystemAttributeType.Float;
	}

	@Override
	public boolean matches(Object pattern) {
		SystemAttribute patternAttribute = (SystemAttribute) pattern;
		if (this == patternAttribute) {
			return true;
		}
		return name == patternAttribute.name;
	}

	@Override
	public Object clone() {
		switch (type) {
		case Float:
			return new SystemAttribute(name, getFloat());
		case Integer:
			return new SystemAttribute(name, getInteger());
		case String:
			return new SystemAttribute(name, getString());
		default:
			return null;
		}
	}

	/**
	 * ��������������
	 *
	 * @param modification �����������
	 */
	public void modify(SystemAttribute modification) {
		switch (type) {
		case Float:
			value = getFloat() + modification.getFloat();
			break;
		case Integer:
			value = getInteger() + modification.getInteger();
			break;
		case String:
			value = getString() + modification.getString();
			break;
		default:
			break;
		}
	}

	public int getInteger() {
		return (Integer) value;
	}

	public void setInteger(int i) {
		value = i;
		type = SystemAttributeType.Integer;
	}

	public float getFloat() {
		return (Float) value;
	}

	public void setFloat(float v) {
		value = v;
		type = SystemAttributeType.Float;
	}

	public String getString() {
		return (String) value;
	}

	public void setString(String s) {
		value = s;
		type = SystemAttributeType.String;
	}

	@Override
	public int compareTo(CollectionItem o) {
		SystemAttribute obj = (SystemAttribute) o;
		if (obj == null) {
			return 1;
		}
		if (obj == this) {
			return 0;
		}
		int compareToResultForNames = name.compareTo(obj.name);
		if (compareToResultForNames != 0) {
			return compareToResultForNames;
		}
		int compareToResultForValues = 0;
		switch (type) {
		case Float:
			compareToResultForValues = Math.round(getFloat() - obj.getFloat());
			break;
		case Integer:
			compareToResultForValues = getInteger() - obj.getInteger();
			break;
		case String:
			compareToResultForValues = getString().compareTo(obj.getString());
			break;
		default:
			break;

		}
		return compareToResultForValues;
	}
}
