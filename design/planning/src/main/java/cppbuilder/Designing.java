package cppbuilder;

import java.util.List;

import cppbuilder.knowledge.CoordinateCalculator;
import cppbuilder.management.Command;
import cppbuilder.objects.Part;

/** ������ �������������� */
public class Designing {

	/** ������ ������� */
	public SystemStateTree nodeTree = new SystemStateTree();

	/** �������������� ������� � ���������� */
	public CoordinateCalculator calculator = new CoordinateCalculator();

	/** �������������� */
	public void prepareCommands(List<Part> parts, List<Command> commands) {
		addCommonStartCommands(commands);

		for (Part part : parts) {
			addPartSpecificCommand(part, commands);
		}

		addCommonFinishCommands(commands);
	}

	/** ������� ������ */
	public boolean solve(SystemState begin, SystemState end, ElementBase elements) {
		nodeTree.buildTree(begin, end, elements);
		nodeTree.prepareRoutes();
		return (nodeTree.routes.size() > 0);
	}

	/** �������� ������� */
	public Route getSolution() {
		return nodeTree.routes.getShortestRoute();
	}

	private Command command;

	/** ���������� ����� ��������� ����� */
	protected void addCommonStartCommands(List<Command> commands) {
		// �� - ������� ���� � ������������ ����� � ������� 2
		command = new Command(1, 2, 0, 0, 0);
		commands.add(command);
	}

	/** ���������� ����� �������� ����� */
	protected void addCommonFinishCommands(List<Command> commands) {
		// �� - �������������� ����������� (0,0)
		command = new Command(2, 1, 0, 0, 0);
		commands.add(command);

		// �� - ������� ���� � ������� 2 �� ������������ �����
		command = new Command(1, 4, 0, 0, 0);
		commands.add(command);
	}

	/** ���������� ������ ���� */
	protected void addPartSpecificCommand(Part part, List<Command> commands) {
		// �� - �������������� ����������� ()
		command = new Command(2, 1, calculator.getXCoordinateForPosition(part.lens.position),
				calculator.getYCoordinateForPosition(part.lens.position), 0);
		commands.add(command);

		// �� - ������������ ����������� (1[��������], 1[�����])
		command = new Command(2, 2, calculator.getZCoordinateForPosition(1), 1, 0);
		commands.add(command);

		// �� - ������������ ����������� (0[�������], 0[������])
		command = new Command(2, 2, calculator.getZCoordinateForPosition(0), 0, 0);
		commands.add(command);

		// �� - �������������� ����������� ()
		command = new Command(2, 1, calculator.getXCoordinateForPosition(part.frame.position),
				calculator.getYCoordinateForPosition(part.frame.position), 0);
		commands.add(command);

		// �� - ������������ ����������� (1[��������], 2[�������])
		command = new Command(2, 2, calculator.getZCoordinateForPosition(1), 2, 0);
		commands.add(command);

		// �� - ������������ ����������� (0[�������], 0[������])
		command = new Command(2, 2, calculator.getZCoordinateForPosition(0), 0, 0);
		commands.add(command);
	}
}
