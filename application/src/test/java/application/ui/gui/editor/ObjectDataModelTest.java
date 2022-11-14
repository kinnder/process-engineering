package application.ui.gui.editor;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import org.jmock.Expectations;
import org.jmock.imposters.ByteBuddyClassImposteriser;
import org.jmock.junit5.JUnit5Mockery;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;

import planning.model.SystemObject;

public class ObjectDataModelTest {

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

	JTextField jtfObjectName_mock;

	JTextField jtfObjectId_mock;

	EditorDataModel editorDataModel_mock;

	ObjectDataModel testable;

	@BeforeEach
	public void setup() {
		jtfObjectName_mock = context.mock(JTextField.class, "objectName");
		jtfObjectId_mock = context.mock(JTextField.class, "objectId");
		editorDataModel_mock = context.mock(EditorDataModel.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfObjectName_mock).addKeyListener(with(any(KeyListener.class)));

				oneOf(jtfObjectId_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new ObjectDataModel(jtfObjectName_mock, jtfObjectId_mock, editorDataModel_mock);
	}

	@Test
	public void loadSystemObject() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getName();
				will(returnValue("object-name"));

				oneOf(selectedObject_mock).getId();
				will(returnValue("object-id"));

				oneOf(jtfObjectName_mock).setText("object-name");

				oneOf(jtfObjectId_mock).setText("object-id");
			}
		});

		testable.loadSystemObject(selectedObject_mock, selectedNode_mock);
	}

	@Test
	public void clear() {
		testable.clear();
	}

	@Test
	public void objectName_keyReleased() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getName();
				will(returnValue("object-name"));

				oneOf(selectedObject_mock).getId();
				will(returnValue("object-id"));

				oneOf(jtfObjectName_mock).setText("object-name");

				oneOf(jtfObjectId_mock).setText("object-id");
			}
		});
		testable.loadSystemObject(selectedObject_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfObjectName_mock).getText();
				will(returnValue("new-object-name"));

				oneOf(selectedObject_mock).setName("new-object-name");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfObjectNameKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void objectId_keyReleased() {
		final SystemObject selectedObject_mock = context.mock(SystemObject.class);
		final DefaultMutableTreeNode selectedNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(selectedObject_mock).getName();
				will(returnValue("object-name"));

				oneOf(selectedObject_mock).getId();
				will(returnValue("object-id"));

				oneOf(jtfObjectName_mock).setText("object-name");

				oneOf(jtfObjectId_mock).setText("object-id");
			}
		});
		testable.loadSystemObject(selectedObject_mock, selectedNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfObjectId_mock).getText();
				will(returnValue("new-object-id"));

				oneOf(selectedObject_mock).setId("new-object-id");

				oneOf(editorDataModel_mock).nodeChanged(selectedNode_mock);
			}
		});

		testable.jtfObjectIdKeyAdapter.keyReleased(keyEvent_mock);
	}
}
