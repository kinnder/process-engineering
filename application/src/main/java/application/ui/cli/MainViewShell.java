package application.ui.cli;

import java.io.PrintStream;

import application.Application;
import application.Arguments;
import application.event.CommandEvent;
import application.event.UserEvent;
import application.ui.UserInterface;

public class MainViewShell implements UserInterface {

	private PrintStream printStream;

	private Application application;

	public MainViewShell(Application application, PrintStream printStream) {
		this.printStream = printStream;
		this.application = application;
	}

	@Override
	public void notifyUserEvent(UserEvent event) {
		printStream.println(event.message);
	}

	@Override
	public void notifyCommandEvent(CommandEvent event) {
		printStream.println(event.message);
	}

	@Override
	public void run() throws Exception {
		Arguments arguments = application.getArguments();

		if (arguments.hasArgument_plan()) {
			application.plan();
		} else if (arguments.hasArgument_new_st()) {
			application.newSystemTransformations();
		} else if (arguments.hasArgument_new_td()) {
			application.newTaskDescription();
		} else if (arguments.hasArgument_verify()) {
			application.verify();
		} else if (arguments.hasArgument_convert()) {
			application.convert();
		} else {
			application.usageHelp();
		}
	}

	@Override
	public void stop() {
	}
}
