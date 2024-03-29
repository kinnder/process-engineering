package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.method.Node;
import planning.model.System;

public class NodeXMLSchema implements XMLSchema<Node> {

	final private static String TAG_node = "node";

	final private static String TAG_id = "id";

	final private static String TAG_checked = "checked";

	private SystemXMLSchema systemXMLSchema;

	public NodeXMLSchema() {
		this(new SystemXMLSchema());
	}

	NodeXMLSchema(SystemXMLSchema systemXMLSchema) {
		this.systemXMLSchema = systemXMLSchema;
	}

	@Override
	public String getSchemaName() {
		return TAG_node;
	}

	@Override
	public Node parse(Element root) throws DataConversionException {
		final String id = root.getChildText(TAG_id);
		final boolean checked = Boolean.valueOf(root.getChildText(TAG_checked));
		System system;
		{
			final Element element = root.getChild(systemXMLSchema.getSchemaName());
			system = systemXMLSchema.parse(element);
		}

		return new Node(id, system, checked);
	}

	@Override
	public Element combine(Node node) {
		final Element root = new Element(TAG_node);
		{
			final Element element = new Element(TAG_id);
			element.setText(node.getId());
			root.addContent(element);
		}
		{
			final Element element = new Element(TAG_checked);
			element.setText(Boolean.toString(node.getChecked()));
			root.addContent(element);
		}
		{
			final Element element = systemXMLSchema.combine(node.getSystem());
			root.addContent(element);
		}

		return root;
	}
}
