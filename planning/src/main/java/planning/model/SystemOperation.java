package planning.model;

import java.util.Map;

public class SystemOperation {

	public SystemOperation(Action action, Map<String, String> actionParameters) {
		this.action = action;
		this.actionParameters = actionParameters;
	}

	private Action action;

	// TODO (2020-07-05 #22): убрать ссылку на Action
	public Action getAction() {
		return action;
	}

	// TODO (2020-09-17 #31): скрыть структуру хранения параметров
	private Map<String, String> actionParameters;

	public Map<String, String> getActionParameters() {
		return actionParameters;
	}

	public String getName() {
		return action.getName();
	}

	public String getParameter(String parameterName) {
		return actionParameters.get(parameterName);
	}

	public void setName(String operationName) {
		//TODO (2022-12-25 #74): operationName является неизменяемым
	}
}
