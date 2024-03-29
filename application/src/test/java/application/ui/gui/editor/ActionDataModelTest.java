package application.ui.gui.editor;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import planning.model.Action;

public class ActionDataModelTest {

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

	JTextField jtfActionName_mock;

	ActionFunctionsDataModel actionFunctionsDataModel_mock;

	ActionDataModel testable;

	@BeforeEach
	public void setup() {
		editorDataModel_mock = context.mock(EditorDataModel.class);
		jtfActionName_mock = context.mock(JTextField.class);
		actionFunctionsDataModel_mock = context.mock(ActionFunctionsDataModel.class);

		context.checking(new Expectations() {
			{
				oneOf(jtfActionName_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new ActionDataModel(jtfActionName_mock, editorDataModel_mock, actionFunctionsDataModel_mock);
	}

	@Test
	public void newInstance() {
		context.checking(new Expectations() {
			{
				oneOf(jtfActionName_mock).addKeyListener(with(any(KeyListener.class)));
			}
		});

		testable = new ActionDataModel(jtfActionName_mock, editorDataModel_mock);
	}

	@Test
	public void clear() {
		context.checking(new Expectations() {
			{
				oneOf(actionFunctionsDataModel_mock).clear();
			}
		});

		testable.clear();
	}

	@Test
	public void loadAction() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getName();
				will(returnValue("action-name"));

				oneOf(jtfActionName_mock).setText("action-name");

				oneOf(actionFunctionsDataModel_mock).loadActionFunctions(action_mock, treeNode_mock);
			}
		});

		testable.loadAction(action_mock, treeNode_mock);
	}

	@Test
	public void actionName_keyReleased() {
		final Action action_mock = context.mock(Action.class);
		final DefaultMutableTreeNode treeNode_mock = context.mock(DefaultMutableTreeNode.class);

		context.checking(new Expectations() {
			{
				oneOf(action_mock).getName();
				will(returnValue("action-name"));

				oneOf(jtfActionName_mock).setText("action-name");

				oneOf(actionFunctionsDataModel_mock).loadActionFunctions(action_mock, treeNode_mock);
			}
		});
		testable.loadAction(action_mock, treeNode_mock);

		final KeyEvent keyEvent_mock = context.mock(KeyEvent.class);
		context.checking(new Expectations() {
			{
				oneOf(jtfActionName_mock).getText();
				will(returnValue("new-action-name"));

				oneOf(action_mock).setName("new-action-name");

				oneOf(editorDataModel_mock).nodeChanged(treeNode_mock);
			}
		});

		testable.jtfActionNameKeyAdapter.keyReleased(keyEvent_mock);
	}

	@Test
	public void getActionFunctionsDataModel() {
		assertEquals(actionFunctionsDataModel_mock, testable.getActionFunctionsDataModel());
	}

	@Test
	public void insertActionFunction() {
		context.checking(new Expectations() {
			{
				oneOf(actionFunctionsDataModel_mock).insertActionFunction();
			}
		});

		testable.insertActionFunction();
	}

	@Test
	public void deleteActionFunction() {
		context.checking(new Expectations() {
			{
				oneOf(actionFunctionsDataModel_mock).deleteActionFunction(2);
			}
		});

		testable.deleteActionFunction(2);
	}
}
