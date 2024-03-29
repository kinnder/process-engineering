package planning.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class LinkTemplate implements Cloneable {

	private String id1;

	private String id2;

	private String name;

	public LinkTemplate() {
		this.name = "linkTemplate-" + UUID.randomUUID().toString();
		this.id1 = "";
		this.id2 = "";
	}

	public LinkTemplate(String name, String id1, String id2) {
		this.name = name;
		this.id1 = id1;
		this.id2 = id2;
	}

	public String getId1() {
		return id1;
	}

	public void setId1(String id1) {
		this.id1 = id1;
	}

	public String getId2() {
		return id2;
	}

	public void setId2(String id2) {
		this.id2 = id2;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (obj instanceof LinkTemplate) {
			final LinkTemplate linkTemplate = (LinkTemplate) obj;
			return Objects.equals(name, linkTemplate.name) && Objects.equals(id1, linkTemplate.id1)
					&& Objects.equals(id2, linkTemplate.id2);
		}
		return false;
	}

	public boolean matches(Link link, IdsMatching matching) {
		return link.getName().equals(name) && Objects.equals(matching.get(id1), link.getId1())
				&& Objects.equals(matching.get(id2), link.getId2());
	}

	@Override
	public LinkTemplate clone() throws CloneNotSupportedException {
		final LinkTemplate clone = (LinkTemplate) super.clone();
		clone.name = name;
		clone.id1 = id1;
		clone.id2 = id2;
		return clone;
	}

	public Set<String> getIds() {
		final Set<String> ids = new HashSet<>();
		if (id1 != null) {
			ids.add(id1);
		}
		if (id2 != null) {
			ids.add(id2);
		}
		return ids;
	}
}
