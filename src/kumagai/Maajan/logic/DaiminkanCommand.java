package kumagai.Maajan.logic;

/**
 * 大明槓コマンド。
 * @author kumagai
 */
public class DaiminkanCommand
	extends Command
{
	public final Pai pai;
	public final int direction;

	/**
	 * 大明槓コマンドを構築する。
	 * @param pai 大明槓する牌
	 * @param direction 大明槓する牌を捨てたプレイヤーの方向
	 */
	public DaiminkanCommand(Pai pai, int direction)
	{
		super(CommandType.Daiminkan);

		this.pai = pai;
		this.direction = direction;
	}
}
