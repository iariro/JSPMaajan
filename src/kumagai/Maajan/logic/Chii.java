package kumagai.Maajan.logic;

/**
 * チーの情報。
 * @author kumagai
 */
public class Chii
	extends Naki
{
	/**
	 * チーの情報を構築する。
	 * @param direction チーする牌を捨てたプレイヤーの方向
	 * @param pai1 牌１
	 * @param pai2 牌２
	 * @param pai3 牌３
	 */
	public Chii(int direction, Pai pai1, Pai pai2, Pai pai3)
	{
		super(MentsuType.Chii.ordinal(), direction);

		add(pai1);
		add(pai2);
		add(pai3);
	}

	/**
	 * 先頭の牌を取得。
	 * @return 先頭の牌
	 */
	public Pai getMin()
	{
		if (get(0).kind < get(1).kind && get(0).kind < get(2).kind)
		{
			// 123

			return get(0);
		}
		else if (get(1).kind < get(0).kind && get(1).kind < get(2).kind)
		{
			// 213

			return get(1);
		}
		else
		{
			// ないはず。

			return null;
		}
	}
}
