package planning.storage;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.model.Attribute;
import planning.model.SystemObject;

public class SystemObjectXMLSchema implements XMLSchema<SystemObject> {

	final private static String TAG_schema = "systemObject";

	@Override
	public String getSchemaName() {
		return TAG_schema;
	}

	private AttributeXMLSchema attributeSchema = new AttributeXMLSchema();

	@Override
	public SystemObject parse(Element root) throws DataConversionException {
		String name = root.getChildText("name");
		String id = root.getChildText("id");

		SystemObject object = new SystemObject(name, id);

		for (Element element : root.getChildren("attribute")) {
			Attribute attribute = attributeSchema.parse(element);
			object.addAttribute(attribute);
		}

		return object;
	}

	@Override
	public Element combine(SystemObject systemObject) {
		Element root = new Element("systemObject");

		Element name = new Element("name");
		name.setText(systemObject.getName());
		root.addContent(name);

		Element id = new Element("id");
		id.setText(systemObject.getId());
		root.addContent(id);

		for (Attribute attribute : systemObject.getAttributes()) {
			Element element = attributeSchema.combine(attribute);
			root.addContent(element);
		}

		return root;
	}
}
