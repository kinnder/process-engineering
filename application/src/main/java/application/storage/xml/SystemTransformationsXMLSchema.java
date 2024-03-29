package application.storage.xml;

import java.util.List;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;
import planning.method.SystemTransformations;
import planning.model.SystemTransformation;

public class SystemTransformationsXMLSchema implements XMLSchema<SystemTransformations> {

	final private static String TAG_systemTransformations = "systemTransformations";

	@Override
	public String getSchemaName() {
		return TAG_systemTransformations;
	}

	public SystemTransformationsXMLSchema() {
		this(new SystemTransformationXMLSchema());
	}

	SystemTransformationsXMLSchema(SystemTransformationXMLSchema systemTransformationXMLSchema) {
		this.systemTransformationXMLSchema = systemTransformationXMLSchema;
	}

	private SystemTransformationXMLSchema systemTransformationXMLSchema;

	@Override
	public SystemTransformations parse(Element element) throws DataConversionException {
		final SystemTransformations systemTransformations = new SystemTransformations();
		final List<Element> elements = element.getChildren(systemTransformationXMLSchema.getSchemaName());
		for (Element e : elements) {
			final SystemTransformation systemTransformation = systemTransformationXMLSchema.parse(e);
			systemTransformations.add(systemTransformation);
		}
		return systemTransformations;
	}

	@Override
	public Element combine(SystemTransformations systemTransformations) {
		final Element root = new Element(TAG_systemTransformations);
		final Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../systemTransformations.xsd", xsiNamespace);
		for (SystemTransformation systemTransformation : systemTransformations) {
			final Element element = systemTransformationXMLSchema.combine(systemTransformation);
			root.addContent(element);
		}
		return root;
	}
}
