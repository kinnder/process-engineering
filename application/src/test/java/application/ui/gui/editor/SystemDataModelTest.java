package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.System;

public class SystemDataModelTest {

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

	JTextField jtfSystemName_mock;

	JComboBox<String> jcbSystemType_mock;

	LinksDataModel linksDataModel_mock;

	ObjectsDataModel objectsDataModel_mock;

	SystemDataModel testable;

	@SuppressWarnings("unchecked")
	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);
		jtfSystemName_mock = context.mock(JTextField.class);
		jcbSystemType_mock = context.mock(JComboBox.class);
		linksDataModel_mock = context.mock(LinksDataModel.class);
		objectsDataModel_mock = context.mock(ObjectsDataModel.class);

		context.checking(new Expectations() {
			{
				oneOf(jcbSystemType_mock).addItemListener(with(any(ItemListener.class)));

				oneOf(jtfSystemName_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new SystemDataModel(jtfSystemName_mock, jcbSystemType_mock, editorDataModel_mock,
				linksDataModel_mock, objectsDataModel_mock);
	}

	@Test
	public void newInstance() {
		context.checking(new Expectations() {
			{
				oneOf(jcbSystemType_mock).addItemListener(with(any(ItemListener.class)));

				oneOf(jtfSystemName_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new SystemDataModel(jtfSystemName_mock, jcbSystemType_mock, editorDataModel_mock);
	}

	@Test
	public void loadSystem_regular() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getName();
				will(returnValue("system-name"));

				oneOf(jtfSystemName_mock).setText("system-name");

				oneOf(jcbSystemType_mock).setSelectedIndex(SystemDataModel.SYSTEM_TYPE_REGULAR);

				oneOf(objectsDataModel_mock).loadObjects(selectedSystem_mock, selectedNode_mock);

				oneOf(linksDataModel_mock).loadLinks(selectedSystem_mock, selectedNode_mock);
			}
		});

		testable.loadSystem(selectedSystem_mock, selectedNode_mock);
	}

	@Test
	public void loadSystem_initialSystem() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getName();
				will(returnValue("initialSystem"));

				oneOf(jtfSystemName_mock).setText("initialSystem");

				oneOf(jcbSystemType_mock).setSelectedIndex(SystemDataModel.SYSTEM_TYPE_INITIAL);

				oneOf(objectsDataModel_mock).loadObjects(selectedSystem_mock, selectedNode_mock);

				oneOf(linksDataModel_mock).loadLinks(selectedSystem_mock, selectedNode_mock);
			}
		});

		testable.loadSystem(selectedSystem_mock, selectedNode_mock);
	}

	@Test
	public void loadSystem_finalSystem() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getName();
				will(returnValue("finalSystem"));

				oneOf(jtfSystemName_mock).setText("finalSystem");

				oneOf(jcbSystemType_mock).setSelectedIndex(SystemDataModel.SYSTEM_TYPE_FINAL);

				oneOf(objectsDataModel_mock).loadObjects(selectedSystem_mock, selectedNode_mock);

				oneOf(linksDataModel_mock).loadLinks(selectedSystem_mock, selectedNode_mock);
			}
		});

		testable.loadSystem(selectedSystem_mock, selectedNode_mock);
	}

	@Test
	public void clear() {
		context.checking(new Expectations() {
			{
				oneOf(objectsDataModel_mock).clear();

				oneOf(linksDataModel_mock).clear();
			}
		});

		testable.clear();
	}

	@Test
	public void systemName_keyReleased() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getName();
				will(returnValue("system-name"));

				oneOf(jtfSystemName_mock).setText("system-name");

				oneOf(jcbSystemType_mock).setSelectedIndex(SystemDataModel.SYSTEM_TYPE_REGULAR);

				oneOf(objectsDataModel_mock).loadObjects(selectedSystem_mock, selectedNode_mock);

				oneOf(linksDataModel_mock).loadLinks(selectedSystem_mock, selectedNode_mock);
			}
		});
		testable.loadSystem(selectedSystem_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfSystemName_mock).getText();
				will(returnValue("new-system-name"));

				oneOf(selectedSystem_mock).setName("new-system-name");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfSystemNameKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void systemType_ItemStateChanged_DESELECTED() {
		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.DESELECTED));
			}
		});

		testable.jcbSystemTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void systemType_ItemStateChanged_SELECTED() {
		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.SELECTED));
			}
		});

		testable.jcbSystemTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void systemType_ItemStateChanged_SELECTED_initial() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getName();
				will(returnValue("system-name"));

				oneOf(jtfSystemName_mock).setText("system-name");

				oneOf(jcbSystemType_mock).setSelectedIndex(SystemDataModel.SYSTEM_TYPE_REGULAR);

				oneOf(objectsDataModel_mock).loadObjects(selectedSystem_mock, selectedNode_mock);

				oneOf(linksDataModel_mock).loadLinks(selectedSystem_mock, selectedNode_mock);
			}
		});
		testable.loadSystem(selectedSystem_mock, selectedNode_mock);

		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.SELECTED));

				oneOf(jcbSystemType_mock).getSelectedIndex();
				will(returnValue(SystemDataModel.SYSTEM_TYPE_INITIAL));

				oneOf(selectedSystem_mock).setName("initialSystem");

				oneOf(jtfSystemName_mock).setText("initialSystem");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jcbSystemTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void systemType_ItemStateChanged_SELECTED_final() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getName();
				will(returnValue("system-name"));

				oneOf(jtfSystemName_mock).setText("system-name");

				oneOf(jcbSystemType_mock).setSelectedIndex(SystemDataModel.SYSTEM_TYPE_REGULAR);

				oneOf(objectsDataModel_mock).loadObjects(selectedSystem_mock, selectedNode_mock);

				oneOf(linksDataModel_mock).loadLinks(selectedSystem_mock, selectedNode_mock);
			}
		});
		testable.loadSystem(selectedSystem_mock, selectedNode_mock);

		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.SELECTED));

				oneOf(jcbSystemType_mock).getSelectedIndex();
				will(returnValue(SystemDataModel.SYSTEM_TYPE_FINAL));

				oneOf(selectedSystem_mock).setName("finalSystem");

				oneOf(jtfSystemName_mock).setText("finalSystem");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jcbSystemTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void systemType_ItemStateChanged_SELECTED_regular() {
		final System selectedSystem_mock = context.mock(System.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedSystem_mock).getName();
				will(returnValue("system-name"));

				oneOf(jtfSystemName_mock).setText("system-name");

				oneOf(jcbSystemType_mock).setSelectedIndex(SystemDataModel.SYSTEM_TYPE_REGULAR);

				oneOf(objectsDataModel_mock).loadObjects(selectedSystem_mock, selectedNode_mock);

				oneOf(linksDataModel_mock).loadLinks(selectedSystem_mock, selectedNode_mock);
			}
		});
		testable.loadSystem(selectedSystem_mock, selectedNode_mock);

		final ItemEvent itemEvent_mock = context.mock(ItemEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(itemEvent_mock).getStateChange();
				will(returnValue(ItemEvent.SELECTED));

				oneOf(jcbSystemType_mock).getSelectedIndex();
				will(returnValue(SystemDataModel.SYSTEM_TYPE_REGULAR));

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jcbSystemTypeItemListener.itemStateChanged(itemEvent_mock);
	}

	@Test
	public void getLinksDataModel() {
		assertEquals(linksDataModel_mock, testable.getLinksDataModel());
	}

	@Test
	public void getObjectsDataModel() {
		assertEquals(objectsDataModel_mock, testable.getObjectsDataModel());
	}

	@Test
	public void insertObject() {
		context.checking(new Expectations() {
			{
				oneOf(objectsDataModel_mock).insertObject();
			}
		});

		testable.insertObject();
	}

	@Test
	public void deleteObject() {
		context.checking(new Expectations() {
			{
				oneOf(objectsDataModel_mock).deleteObject(2);
			}
		});

		testable.deleteObject(2);
	}

	@Test
	public void insertLink() {
		context.checking(new Expectations() {
			{
				oneOf(linksDataModel_mock).insertLink();
			}
		});

		testable.insertLink();
	}

	@Test
	public void deleteLink() {
		context.checking(new Expectations() {
			{
				oneOf(linksDataModel_mock).deleteLink(2);
			}
		});

		testable.deleteLink(2);
	}
}
