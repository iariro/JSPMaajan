package kumagai.Maajan.logic;

/**
 * 打牌メッセージ。
 * @author kumagai
 */
public class DahaiMessage
	extends Message
{
	public final int index;

	/**
	 * 打牌メッセージを構築。
	 * @param index 打牌する牌のインデックス
	 */
	public DahaiMessage(int index)
	{
		super(MessageType.Dahai);

		this.index = index;
	}
}
