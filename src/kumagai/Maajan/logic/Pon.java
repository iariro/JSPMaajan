package kumagai.Maajan.logic;

/**
 * ポンの情報。
 */
public class Pon
	extends Naki
{
	/**
	 * ポンの情報を構築。
	 * @param direction 鳴く牌を捨てたプレイヤーの方向
	 * @param pai ポンする牌
	 */
	public Pon(int direction, Pai pai)
	{
		super(MentsuType.Pon.ordinal(), direction);

		for (int i=0 ; i<3 ; i++)
		{
			add(pai);
		}
	}
}
