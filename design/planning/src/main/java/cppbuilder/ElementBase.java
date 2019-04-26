package cppbuilder;

/** ���� ������� ��������� */
public class ElementBase {

	/** ������� �������� */
	public ElementCollection elements = new ElementCollection();

	/** ����� ��������� */
	public ElementCollection find(SystemState systemState) {
		ElementCollection result = new ElementCollection();
		for (Element element : elements) {
			if (element.systemStateBeforeTransition.matches(systemState)) {
				result.add(element);
			}
		}
		return result;
	}

	/** ����� ��������, ���������� ��������� */
	public ElementCollection findStateChangingElements(SystemState systemState) {
		ElementCollection result = new ElementCollection();
		for (Element element : elements) {
			if (element.systemStateBeforeTransition.matches(systemState)) {
				if (element.transition.type == TransitionType.ChangeAttributes) {
					result.add(element);
				}
			}
		}
		return result;
	}

}
