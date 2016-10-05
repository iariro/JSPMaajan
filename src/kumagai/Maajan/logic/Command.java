package kumagai.Maajan.logic;

/**
 * コマンド情報。
 * @author kumagai
 */
public class Command
{
	public final CommandType type;

	/**
	 * コマンドを構築。
	 * @param type コマンドタイプ
	 */
	public Command(CommandType type)
	{
		this.type = type;
	}
}
