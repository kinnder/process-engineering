package application.ui.gui.editor;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import planning.model.System;

public class SystemDataModel {

	private JTextField jtfSystemName;

	private JComboBox<String> jcbSystemType;

	private EditorDataModel editorDataModel;

	private System system;

	public static final int SYSTEM_TYPE_INITIAL = 0;

	public static final int SYSTEM_TYPE_REGULAR = 1;

	public static final int SYSTEM_TYPE_FINAL = 2;

	ItemListener jcbSystemTypeItemListener = new ItemListener() {
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (system == null) {
					return;
				}
				final int index = jcbSystemType.getSelectedIndex();
				switch (index) {
				case SYSTEM_TYPE_INITIAL:
					system.setName("initialSystem");
					jtfSystemName.setText("initialSystem");
					break;
				case SYSTEM_TYPE_FINAL:
					system.setName("finalSystem");
					jtfSystemName.setText("finalSystem");
					break;
				default:
					;
				}
				editorDataModel.nodeChanged(treeNode);
			}
		}
	};

	KeyAdapter jtfSystemNameKeyAdapter = new KeyAdapter() {
		@Override
		public void keyReleased(KeyEvent e) {
			final String systemName = jtfSystemName.getText();
			system.setName(systemName);
			editorDataModel.nodeChanged(treeNode);
		}
	};

	private LinksDataModel linksDataModel;

	public LinksDataModel getLinksDataModel() {
		return this.linksDataModel;
	}

	private ObjectsDataModel objectsDataModel;

	public ObjectsDataModel getObjectsDataModel() {
		return this.objectsDataModel;
	}

	public SystemDataModel(JTextField jtfSystemName, JComboBox<String> jcbSystemType, EditorDataModel editorDataModel) {
		this(jtfSystemName, jcbSystemType, editorDataModel, new LinksDataModel(editorDataModel),
				new ObjectsDataModel(editorDataModel));
	}

	SystemDataModel(JTextField jtfSystemName, JComboBox<String> jcbSystemType, EditorDataModel editorDataModel,
			LinksDataModel linksDataModel, ObjectsDataModel objectsDataModel) {
		this.jtfSystemName = jtfSystemName;
		this.jcbSystemType = jcbSystemType;
		this.editorDataModel = editorDataModel;

		this.linksDataModel = linksDataModel;
		this.objectsDataModel = objectsDataModel;

		jcbSystemType.addItemListener(jcbSystemTypeItemListener);
		jtfSystemName.addKeyListener(jtfSystemNameKeyAdapter);
	}

	private DefaultMutableTreeNode treeNode;

	public void loadSystem(System selectedSystem, DefaultMutableTreeNode selectedNode) {
		final String name = selectedSystem.getName();
		// TODO (2022-09-23 #72): перенести в System и заменить на Enum
		int type;
		if ("initialSystem".equals(name)) {
			type = SYSTEM_TYPE_INITIAL;
		} else if ("finalSystem".equals(name)) {
			type = SYSTEM_TYPE_FINAL;
		} else {
			type = SYSTEM_TYPE_REGULAR;
		}
		jtfSystemName.setText(name);
		jcbSystemType.setSelectedIndex(type);

		system = selectedSystem;
		treeNode = selectedNode;

		objectsDataModel.loadObjects(selectedSystem, selectedNode);
		linksDataModel.loadLinks(selectedSystem, selectedNode);
	}

	public void clear() {
		system = null;
		treeNode = null;

		objectsDataModel.clear();
		linksDataModel.clear();
	}

	public void insertObject() {
		objectsDataModel.insertObject();
	}

	public void deleteObject(int idx) {
		objectsDataModel.deleteObject(idx);
	}

	public void insertLink() {
		linksDataModel.insertLink();
	}

	public void deleteLink(int idx) {
		linksDataModel.deleteLink(idx);
	}
}
