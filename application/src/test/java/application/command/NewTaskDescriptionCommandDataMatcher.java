package application.command;

import testtools.Matcher;

public class NewTaskDescriptionCommandDataMatcher extends Matcher<NewTaskDescriptionCommandData> {

	// TODO (2020-07-24 #29): имеется аналогичный метод в классе PlanCommandDataMatcher
	public NewTaskDescriptionCommandDataMatcher expectTaskDescriptionFile(String taskDescriptionFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(NewTaskDescriptionCommandData arg) {
				compare("taskDescriptionFile", taskDescriptionFile, arg.taskDescriptionFile);
			}
		});
		return this;
	}

	public NewTaskDescriptionCommandDataMatcher expectDomain(String domain) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(NewTaskDescriptionCommandData arg) {
				compare("domain", domain, arg.domain);
			}
		});
		return this;
	}
}
