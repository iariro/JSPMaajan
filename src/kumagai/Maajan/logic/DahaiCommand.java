package kumagai.Maajan.logic;

/**
 * 打牌コマンド。
 * @author kumagai
 */
public class DahaiCommand
	extends Command
{
	public final int index;

	/**
	 * 打牌コマンドを構築する。
	 * @param index 手牌中のインデックス
	 */
	public DahaiCommand(int index)
	{
		super(CommandType.Dahai);

		this.index = index;
	}
}
