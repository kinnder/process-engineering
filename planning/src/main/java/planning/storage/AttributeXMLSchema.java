package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Attribute;

public class AttributeXMLSchema implements XMLSchema<Attribute> {

	final private static String TAG_schema = "attribute";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	private ValueXMLSchema valueSchema = new ValueXMLSchema();

	@Override
	public Attribute parse(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		Object value = valueSchema.parse(root.getChild("value"));
		return new Attribute(name, value);
	}

	@Override
	public Element combine(Attribute attribute) {
		Element root = new Element("attribute");

		Element name = new Element("name");
		name.setText(attribute.getName());
		root.addContent(name);

		Object attributeValue = attribute.getValue();
		if (attributeValue != null) {
			Element value = valueSchema.combine(attributeValue);
			root.addContent(value);
		}
		return root;
	}
}
