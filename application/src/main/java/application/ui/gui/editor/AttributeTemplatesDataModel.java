package application.ui.gui.editor;

import javax.swing.table.DefaultTableModel;

public class AttributeTemplatesDataModel extends DefaultTableModel {

	private static final long serialVersionUID = -1944552811851244410L;

	private EditorDataModel editorDataModel;

	public AttributeTemplatesDataModel(EditorDataModel editorDataModel) {
		super(new String[] { "name", "type", "value" }, 0);
		this.editorDataModel = editorDataModel;
	}

	private Class<?>[] types = new Class[] { String.class, String.class, String.class };

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return types[columnIndex];
	}
}
