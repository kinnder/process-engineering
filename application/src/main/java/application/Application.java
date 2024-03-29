package application;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.cli.UnrecognizedOptionException;
import org.jdom2.JDOMException;
import org.slf4j.LoggerFactory;

import application.command.CommandManager;
import application.command.ConvertCommand;
import application.command.ConvertCommandData;
import application.command.UsageHelpCommand;
import application.command.UsageHelpCommandData;
import application.command.NewSystemTransformationsCommand;
import application.command.NewSystemTransformationsCommandData;
import application.command.NewTaskDescriptionCommand;
import application.command.NewTaskDescriptionCommandData;
import application.command.PlanCommand;
import application.command.PlanCommandData;
import application.command.StopApplicationCommand;
import application.command.StopApplicationCommandData;
import application.command.VerifyCommand;
import application.command.VerifyCommandData;
import application.event.CommandEvent;
import application.event.Event;
import application.event.UserEvent;
import application.storage.PersistanceStorage;
import application.ui.UserInterfaceManager;
import application.ui.UserInterfaceFactory.UserInterfaceType;
import planning.method.NodeNetwork;
import planning.method.SystemTransformations;
import planning.method.TaskDescription;
import planning.model.SystemProcess;

public class Application {

	public Application() {
		persistanceStorage = new PersistanceStorage();
		arguments = new Arguments();

		userInterfaceManager = new UserInterfaceManager(this);
		commandManager = new CommandManager(this);

		executor = Executors.newFixedThreadPool(2);
	}

	private ExecutorService executor;

	Application(CommandManager commandManager, PersistanceStorage persistanceStorage, Arguments arguments, UserInterfaceManager userInterfaceManager, ExecutorService executorService) {
		this.persistanceStorage = persistanceStorage;
		this.arguments = arguments;

		this.userInterfaceManager = userInterfaceManager;
		this.commandManager = commandManager;

		this.executor = executorService;
	}

	public void start(String[] args) throws Exception {
		try {
			arguments.parseArguments(args);
			userInterfaceManager.createUserInterface(arguments.hasArgument_gui() ? UserInterfaceType.gui : UserInterfaceType.cli);
			userInterfaceManager.start();
			executor.submit(userInterfaceManager);
			executor.submit(commandManager);
			if (arguments.hasArgument_gui() == false) {
				stopApplication();
			}
		} catch (UnrecognizedOptionException e) {
			userInterfaceManager.createUserInterface(UserInterfaceType.cli);
			userInterfaceManager.start();
			executor.submit(userInterfaceManager);
			executor.submit(commandManager);
			pushEvent(UserEvent.error(e.getMessage()));
			usageHelp();
			stopApplication();
		}
	}

	public void stop() {
		commandManager.stop();
		userInterfaceManager.stop();
		executor.shutdown();
	}

	private UserInterfaceManager userInterfaceManager;

	public void pushEvent(Event event) {
		userInterfaceManager.pushEvent(event);
		commandManager.pushEvent(event);
	}

	private PersistanceStorage persistanceStorage;

	public void saveSystemTransformations(SystemTransformations systemTransformations, String path) throws IOException {
		persistanceStorage.saveSystemTransformations(systemTransformations, path);
	}

	// TODO (2022-11-16 #73): переделать в команду
	public void saveSystemTransformations(SystemTransformations systemTransformations) {
		final String st_path = arguments.getArgument_st("systemTransformations.xml");
		try {
			persistanceStorage.saveSystemTransformations(systemTransformations, st_path);
		} catch (IOException e) {
			pushEvent(CommandEvent.errored("saveSystemTransformations"));
			LoggerFactory.getLogger(getClass()).error("", e);
		}
	}

	public void saveTaskDescription(TaskDescription taskDescription, String path) throws IOException {
		persistanceStorage.saveTaskDescription(taskDescription, path);
	}

	// TODO (2022-10-11 #72): переделать в команду
	public void saveTaskDescription(TaskDescription taskDescription) {
		final String td_path = arguments.getArgument_td("taskDescription.xml");
		try {
			persistanceStorage.saveTaskDescription(taskDescription, td_path);
		} catch (IOException e) {
			pushEvent(CommandEvent.errored("saveTaskDescription"));
			LoggerFactory.getLogger(getClass()).error("", e);
		}
	}

	public void saveSystemProcess(SystemProcess systemProcess, String path) throws IOException {
		persistanceStorage.saveSystemProcess(systemProcess, path);
	}

	public void saveNodeNetwork(NodeNetwork nodeNetwork, String path) throws IOException {
		persistanceStorage.saveNodeNetwork(nodeNetwork, path);
	}

	// TODO (2023-03-20 #88): объединить с аналогичной командой
	public SystemTransformations newSystemTransformations_v2() {
		return new SystemTransformations();
	}

	public SystemTransformations loadSystemTransformations(String path) throws IOException, JDOMException {
		return persistanceStorage.loadSystemTransformations(path);
	}

	// TODO (2022-09-18 #72): переделать в команду
	public SystemTransformations loadSystemTransformations() {
		final String st_path = arguments.getArgument_st("systemTransformations.xml");
		SystemTransformations systemTransformations = null;
		try {
			systemTransformations = loadSystemTransformations(st_path);
		} catch (IOException | JDOMException e) {
			pushEvent(CommandEvent.errored("loadSystemTransformations"));
			LoggerFactory.getLogger(getClass()).error("", e);
		}
		return systemTransformations;
	}

