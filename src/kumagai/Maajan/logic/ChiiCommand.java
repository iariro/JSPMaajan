package kumagai.Maajan.logic;

import java.util.*;

/**
 * チーの情報。
 * @author kumagai
 */
public class ChiiCommand
	extends Command
{
	public final Pai pai;
	public int direction;
	public int index1;
	public int index2;

	/**
	 * 牌の数字の上下関係からインデックスを逆算しチー情報を構築。
	 * プレイヤーに指示を受けない自動判定時に使用。
	 * @param pai チーする牌
	 * @param direction チーする牌を捨てたプレイヤーの方向
	 * @param tehai 鳴くプレイヤーの手牌
	 * @param relation1 1,2,-1,-2のどれか
	 * @param relation2 1,2,-1,-2のどれか
	 */
	public ChiiCommand(Pai pai, int direction, ArrayList<Pai> tehai,
		int relation1, int relation2)
	{
		super(CommandType.Chii);

		// 複数あった場合ははじめに見つけた方が対象となる。
		this.pai = pai;
		this.direction = direction;
		this.index1 = -1;
		this.index2 = -1;

		for (int i=0 ; i<tehai.size() ; i++)
		{
			if (index1 < 0 && tehai.get(i).kind == pai.kind + relation1)
			{
				// まだ見つかっておらず、かつ関係は合致する。

				this.index1 = i;
			}

			if (index2 < 0 && tehai.get(i).kind == pai.kind + relation2)
			{
				// まだ見つかっておらず、かつ関係は合致する。

				this.index2 = i;
			}
		}
	}

	/**
	 * 手牌内のインデックスの形でチー情報を構築。プレイヤーの直接指定時に使用。
	 * @param pai チーする牌
	 * @param direction チーする牌を捨てたプレイヤーの方向
	 * @param index1 手牌内のインデックス１
	 * @param index2 手牌内のインデックス２
	 * @throws Exception
	 */
	public ChiiCommand(Pai pai, int direction, int index1, int index2) throws Exception
	{
		super(CommandType.Chii);

		this.pai = pai;
		this.direction = direction;

		if (index1 < index2)
		{
			// 小・大の順。

			this.index1 = index1;
			this.index2 = index2;
		}
		else if (index1 > index2)
		{
			// 大・小の順。

			this.index1 = index2;
			this.index2 = index1;
		}
		else
		{
			// 同じ。

			throw new Exception("不正なチー");
		}
	}

	/**
	 * 食い替えとなる牌を取得。
	 * @param tehai 鳴くプレイヤーの手牌
	 * @return 食い替え牌
	 * @throws Exception
	 */
	public ArrayList<Integer> getKuikaePai(ArrayList<Pai> tehai)
		throws Exception
	{
		ArrayList<Integer> kuikaePai = new ArrayList<Integer>();

		if (pai.kind + 1 == tehai.get(index1).kind &&
			tehai.get(index1).kind + 1 == tehai.get(index2).kind)
		{
			// １＋２３ → ４

			if (tehai.get(index2).getNumber() < 9)
			{
				// 先がある。

				kuikaePai.add(tehai.get(index2).kind + 1);
			}
		}
		else if (pai.kind - 1 == tehai.get(index2).kind &&
			tehai.get(index2).kind - 1 == tehai.get(index1).kind)
		{
			// ２３＋４ → １

			if (tehai.get(index1).getNumber() > 1)
			{
				// 先がある。

				kuikaePai.add(tehai.get(index2).kind - 1);
			}
		}

		return kuikaePai;
	}
}
