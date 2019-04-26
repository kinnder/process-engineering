package cppbuilder;

/** ��� �������� */
public enum TransitionType {
	/** ���������� */
	Unknown,

	/** ������� ��������� */
	DeltaAttributes,

	/** ������ ��������� */
	ChangeAttributes;

	public static TransitionType valueOf(int value) {
		switch (value) {
		case 1:
			return TransitionType.DeltaAttributes;
		case 2:
			return TransitionType.ChangeAttributes;
		default:
			return TransitionType.Unknown;
		}
	}
}
