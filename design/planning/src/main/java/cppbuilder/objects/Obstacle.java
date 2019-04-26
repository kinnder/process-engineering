package cppbuilder.objects;

import cppbuilder.Attribute;
import cppbuilder.Element;
import cppbuilder.SystemObject;
import cppbuilder.Transition;
import cppbuilder.TransitionType;

/** ����������� */
public class Obstacle extends SystemObject {

	/** �������� �� ��������� */
	public final static String DEFAULT_NAME = "�����������";

	/** ����������� */
	public Obstacle() {
		this(0, 0, 0, 0);
	}

	/** ����������� � ����������� */
	public Obstacle(Rect obstacle) {
		this(obstacle.left, obstacle.top, obstacle.right, obstacle.bottom);
	}

	/** ����������� � ����������� */
	public Obstacle(int left, int top, int right, int bottom) {
		super(DEFAULT_NAME);
		attributes.add(new Attribute(Attribute.ATTRIBUTE_IS_AN_OBSTACLE));
		attributes.add(new Attribute(Attribute.ATTRIBUTE_X, left, right));
		attributes.add(new Attribute(Attribute.ATTRIBUTE_Y, bottom, top));
		attributes.shuffle();
	}

	/** ������� ������� - ������������ */
	public static Element collision() {
		Element element = new Element();
		element.systemStateBeforeTransition.systemObjects
				.add(new MaterialPoint(new Point(0, 0), Attribute.ATTRIBUTE_MOVEABLE));
		element.systemStateBeforeTransition.systemObjects.add(new Obstacle());
		element.systemStateAfterTransition.systemObjects
				.add(new MaterialPoint(new Point(0, 0), Attribute.ATTRIBUTE_NOT_MOVEABLE));
		element.systemStateAfterTransition.systemObjects.add(new Obstacle());
		element.transition = new Transition("������������", TransitionType.ChangeAttributes);
		return element;
	}
}
