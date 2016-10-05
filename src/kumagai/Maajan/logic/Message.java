package kumagai.Maajan.logic;

/**
 * プレイヤー操作メッセージ。
 */
public class Message
{
	public final MessageType type;

	/**
	 * メッセージタイプを割り当てオブジェクトを構築。
	 * @param type メッセージタイプ
	 */
	public Message(MessageType type)
	{
		this.type = type;
	}
}
