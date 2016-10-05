package kumagai.Maajan.logic;

/**
 * 和がりコマンド。
 * @author kumagai
 */
public class AgariCommand
	extends Command
{
	public final int direction;

	/**
	 * 和がりコマンドを構築する。
	 * @param direction 当たり牌をツモまたは捨てたプレイヤーの方向
	 */
	public AgariCommand(int direction)
	{
		super(CommandType.Agari);

		this.direction = direction;
	}
}
