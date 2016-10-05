package kumagai.Maajan.logic;

/**
 * ポンコマンド。
 */
public class PonCommand
	extends Command
{
	public final Pai pai;
	public final int direction;

	/**
	 * ポンコマンドを構築する。
	 * @param pai ポンする牌
	 * @param direction ポンする牌を捨てたプレイヤーの方向
	 */
	public PonCommand(Pai pai, int direction)
	{
		super(CommandType.Pon);

		this.pai = pai;
		this.direction = direction;
	}
}
