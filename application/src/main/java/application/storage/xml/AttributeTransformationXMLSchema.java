package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.AttributeTransformation;

public class AttributeTransformationXMLSchema implements XMLSchema<AttributeTransformation> {

	final private static String TAG_attributeTransformation = "attributeTransformation";

	final private static String TAG_id = "id";

	final private static String TAG_name = "name";

	@Override
	public String getSchemaName() {
		return TAG_attributeTransformation;
	}

	public AttributeTransformationXMLSchema() {
		this(new ValueXMLSchema());
	}

	AttributeTransformationXMLSchema(ValueXMLSchema valueXMLSchema) {
		this.valueXMLSchema = valueXMLSchema;
	}

	private ValueXMLSchema valueXMLSchema;

	@Override
	public AttributeTransformation parse(Element root) throws DataConversionException {
		String id = root.getChildText(TAG_id);
		String name = root.getChildText(TAG_name);
		Object value = valueXMLSchema.parse(root.getChild(valueXMLSchema.getSchemaName()));
		return new AttributeTransformation(id, name, value);
	}

	@Override
	public Element combine(AttributeTransformation transformation) {
		Element id = new Element(TAG_id);
		id.setText(transformation.getId());
		Element name = new Element(TAG_name);
		name.setText(transformation.getAttributeName());
		Element value = valueXMLSchema.combine(transformation.getAttributeValue());
		Element root = new Element(TAG_attributeTransformation);
		root.addContent(id);
		root.addContent(name);
		root.addContent(value);
		return root;
	}

}
