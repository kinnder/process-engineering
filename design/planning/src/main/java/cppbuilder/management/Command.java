package cppbuilder.management;

/** ������� */
public class Command {

	/** ����� ���������� */
	public int deviceId;

	/** ����� ��������� */
	public int programId;

	/** 1-� �������� ��������� */
	public int parameter1;

	/** 2-� �������� ��������� */
	public int parameter2;

	/** 3-� �������� ��������� */
	public int parameter3;

	public Command(int deviceId, int programId, int parameter1, int parameter2, int parameter3) {
		this.deviceId = deviceId;
		this.programId = programId;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.parameter3 = parameter3;
	}
}
