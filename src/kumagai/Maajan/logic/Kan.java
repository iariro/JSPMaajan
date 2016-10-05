package kumagai.Maajan.logic;

/**
 * 槓の情報。
 */
public class Kan
	extends Naki
{
	/**
	 * 槓の情報を構築。
	 * @param direction 鳴く牌を捨てたプレイヤーの方向
	 * @param mentsuType 槓の区別
	 * @param pai 槓する牌
	 */
	public Kan(int direction, int mentsuType, Pai pai)
	{
		super(mentsuType, direction);

		for (int i=0 ; i<4 ; i++)
		{
			add(pai);
		}
	}
}
