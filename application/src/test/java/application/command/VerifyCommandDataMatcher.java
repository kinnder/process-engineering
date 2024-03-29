package application.command;

import testtools.Matcher;

public class VerifyCommandDataMatcher extends Matcher<VerifyCommandData> {

	public VerifyCommandDataMatcher expectProcessFile(String processFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(VerifyCommandData arg) {
				compare("processFile", processFile, arg.processFile);
			}
		});
		return this;
	}

	public VerifyCommandDataMatcher expectTaskDescriptionFile(String taskDescriptionFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(VerifyCommandData arg) {
				compare("taskDescriptionFile", taskDescriptionFile, arg.taskDescriptionFile);
			}
		});
		return this;
	}

	public VerifyCommandDataMatcher expectSystemTransformationsFile(String systemTransformationsFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(VerifyCommandData arg) {
				compare("systemTransformationsFile", systemTransformationsFile, arg.systemTransformationsFile);
			}
		});
		return this;
	}

	public VerifyCommandDataMatcher expectNodeNetworkFile(String nodeNetworkFile) {
		addExpectation(new MatcherExpectation() {
			@Override
			public void trigger(VerifyCommandData arg) {
				compare("nodeNetworkFile", nodeNetworkFile, arg.nodeNetworkFile);
			}
		});
		return this;
	}
}
