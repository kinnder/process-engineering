package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;
import org.jdom2.Namespace;

import planning.model.SystemProcess;
import planning.model.SystemOperation;

public class SystemProcessXMLSchema implements XMLSchema<SystemProcess> {

	final private static String TAG_process = "process";

	@Override
	public String getSchemaName() {
		return TAG_process;
	}

	public SystemProcessXMLSchema() {
		this(new SystemOperationXMLSchema());
	}

	SystemProcessXMLSchema(SystemOperationXMLSchema systemOperationXMLSchema) {
		this.systemOperationXMLSchema = systemOperationXMLSchema;
	}

	private SystemOperationXMLSchema systemOperationXMLSchema;

	@Override
	public Element combine(SystemProcess process) {
		final Element root = new Element(TAG_process);
		final Namespace xsiNamespace = Namespace.getNamespace("xsi", "http://www.w3.org/2001/XMLSchema-instance");
		root.setAttribute("noNamespaceSchemaLocation", "../process.xsd", xsiNamespace);
		for (SystemOperation systemOperation : process) {
			final Element element = systemOperationXMLSchema.combine(systemOperation);
			root.addContent(element);
		}
		return root;
	}

	@Override
	public SystemProcess parse(Element element) throws DataConversionException {
		final SystemProcess systemProcess = new SystemProcess();

		for (Element e : element.getChildren(systemOperationXMLSchema.getSchemaName())) {
			final SystemOperation systemOperation = systemOperationXMLSchema.parse(e);
			systemProcess.add(systemOperation);
		}

		return systemProcess;
	}
}
