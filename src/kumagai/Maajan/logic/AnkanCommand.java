package kumagai.Maajan.logic;

/**
 * 暗槓コマンド。
 * @author kumagai
 */
public class AnkanCommand
	extends Command
{
	public final Pai pai;

	/**
	 * 暗槓コマンドを構築する。
	 * @param pai 暗槓する牌
	 */
	public AnkanCommand(Pai pai)
	{
		super(CommandType.Ankan);

		this.pai = pai;
	}
}
