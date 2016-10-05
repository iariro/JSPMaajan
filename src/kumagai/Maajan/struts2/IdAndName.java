package kumagai.Maajan.struts2;

/**
 * IDと文字列の対。
 * @author kumagai
 */
public class IdAndName
{
	public int id;
	public String name;

	/**
	 * 指定の値をメンバーに割り当て。
	 * @param id ID
	 * @param name 文字列
	 */
	public IdAndName(int id, String name)
	{
		this.id = id;
		this.name = name;
	}
}
