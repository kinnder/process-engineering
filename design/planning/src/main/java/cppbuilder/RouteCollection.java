package cppbuilder;

import cppbuilder.utility.Collection;

/** ��������� ����� */
public class RouteCollection extends Collection<Route> {

	/** �������� ���������� ���� */
	public Route getShortestRoute() {
		Route result = items.get(0);
		for (Route route : items) {
			if (result.nodes.size() > route.nodes.size()) {
				result = route;
			}
		}
		return result;
	}
}
