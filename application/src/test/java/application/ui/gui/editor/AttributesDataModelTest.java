package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.Attribute;
import planning.model.AttributeType;
import planning.model.SystemObject;

public class AttributesDataModelTest {

	@RegisterExtension
	JUnit5Mockery context = new JUnit5Mockery() {
		{
			setImposteriser(ByteBuddyClassImposteriser.INSTANCE);
		}
	};

	@AfterEach
	public void teardown() {
		context.assertIsSatisfied();
	}

	EditorDataModel editorDataModel_mock;

	AttributesDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);

		testable = new AttributesDataModel(editorDataModel_mock);
	}

	@Test
	public void newInstance() {
		assertEquals(0, testable.getRowCount());
	}

	@Test
	public void loadAttributes() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});

		testable.loadAttributes(selectedObject_mock, selectedNode_mock);
		assertEquals(2, testable.getRowCount());
	}

	@Test
	public void getColumnClass() {
		assertEquals(String.class, testable.getColumnClass(AttributesDataModel.COLUMN_IDX_NAME));
		assertEquals(String.class, testable.getColumnClass(AttributesDataModel.COLUMN_IDX_TYPE));
		assertEquals(String.class, testable.getColumnClass(AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getColumnName() {
		assertEquals("name", testable.getColumnName(AttributesDataModel.COLUMN_IDX_NAME));
		assertEquals("type", testable.getColumnName(AttributesDataModel.COLUMN_IDX_TYPE));
		assertEquals("value", testable.getColumnName(AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void insertAttribute() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).addAttribute(with(any(Attribute.class)));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.insertAttribute();
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void deleteAttribute_no_row_selected() {
		testable.deleteAttribute(-1);
	}

	@Test
	public void deleteAttribute() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).removeAttribute(attribute_1_mock);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.deleteAttribute(0);
		assertEquals(1, testable.getRowCount());
	}

	@Test
	public void setValueAt_name() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setName("value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, AttributesDataModel.COLUMN_IDX_NAME);
	}

	@Test
	public void setValueAt_type() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setType(AttributeType.BOOLEAN);

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("boolean", 0, AttributesDataModel.COLUMN_IDX_TYPE);
	}

	@Test
	public void setValueAt_value() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).setValue("new-value");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("new-value", 0, AttributesDataModel.COLUMN_IDX_VALUE);
	}

	@Test
	public void setValueAt() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.setValueAt("value", 0, -1);
	}

	@Test
	public void getValueAt_name() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getName();
				will(returnValue("attribute-name"));
			}
		});

		assertEquals("attribute-name", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_NAME));
	}

	@Test
	public void getValueAt_type() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getType();
				will(returnValue(AttributeType.STRING));
			}
		});

		assertEquals(AttributeType.STRING, testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_TYPE));
	}

	@Test
	public void getValueAt_value() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		context.checking(new Expectations() {
			{
				oneOf(attribute_1_mock).getValue();
				will(returnValue("this-value"));
			}
		});

		assertEquals("this-value", testable.getValueAt(0, AttributesDataModel.COLUMN_IDX_VALUE));
	}

	@Test
	public void getValueAt() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);
		final List<Attribute> attributes = new ArrayList<Attribute>();
		final Attribute attribute_1_mock = context.mock(Attribute.class, "attribute-1");
		final Attribute attribute_2_mock = context.mock(Attribute.class, "attribute-2");
		attributes.add(attribute_1_mock);
		attributes.add(attribute_2_mock);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getAttributes();
				will(returnValue(attributes));
			}
		});
		testable.loadAttributes(selectedObject_mock, selectedNode_mock);

		assertEquals(null, testable.getValueAt(0, -1));
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void setColumnCellEditors() {
		final JTable jTable_mock = context.mock(JTable.class);
		final TableColumnModel tableColumnModel_mock = context.mock(TableColumnModel.class);
		final TableColumn tableColumn_mock = context.mock(TableColumn.class);

		context.checking(new Expectations() {
			{
				oneOf(jTable_mock).getColumnModel();
				will(returnValue(tableColumnModel_mock));

				oneOf(tableColumnModel_mock).getColumn(AttributesDataModel.COLUMN_IDX_TYPE);
				will(returnValue(tableColumn_mock));

				// TODO (2023-02-22 #82): добавить Matcher для DefaultCellEditor
				oneOf(tableColumn_mock).setCellEditor(with(any(DefaultCellEditor.class)));
			}
		});

		testable.setColumnCellEditors(jTable_mock);
	}
}
