package cppbuilder;

import cppbuilder.utility.LogicOperators;
import cppbuilder.utility.XMLTagBuilder;

/** ������� */
public class Attribute extends LogicOperators<Attribute> {

	/** �������� */
	public String name;

	/** �������� */
	public String value;

	/** ����������� �������� */
	public String minValue = "";

	/** ������������ �������� */
	public String maxValue = "";

	/** �������� �������� - ���������� ������ 1 */
	public static final String VALUE_TABLE1 = "���������� ������ 1";

	/** �������� �������� - ���� */
	public static final String VALUE_SHUTTLE = "����";

	/** �������� �������� - �������������� */
	public static final String ATTRIBUTE_POSITION = "��������������";

	/** �������� �������� - �������� ������������ */
	public static final String ATTRIBUTE_IS_AN_OBSTACLE = "�������� ������������";

	/** �������� �������� - �� ����� ��������� */
	public static final String ATTRIBUTE_NOT_MOVEABLE = "�� ����� ���������";

	/** �������� �������� - ����� ��������� */
	public static final String ATTRIBUTE_MOVEABLE = "����� ���������";

	/** �������� �������� - ����������� */
	public static final String ATTRIBUTE_UNKNOWN = "�����������";

	/** �������� �������� - z */
	public static final String ATTRIBUTE_Z = "z";

	/** �������� �������� - y */
	public static final String ATTRIBUTE_Y = "y";

	/** �������� �������� - x */
	public static final String ATTRIBUTE_X = "x";

	/** ����������� */
	public Attribute() {
		this("undefined", 0);
	}

	/** ����������� � ����������� */
	public Attribute(String name) {
		this(name, 0);
	}

	/** ����������� � ����������� */
	public Attribute(String name, int value) {
		this(name, String.valueOf(value));
	}

	/** ����������� � ����������� */
	public Attribute(String name, String value) {
		this.name = name;
		this.value = value;
	}

	/** ����������� � ����������� */
	public Attribute(String name, int minValue, int maxValue) {
		this(name, 0);
		if (minValue > maxValue) {
			this.minValue = String.valueOf(maxValue);
			this.maxValue = String.valueOf(minValue);
		} else {
			this.minValue = String.valueOf(minValue);
			this.maxValue = String.valueOf(maxValue);
		}
	}

	@Override
	public String toXMLString() {
		XMLTagBuilder attributeTag = new XMLTagBuilder("attribute");
		XMLTagBuilder nameTag = new XMLTagBuilder("name");
		XMLTagBuilder valueTag = new XMLTagBuilder("value");
		XMLTagBuilder minTag = new XMLTagBuilder("min");
		XMLTagBuilder maxTag = new XMLTagBuilder("max");
		return attributeTag.startTag() + nameTag.print(name) + valueTag.print(value) + minTag.print(minValue)
				+ maxTag.print(maxValue) + attributeTag.endTag();
	}

	public boolean haveEqualValues(Attribute attribute) {
		if (value.equals(attribute.value)) {
			return true;
		}
		if (!minValue.isEmpty() && !maxValue.isEmpty()) {
			boolean greaterThanMin = Integer.parseInt(minValue) <= Integer.parseInt(attribute.value);
			boolean lessThanMin = Integer.parseInt(maxValue) >= Integer.parseInt(attribute.value);
			if (greaterThanMin && lessThanMin) {
				return true;
			}
		}
		if (!attribute.minValue.isEmpty() && !attribute.maxValue.isEmpty()) {
			boolean greaterThanMin = Integer.parseInt(value) >= Integer.parseInt(attribute.minValue);
			boolean lessThanMax = Integer.parseInt(value) <= Integer.parseInt(attribute.maxValue);
			if (greaterThanMin && lessThanMax) {
				return true;
			}
		}
		return false;
	}

	@Override
	protected boolean allFieldsAreEqual(Attribute attribute) {
		return name.equals(attribute.name) && value.equals(attribute.value);
	}

	@Override
	protected boolean haveMatchingFields(Attribute attribute) {
		return name.equals(attribute.name);
	}

	@Override
	protected boolean haveSubset(Attribute attribute) {
		return name.equals(attribute.name) && value.equals(attribute.value);
	}
}
