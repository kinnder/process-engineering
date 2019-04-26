package cppbuilder;

import cppbuilder.utility.LogicOperators;
import cppbuilder.utility.XMLTagBuilder;

/** ������� */
public class Transition extends LogicOperators<Transition> {

	/** �������� */
	public String name;

	/** ��� */
	public TransitionType type;

	/** ����������� */
	public Transition() {
		this("unknown", TransitionType.Unknown);
	}

	/** ����������� � ����������� */
	public Transition(String name) {
		this(name, TransitionType.Unknown);
	}

	/** ����������� � ����������� */
	public Transition(String name, TransitionType type) {
		this.name = name;
		this.type = type;
	}

	@Override
	protected boolean allFieldsAreEqual(Transition transition) {
		return (name.equals(transition.name)) && (type == transition.type);
	}

	@Override
	public String toXMLString() {
		XMLTagBuilder transitionTag = new XMLTagBuilder("transition");
		XMLTagBuilder nameTag = new XMLTagBuilder("name");
		XMLTagBuilder typeTag = new XMLTagBuilder("type");

		return transitionTag.startTag() + nameTag.print(name) + typeTag.print(type.toString()) + transitionTag.endTag();
	}
}
