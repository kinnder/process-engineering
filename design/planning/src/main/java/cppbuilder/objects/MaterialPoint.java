package cppbuilder.objects;

import cppbuilder.Attribute;
import cppbuilder.Element;
import cppbuilder.SystemObject;
import cppbuilder.SystemState;
import cppbuilder.Transition;
import cppbuilder.TransitionType;

/** ������������ ����� */
public class MaterialPoint extends SystemObject {

	public final static String MOVE_LEFT = "�����";

	public final static String MOVE_RIGHT = "������";

	public final static String MOVE_UP = "�����";

	public final static String MOVE_DOWN = "����";

	public final static String DEFAULT_NAME = "�����";

	/** ����������� */
	public MaterialPoint() {
		this(new Point(0, 0), Attribute.ATTRIBUTE_MOVEABLE);
	}

	/** ����������� � ����������� */
	public MaterialPoint(Point position) {
		this(position, Attribute.ATTRIBUTE_MOVEABLE);
	}

	/** ����������� � ����������� */
	public MaterialPoint(Point position, String moveableValue) {
		super(DEFAULT_NAME);
		attributes.add(new Attribute(Attribute.ATTRIBUTE_X, position.x));
		attributes.add(new Attribute(Attribute.ATTRIBUTE_Y, position.y));
		attributes.add(new Attribute(moveableValue));
		attributes.shuffle();
	}

	/** ������� ������� - ����������� ����� */
	public static Element moveUp() {
		return movement(MOVE_UP, Attribute.ATTRIBUTE_Y, 1);
	}

	/** ������� ������� - ����������� ���� */
	public static Element moveDown() {
		return movement(MOVE_DOWN, Attribute.ATTRIBUTE_Y, -1);
	}

	/** ������� ������� - ����������� ����� */
	public static Element moveLeft() {
		return movement(MOVE_LEFT, Attribute.ATTRIBUTE_X, -1);
	}

	/** ������� ������� - ����������� ������ */
	public static Element moveRight() {
		return movement(MOVE_RIGHT, Attribute.ATTRIBUTE_X, 1);
	}

	/** ������� ������� - ����������� */
	private static Element movement(String transition, String attribute, int value) {
		Element element = new Element();
		element.systemStateBeforeTransition = create_Object_MovementCondition(attribute);
		element.systemStateAfterTransition = create_Object_Delta(attribute, value);
		element.transition = new Transition(transition, TransitionType.DeltaAttributes);
		return element;
	}

	/** ������� ������ - ������� ����������� */
	private static SystemState create_Object_MovementCondition(String attribute) {
		SystemState state = new SystemState();
		SystemObject object = new SystemObject(MaterialPoint.DEFAULT_NAME);
		object.attributes.add(new Attribute(attribute));
		object.attributes.add(new Attribute(Attribute.ATTRIBUTE_MOVEABLE));
		object.attributes.shuffle();
		state.systemObjects.add(object);
		return state;
	}

	/** ������� ������ - ������� ��������� */
	private static SystemState create_Object_Delta(String attribute, int value) {
		SystemState state = new SystemState();
		SystemObject object = new SystemObject(MaterialPoint.DEFAULT_NAME);
		object.attributes.add(new Attribute(attribute, value));
		state.systemObjects.add(object);
		return state;
	}
}
