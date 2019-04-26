package cppbuilder.utility;

import java.util.Random;

/** ��������� ��������� ������� */
public class RandomGenerator {

	private static Random random = new Random();

	/** ��������� ��������� ������ */
	public static String getString(int length) {
		char result[] = new char[length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (char) (random.nextInt(26) + 'a');
		}
		return new String(result);
	}

	/** ��������� ��������� ������ �� ��������� */
	public static String getStringFrom(String[] variants) {
		int id = random.nextInt(variants.length);
		return variants[id];
	}

	/** ��������� ���������� ������ ����� �� ���������� ��������� ����� */
	public static int getInteger(int min, int max) {
		int bound = max - min;
		return random.nextInt(bound) + min;
	}

	/** ��������� ���������� ������ ����� �� ��������� */
	public static int getIntegerFrom(int[] variants) {
		int id = random.nextInt(variants.length);
		return variants[id];
	}

	/** ��������� ���������� ����������� */
	public static boolean getBool() {
		if (getInteger(0, 10000) > 5000) {
			return true;
		}
		return false;
	}

	/**
	 * ��������� ���������� ������������� ����� �� ���������� ��������� ������������
	 * �����
	 */
	public static double getDouble(int min, int max, int precision) {
		// ������������ ����� �����
		double result = getInteger(min, max);
		// ������������ ������� �����
		double rational = 0.0;
		int weight = 1;
		for (int i = 0; i < precision; i++) {
			rational += getInteger(0, 9) * weight;
			weight *= 10;
		}
		// ����������� ������
		result += rational / weight;
		return result;
	}
}
