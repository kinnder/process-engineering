package application.ui.gui;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;

import application.Application;
import application.ui.UserInterfaceFactory;
import application.ui.gui.editor.ActionDataModel;
import application.ui.gui.editor.ActionFunctionDataModel;
import application.ui.gui.editor.EdgeDataModel;
import application.ui.gui.editor.EditorDataModel;
import application.ui.gui.editor.NodeDataModel;
import application.ui.gui.editor.ObjectDataModel;
import application.ui.gui.editor.ObjectTemplateDataModel;
import application.ui.gui.editor.SystemDataModel;
import application.ui.gui.editor.SystemTemplateDataModel;
import application.ui.gui.editor.SystemTransformationDataModel;
import application.ui.gui.editor.SystemTransformationsDataModel;
import application.ui.gui.editor.TransformationDataModel;
import application.ui.gui.editor.TransformationsDataModel;
import java.awt.CardLayout;
import planning.method.Edge;
import planning.method.Node;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemObject;
import planning.model.SystemObjectTemplate;
import planning.model.SystemProcess;
import planning.model.SystemTemplate;
import planning.model.SystemTransformation;
import planning.model.Transformation;
import planning.model.Transformations;
import planning.model.ActionFunction;
import planning.model.System;

// TODO (2022-09-18 #72): инициализацию компонентов перенести в CustomCode визуального редактора
public class EditorFrame extends javax.swing.JFrame {
	private static final long serialVersionUID = -6624128935908845123L;

	private Application application;

	public EditorFrame(Application application) {
		this.application = application;
		this.editorDataModel = new EditorDataModel();
		this.systemTransformationsDataModel = new SystemTransformationsDataModel(editorDataModel);
		this.transformationsDataModel = new TransformationsDataModel(editorDataModel);

		initComponents();

		this.systemDataModel = new SystemDataModel(jtfSystemName, jcbSystemType, editorDataModel);
		this.objectDataModel = new ObjectDataModel(jtfObjectName, jtfObjectId, editorDataModel);
		this.systemTransformationDataModel = new SystemTransformationDataModel(jtfSystemTransformationName, editorDataModel);
		this.systemTemplateDataModel = new SystemTemplateDataModel(jtfSystemTemplateName, jcbSystemTemplateType, editorDataModel);
		this.objectTemplateDataModel = new ObjectTemplateDataModel(jtfObjectTemplateName, jtfObjectTemplateId, editorDataModel);
		this.actionDataModel = new ActionDataModel(jtfActionName, editorDataModel);
		this.actionFunctionDataModel = new ActionFunctionDataModel(jcbActionFunctionType, jtaActionFunctionLines, editorDataModel);
		this.transformationDataModel = new TransformationDataModel(jrbTransformation, jtfTransformationObjectId,
				jrbAttributeTransformation, jtfAttributeTransformationName, jtfAttributeTransformationValue,
				jrbLinkTransformation, jtfLinkTransformationName, jtfLinkTransformationId2old,
				jtfLinkTransformationId2new, editorDataModel);
		this.nodeDataModel = new NodeDataModel(jtfNodeId, jcbNodeChecked, editorDataModel);
		this.edgeDataModel = new EdgeDataModel(jtfEdgeId, jtfBeginNodeId, jtfEndNodeId, jtfOperationName, editorDataModel);

		setModels();
		setActions();
		setComponents();
	}

	EditorFrame(Application application, EditorDataModel editorDataModel, SystemDataModel systemDataModel,
			ObjectDataModel objectDataModel, SystemTransformationsDataModel systemTransformationsDataModel,
			SystemTemplateDataModel systemTemplateDataModel, TransformationsDataModel transformationsDataModel,
			ObjectTemplateDataModel objectTemplateDataModel, ActionDataModel actionDataModel,
			NodeDataModel nodeDataModel, EdgeDataModel edgeDataModel) {
		this.application = application;
		this.editorDataModel = editorDataModel;
		this.systemTransformationsDataModel = systemTransformationsDataModel;
		this.transformationsDataModel = transformationsDataModel;

		initComponents();

		this.systemDataModel = systemDataModel;
		this.objectDataModel = objectDataModel;
		this.systemTransformationDataModel = new SystemTransformationDataModel(jtfSystemTransformationName, editorDataModel);
		this.systemTemplateDataModel = systemTemplateDataModel;
		this.objectTemplateDataModel = objectTemplateDataModel;
		this.actionDataModel = actionDataModel;
		this.actionFunctionDataModel = new ActionFunctionDataModel(jcbActionFunctionType, jtaActionFunctionLines, editorDataModel);
		this.transformationDataModel = new TransformationDataModel(jrbTransformation, jtfTransformationObjectId,
				jrbAttributeTransformation, jtfAttributeTransformationName, jtfAttributeTransformationValue,
				jrbLinkTransformation, jtfLinkTransformationName, jtfLinkTransformationId2old,
				jtfLinkTransformationId2new, editorDataModel);
		this.nodeDataModel = nodeDataModel;
		this.edgeDataModel = edgeDataModel;

		setModels();
		setActions();
		setComponents();
	}

	private EditorDataModel editorDataModel;

	private SystemDataModel systemDataModel;

	private ObjectDataModel objectDataModel;

	private SystemTransformationsDataModel systemTransformationsDataModel;

	private SystemTransformationDataModel systemTransformationDataModel;

	private SystemTemplateDataModel systemTemplateDataModel;

	private ObjectTemplateDataModel objectTemplateDataModel;

	private ActionDataModel actionDataModel;

	private ActionFunctionDataModel actionFunctionDataModel;

	private TransformationsDataModel transformationsDataModel;

	private TransformationDataModel transformationDataModel;

	private NodeDataModel nodeDataModel;

	private EdgeDataModel edgeDataModel;

	private void setModels() {
		jtParameters.setModel(edgeDataModel.getParametersDataModel());
		jtEdges.setModel(nodeDataModel.getEdgesDataModel());
		jtActionFunctions.setModel(actionDataModel.getActionFunctionsDataModel());
		jtAttributeTemplates.setModel(objectTemplateDataModel.getAttributeTemplatesDataModel());
		jtObjectTemplates.setModel(systemTemplateDataModel.getObjectTemplatesDataModel());
		jtLinkTemplates.setModel(systemTemplateDataModel.getLinkTemplatesDataModel());
		jtAttributes.setModel(objectDataModel.getAttributesDataModel());
		jtObjects.setModel(systemDataModel.getObjectsDataModel());
		jtLinks.setModel(systemDataModel.getLinksDataModel());
	}

	private void setActions() {
		jmiTaskDescriptionNew.setAction(taskDescriptionNewAction);
		jmiTaskDescriptionLoad.setAction(taskDescriptionLoadAction);
		jmiTaskDescriptionSave.setAction(taskDescriptionSaveAction);

		jmiSystemTransformationsNew.setAction(systemTransformationsNewAction);
		jmiSystemTransformationsLoad.setAction(systemTransformationsLoadAction);
		jmiSystemTransformationsSave.setAction(systemTransformationsSaveAction);

		jmiNodeNetworkNew.setAction(nodeNetworkNewAction);
		jmiNodeNetworkLoad.setAction(nodeNetworkLoadAction);

		jmiProcessNew.setAction(processNewAction);
		jmiProcessLoad.setAction(processLoadAction);

		// TODO (2022-11-18 #73): синхронизировать множественные и единственные числа в названиях
		jbObjectsInsert.setAction(objectsInsertAction);
		jbObjectsDelete.setAction(objectsDeleteAction);

		jbLinksInsert.setAction(linksInsertAction);
		jbLinksDelete.setAction(linksDeleteAction);

		jbAttributesInsert.setAction(attributesInsertAction);
		jbAttributesDelete.setAction(attributesDeleteAction);

		jbSystemTransformationsInsert.setAction(systemTransformationsInsertAction);
		jbSystemTransformationsDelete.setAction(systemTransformationsDeleteAction);

		jbObjectTemplateInsert.setAction(objectTemplateInsertAction);
		jbObjectTemplateDelete.setAction(objectTemplateDeleteAction);

		jbLinkTemplatesInsert.setAction(linkTemplatesInsertAction);
		jbLinkTemplatesDelete.setAction(linkTemplatesDeleteAction);

		jbAttributeTemplateInsert.setAction(attributeTemplateInsertAction);
		jbAttributeTemplateDelete.setAction(attributeTemplateDeleteAction);

		jbActionFunctionsInsert.setAction(actionFunctionsInsertAction);
		jbActionFunctionsDelete.setAction(actionFunctionsDeleteAction);

		jbTransformationsInsertTransformation.setAction(transformationsInsertTransformationAction);
		jbTransformationsInsertLinkTransformation.setAction(transformationsInsertLinkTransformationAction);
		jbTransformationsInserAttributeTransformation.setAction(transformationsInsertAttributeTransformationAction);
		jbTransformationsDelete.setAction(transformationsDeleteTransformationAction);
	}

	private void setComponents() {
		objectDataModel.setComponents(jtAttributes);
		objectTemplateDataModel.setComponents(jtAttributeTemplates);

		selectEditor("empty");
	}

