package model;

import java.util.ArrayList;
import java.util.List;

/** ���� ������� ��������� */
public class ElementsDataBase {

	/** �������� */
	public List<Element> elements;

	/** ����������� */
	public ElementsDataBase() {
		elements = new ArrayList<Element>();
	}

	/**
	 * �������� - ���� ������� � �������� ����������, ����������� � ���������
	 * ����������
	 *
	 * @param state - ���������
	 * @return
	 */
	public boolean haveElementMatchingState(SystemState state) {
		for (Element element : elements) {
			if (state.matches(element.getStatePattern())) {
				return true;
			}
		}
		return false;
	}
}
