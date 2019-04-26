package cppbuilder.systems;

import cppbuilder.Attribute;
import cppbuilder.ElementBase;
import cppbuilder.SystemState;
import cppbuilder.objects.MaterialPoint;
import cppbuilder.objects.Obstacle;
import cppbuilder.objects.Point;
import cppbuilder.objects.Rect;

/** ���������� ������� - ������������ ����� */
public class MaterialPoints {

	/** ������� ��������� ������� - ���� ������������ ����� */
	public static SystemState onePoint(Point point) {
		return nPoints(new Point[] { point });
	}

	/** ������� ��������� ������� - ��� ������������ ����� */
	public static SystemState twoPoints(Point point1, Point point2) {
		return nPoints(new Point[] { point1, point2 });
	}

	/** ������� ��������� ������� - ���� ������������ ����� � ����������� */
	public static SystemState onePointWithObstacles(Point point) {
		SystemState state = onePoint(point);
		add_Obstacles(state);
		return state;
	}

	/** ������� ��������� ������� - ���� ������������ ����� � ���� ����������� */
	public static SystemState onePointWithOneObstacle() {
		return onePointWithOneObstacle(new Point(0, 0), new Rect(0, 0, 0, 0));
	}

	/** ������� ��������� ������� - ���� ������������ ����� � ���� ����������� */
	public static SystemState onePointWithOneObstacle(Point point, Rect obstacle) {
		SystemState state = onePoint(point);
		state.systemObjects.add(new Obstacle(obstacle));
		return state;
	}

	/**
	 * ������� ��������� ������� - ���� ������������ ����� � ���� �����������
	 * (������������)
	 */
	public static SystemState onePointCollidedWithOneObstacle(Point point, Rect obstacle) {
		SystemState state = new SystemState();
		state.systemObjects.add(new MaterialPoint(point, Attribute.ATTRIBUTE_NOT_MOVEABLE));
		state.systemObjects.add(new Obstacle(obstacle));
		return state;
	}

	/** ������� ��������� ������� - ��� ������������ ����� � ��� ����������� */
	public static SystemState twoPointsWithTwoObstacles(Point point1, Point point2, Rect obstacle1, Rect obstacle2) {
		SystemState state = twoPoints(point1, point2);
		state.systemObjects.add(new Obstacle(obstacle1));
		state.systemObjects.add(new Obstacle(obstacle2));
		return state;
	}

	/** ������� ���� ������� ��������� - ����������� (���) */
	public static ElementBase movements() {
		ElementBase base = new ElementBase();
		base.elements.add(MaterialPoint.moveUp());
		base.elements.add(MaterialPoint.moveDown());
		base.elements.add(MaterialPoint.moveLeft());
		base.elements.add(MaterialPoint.moveRight());
		base.elements.add(Obstacle.collision());
		return base;
	}

	/** ������� ���� ������� ��������� - ����������� (�����) */
	public static ElementBase movementLeft() {
		ElementBase base = new ElementBase();
		base.elements.add(MaterialPoint.moveLeft());
		return base;
	}

	/** ������� ���� ������� ��������� - ����������� (������) */
	public static ElementBase movementRight() {
		ElementBase base = new ElementBase();
		base.elements.add(MaterialPoint.moveRight());
		return base;
	}

	/** ������� ��������� ������� - ��������� ������������ ����� */
	private static SystemState nPoints(Point[] points) {
		SystemState state = new SystemState();
		for (Point point : points) {
			state.systemObjects.add(new MaterialPoint(point));
		}
		return state;
	}

	/** ����������� ������ - ����� */
	private static void prepare_Border(SystemState systemState, Rect rectangle) {
		systemState.systemObjects.add(new Obstacle(rectangle.left, rectangle.top, rectangle.right, rectangle.top));
		systemState.systemObjects.add(new Obstacle(rectangle.right, rectangle.top, rectangle.right, rectangle.bottom));
		systemState.systemObjects
				.add(new Obstacle(rectangle.right, rectangle.bottom, rectangle.left, rectangle.bottom));
		systemState.systemObjects.add(new Obstacle(rectangle.left, rectangle.bottom, rectangle.left, rectangle.top));
	}

	/** �������� - ����� */
	private static void add_Obstacle(SystemState systemState, Rect rectangle) {
		systemState.systemObjects.add(new Obstacle(rectangle));
	}

	/** �������� - ����������� */
//	 y
//	 6 + - - - - - - - - - +
//	 5 | . - - + - - - + . |
//	 4 | . . . | . . . | . |
//	 3 | . | . | . | . | . |
//	 2 | . | . . . | . . . |
//	 1 | . + - - - + - - . |
//	 0 + - - - - - - - - - +
//	 0 1 2 3 4 5 6 7 8 9 A x
	private static void add_Obstacles(SystemState systemState) {
		prepare_Border(systemState, new Rect(0, 0, 10, 6));
		add_Obstacle(systemState, new Rect(4, 5, 4, 3));
		add_Obstacle(systemState, new Rect(8, 5, 8, 3));
		add_Obstacle(systemState, new Rect(2, 3, 2, 1));
		add_Obstacle(systemState, new Rect(6, 3, 6, 1));
		add_Obstacle(systemState, new Rect(2, 1, 8, 1));
		add_Obstacle(systemState, new Rect(2, 5, 2, 8));
	}
}
