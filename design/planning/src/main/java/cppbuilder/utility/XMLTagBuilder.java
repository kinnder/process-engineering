package cppbuilder.utility;

/**
 * XML ��� <br>
 * ��������������� ������� ��� ������ � XML ������
 */
public class XMLTagBuilder {

	/** ��� ���� */
	private String nodeName;

	/** ����������� */
	public XMLTagBuilder(String nodeName) {
		this.nodeName = nodeName;
	}

	/** ��������� ��� */
	public String startTag() {
		return "<" + nodeName + ">";
	}

	/** �������� ��� */
	public String endTag() {
		return "</" + nodeName + ">";
	}

	/** ����� �������� */
	public String print(String value) {
		return startTag() + value + endTag();
	}
}
