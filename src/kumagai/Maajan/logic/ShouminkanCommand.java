package kumagai.Maajan.logic;

/**
 * 小明槓コマンド。
 */
public class ShouminkanCommand
	extends Command
{
	public final Pai pai;

	/**
	 * 小明槓コマンドを構築する。
	 * @param pai 小明槓する牌
	 */
	public ShouminkanCommand(Pai pai)
	{
		super(CommandType.Shouminkan);

		this.pai = pai;
	}
}