	// TODO (2023-03-20 #88): объединить с аналогичной командой
	public TaskDescription newTaskDescription_v2() {
		return new TaskDescription();
	}

	public TaskDescription loadTaskDescription(String path) throws IOException, JDOMException {
		return persistanceStorage.loadTaskDescription(path);
	}

	// TODO (2022-09-18 #72): переделать в команду
	public TaskDescription loadTaskDescription() {
		final String td_path = arguments.getArgument_td("taskDescription.xml");
		TaskDescription taskDescription = null;
		try {
			taskDescription = loadTaskDescription(td_path);
		} catch (IOException | JDOMException e) {
			pushEvent(CommandEvent.errored("loadTaskDescription"));
			LoggerFactory.getLogger(getClass()).error("", e);
		}
		return taskDescription;
	}

	// TODO (2023-03-20 #88): объединить с аналогичной командой
	public NodeNetwork newNodeNetwork_v2() {
		return new NodeNetwork();
	}

	public NodeNetwork loadNodeNetwork(String path) throws IOException, JDOMException {
		return persistanceStorage.loadNodeNetwork(path);
	}

	// TODO (2022-09-18 #72): переделать в команду
	public NodeNetwork loadNodeNetwork() {
		final String nn_path = arguments.getArgument_nn("nodeNetwork.xml");
		NodeNetwork nodeNetwork = null;
		try {
			nodeNetwork = loadNodeNetwork(nn_path);
		} catch (IOException | JDOMException e) {
			pushEvent(CommandEvent.errored("loadNodeNetwork"));
			LoggerFactory.getLogger(getClass()).error("", e);
		}
		return nodeNetwork;
	}

	// TODO (2023-03-20 #88): объединить с аналогичной командой
	public SystemProcess newSystemProcess_v2() {
		return new SystemProcess();
	}

	public SystemProcess loadSystemProcess(String path) throws IOException, JDOMException {
		return persistanceStorage.loadSystemProcess(path);
	}

	// TODO (2022-09-18 #72): переделать в команду
	public SystemProcess loadSystemProcess() {
		final String pr_path = arguments.getArgument_p("process.xml");
		SystemProcess systemProcess = null;
		try {
			systemProcess = loadSystemProcess(pr_path);
		} catch (IOException | JDOMException e) {
			pushEvent(CommandEvent.errored("loadSystemProcess"));
			LoggerFactory.getLogger(getClass()).error("", e);
		}
		return systemProcess;
	}

	public InputStream getResourceAsStream(String resourcePath) {
		return persistanceStorage.getResourceAsStream(resourcePath);
	}

	private Arguments arguments;

	public Arguments getArguments() {
		return arguments;
	}

	private CommandManager commandManager;

	public void plan() {
		final PlanCommandData data = new PlanCommandData();
		data.taskDescriptionFile = arguments.getArgument_td("taskDescription.xml");
		data.systemTransformationsFile = arguments.getArgument_st("systemTransformations.xml");
		data.processFile = arguments.getArgument_p("process.xml");
		data.nodeNetworkFile = arguments.getArgument_nn("nodeNetwork.xml");
		pushEvent(CommandEvent.start(PlanCommand.NAME, data));
	}

	public void verify() {
		final VerifyCommandData data = new VerifyCommandData();
		data.taskDescriptionFile = arguments.getArgument_td(null);
		data.systemTransformationsFile = arguments.getArgument_st(null);
		data.processFile = arguments.getArgument_p(null);
		data.nodeNetworkFile = arguments.getArgument_nn(null);
		pushEvent(CommandEvent.start(VerifyCommand.NAME, data));
	}

	public void newTaskDescription() {
		final NewTaskDescriptionCommandData data = new NewTaskDescriptionCommandData();
		data.taskDescriptionFile = arguments.getArgument_td("taskDescription.xml");
		data.domain = arguments.getArgument_d("unknown");
		pushEvent(CommandEvent.start(NewTaskDescriptionCommand.NAME, data));
	}

	public void newSystemTransformations() {
		final NewSystemTransformationsCommandData data = new NewSystemTransformationsCommandData();
		data.systemTransformationsFile = arguments.getArgument_st("systemTransformations.xml");
		data.domain = arguments.getArgument_d("unknown");
		pushEvent(CommandEvent.start(NewSystemTransformationsCommand.NAME, data));
	}

	public void convert() {
		final ConvertCommandData data = new ConvertCommandData();
		data.taskDescriptionFile = arguments.getArgument_td(null);
		data.systemTransformationsFile = arguments.getArgument_st(null);
		data.processFile = arguments.getArgument_p(null);
		data.nodeNetworkFile = arguments.getArgument_nn(null);
		pushEvent(CommandEvent.start(ConvertCommand.NAME, data));
	}

	public void usageHelp() {
		final UsageHelpCommandData data = new UsageHelpCommandData();
		data.options = arguments.getOptions();
		pushEvent(CommandEvent.start(UsageHelpCommand.NAME, data));
	}

	public void stopApplication() {
		final StopApplicationCommandData data = new StopApplicationCommandData();
		pushEvent(CommandEvent.start(StopApplicationCommand.NAME, data));
	}
}
