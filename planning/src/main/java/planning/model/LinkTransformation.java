package planning.model;

public class LinkTransformation extends Transformation {

	private String linkName;

	private String id2New;

	private String id2Old;

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName2) {
		this.linkName = linkName2;
	}

	public String getId1() {
		return getId();
	}

	public String getId2New() {
		return id2New;
	}

	public void setId2New(String id2New) {
		this.id2New = id2New;
	}

	public String getId2Old() {
		return id2Old;
	}

	public void setId2Old(String id2Old) {
		this.id2Old = id2Old;
	}

	public LinkTransformation() {
		super("object-id");
		this.linkName = "link-name";
		this.id2Old = "id-2-old";
		this.id2New = "id-2-new";
	}

	public LinkTransformation(String id1, String linkName, String id2Old, String id2New) {
		super(id1);
		this.linkName = linkName;
		this.id2Old = id2Old;
		this.id2New = id2New;
	}

	@Override
	public void applyTo(SystemVariant systemVariant) {
		final SystemObject object = systemVariant.getObjectByIdMatch(getId());
		final String id1Actual = object.getId();
		final String id2NewActual = systemVariant.getObjectIdByIdMatch(id2New);
		final String id2OldActual = systemVariant.getObjectIdByIdMatch(id2Old);

		final Link link = systemVariant.getSystem().getLink(linkName, id1Actual, id2OldActual);
		link.setId2(id2NewActual);
	}

	@Override
	public String toString() {
		return "link-transformation";
	}
}
