package model;

import utility.Collection;

/** �������� */
public class SystemModification {

	public SystemObject target;

	public Collection modifications;

	public SystemModification() {
		target = null;

		modifications = new Collection();
	}
}
