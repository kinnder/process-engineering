package cppbuilder.knowledge;

/** �������������� ������� � ���������� */
public class CoordinateCalculator {

	/** ��� ��������� �� X */
	public int x_step = 2;

	/** ���������� 1 ������� �� X */
	public int x_zero = 4;

	/** ��� ��������� �� Y */
	public int y_step = 3;

	/** ���������� 1 ������� �� Y */
	public int y_zero = 12;

	/** ��� ��������� �� Z */
	public int z_step = 10;

	/** ���������� 1 ������� �� Z */
	public int z_zero = 0;

	/** ��������� X ���������� */
	public int getXCoordinateForPosition(int position) {
		position = (position - 1) % 5;
		return position * x_step + x_zero;
	}

	/** ��������� Y ���������� */
	public int getYCoordinateForPosition(int position) {
		return (position > 5) ? y_zero : y_zero + y_step;
	}

	/** ��������� Z ���������� */
	public int getZCoordinateForPosition(int position) {
		return z_zero + z_step * position;
	}
}
