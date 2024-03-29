package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

import planning.method.Edge;
import planning.model.SystemOperation;

public class EdgeXMLSchema implements XMLSchema<Edge> {

	final private static String TAG_edge = "edge";

	final private static String TAG_id = "id";

	final private static String TAG_beginNodeId = "beginNodeId";

	final private static String TAG_endNodeId = "endNodeId";

	private SystemOperationXMLSchema systemOperationXMLSchema;

	public EdgeXMLSchema() {
		this(new SystemOperationXMLSchema());
	}

	EdgeXMLSchema(SystemOperationXMLSchema systemOperationXMLSchema) {
		this.systemOperationXMLSchema = systemOperationXMLSchema;
	}

	@Override
	public String getSchemaName() {
		return TAG_edge;
	}

	@Override
	public Edge parse(Element root) throws DataConversionException {
		final String id = root.getChildText(TAG_id);
		final String beginNodeId = root.getChildText(TAG_beginNodeId);
		final String endNodeId = root.getChildText(TAG_endNodeId);
		SystemOperation systemOperation;
		{
			final Element element = root.getChild(systemOperationXMLSchema.getSchemaName());
			systemOperation = systemOperationXMLSchema.parse(element);
		}

		return new Edge(id, beginNodeId, endNodeId, systemOperation);
	}

	@Override
	public Element combine(Edge edge) {
		final Element root = new Element(TAG_edge);
		{
			final Element element = new Element(TAG_id);
			element.setText(edge.getId());
			root.addContent(element);
		}
		{
			final Element element = new Element(TAG_beginNodeId);
			element.setText(edge.getBeginNodeId());
			root.addContent(element);
		}
		{
			final Element element = new Element(TAG_endNodeId);
			element.setText(edge.getEndNodeId());
			root.addContent(element);
		}
		{
			final Element element = systemOperationXMLSchema.combine(edge.getSystemOperation());
			root.addContent(element);
		}

		return root;
	}
}
