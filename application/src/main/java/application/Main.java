package application;

import org.slf4j.LoggerFactory;

public class Main {

	public static void main(String[] args) {
		try {
			UserInterfaceImp ui = new UserInterfaceImp(System.out);
			Application application = new Application();
			application.registerUserInterface(ui);
			application.run(args);
		} catch (Exception e) {
			LoggerFactory.getLogger(Main.class).error("", e);
		}
	}
}
