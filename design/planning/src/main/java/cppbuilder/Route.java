package cppbuilder;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

import cppbuilder.utility.LogicOperators;
import cppbuilder.utility.XMLTagBuilder;

/** ���� */
public class Route extends LogicOperators<Route> {

	/** ���� */
	public NodeCollection nodes = new NodeCollection();

	@Override
	public String toXMLString() {
		XMLTagBuilder element = new XMLTagBuilder("route");
		return element.startTag() + nodes.toXMLString() + element.endTag();
	}

	/**
	 * ��������� � ����
	 *
	 * @throws FileNotFoundException
	 */
	public void saveToXMLFile(String fileName) throws FileNotFoundException {
		try (PrintWriter out = new PrintWriter(fileName)) {
			out.println(toXMLString());
		}
	}

	@Override
	protected boolean allFieldsAreEqual(Route route) {
		return nodes.operator_equality(route.nodes);
	}
}