	Action taskDescriptionNewAction = new AbstractAction("New") {
		private static final long serialVersionUID = 8331309669949257478L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final TaskDescription taskDescription = application.newTaskDescription_v2();
			if (taskDescription != null) {
				editorDataModel.loadTaskDescription(taskDescription);
			}
		}
	};

	Action taskDescriptionLoadAction = new AbstractAction("Load") {
		private static final long serialVersionUID = 8331309669949257478L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final TaskDescription taskDescription = application.loadTaskDescription();
			if (taskDescription != null) {
				editorDataModel.loadTaskDescription(taskDescription);
			}
		}
	};

	Action taskDescriptionSaveAction = new AbstractAction("Save") {
		private static final long serialVersionUID = -6152997887082597540L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final TaskDescription taskDescription = editorDataModel.saveTaskDescription();
			application.saveTaskDescription(taskDescription);
		}
	};

	Action systemTransformationsNewAction = new AbstractAction("New") {
		private static final long serialVersionUID = -2934465114601682626L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final SystemTransformations systemTransformations = application.newSystemTransformations_v2();
			if (systemTransformations != null) {
				editorDataModel.loadSystemTransformations(systemTransformations);
			}
		}
	};

	Action systemTransformationsLoadAction = new AbstractAction("Load") {
		private static final long serialVersionUID = -6152997887082597540L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final SystemTransformations systemTransformations = application.loadSystemTransformations();
			if (systemTransformations != null) {
				editorDataModel.loadSystemTransformations(systemTransformations);
			}
		}
	};

	Action systemTransformationsSaveAction = new AbstractAction("Save") {
		private static final long serialVersionUID = -2934465114601682626L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final SystemTransformations systemTransformations = editorDataModel.saveSystemTransformations();
			application.saveSystemTransformations(systemTransformations);
		}
	};

	Action nodeNetworkNewAction = new AbstractAction("New") {
		private static final long serialVersionUID = 7657799313389376389L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final NodeNetwork nodeNetwork = application.newNodeNetwork_v2();
			if (nodeNetwork != null) {
				editorDataModel.loadNodeNetwork(nodeNetwork);
			}
		}
	};

	Action nodeNetworkLoadAction = new AbstractAction("Load") {
		private static final long serialVersionUID = -6796904580309558162L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final NodeNetwork nodeNetwork = application.loadNodeNetwork();
			if (nodeNetwork != null) {
				editorDataModel.loadNodeNetwork(nodeNetwork);
			}
		}
	};

	Action processNewAction = new AbstractAction("New") {
		private static final long serialVersionUID = 7990486675464420960L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final SystemProcess systemProcess = application.newSystemProcess_v2();
			if (systemProcess != null) {
				editorDataModel.loadSystemProcess(systemProcess);
			}
		}
	};

	Action processLoadAction = new AbstractAction("Load") {
		private static final long serialVersionUID = -2934465114601682626L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final SystemProcess systemProcess = application.loadSystemProcess();
			if (systemProcess != null) {
				editorDataModel.loadSystemProcess(systemProcess);
			}
		}
	};

	Action objectsInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = -1700274277944696641L;

		@Override
		public void actionPerformed(ActionEvent e) {
			systemDataModel.insertObject();
		}
	};

	Action objectsDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = 7657799313389376389L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtObjects.getSelectedRow();
			systemDataModel.deleteObject(idx);
		}
	};

	Action linksInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = -9112378954512358406L;

		@Override
		public void actionPerformed(ActionEvent e) {
			systemDataModel.insertLink();
		}
	};

	Action linksDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = 5435734964836282376L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtLinks.getSelectedRow();
			systemDataModel.deleteLink(idx);
		}
	};

	Action attributesInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = -9112378954512358406L;

		@Override
		public void actionPerformed(ActionEvent e) {
			objectDataModel.insertAttribute();
		}
	};

	Action attributesDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = 5435734964836282376L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtAttributes.getSelectedRow();
			objectDataModel.deleteAttribute(idx);
		}
	};

	Action systemTransformationsInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = -2727288841497369038L;

		@Override
		public void actionPerformed(ActionEvent e) {
			systemTransformationsDataModel.insertSystemTransformation();
		}
	};

	Action systemTransformationsDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = -1592101301496360537L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtSystemTransformations.getSelectedRow();
			systemTransformationsDataModel.deleteSystemTransformation(idx);
		}
	};

	Action objectTemplateInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = -4851992641857540153L;

		@Override
		public void actionPerformed(ActionEvent e) {
			systemTemplateDataModel.insertObjectTemplate();
		}
	};

	Action objectTemplateDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = 812115304797324835L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtObjectTemplates.getSelectedRow();
			systemTemplateDataModel.deleteObjectTemplate(idx);
		}
	};

	Action linkTemplatesInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = 6137385685062002073L;

		@Override
		public void actionPerformed(ActionEvent e) {
			systemTemplateDataModel.insertLinkTemplate();
		}
	};

	Action linkTemplatesDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = -3154157962525794280L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtLinkTemplates.getSelectedRow();
			systemTemplateDataModel.deleteLinkTemplate(idx);
		}
	};

	Action attributeTemplateInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = -7471052641340519481L;

		@Override
		public void actionPerformed(ActionEvent e) {
			objectTemplateDataModel.insertAttributeTemplate();
		}
	};

	Action attributeTemplateDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = 3977994079503478515L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtAttributeTemplates.getSelectedRow();
			objectTemplateDataModel.deleteAttributeTemplate(idx);
		}
	};

	Action actionFunctionsInsertAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = 25200714754072766L;

		@Override
		public void actionPerformed(ActionEvent e) {
			actionDataModel.insertActionFunction();
		}
	};

	Action actionFunctionsDeleteAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = -2297090309084649635L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtActionFunctions.getSelectedRow();
			actionDataModel.deleteActionFunction(idx);
		}
	};

	Action transformationsInsertTransformationAction = new AbstractAction("Insert") {
		private static final long serialVersionUID = 570026072927635395L;

		@Override
		public void actionPerformed(ActionEvent e) {
			transformationsDataModel.insertTransformation();
		}
	};

	Action transformationsDeleteTransformationAction = new AbstractAction("Delete") {
		private static final long serialVersionUID = -3446892158319364094L;

		@Override
		public void actionPerformed(ActionEvent e) {
			final int idx = jtTransformations.getSelectedRow();
			transformationsDataModel.deleteTransformation(idx);
		}
	};

	Action transformationsInsertLinkTransformationAction = new AbstractAction("Insert Link") {
		private static final long serialVersionUID = -2201493982792914913L;

		@Override
		public void actionPerformed(ActionEvent e) {
			transformationsDataModel.insertLinkTransformation();
		}
	};

	Action transformationsInsertAttributeTransformationAction = new AbstractAction("Insert Attribute") {
		private static final long serialVersionUID = -3885835311230793111L;

		@Override
		public void actionPerformed(ActionEvent e) {
			transformationsDataModel.insertAttributeTransformation();
		}
	};

	/**
	 * This method is called from within the constructor to initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is always
	 * regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
	private void initComponents() {
		bgTransformationType = new javax.swing.ButtonGroup();
		jspWorkArea = new javax.swing.JSplitPane();
		jspData = new javax.swing.JScrollPane();
		jtData = new javax.swing.JTree();
		jpEditors = new javax.swing.JPanel();
		jpSystemEditor = new javax.swing.JPanel();
		jpSystem = new javax.swing.JPanel();
		jlSystemName = new javax.swing.JLabel();
		jlSystemType = new javax.swing.JLabel();
		jtfSystemName = new javax.swing.JTextField();
		jcbSystemType = new javax.swing.JComboBox<>();
		jpSystemData = new javax.swing.JPanel();
		jspSystemData = new javax.swing.JSplitPane();
		jpObjectsEditor = new javax.swing.JPanel();
		jlObjects = new javax.swing.JLabel();
		jspObjects = new javax.swing.JScrollPane();
		jtObjects = new javax.swing.JTable();
		jpObjectsButtons = new javax.swing.JPanel();
		jbObjectsInsert = new javax.swing.JButton();
		jbObjectsDelete = new javax.swing.JButton();
		jpLinksEditor = new javax.swing.JPanel();
		jlLinks = new javax.swing.JLabel();
		jpLinksButtons = new javax.swing.JPanel();
		jbLinksInsert = new javax.swing.JButton();
		jbLinksDelete = new javax.swing.JButton();
		jspLinks = new javax.swing.JScrollPane();
		jtLinks = new javax.swing.JTable();
		jpObjectEditor = new javax.swing.JPanel();
		jpObject = new javax.swing.JPanel();
		jlObjectName = new javax.swing.JLabel();
		jtfObjectName = new javax.swing.JTextField();
		jtfObjectId = new javax.swing.JTextField();
		jlObjectId = new javax.swing.JLabel();
		jpAttributesEditor = new javax.swing.JPanel();
		jlAttributes = new javax.swing.JLabel();
		jspAttributes = new javax.swing.JScrollPane();
		jtAttributes = new javax.swing.JTable();
		jpAttributesButtons = new javax.swing.JPanel();
		jbAttributesInsert = new javax.swing.JButton();
		jbAttributesDelete = new javax.swing.JButton();
		jpSystemTransformationsEditor = new javax.swing.JPanel();
		jlSystemTransformations = new javax.swing.JLabel();
		jspSystemTransformations = new javax.swing.JScrollPane();
		jtSystemTransformations = new javax.swing.JTable();
		jpSystemTransformationsButtons = new javax.swing.JPanel();
		jbSystemTransformationsInsert = new javax.swing.JButton();
		jbSystemTransformationsDelete = new javax.swing.JButton();
		jpSystemTransformationEditor = new javax.swing.JPanel();
		jlSystemTransformationName = new javax.swing.JLabel();
		jtfSystemTransformationName = new javax.swing.JTextField();
		jpSystemTemplateEditor = new javax.swing.JPanel();
		jpSystemTemplate = new javax.swing.JPanel();
		jlSystemTemplateName = new javax.swing.JLabel();
		jlSystemTemplateType = new javax.swing.JLabel();
		jtfSystemTemplateName = new javax.swing.JTextField();
		jcbSystemTemplateType = new javax.swing.JComboBox<>();
		jpSystemTemplateData = new javax.swing.JPanel();
		jspSystemTemplateData = new javax.swing.JSplitPane();
		jpObjectTemplatesEditor = new javax.swing.JPanel();
		jlObjectTemplates = new javax.swing.JLabel();
		jspObjectTemplates = new javax.swing.JScrollPane();
		jtObjectTemplates = new javax.swing.JTable();
		jpObjectTemplatesButtons = new javax.swing.JPanel();
		jbObjectTemplateInsert = new javax.swing.JButton();
		jbObjectTemplateDelete = new javax.swing.JButton();
		jpLinkTemplatesEditor = new javax.swing.JPanel();
		jlLinkTemplates = new javax.swing.JLabel();
		jpLinkTemplatesButtons = new javax.swing.JPanel();
		jbLinkTemplatesInsert = new javax.swing.JButton();
		jbLinkTemplatesDelete = new javax.swing.JButton();
		jspLinkTemplates = new javax.swing.JScrollPane();
		jtLinkTemplates = new javax.swing.JTable();
		jpObjectTemplateEditor = new javax.swing.JPanel();
		jpObjectTemplate = new javax.swing.JPanel();
		jlObjectTemplateName = new javax.swing.JLabel();
		jlObjectTemplateId = new javax.swing.JLabel();
		jtfObjectTemplateName = new javax.swing.JTextField();
		jtfObjectTemplateId = new javax.swing.JTextField();
		jpAttributeTemplatesEditor = new javax.swing.JPanel();
		jlAttributeTemplates = new javax.swing.JLabel();
		jspAttributeTemplates = new javax.swing.JScrollPane();
		jtAttributeTemplates = new javax.swing.JTable();
		jpAttributeTemplatesButtons = new javax.swing.JPanel();
		jbAttributeTemplateInsert = new javax.swing.JButton();
		jbAttributeTemplateDelete = new javax.swing.JButton();
		jpTransformationsEditor = new javax.swing.JPanel();
		jlTransformations = new javax.swing.JLabel();
		jpTransformationsButtons = new javax.swing.JPanel();
		jbTransformationsInsertTransformation = new javax.swing.JButton();
		jbTransformationsDelete = new javax.swing.JButton();
		jbTransformationsInsertLinkTransformation = new javax.swing.JButton();
		jbTransformationsInserAttributeTransformation = new javax.swing.JButton();
		jspTransformations = new javax.swing.JScrollPane();
		jtTransformations = new javax.swing.JTable();
		jpTransformationEditor = new javax.swing.JPanel();
		jpTransformation = new javax.swing.JPanel();
		jlTransformationObjectId = new javax.swing.JLabel();
		jtfTransformationObjectId = new javax.swing.JTextField();
		jrbTransformation = new javax.swing.JRadioButton();
		jpLinkTransformation = new javax.swing.JPanel();
		jrbLinkTransformation = new javax.swing.JRadioButton();
		jlLinkTransformationId2new = new javax.swing.JLabel();
		jtfLinkTransformationId2new = new javax.swing.JTextField();
		jlLinkTransformationName = new javax.swing.JLabel();
		jtfLinkTransformationName = new javax.swing.JTextField();
		jlLinkTransformationId2old = new javax.swing.JLabel();
		jtfLinkTransformationId2old = new javax.swing.JTextField();
		jpAttributeTransformation = new javax.swing.JPanel();
		jrbAttributeTransformation = new javax.swing.JRadioButton();
		jlAttributeTransformationValue = new javax.swing.JLabel();
		jtfAttributeTransformationValue = new javax.swing.JTextField();
		jtfAttributeTransformationName = new javax.swing.JTextField();
		jlAttributeTransformationName = new javax.swing.JLabel();
		jpActionEditor = new javax.swing.JPanel();
		jpAction = new javax.swing.JPanel();
		jlActionName = new javax.swing.JLabel();
		jtfActionName = new javax.swing.JTextField();
		jpActionFunctionsEditor = new javax.swing.JPanel();
		jlActionFunctions = new javax.swing.JLabel();
		jpActionFunctionsButtons = new javax.swing.JPanel();
		jbActionFunctionsInsert = new javax.swing.JButton();
		jbActionFunctionsDelete = new javax.swing.JButton();
		jspActionFunctions = new javax.swing.JScrollPane();
		jtActionFunctions = new javax.swing.JTable();
		jpActionFunctionEditor = new javax.swing.JPanel();
		jlActionFunctionType = new javax.swing.JLabel();
		jcbActionFunctionType = new javax.swing.JComboBox<>();
		jspActionFunctionLines = new javax.swing.JScrollPane();
		jtaActionFunctionLines = new javax.swing.JTextArea();
		jpNodeEditor = new javax.swing.JPanel();
		jpNode = new javax.swing.JPanel();
		jlNodeId = new javax.swing.JLabel();
		jcbNodeChecked = new javax.swing.JCheckBox();
		jtfNodeId = new javax.swing.JTextField();
		jpEdgesEditor = new javax.swing.JPanel();
		jlEdges = new javax.swing.JLabel();
		jspEdges = new javax.swing.JScrollPane();
		jtEdges = new javax.swing.JTable();
		jpEdgesButtons = new javax.swing.JPanel();
		jbEdgesInsert = new javax.swing.JButton();
		jbEdgesDelete = new javax.swing.JButton();
		jpEdgeEditor = new javax.swing.JPanel();
		jpEdge = new javax.swing.JPanel();
		jlEdgeId = new javax.swing.JLabel();
		jlBeginNodeId = new javax.swing.JLabel();
		jlEndNodeId = new javax.swing.JLabel();
		jtfEdgeId = new javax.swing.JTextField();
		jtfBeginNodeId = new javax.swing.JTextField();
		jtfEndNodeId = new javax.swing.JTextField();
		jpOperationEditor = new javax.swing.JPanel();
		jpOperation = new javax.swing.JPanel();
		jlOperationName = new javax.swing.JLabel();
		jtfOperationName = new javax.swing.JTextField();
		jlOperation = new javax.swing.JLabel();
		jpParametersEditor = new javax.swing.JPanel();
		jlParameters = new javax.swing.JLabel();
		jpParametersButtons = new javax.swing.JPanel();
		jbParametersInsert = new javax.swing.JButton();
		jbParametersDelete = new javax.swing.JButton();
		jspParameters = new javax.swing.JScrollPane();
		jtParameters = new javax.swing.JTable();
		jpEmpty = new javax.swing.JPanel();
		jmbMenu = new javax.swing.JMenuBar();
		jmTaskDescription = new javax.swing.JMenu();
		jmiTaskDescriptionNew = new javax.swing.JMenuItem();
		jmiTaskDescriptionLoad = new javax.swing.JMenuItem();
		jmiTaskDescriptionSave = new javax.swing.JMenuItem();
		jmSystemTransformations = new javax.swing.JMenu();
		jmiSystemTransformationsNew = new javax.swing.JMenuItem();
		jmiSystemTransformationsLoad = new javax.swing.JMenuItem();
		jmiSystemTransformationsSave = new javax.swing.JMenuItem();
		jmNodeNetwork = new javax.swing.JMenu();
		jmiNodeNetworkNew = new javax.swing.JMenuItem();
		jmiNodeNetworkLoad = new javax.swing.JMenuItem();
		jmProcess = new javax.swing.JMenu();
		jmiProcessNew = new javax.swing.JMenuItem();
		jmiProcessLoad = new javax.swing.JMenuItem();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Editor");
		setName("jfEditor"); // NOI18N

		jspData.setPreferredSize(new java.awt.Dimension(150, 275));

		jtData.setModel(editorDataModel);
		jtData.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
			@Override
			public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
				jtDataValueChanged(evt);
			}
		});
		jspData.setViewportView(jtData);

		jspWorkArea.setLeftComponent(jspData);

		jpEditors.setLayout(new java.awt.CardLayout());

		jlSystemName.setText("name");

		jlSystemType.setText("type");

		jtfSystemName.setText("system-non-unique-name");

		jcbSystemType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "initial", "regular", "final" }));
		jcbSystemType.setSelectedIndex(1);

		final javax.swing.GroupLayout jpSystemLayout = new javax.swing.GroupLayout(jpSystem);
		jpSystem.setLayout(jpSystemLayout);
		jpSystemLayout.setHorizontalGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jpSystemLayout.createSequentialGroup().addComponent(jlSystemName)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jtfSystemName))
								.addGroup(jpSystemLayout.createSequentialGroup().addComponent(jlSystemType)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
										.addComponent(jcbSystemType, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
						.addContainerGap()));
		jpSystemLayout.setVerticalGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlSystemName).addComponent(jtfSystemName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpSystemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlSystemType).addComponent(jcbSystemType,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jspSystemData.setDividerLocation(150);
		jspSystemData.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

		jpObjectsEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlObjects.setText("Objects");

		jspObjects.setViewportView(jtObjects);

		jbObjectsInsert.setText("Insert");

		jbObjectsDelete.setText("Delete");

		final javax.swing.GroupLayout jpObjectsButtonsLayout = new javax.swing.GroupLayout(jpObjectsButtons);
		jpObjectsButtons.setLayout(jpObjectsButtonsLayout);
		jpObjectsButtonsLayout.setHorizontalGroup(jpObjectsButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectsButtonsLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpObjectsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jbObjectsDelete).addComponent(jbObjectsInsert))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpObjectsButtonsLayout.setVerticalGroup(jpObjectsButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectsButtonsLayout.createSequentialGroup().addContainerGap().addComponent(jbObjectsInsert)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jbObjectsDelete).addContainerGap(62, Short.MAX_VALUE)));

		final javax.swing.GroupLayout jpObjectsEditorLayout = new javax.swing.GroupLayout(jpObjectsEditor);
		jpObjectsEditor.setLayout(jpObjectsEditorLayout);
		jpObjectsEditorLayout.setHorizontalGroup(jpObjectsEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectsEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlObjects)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(jpObjectsEditorLayout.createSequentialGroup()
						.addComponent(jspObjects, javax.swing.GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpObjectsButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpObjectsEditorLayout.setVerticalGroup(jpObjectsEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectsEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlObjects)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpObjectsEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jpObjectsButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jspObjects, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
										Short.MAX_VALUE))));

		jspSystemData.setTopComponent(jpObjectsEditor);

		jpLinksEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlLinks.setText("Links");

		jbLinksInsert.setText("Insert");

		jbLinksDelete.setText("Delete");

		final javax.swing.GroupLayout jpLinksButtonsLayout = new javax.swing.GroupLayout(jpLinksButtons);
		jpLinksButtons.setLayout(jpLinksButtonsLayout);
		jpLinksButtonsLayout.setHorizontalGroup(jpLinksButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinksButtonsLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpLinksButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jbLinksInsert).addComponent(jbLinksDelete))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpLinksButtonsLayout.setVerticalGroup(jpLinksButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinksButtonsLayout.createSequentialGroup().addContainerGap().addComponent(jbLinksInsert)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jbLinksDelete)
						.addContainerGap(367, Short.MAX_VALUE)));

		jspLinks.setViewportView(jtLinks);

		final javax.swing.GroupLayout jpLinksEditorLayout = new javax.swing.GroupLayout(jpLinksEditor);
		jpLinksEditor.setLayout(jpLinksEditorLayout);
		jpLinksEditorLayout
				.setHorizontalGroup(jpLinksEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpLinksEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlLinks)
								.addGap(93, 447, Short.MAX_VALUE))
						.addGroup(jpLinksEditorLayout.createSequentialGroup()
								.addComponent(jspLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpLinksButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpLinksEditorLayout.setVerticalGroup(jpLinksEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinksEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlLinks)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpLinksEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jpLinksButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jspLinks, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))));

		jspSystemData.setRightComponent(jpLinksEditor);

		final javax.swing.GroupLayout jpSystemDataLayout = new javax.swing.GroupLayout(jpSystemData);
		jpSystemData.setLayout(jpSystemDataLayout);
		jpSystemDataLayout.setHorizontalGroup(jpSystemDataLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspSystemData));
		jpSystemDataLayout.setVerticalGroup(jpSystemDataLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspSystemData));

		final javax.swing.GroupLayout jpSystemEditorLayout = new javax.swing.GroupLayout(jpSystemEditor);
		jpSystemEditor.setLayout(jpSystemEditorLayout);
		jpSystemEditorLayout.setHorizontalGroup(jpSystemEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpSystem, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpSystemData, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		jpSystemEditorLayout
				.setVerticalGroup(jpSystemEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpSystemEditorLayout.createSequentialGroup()
								.addComponent(jpSystem, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpSystemData, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpEditors.add(jpSystemEditor, "systemEditor");

		jlObjectName.setText("name");

		jtfObjectName.setText("object-workpiece");

		jtfObjectId.setText("id-workpiece");

		jlObjectId.setText("id");

		final javax.swing.GroupLayout jpObjectLayout = new javax.swing.GroupLayout(jpObject);
		jpObject.setLayout(jpObjectLayout);
		jpObjectLayout.setHorizontalGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpObjectLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(
										javax.swing.GroupLayout.Alignment.TRAILING,
										jpObjectLayout.createSequentialGroup().addComponent(jlObjectName)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
								.addGroup(jpObjectLayout.createSequentialGroup().addComponent(jlObjectId).addGap(26, 26,
										26)))
						.addGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jtfObjectName, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
								.addComponent(jtfObjectId))
						.addContainerGap()));
		jpObjectLayout.setVerticalGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlObjectName).addComponent(
										jtfObjectName, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpObjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfObjectId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlObjectId))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpAttributesEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlAttributes.setText("Attributes");

		jspAttributes.setViewportView(jtAttributes);

		jbAttributesInsert.setText("Insert");

		jbAttributesDelete.setText("Delete");

		final javax.swing.GroupLayout jpAttributesButtonsLayout = new javax.swing.GroupLayout(jpAttributesButtons);
		jpAttributesButtons.setLayout(jpAttributesButtonsLayout);
		jpAttributesButtonsLayout.setHorizontalGroup(
				jpAttributesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpAttributesButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpAttributesButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbAttributesInsert).addComponent(jbAttributesDelete))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpAttributesButtonsLayout.setVerticalGroup(
				jpAttributesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpAttributesButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbAttributesInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbAttributesDelete)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		final javax.swing.GroupLayout jpAttributesEditorLayout = new javax.swing.GroupLayout(jpAttributesEditor);
		jpAttributesEditor.setLayout(jpAttributesEditorLayout);
		jpAttributesEditorLayout.setHorizontalGroup(jpAttributesEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpAttributesEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlAttributes)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(jpAttributesEditorLayout.createSequentialGroup()
						.addComponent(jspAttributes, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpAttributesButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpAttributesEditorLayout.setVerticalGroup(jpAttributesEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpAttributesEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlAttributes)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								jpAttributesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpAttributesButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspAttributes, javax.swing.GroupLayout.DEFAULT_SIZE, 580,
												Short.MAX_VALUE))));

		final javax.swing.GroupLayout jpObjectEditorLayout = new javax.swing.GroupLayout(jpObjectEditor);
		jpObjectEditor.setLayout(jpObjectEditorLayout);
		jpObjectEditorLayout.setHorizontalGroup(jpObjectEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpObject, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpAttributesEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpObjectEditorLayout
				.setVerticalGroup(jpObjectEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpObjectEditorLayout.createSequentialGroup()
								.addComponent(jpObject, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpAttributesEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpEditors.add(jpObjectEditor, "objectEditor");

		jlSystemTransformations.setText("System Transformations");

		jtSystemTransformations.setModel(systemTransformationsDataModel);
		jspSystemTransformations.setViewportView(jtSystemTransformations);

		jbSystemTransformationsInsert.setText("Insert");

		jbSystemTransformationsDelete.setText("Delete");

		final javax.swing.GroupLayout jpSystemTransformationsButtonsLayout = new javax.swing.GroupLayout(
				jpSystemTransformationsButtons);
		jpSystemTransformationsButtons.setLayout(jpSystemTransformationsButtonsLayout);
		jpSystemTransformationsButtonsLayout.setHorizontalGroup(
				jpSystemTransformationsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpSystemTransformationsButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpSystemTransformationsButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbSystemTransformationsInsert)
										.addComponent(jbSystemTransformationsDelete))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpSystemTransformationsButtonsLayout.setVerticalGroup(
				jpSystemTransformationsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpSystemTransformationsButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbSystemTransformationsInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbSystemTransformationsDelete)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		final javax.swing.GroupLayout jpSystemTransformationsEditorLayout = new javax.swing.GroupLayout(
				jpSystemTransformationsEditor);
		jpSystemTransformationsEditor.setLayout(jpSystemTransformationsEditorLayout);
		jpSystemTransformationsEditorLayout.setHorizontalGroup(jpSystemTransformationsEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemTransformationsEditorLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlSystemTransformations).addContainerGap(349, Short.MAX_VALUE))
				.addGroup(jpSystemTransformationsEditorLayout.createSequentialGroup()
						.addComponent(jspSystemTransformations, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
								Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpSystemTransformationsButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpSystemTransformationsEditorLayout.setVerticalGroup(jpSystemTransformationsEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemTransformationsEditorLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlSystemTransformations)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpSystemTransformationsEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jpSystemTransformationsButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jspSystemTransformations, javax.swing.GroupLayout.DEFAULT_SIZE, 650,
										Short.MAX_VALUE))));

		jpEditors.add(jpSystemTransformationsEditor, "systemTransformationsEditor");

		jlSystemTransformationName.setText("name");

		jtfSystemTransformationName.setText("system transformation name");

		final javax.swing.GroupLayout jpSystemTransformationEditorLayout = new javax.swing.GroupLayout(
				jpSystemTransformationEditor);
		jpSystemTransformationEditor.setLayout(jpSystemTransformationEditorLayout);
		jpSystemTransformationEditorLayout
				.setHorizontalGroup(
						jpSystemTransformationEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addGroup(
										jpSystemTransformationEditorLayout.createSequentialGroup().addContainerGap()
												.addComponent(jlSystemTransformationName)
												.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
												.addComponent(jtfSystemTransformationName,
														javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
												.addContainerGap()));
		jpSystemTransformationEditorLayout.setVerticalGroup(jpSystemTransformationEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemTransformationEditorLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpSystemTransformationEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlSystemTransformationName).addComponent(jtfSystemTransformationName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(650, Short.MAX_VALUE)));

		jpEditors.add(jpSystemTransformationEditor, "systemTransformationEditor");

		jlSystemTemplateName.setText("name");

		jlSystemTemplateType.setText("type");

		jtfSystemTemplateName.setText("jTextField1");

		jcbSystemTemplateType
				.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "initial", "regular", "final" }));
		jcbSystemTemplateType.setSelectedIndex(1);

		final javax.swing.GroupLayout jpSystemTemplateLayout = new javax.swing.GroupLayout(jpSystemTemplate);
		jpSystemTemplate.setLayout(jpSystemTemplateLayout);
		jpSystemTemplateLayout.setHorizontalGroup(jpSystemTemplateLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemTemplateLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpSystemTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jlSystemTemplateName).addComponent(jlSystemTemplateType))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
						.addGroup(jpSystemTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jtfSystemTemplateName).addComponent(jcbSystemTemplateType, 0,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap()));
		jpSystemTemplateLayout.setVerticalGroup(jpSystemTemplateLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpSystemTemplateLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpSystemTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlSystemTemplateName).addComponent(jtfSystemTemplateName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpSystemTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlSystemTemplateType).addComponent(jcbSystemTemplateType,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jspSystemTemplateData.setDividerLocation(150);
		jspSystemTemplateData.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

		jpObjectTemplatesEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlObjectTemplates.setText("Object Templates");

		jspObjectTemplates.setViewportView(jtObjectTemplates);

		jbObjectTemplateInsert.setText("Insert");

		jbObjectTemplateDelete.setText("Delete");

		final javax.swing.GroupLayout jpObjectTemplatesButtonsLayout = new javax.swing.GroupLayout(jpObjectTemplatesButtons);
		jpObjectTemplatesButtons.setLayout(jpObjectTemplatesButtonsLayout);
		jpObjectTemplatesButtonsLayout.setHorizontalGroup(
				jpObjectTemplatesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpObjectTemplatesButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpObjectTemplatesButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbObjectTemplateInsert).addComponent(jbObjectTemplateDelete))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpObjectTemplatesButtonsLayout.setVerticalGroup(
				jpObjectTemplatesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpObjectTemplatesButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbObjectTemplateInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbObjectTemplateDelete)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		final javax.swing.GroupLayout jpObjectTemplatesEditorLayout = new javax.swing.GroupLayout(jpObjectTemplatesEditor);
		jpObjectTemplatesEditor.setLayout(jpObjectTemplatesEditorLayout);
		jpObjectTemplatesEditorLayout.setHorizontalGroup(jpObjectTemplatesEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectTemplatesEditorLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlObjectTemplates).addContainerGap(382, Short.MAX_VALUE))
				.addGroup(jpObjectTemplatesEditorLayout.createSequentialGroup()
						.addComponent(jspObjectTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpObjectTemplatesButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpObjectTemplatesEditorLayout.setVerticalGroup(
				jpObjectTemplatesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpObjectTemplatesEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlObjectTemplates)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpObjectTemplatesEditorLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpObjectTemplatesButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspObjectTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
												Short.MAX_VALUE))));

		jspSystemTemplateData.setTopComponent(jpObjectTemplatesEditor);

		jpLinkTemplatesEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlLinkTemplates.setText("Link Templates");

		jbLinkTemplatesInsert.setText("Insert");

		jbLinkTemplatesDelete.setText("Delete");

		final javax.swing.GroupLayout jpLinkTemplatesButtonsLayout = new javax.swing.GroupLayout(jpLinkTemplatesButtons);
		jpLinkTemplatesButtons.setLayout(jpLinkTemplatesButtonsLayout);
		jpLinkTemplatesButtonsLayout.setHorizontalGroup(
				jpLinkTemplatesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpLinkTemplatesButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpLinkTemplatesButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbLinkTemplatesInsert).addComponent(jbLinkTemplatesDelete))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpLinkTemplatesButtonsLayout.setVerticalGroup(
				jpLinkTemplatesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpLinkTemplatesButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbLinkTemplatesInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbLinkTemplatesDelete).addContainerGap(367, Short.MAX_VALUE)));

		jspLinkTemplates.setViewportView(jtLinkTemplates);

		final javax.swing.GroupLayout jpLinkTemplatesEditorLayout = new javax.swing.GroupLayout(jpLinkTemplatesEditor);
		jpLinkTemplatesEditor.setLayout(jpLinkTemplatesEditorLayout);
		jpLinkTemplatesEditorLayout.setHorizontalGroup(jpLinkTemplatesEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jpLinkTemplatesEditorLayout.createSequentialGroup()
								.addComponent(jspLinkTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
										Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpLinkTemplatesButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
				.addGroup(jpLinkTemplatesEditorLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlLinkTemplates).addContainerGap(395, Short.MAX_VALUE)));
		jpLinkTemplatesEditorLayout.setVerticalGroup(
				jpLinkTemplatesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpLinkTemplatesEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlLinkTemplates)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpLinkTemplatesEditorLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpLinkTemplatesButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspLinkTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
												Short.MAX_VALUE))));

		jspSystemTemplateData.setRightComponent(jpLinkTemplatesEditor);

		final javax.swing.GroupLayout jpSystemTemplateDataLayout = new javax.swing.GroupLayout(jpSystemTemplateData);
		jpSystemTemplateData.setLayout(jpSystemTemplateDataLayout);
		jpSystemTemplateDataLayout.setHorizontalGroup(jpSystemTemplateDataLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspSystemTemplateData));
		jpSystemTemplateDataLayout.setVerticalGroup(jpSystemTemplateDataLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspSystemTemplateData));

		final javax.swing.GroupLayout jpSystemTemplateEditorLayout = new javax.swing.GroupLayout(jpSystemTemplateEditor);
		jpSystemTemplateEditor.setLayout(jpSystemTemplateEditorLayout);
		jpSystemTemplateEditorLayout.setHorizontalGroup(
				jpSystemTemplateEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jpSystemTemplate, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jpSystemTemplateData, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpSystemTemplateEditorLayout.setVerticalGroup(
				jpSystemTemplateEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpSystemTemplateEditorLayout.createSequentialGroup()
								.addComponent(jpSystemTemplate, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpSystemTemplateData, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpEditors.add(jpSystemTemplateEditor, "systemTemplateEditor");

		jlObjectTemplateName.setText("name");

		jlObjectTemplateId.setText("id");

		jtfObjectTemplateName.setText("jTextField1");

		jtfObjectTemplateId.setText("jTextField2");

		final javax.swing.GroupLayout jpObjectTemplateLayout = new javax.swing.GroupLayout(jpObjectTemplate);
		jpObjectTemplate.setLayout(jpObjectTemplateLayout);
		jpObjectTemplateLayout.setHorizontalGroup(jpObjectTemplateLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectTemplateLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpObjectTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jlObjectTemplateName).addComponent(jlObjectTemplateId))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpObjectTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jtfObjectTemplateName, javax.swing.GroupLayout.DEFAULT_SIZE, 434,
										Short.MAX_VALUE)
								.addComponent(jtfObjectTemplateId))
						.addContainerGap()));
		jpObjectTemplateLayout.setVerticalGroup(jpObjectTemplateLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpObjectTemplateLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpObjectTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlObjectTemplateName).addComponent(jtfObjectTemplateName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpObjectTemplateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlObjectTemplateId).addComponent(jtfObjectTemplateId,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpAttributeTemplatesEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlAttributeTemplates.setText("Attribute Templates");

		jspAttributeTemplates.setViewportView(jtAttributeTemplates);

		jbAttributeTemplateInsert.setText("Insert");

		jbAttributeTemplateDelete.setText("Delete");

		final javax.swing.GroupLayout jpAttributeTemplatesButtonsLayout = new javax.swing.GroupLayout(
				jpAttributeTemplatesButtons);
		jpAttributeTemplatesButtons.setLayout(jpAttributeTemplatesButtonsLayout);
		jpAttributeTemplatesButtonsLayout.setHorizontalGroup(jpAttributeTemplatesButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpAttributeTemplatesButtonsLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpAttributeTemplatesButtonsLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jbAttributeTemplateInsert).addComponent(jbAttributeTemplateDelete))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpAttributeTemplatesButtonsLayout.setVerticalGroup(
				jpAttributeTemplatesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpAttributeTemplatesButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbAttributeTemplateInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbAttributeTemplateDelete)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		final javax.swing.GroupLayout jpAttributeTemplatesEditorLayout = new javax.swing.GroupLayout(
				jpAttributeTemplatesEditor);
		jpAttributeTemplatesEditor.setLayout(jpAttributeTemplatesEditorLayout);
		jpAttributeTemplatesEditorLayout.setHorizontalGroup(
				jpAttributeTemplatesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpAttributeTemplatesEditorLayout
								.createSequentialGroup().addContainerGap().addComponent(jlAttributeTemplates)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(jpAttributeTemplatesEditorLayout.createSequentialGroup()
								.addComponent(jspAttributeTemplates, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
										Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpAttributeTemplatesButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpAttributeTemplatesEditorLayout.setVerticalGroup(
				jpAttributeTemplatesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpAttributeTemplatesEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlAttributeTemplates)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpAttributeTemplatesEditorLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpAttributeTemplatesButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspAttributeTemplates, javax.swing.GroupLayout.DEFAULT_SIZE, 580,
												Short.MAX_VALUE))));

		final javax.swing.GroupLayout jpObjectTemplateEditorLayout = new javax.swing.GroupLayout(jpObjectTemplateEditor);
		jpObjectTemplateEditor.setLayout(jpObjectTemplateEditorLayout);
		jpObjectTemplateEditorLayout.setHorizontalGroup(
				jpObjectTemplateEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jpObjectTemplate, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jpAttributeTemplatesEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpObjectTemplateEditorLayout.setVerticalGroup(
				jpObjectTemplateEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpObjectTemplateEditorLayout.createSequentialGroup()
								.addComponent(jpObjectTemplate, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpAttributeTemplatesEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpEditors.add(jpObjectTemplateEditor, "objectTemplateEditor");

		jlTransformations.setText("Transformations");

		jbTransformationsInsertTransformation.setText("Insert");

		jbTransformationsDelete.setText("Delete");

		jbTransformationsInsertLinkTransformation.setText("Insert Link");

		jbTransformationsInserAttributeTransformation.setText("Insert Attribute");

		final javax.swing.GroupLayout jpTransformationsButtonsLayout = new javax.swing.GroupLayout(jpTransformationsButtons);
		jpTransformationsButtons.setLayout(jpTransformationsButtonsLayout);
		jpTransformationsButtonsLayout.setHorizontalGroup(
				jpTransformationsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpTransformationsButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpTransformationsButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
												jpTransformationsButtonsLayout.createSequentialGroup()
														.addGap(0, 0, Short.MAX_VALUE)
														.addComponent(jbTransformationsInserAttributeTransformation))
										.addGroup(jpTransformationsButtonsLayout.createSequentialGroup()
												.addGroup(jpTransformationsButtonsLayout
														.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
														.addComponent(jbTransformationsInsertLinkTransformation)
														.addComponent(jbTransformationsDelete)
														.addComponent(jbTransformationsInsertTransformation))
												.addGap(0, 0, Short.MAX_VALUE)))
								.addContainerGap()));
		jpTransformationsButtonsLayout.setVerticalGroup(
				jpTransformationsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpTransformationsButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbTransformationsInsertTransformation)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbTransformationsInsertLinkTransformation)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbTransformationsInserAttributeTransformation)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbTransformationsDelete).addContainerGap(534, Short.MAX_VALUE)));

		jtTransformations.setModel(transformationsDataModel);
		jspTransformations.setViewportView(jtTransformations);

		final javax.swing.GroupLayout jpTransformationsEditorLayout = new javax.swing.GroupLayout(jpTransformationsEditor);
		jpTransformationsEditor.setLayout(jpTransformationsEditorLayout);
		jpTransformationsEditorLayout.setHorizontalGroup(jpTransformationsEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpTransformationsEditorLayout.createSequentialGroup().addContainerGap()
						.addComponent(jlTransformations).addContainerGap(390, Short.MAX_VALUE))
				.addGroup(jpTransformationsEditorLayout.createSequentialGroup()
						.addComponent(jspTransformations, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpTransformationsButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpTransformationsEditorLayout.setVerticalGroup(
				jpTransformationsEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpTransformationsEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlTransformations)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpTransformationsEditorLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpTransformationsButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspTransformations, javax.swing.GroupLayout.DEFAULT_SIZE, 650,
												Short.MAX_VALUE))));

		jpEditors.add(jpTransformationsEditor, "transformationsEditor");

		jlTransformationObjectId.setText("object-id");

		jtfTransformationObjectId.setText("id-requirement");

		bgTransformationType.add(jrbTransformation);
		jrbTransformation.setSelected(true);
		jrbTransformation.setText("transformation");

		final javax.swing.GroupLayout jpTransformationLayout = new javax.swing.GroupLayout(jpTransformation);
		jpTransformation.setLayout(jpTransformationLayout);
		jpTransformationLayout.setHorizontalGroup(jpTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpTransformationLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpTransformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jpTransformationLayout.createSequentialGroup()
										.addComponent(jlTransformationObjectId)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jtfTransformationObjectId))
								.addGroup(jpTransformationLayout.createSequentialGroup().addComponent(jrbTransformation)
										.addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));
		jpTransformationLayout.setVerticalGroup(jpTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpTransformationLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpTransformationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlTransformationObjectId).addComponent(jtfTransformationObjectId,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jrbTransformation)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpLinkTransformation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		bgTransformationType.add(jrbLinkTransformation);
		jrbLinkTransformation.setText("linkTransformation");

		jlLinkTransformationId2new.setText("id2new");

		jtfLinkTransformationId2new.setText("id-2-new");

		jlLinkTransformationName.setText("name");

		jtfLinkTransformationName.setText("link-transformation-name");

		jlLinkTransformationId2old.setText("id2old");

		jtfLinkTransformationId2old.setText("id-2-old");

		final javax.swing.GroupLayout jpLinkTransformationLayout = new javax.swing.GroupLayout(jpLinkTransformation);
		jpLinkTransformation.setLayout(jpLinkTransformationLayout);
		jpLinkTransformationLayout.setHorizontalGroup(jpLinkTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinkTransformationLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpLinkTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jpLinkTransformationLayout.createSequentialGroup()
										.addGroup(jpLinkTransformationLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jlLinkTransformationName)
												.addComponent(jlLinkTransformationId2new)
												.addComponent(jlLinkTransformationId2old))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jpLinkTransformationLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
												.addComponent(jtfLinkTransformationId2old,
														javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jtfLinkTransformationId2new,
														javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jtfLinkTransformationName,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)))
								.addComponent(jrbLinkTransformation))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpLinkTransformationLayout.setVerticalGroup(jpLinkTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpLinkTransformationLayout.createSequentialGroup().addContainerGap()
						.addComponent(jrbLinkTransformation)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpLinkTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlLinkTransformationName).addComponent(jtfLinkTransformationName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jpLinkTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfLinkTransformationId2old, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlLinkTransformationId2old))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpLinkTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfLinkTransformationId2new, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlLinkTransformationId2new))
						.addContainerGap()));

		jpAttributeTransformation.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		bgTransformationType.add(jrbAttributeTransformation);
		jrbAttributeTransformation.setText("attributeTransformation");

		jlAttributeTransformationValue.setText("value");

		jtfAttributeTransformationValue.setText("true");

		jtfAttributeTransformationName.setText("attribute-diameter-requirement-status");

		jlAttributeTransformationName.setText("name");

		final javax.swing.GroupLayout jpAttributeTransformationLayout = new javax.swing.GroupLayout(jpAttributeTransformation);
		jpAttributeTransformation.setLayout(jpAttributeTransformationLayout);
		jpAttributeTransformationLayout.setHorizontalGroup(jpAttributeTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpAttributeTransformationLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpAttributeTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jpAttributeTransformationLayout.createSequentialGroup()
										.addGroup(jpAttributeTransformationLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jlAttributeTransformationName)
												.addComponent(jlAttributeTransformationValue,
														javax.swing.GroupLayout.Alignment.TRAILING))
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addGroup(jpAttributeTransformationLayout
												.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
												.addComponent(jtfAttributeTransformationName,
														javax.swing.GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
												.addComponent(jtfAttributeTransformationValue)))
								.addGroup(jpAttributeTransformationLayout.createSequentialGroup()
										.addComponent(jrbAttributeTransformation).addGap(0, 0, Short.MAX_VALUE)))
						.addContainerGap()));
		jpAttributeTransformationLayout.setVerticalGroup(jpAttributeTransformationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpAttributeTransformationLayout.createSequentialGroup().addContainerGap()
						.addComponent(jrbAttributeTransformation)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jpAttributeTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfAttributeTransformationName, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlAttributeTransformationName))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpAttributeTransformationLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfAttributeTransformationValue, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlAttributeTransformationValue))
						.addContainerGap()));

		final javax.swing.GroupLayout jpTransformationEditorLayout = new javax.swing.GroupLayout(jpTransformationEditor);
		jpTransformationEditor.setLayout(jpTransformationEditorLayout);
		jpTransformationEditorLayout.setHorizontalGroup(
				jpTransformationEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addComponent(jpTransformation, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jpLinkTransformation, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(jpAttributeTransformation, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpTransformationEditorLayout.setVerticalGroup(
				jpTransformationEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpTransformationEditorLayout.createSequentialGroup()
								.addComponent(jpTransformation, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpAttributeTransformation, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpLinkTransformation, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGap(0, 395, Short.MAX_VALUE)));

		jpEditors.add(jpTransformationEditor, "transformationEditor");

		jlActionName.setText("name");

		jtfActionName.setText("operation-name");

		final javax.swing.GroupLayout jpActionLayout = new javax.swing.GroupLayout(jpAction);
		jpAction.setLayout(jpActionLayout);
		jpActionLayout.setHorizontalGroup(jpActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpActionLayout.createSequentialGroup().addContainerGap().addComponent(jlActionName)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jtfActionName, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
						.addContainerGap()));
		jpActionLayout.setVerticalGroup(jpActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpActionLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpActionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlActionName).addComponent(jtfActionName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpActionFunctionsEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlActionFunctions.setText("Action Functions");

		jbActionFunctionsInsert.setText("Insert");

		jbActionFunctionsDelete.setText("Delete");

		final javax.swing.GroupLayout jpActionFunctionsButtonsLayout = new javax.swing.GroupLayout(jpActionFunctionsButtons);
		jpActionFunctionsButtons.setLayout(jpActionFunctionsButtonsLayout);
		jpActionFunctionsButtonsLayout.setHorizontalGroup(
				jpActionFunctionsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionFunctionsButtonsLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpActionFunctionsButtonsLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jbActionFunctionsInsert).addComponent(jbActionFunctionsDelete))
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpActionFunctionsButtonsLayout.setVerticalGroup(
				jpActionFunctionsButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionFunctionsButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbActionFunctionsInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbActionFunctionsDelete)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jspActionFunctions.setViewportView(jtActionFunctions);

		final javax.swing.GroupLayout jpActionFunctionsEditorLayout = new javax.swing.GroupLayout(jpActionFunctionsEditor);
		jpActionFunctionsEditor.setLayout(jpActionFunctionsEditorLayout);
		jpActionFunctionsEditorLayout.setHorizontalGroup(
				jpActionFunctionsEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionFunctionsEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlActionFunctions)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(jpActionFunctionsEditorLayout.createSequentialGroup()
								.addComponent(jspActionFunctions, javax.swing.GroupLayout.PREFERRED_SIZE, 0,
										Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpActionFunctionsButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpActionFunctionsEditorLayout.setVerticalGroup(
				jpActionFunctionsEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionFunctionsEditorLayout.createSequentialGroup().addContainerGap()
								.addComponent(jlActionFunctions)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpActionFunctionsEditorLayout
										.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpActionFunctionsButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspActionFunctions, javax.swing.GroupLayout.DEFAULT_SIZE, 608,
												Short.MAX_VALUE))));

		final javax.swing.GroupLayout jpActionEditorLayout = new javax.swing.GroupLayout(jpActionEditor);
		jpActionEditor.setLayout(jpActionEditorLayout);
		jpActionEditorLayout.setHorizontalGroup(jpActionEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpAction, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpActionFunctionsEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpActionEditorLayout
				.setVerticalGroup(jpActionEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpActionEditorLayout.createSequentialGroup()
								.addComponent(jpAction, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpActionFunctionsEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpEditors.add(jpActionEditor, "actionEditor");

		jlActionFunctionType.setText("type");

		jcbActionFunctionType.setModel(new javax.swing.DefaultComboBoxModel<>(
				new String[] { "unknown", "parameterUpdater", "preConditionChecker" }));

		jtaActionFunctionLines.setColumns(20);
		jtaActionFunctionLines.setRows(5);
		jtaActionFunctionLines.setText("lua line 1\nlua line 2\nlua line 3\nlua line 4");
		jspActionFunctionLines.setViewportView(jtaActionFunctionLines);

		final javax.swing.GroupLayout jpActionFunctionEditorLayout = new javax.swing.GroupLayout(jpActionFunctionEditor);
		jpActionFunctionEditor.setLayout(jpActionFunctionEditorLayout);
		jpActionFunctionEditorLayout.setHorizontalGroup(jpActionFunctionEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpActionFunctionEditorLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpActionFunctionEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jspActionFunctionLines, javax.swing.GroupLayout.DEFAULT_SIZE, 470,
										Short.MAX_VALUE)
								.addGroup(jpActionFunctionEditorLayout.createSequentialGroup()
										.addComponent(jlActionFunctionType)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
										.addComponent(jcbActionFunctionType, 0, javax.swing.GroupLayout.DEFAULT_SIZE,
												Short.MAX_VALUE)))
						.addContainerGap()));
		jpActionFunctionEditorLayout.setVerticalGroup(jpActionFunctionEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpActionFunctionEditorLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpActionFunctionEditorLayout
								.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlActionFunctionType).addComponent(jcbActionFunctionType,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jspActionFunctionLines, javax.swing.GroupLayout.DEFAULT_SIZE, 638,
								Short.MAX_VALUE)
						.addContainerGap()));

		jpEditors.add(jpActionFunctionEditor, "actionFunctionEditor");

		jlNodeId.setText("id");

		jcbNodeChecked.setText("checked");

		jtfNodeId.setText("unique-node-id");

		final javax.swing.GroupLayout jpNodeLayout = new javax.swing.GroupLayout(jpNode);
		jpNode.setLayout(jpNodeLayout);
		jpNodeLayout.setHorizontalGroup(jpNodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpNodeLayout.createSequentialGroup().addContainerGap().addComponent(jlNodeId)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpNodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addGroup(jpNodeLayout.createSequentialGroup().addComponent(jcbNodeChecked).addGap(0, 0,
										Short.MAX_VALUE))
								.addComponent(jtfNodeId, javax.swing.GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE))
						.addContainerGap()));
		jpNodeLayout.setVerticalGroup(jpNodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpNodeLayout.createSequentialGroup()
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(jpNodeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlNodeId).addComponent(jtfNodeId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jcbNodeChecked)));

		jpEdgesEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlEdges.setText("Edges");

		jspEdges.setViewportView(jtEdges);

		jbEdgesInsert.setText("Insert");
		jbEdgesInsert.setEnabled(false);

		jbEdgesDelete.setText("Delete");
		jbEdgesDelete.setEnabled(false);

		final javax.swing.GroupLayout jpEdgesButtonsLayout = new javax.swing.GroupLayout(jpEdgesButtons);
		jpEdgesButtons.setLayout(jpEdgesButtonsLayout);
		jpEdgesButtonsLayout.setHorizontalGroup(jpEdgesButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpEdgesButtonsLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpEdgesButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jbEdgesInsert).addComponent(jbEdgesDelete))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		jpEdgesButtonsLayout.setVerticalGroup(jpEdgesButtonsLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpEdgesButtonsLayout.createSequentialGroup().addContainerGap().addComponent(jbEdgesInsert)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addComponent(jbEdgesDelete)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		final javax.swing.GroupLayout jpEdgesEditorLayout = new javax.swing.GroupLayout(jpEdgesEditor);
		jpEdgesEditor.setLayout(jpEdgesEditorLayout);
		jpEdgesEditorLayout
				.setHorizontalGroup(jpEdgesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpEdgesEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlEdges)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(jpEdgesEditorLayout.createSequentialGroup()
								.addComponent(jspEdges, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpEdgesButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpEdgesEditorLayout.setVerticalGroup(jpEdgesEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpEdgesEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlEdges)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpEdgesEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
								.addComponent(jpEdgesButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(jspEdges, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE))));

		final javax.swing.GroupLayout jpNodeEditorLayout = new javax.swing.GroupLayout(jpNodeEditor);
		jpNodeEditor.setLayout(jpNodeEditorLayout);
		jpNodeEditorLayout.setHorizontalGroup(jpNodeEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpNode, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpEdgesEditor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE));
		jpNodeEditorLayout
				.setVerticalGroup(jpNodeEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpNodeEditorLayout.createSequentialGroup()
								.addComponent(jpNode, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpEdgesEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpEditors.add(jpNodeEditor, "nodeEditor");

		jlEdgeId.setText("id");

		jlBeginNodeId.setText("beginNodeId");

		jlEndNodeId.setText("endNodeId");

		jtfEdgeId.setText("edge-unique-id");

		jtfBeginNodeId.setText("node-unique-id-1");

		jtfEndNodeId.setText("node-unique-id-2");

		final javax.swing.GroupLayout jpEdgeLayout = new javax.swing.GroupLayout(jpEdge);
		jpEdge.setLayout(jpEdgeLayout);
		jpEdgeLayout
				.setHorizontalGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpEdgeLayout.createSequentialGroup().addContainerGap()
								.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jlBeginNodeId).addComponent(jlEndNodeId).addComponent(jlEdgeId))
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jtfEdgeId).addComponent(jtfEndNodeId,
												javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
										.addComponent(jtfBeginNodeId))
								.addContainerGap()));
		jpEdgeLayout.setVerticalGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jpEdgeLayout.createSequentialGroup()
						.addContainerGap()
						.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlEdgeId).addComponent(jtfEdgeId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfBeginNodeId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlBeginNodeId))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addGroup(jpEdgeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jtfEndNodeId, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addComponent(jlEndNodeId))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpOperationEditor.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

		jlOperationName.setText("name");

		jtfOperationName.setText("operation-name");

		final javax.swing.GroupLayout jpOperationLayout = new javax.swing.GroupLayout(jpOperation);
		jpOperation.setLayout(jpOperationLayout);
		jpOperationLayout.setHorizontalGroup(jpOperationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpOperationLayout.createSequentialGroup().addContainerGap().addComponent(jlOperationName)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jtfOperationName).addContainerGap()));
		jpOperationLayout.setVerticalGroup(jpOperationLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpOperationLayout.createSequentialGroup().addContainerGap()
						.addGroup(jpOperationLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jlOperationName).addComponent(jtfOperationName,
										javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.PREFERRED_SIZE))
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jlOperation.setText("Operation");

		jlParameters.setText("Parameters");

		jbParametersInsert.setText("Insert");
		jbParametersInsert.setEnabled(false);

		jbParametersDelete.setText("Delete");
		jbParametersDelete.setEnabled(false);

		final javax.swing.GroupLayout jpParametersButtonsLayout = new javax.swing.GroupLayout(jpParametersButtons);
		jpParametersButtons.setLayout(jpParametersButtonsLayout);
		jpParametersButtonsLayout.setHorizontalGroup(
				jpParametersButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpParametersButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbParametersDelete)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
								jpParametersButtonsLayout.createSequentialGroup()
										.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jbParametersInsert).addContainerGap()));
		jpParametersButtonsLayout.setVerticalGroup(
				jpParametersButtonsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpParametersButtonsLayout.createSequentialGroup().addContainerGap()
								.addComponent(jbParametersInsert)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jbParametersDelete)
								.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jspParameters.setViewportView(jtParameters);

		final javax.swing.GroupLayout jpParametersEditorLayout = new javax.swing.GroupLayout(jpParametersEditor);
		jpParametersEditor.setLayout(jpParametersEditorLayout);
		jpParametersEditorLayout.setHorizontalGroup(jpParametersEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpParametersEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlParameters)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(javax.swing.GroupLayout.Alignment.TRAILING,
						jpParametersEditorLayout.createSequentialGroup()
								.addComponent(jspParameters, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpParametersButtons, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)));
		jpParametersEditorLayout.setVerticalGroup(jpParametersEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpParametersEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlParameters)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED).addGroup(
								jpParametersEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
										.addComponent(jpParametersButtons, javax.swing.GroupLayout.DEFAULT_SIZE,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jspParameters, javax.swing.GroupLayout.DEFAULT_SIZE, 484,
												Short.MAX_VALUE))));

		final javax.swing.GroupLayout jpOperationEditorLayout = new javax.swing.GroupLayout(jpOperationEditor);
		jpOperationEditor.setLayout(jpOperationEditorLayout);
		jpOperationEditorLayout.setHorizontalGroup(jpOperationEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpOperationEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlOperation)
						.addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addComponent(jpOperation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpParametersEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpOperationEditorLayout.setVerticalGroup(jpOperationEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(jpOperationEditorLayout.createSequentialGroup().addContainerGap().addComponent(jlOperation)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpOperation, javax.swing.GroupLayout.PREFERRED_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jpParametersEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
								javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		final javax.swing.GroupLayout jpEdgeEditorLayout = new javax.swing.GroupLayout(jpEdgeEditor);
		jpEdgeEditor.setLayout(jpEdgeEditorLayout);
		jpEdgeEditorLayout.setHorizontalGroup(jpEdgeEditorLayout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addComponent(jpEdge, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE,
						Short.MAX_VALUE)
				.addComponent(jpOperationEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
						javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE));
		jpEdgeEditorLayout
				.setVerticalGroup(jpEdgeEditorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
						.addGroup(jpEdgeEditorLayout.createSequentialGroup()
								.addComponent(jpEdge, javax.swing.GroupLayout.PREFERRED_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
								.addComponent(jpOperationEditor, javax.swing.GroupLayout.DEFAULT_SIZE,
										javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));

		jpEditors.add(jpEdgeEditor, "edgeEditor");

		final javax.swing.GroupLayout jpEmptyLayout = new javax.swing.GroupLayout(jpEmpty);
		jpEmpty.setLayout(jpEmptyLayout);
		jpEmptyLayout.setHorizontalGroup(jpEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 482, Short.MAX_VALUE));
		jpEmptyLayout.setVerticalGroup(jpEmptyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGap(0, 678, Short.MAX_VALUE));

		jpEditors.add(jpEmpty, "empty");

		jspWorkArea.setRightComponent(jpEditors);

		jmTaskDescription.setText("Task Description");

		jmiTaskDescriptionNew.setText("New");
		jmTaskDescription.add(jmiTaskDescriptionNew);

		jmiTaskDescriptionLoad.setText("Load");
		jmTaskDescription.add(jmiTaskDescriptionLoad);

		jmiTaskDescriptionSave.setText("Save");
		jmTaskDescription.add(jmiTaskDescriptionSave);

		jmbMenu.add(jmTaskDescription);

		jmSystemTransformations.setText("System Transformations");

		jmiSystemTransformationsNew.setText("New");
		jmSystemTransformations.add(jmiSystemTransformationsNew);

		jmiSystemTransformationsLoad.setText("Load");
		jmSystemTransformations.add(jmiSystemTransformationsLoad);

		jmiSystemTransformationsSave.setText("Save");
		jmSystemTransformations.add(jmiSystemTransformationsSave);

		jmbMenu.add(jmSystemTransformations);

		jmNodeNetwork.setText("Node Network");

		jmiNodeNetworkNew.setText("New");
		jmNodeNetwork.add(jmiNodeNetworkNew);

		jmiNodeNetworkLoad.setText("Load");
		jmNodeNetwork.add(jmiNodeNetworkLoad);

		jmbMenu.add(jmNodeNetwork);

		jmProcess.setText("Process");

		jmiProcessNew.setText("New");
		jmProcess.add(jmiProcessNew);

		jmiProcessLoad.setText("Load");
		jmProcess.add(jmiProcessLoad);

		jmbMenu.add(jmProcess);

		setJMenuBar(jmbMenu);

		final javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspWorkArea));
		layout.setVerticalGroup(
				layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING).addComponent(jspWorkArea));

		pack();
	}// </editor-fold>//GEN-END:initComponents

	// TODO (2022-11-01 #72): покрытие тестами jtDataValueChanged
	// TODO (2022-12-11 #73): перенести в EditorDataModel
	private void jtDataValueChanged(javax.swing.event.TreeSelectionEvent evt) {// GEN-FIRST:event_jtDataValueChanged
		final DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) evt.getPath().getLastPathComponent();
		if (selectedNode == null) {
			selectEditor("empty");
			return;
		}
		final Object selectedObject = selectedNode.getUserObject();
		if (selectedObject instanceof System) {
			systemDataModel.clear();
			selectEditor("systemEditor");
			systemDataModel.loadSystem((System) selectedObject, selectedNode);
		} else if (selectedObject instanceof SystemObject) {
			objectDataModel.clear();
			selectEditor("objectEditor");
			objectDataModel.loadSystemObject((SystemObject) selectedObject, selectedNode);
		} else if (selectedObject instanceof SystemTransformations) {
			systemTransformationsDataModel.clear();
			selectEditor("systemTransformationsEditor");
			systemTransformationsDataModel.loadSystemTransformations((SystemTransformations) selectedObject, selectedNode);
		} else if (selectedObject instanceof SystemTransformation) {
			systemTransformationDataModel.clear();
			selectEditor("systemTransformationEditor");
			systemTransformationDataModel.loadSystemTransformation((SystemTransformation) selectedObject, selectedNode);
		} else if (selectedObject instanceof SystemTemplate) {
			systemTemplateDataModel.clear();
			selectEditor("systemTemplateEditor");
			systemTemplateDataModel.loadSystemTemplate((SystemTemplate) selectedObject, selectedNode);
		} else if (selectedObject instanceof SystemObjectTemplate) {
			objectTemplateDataModel.clear();
			selectEditor("objectTemplateEditor");
			objectTemplateDataModel.loadSystemObjectTemplate((SystemObjectTemplate) selectedObject, selectedNode);
		} else if (selectedObject instanceof planning.model.Action) {
			actionDataModel.clear();
			selectEditor("actionEditor");
			actionDataModel.loadAction((planning.model.Action) selectedObject, selectedNode);
		} else if (selectedObject instanceof ActionFunction) {
			actionFunctionDataModel.clear();
			selectEditor("actionFunctionEditor");
			actionFunctionDataModel.loadActionFunction((ActionFunction) selectedObject, selectedNode);
		} else if (selectedObject instanceof Transformations) {
			transformationsDataModel.clear();
			selectEditor("transformationsEditor");
			transformationsDataModel.loadTransformations((Transformations) selectedObject, selectedNode);
		} else if (selectedObject instanceof Transformation) {
			transformationDataModel.clear();
			selectEditor("transformationEditor");
			transformationDataModel.loadTransformation((Transformation) selectedObject, selectedNode);
		} else if (selectedObject instanceof Node) {
			nodeDataModel.clear();
			selectEditor("nodeEditor");
			nodeDataModel.loadNode((Node) selectedObject, selectedNode);
		} else if (selectedObject instanceof Edge) {
			edgeDataModel.clear();
			selectEditor("edgeEditor");
			edgeDataModel.loadEdge((Edge) selectedObject, selectedNode);
		} else {
			selectEditor("empty");
			java.lang.System.out.println("unknown: " + selectedObject.toString());
		}
	}// GEN-LAST:event_jtDataValueChanged

	private void selectEditor(String editorName) {
		final CardLayout layout = (CardLayout) jpEditors.getLayout();
		layout.show(jpEditors, editorName);
	}

	public static void main(String args[]) throws Exception {
		UserInterfaceFactory.initializeLookAndFeel();
		SwingUtilities.invokeLater(() -> {
			new EditorFrame(new Application()).setVisible(true);
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.ButtonGroup bgTransformationType;
	private javax.swing.JButton jbActionFunctionsDelete;
	private javax.swing.JButton jbActionFunctionsInsert;
	private javax.swing.JButton jbAttributeTemplateDelete;
	private javax.swing.JButton jbAttributeTemplateInsert;
	private javax.swing.JButton jbAttributesDelete;
	private javax.swing.JButton jbAttributesInsert;
	private javax.swing.JButton jbEdgesDelete;
	private javax.swing.JButton jbEdgesInsert;
	private javax.swing.JButton jbLinkTemplatesDelete;
	private javax.swing.JButton jbLinkTemplatesInsert;
	private javax.swing.JButton jbLinksDelete;
	private javax.swing.JButton jbLinksInsert;
	private javax.swing.JButton jbObjectTemplateDelete;
	private javax.swing.JButton jbObjectTemplateInsert;
	private javax.swing.JButton jbObjectsDelete;
	private javax.swing.JButton jbObjectsInsert;
	private javax.swing.JButton jbParametersDelete;
	private javax.swing.JButton jbParametersInsert;
	private javax.swing.JButton jbSystemTransformationsDelete;
	private javax.swing.JButton jbSystemTransformationsInsert;
	private javax.swing.JButton jbTransformationsDelete;
	private javax.swing.JButton jbTransformationsInserAttributeTransformation;
	private javax.swing.JButton jbTransformationsInsertLinkTransformation;
	private javax.swing.JButton jbTransformationsInsertTransformation;
	private javax.swing.JComboBox<String> jcbActionFunctionType;
	private javax.swing.JCheckBox jcbNodeChecked;
	private javax.swing.JComboBox<String> jcbSystemTemplateType;
	private javax.swing.JComboBox<String> jcbSystemType;
	private javax.swing.JLabel jlActionFunctionType;
	private javax.swing.JLabel jlActionFunctions;
	private javax.swing.JLabel jlActionName;
	private javax.swing.JLabel jlAttributeTemplates;
	private javax.swing.JLabel jlAttributeTransformationName;
	private javax.swing.JLabel jlAttributeTransformationValue;
	private javax.swing.JLabel jlAttributes;
	private javax.swing.JLabel jlBeginNodeId;
	private javax.swing.JLabel jlEdgeId;
	private javax.swing.JLabel jlEdges;
	private javax.swing.JLabel jlEndNodeId;
	private javax.swing.JLabel jlLinkTemplates;
	private javax.swing.JLabel jlLinkTransformationId2new;
	private javax.swing.JLabel jlLinkTransformationId2old;
	private javax.swing.JLabel jlLinkTransformationName;
	private javax.swing.JLabel jlLinks;
	private javax.swing.JLabel jlNodeId;
	private javax.swing.JLabel jlObjectId;
	private javax.swing.JLabel jlObjectName;
	private javax.swing.JLabel jlObjectTemplateId;
	private javax.swing.JLabel jlObjectTemplateName;
	private javax.swing.JLabel jlObjectTemplates;
	private javax.swing.JLabel jlObjects;
	private javax.swing.JLabel jlOperation;
	private javax.swing.JLabel jlOperationName;
	private javax.swing.JLabel jlParameters;
	private javax.swing.JLabel jlSystemName;
	private javax.swing.JLabel jlSystemTemplateName;
	private javax.swing.JLabel jlSystemTemplateType;
	private javax.swing.JLabel jlSystemTransformationName;
	private javax.swing.JLabel jlSystemTransformations;
	private javax.swing.JLabel jlSystemType;
	private javax.swing.JLabel jlTransformationObjectId;
	private javax.swing.JLabel jlTransformations;
	private javax.swing.JMenu jmNodeNetwork;
	private javax.swing.JMenu jmProcess;
	private javax.swing.JMenu jmSystemTransformations;
	private javax.swing.JMenu jmTaskDescription;
	private javax.swing.JMenuBar jmbMenu;
	private javax.swing.JMenuItem jmiNodeNetworkLoad;
	private javax.swing.JMenuItem jmiNodeNetworkNew;
	private javax.swing.JMenuItem jmiProcessLoad;
	private javax.swing.JMenuItem jmiProcessNew;
	private javax.swing.JMenuItem jmiSystemTransformationsLoad;
	private javax.swing.JMenuItem jmiSystemTransformationsNew;
	private javax.swing.JMenuItem jmiSystemTransformationsSave;
	private javax.swing.JMenuItem jmiTaskDescriptionLoad;
	private javax.swing.JMenuItem jmiTaskDescriptionNew;
	private javax.swing.JMenuItem jmiTaskDescriptionSave;
	private javax.swing.JPanel jpAction;
	private javax.swing.JPanel jpActionEditor;
	private javax.swing.JPanel jpActionFunctionEditor;
	private javax.swing.JPanel jpActionFunctionsButtons;
	private javax.swing.JPanel jpActionFunctionsEditor;
	private javax.swing.JPanel jpAttributeTemplatesButtons;
	private javax.swing.JPanel jpAttributeTemplatesEditor;
	private javax.swing.JPanel jpAttributeTransformation;
	private javax.swing.JPanel jpAttributesButtons;
	private javax.swing.JPanel jpAttributesEditor;
	private javax.swing.JPanel jpEdge;
	private javax.swing.JPanel jpEdgeEditor;
	private javax.swing.JPanel jpEdgesButtons;
	private javax.swing.JPanel jpEdgesEditor;
	private javax.swing.JPanel jpEditors;
	private javax.swing.JPanel jpEmpty;
	private javax.swing.JPanel jpLinkTemplatesButtons;
	private javax.swing.JPanel jpLinkTemplatesEditor;
	private javax.swing.JPanel jpLinkTransformation;
	private javax.swing.JPanel jpLinksButtons;
	private javax.swing.JPanel jpLinksEditor;
	private javax.swing.JPanel jpNode;
	private javax.swing.JPanel jpNodeEditor;
	private javax.swing.JPanel jpObject;
	private javax.swing.JPanel jpObjectEditor;
	private javax.swing.JPanel jpObjectTemplate;
	private javax.swing.JPanel jpObjectTemplateEditor;
	private javax.swing.JPanel jpObjectTemplatesButtons;
	private javax.swing.JPanel jpObjectTemplatesEditor;
	private javax.swing.JPanel jpObjectsButtons;
	private javax.swing.JPanel jpObjectsEditor;
	private javax.swing.JPanel jpOperation;
	private javax.swing.JPanel jpOperationEditor;
	private javax.swing.JPanel jpParametersButtons;
	private javax.swing.JPanel jpParametersEditor;
	private javax.swing.JPanel jpSystem;
	private javax.swing.JPanel jpSystemData;
	private javax.swing.JPanel jpSystemEditor;
	private javax.swing.JPanel jpSystemTemplate;
	private javax.swing.JPanel jpSystemTemplateData;
	private javax.swing.JPanel jpSystemTemplateEditor;
	private javax.swing.JPanel jpSystemTransformationEditor;
	private javax.swing.JPanel jpSystemTransformationsButtons;
	private javax.swing.JPanel jpSystemTransformationsEditor;
	private javax.swing.JPanel jpTransformation;
	private javax.swing.JPanel jpTransformationEditor;
	private javax.swing.JPanel jpTransformationsButtons;
	private javax.swing.JPanel jpTransformationsEditor;
	private javax.swing.JRadioButton jrbAttributeTransformation;
	private javax.swing.JRadioButton jrbLinkTransformation;
	private javax.swing.JRadioButton jrbTransformation;
	private javax.swing.JScrollPane jspActionFunctionLines;
	private javax.swing.JScrollPane jspActionFunctions;
	private javax.swing.JScrollPane jspAttributeTemplates;
	private javax.swing.JScrollPane jspAttributes;
	private javax.swing.JScrollPane jspData;
	private javax.swing.JScrollPane jspEdges;
	private javax.swing.JScrollPane jspLinkTemplates;
	private javax.swing.JScrollPane jspLinks;
	private javax.swing.JScrollPane jspObjectTemplates;
	private javax.swing.JScrollPane jspObjects;
	private javax.swing.JScrollPane jspParameters;
	private javax.swing.JSplitPane jspSystemData;
	private javax.swing.JSplitPane jspSystemTemplateData;
	private javax.swing.JScrollPane jspSystemTransformations;
	private javax.swing.JScrollPane jspTransformations;
	private javax.swing.JSplitPane jspWorkArea;
	private javax.swing.JTable jtActionFunctions;
	private javax.swing.JTable jtAttributeTemplates;
	private javax.swing.JTable jtAttributes;
	private javax.swing.JTree jtData;
	private javax.swing.JTable jtEdges;
	private javax.swing.JTable jtLinkTemplates;
	private javax.swing.JTable jtLinks;
	private javax.swing.JTable jtObjectTemplates;
	private javax.swing.JTable jtObjects;
	private javax.swing.JTable jtParameters;
	private javax.swing.JTable jtSystemTransformations;
	private javax.swing.JTable jtTransformations;
	private javax.swing.JTextArea jtaActionFunctionLines;
	private javax.swing.JTextField jtfActionName;
	private javax.swing.JTextField jtfAttributeTransformationName;
	private javax.swing.JTextField jtfAttributeTransformationValue;
	private javax.swing.JTextField jtfBeginNodeId;
	private javax.swing.JTextField jtfEdgeId;
	private javax.swing.JTextField jtfEndNodeId;
	private javax.swing.JTextField jtfLinkTransformationId2new;
	private javax.swing.JTextField jtfLinkTransformationId2old;
	private javax.swing.JTextField jtfLinkTransformationName;
	private javax.swing.JTextField jtfNodeId;
	private javax.swing.JTextField jtfObjectId;
	private javax.swing.JTextField jtfObjectName;
	private javax.swing.JTextField jtfObjectTemplateId;
	private javax.swing.JTextField jtfObjectTemplateName;
	private javax.swing.JTextField jtfOperationName;
	private javax.swing.JTextField jtfSystemName;
	private javax.swing.JTextField jtfSystemTemplateName;
	private javax.swing.JTextField jtfSystemTransformationName;
	private javax.swing.JTextField jtfTransformationObjectId;
	// End of variables declaration//GEN-END:variables
}
