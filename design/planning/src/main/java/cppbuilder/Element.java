package cppbuilder;

import cppbuilder.utility.LogicOperators;

/** ������� ������� */
public class Element extends LogicOperators<Element> {

	/** ��������� ������� �� �������� */
	public SystemState systemStateBeforeTransition = new SystemState();

	/** ������� */
	public Transition transition = new Transition();

	/** ��������� ������� ����� �������� */
	public SystemState systemStateAfterTransition = new SystemState();

	/** ���������� ��������� ������� */
	public SystemStateCollection calculateSystemState(SystemState initialState) {
		SystemStateCollection result = new SystemStateCollection();
		SystemStateCollection subsets = initialState.findSubset(systemStateBeforeTransition);
		if (transition.type == TransitionType.DeltaAttributes) {
			// �������� ���������� ��������
			for (int subsetId = 0; subsetId < subsets.size(); subsetId++) {
				SystemState subset = subsets.get(subsetId);
				for (int objectId = 0; objectId < systemStateAfterTransition.systemObjects.size(); objectId++) {
					SystemObject object = systemStateAfterTransition.systemObjects.get(objectId);
					for (int foundId = 0; foundId < subset.systemObjects.size(); foundId++) {
						SystemObject found = subset.systemObjects.get(foundId);
						if (object.matches(found)) {
							// �������� ����������
							for (int deltaId = 0; deltaId < object.attributes.size(); deltaId++) {
								Attribute delta = object.attributes.get(deltaId);
								for (int attributeId = 0; attributeId < found.attributes.size(); attributeId++) {
									Attribute attribute = found.attributes.get(attributeId);
									if (delta.matches(attribute)) {
										attribute.value = String.valueOf(Integer.parseInt(attribute.value, 10)
												+ Integer.parseInt(delta.value, 10));
										break;
									}
								}
							}
							break;
						}
					}
				}
				subset.shouldBeAdded = true;
			}
		} else if (transition.type == TransitionType.ChangeAttributes) {
			// ������ ����������
			for (int stateId = 0; stateId < subsets.size(); stateId++) {
				SystemState state = subsets.get(stateId);
				// �������� �������
				boolean conditionIsMet = true;
				for (int objectId = 0; objectId < state.systemObjects.size(); objectId++) {
					SystemObject object = state.systemObjects.get(objectId);
					for (int attributeId = 0; attributeId < object.attributes.size(); attributeId++) {
						Attribute attribute = object.attributes.get(attributeId);
						for (int secondId = 0; secondId < state.systemObjects.size(); secondId++) {
							SystemObject second = state.systemObjects.get(secondId);
							Attribute found = second.attributes.find(attribute.name);
							if (found != null) {
								if (!attribute.haveEqualValues(found)) {
									conditionIsMet = false;
								}
							}
						}
					}
				}
				// ���������� ��������
				if (conditionIsMet) {
					for (SystemObject elementObject : systemStateAfterTransition.systemObjects) {
						for (SystemObject realObject : state.systemObjects) {
							if (elementObject.name.equals(realObject.name)) {
								// �������� ���������
								AttributeCollection attributesToRemove = new AttributeCollection();
								for (Attribute attribute : realObject.attributes) {
									Attribute found = elementObject.attributes.find(attribute.name);
									if (found != null) {
										attributesToRemove.add(found);
									}
								}
								for (Attribute attribute : attributesToRemove) {
									realObject.attributes.remove(attribute);
								}
								// ���������� ���������
								for (Attribute attribute : elementObject.attributes) {
									Attribute found = realObject.attributes.find(attribute.name);
									if (found != null) {
										realObject.attributes.add(attribute);
									}
								}
							}
						}
					}
					state.shouldBeAdded = true;
				}
			}
		}
		// ������������ ����� ���������
		for (SystemState subset : subsets) {
			SystemState state = new SystemState();
			for (SystemObject object : initialState.systemObjects) {
				boolean wasAdded = false;
				for (SystemObject found : subset.systemObjects) {
					if (object.id == found.id) {
						state.systemObjects.add(found);
						wasAdded = true;
						break;
					}
				}
				if (!wasAdded) {
					state.systemObjects.add(object);
				}
			}
			if (subset.shouldBeAdded) {
				result.add(state);
			}
		}
		return result;
	}

	/** ���������� �������� ��������� ��������� � ��������� ������� */
	public void applyStateChangeToState(SystemState systemState) {
	}

	@Override
	protected boolean allFieldsAreEqual(Element value) {
		return systemStateAfterTransition.operator_equality(value.systemStateAfterTransition)
				&& systemStateBeforeTransition.operator_equality(value.systemStateBeforeTransition)
				&& transition.operator_equality(value.transition);
	}

	/** ����������� */
	public Element() {
	}

	/** ����������� � ����������� */
	public Element(SystemState systeStateBeforeTransition, SystemState systemStateAfterTransition,
			Transition transition) {
		this.systemStateBeforeTransition = systeStateBeforeTransition;
		this.systemStateAfterTransition = systemStateAfterTransition;
		this.transition = transition;
	}
}
