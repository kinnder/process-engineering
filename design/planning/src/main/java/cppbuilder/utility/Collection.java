package cppbuilder.utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

/** ��������� */
public class Collection<Type extends LogicOperators<Type>> extends LogicOperators<Collection<Type>>
		implements Iterable<Type> {

	/** ������ */
	protected List<Type> items;

	/** XML ��� ��������� */
	protected String xmlName;

	/** ����������� */
	public Collection() {
		items = new ArrayList<Type>();
	}

	/** �������� ������� */
	public void add(Type item) {
		items.add(item);
	}

	/** ������ */
	public int size() {
		return items.size();
	}

	/** �������� ������� */
	public Type get(int index) {
		return items.get(index);
	}

	/** �������� ��������� ������� */
	public Type back() {
		return items.get(items.size() - 1);
	}

	@Override
	public String toXMLString() {
		XMLTagBuilder collection = new XMLTagBuilder("collection");
		String result = collection.startTag();
		for (Type item : items) {
			result += item.toXMLString();
		}
		result += collection.endTag();
		return result;
	}

	@Override
	public Iterator<Type> iterator() {
		return items.iterator();
	}

	/** �������� */
	public void clear() {
		items.clear();
	}

	/** ���������� */
	public void shuffle() {
		Collections.shuffle(items);
	}

	/** ����� */
	public Type find(Predicate<Type> template) {
		return items.stream().filter(template).findAny().orElse(null);
	}

	@Override
	protected boolean allFieldsAreEqual(Collection<Type> collection) {
		if (items.size() != collection.items.size()) {
			return false;
		}
		for (Type item : items) {
			if (collection.find(collectionItem -> collectionItem.equal(item)) == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean haveMatchingFields(Collection<Type> collection) {
		if (items.size() > collection.items.size()) {
			return false;
		}
		for (Type item : items) {
			if (collection.find(collectionItem -> collectionItem.matches(item)) == null) {
				return false;
			}
		}
		return true;
	}

	@Override
	protected boolean haveSubset(Collection<Type> collection) {
		for (Type collectionItem : collection.items) {
			if (find(item -> item.includes(collectionItem)) == null) {
				return false;
			}
		}
		return true;
	}

	/** ������� */
	public void remove(Type item) {
		items.remove(item);
	}

	/** �������� - ������� ���������� */
	public boolean exists(Type item) {
		return items.indexOf(item) != -1;
	}
}
