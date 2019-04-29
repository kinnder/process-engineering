package cppbuilder.utility;

/** ���������� ��������� */
public class LogicOperators<Type> {

	/** �������� ��������� */
	public boolean operator_equality(Type value) {
		return equal(value);
	}

	/** �������� ����������� */
	public boolean operator_not_equality(Type value) {
		return !equal(value);
	}

	/** �������� - ��������� */
	public boolean equal(Type value) {
		return isSameObject(value) || allFieldsAreEqual(value);
	}

	/** �������� - ��������� */
	public boolean matches(Type value) {
		return isSameObject(value) || haveMatchingFields(value);
	}

	/** �������� - �������� */
	public boolean includes(Type value) {
		return isSameObject(value) || haveSubset(value);
	}

	/** �������� - �������� ���������� ������� */
	protected boolean isSameObject(Type value) {
		return this == value;
	}

	/** �������� - ��� ���� ����� */
	protected boolean allFieldsAreEqual(Type value) {
		throw new AssertionError("not implemented");
	}

	/** �������� - ���� ����������� ���� */
	protected boolean haveMatchingFields(Type value) {
		throw new AssertionError("not implemented");
	}

	/** �������� - �������� ��������� */
	protected boolean haveSubset(Type value) {
		throw new AssertionError("not implemented");
	}

	/** ����������� � ���� XML ������ */
	public String toXMLString() {
		throw new AssertionError("not implemented");
	}
}
