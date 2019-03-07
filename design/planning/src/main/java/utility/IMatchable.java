package utility;

/**
 * ��������� - �������� ������������
 */
public interface IMatchable {

	/**
	 * �������������
	 *
	 * @param pattern - ������
	 * @return true - ������ ������������� �������<br>
	 *         false - ������ �� ������������� �������
	 *
	 */
	boolean matches(Object pattern);
}
