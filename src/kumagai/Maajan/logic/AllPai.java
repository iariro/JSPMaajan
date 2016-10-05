package kumagai.Maajan.logic;

import java.util.*;

/**
 * 全牌のコレクション。
 * @author kumagai
 */
public class AllPai
	extends ArrayList<Pai>
{
	static public final int totalPaiNum = (9 * 3 + 4 + 3) * 4;
	static private Random random = new Random();

	private int tsumoIndex = 14;
	private int rinshanIndex = 0;

	/**
	 * 全牌のコレクションを構築。
	 * @param randomize true=ランダム化＝洗牌する／false=しない
	 */
	public AllPai(boolean randomize)
	{
		if (randomize)
		{
			// ランダム化＝洗牌する。

			PaiAndRandomValue [] randomValue =
				new PaiAndRandomValue [totalPaiNum];

			int tsumoIndex = 0;

			for (int pai = 0; pai <= 33; pai++)
			{
				for (int i = 0; i < 4; i++)
				{
					randomValue[tsumoIndex] =
						new PaiAndRandomValue(pai, random.nextDouble());

					tsumoIndex++;
				}
			}

			Arrays.sort(randomValue, new RandomValueComparator());

			for (int i = 0; i < randomValue.length; i++)
			{
				add(new Pai(randomValue[i].pai));
			}
		}
		else
		{
			// ランダム化＝洗牌しない。

			for (int pai = 0; pai <= 33; pai++)
			{
				for (int i = 0; i < 4; i++)
				{
					add(new Pai(pai));
				}
			}
		}
	}

	/**
	 * デバッグ用に積み込みを行い全牌のコレクションを構築する。
	 * @param tsumikomi 積み込む牌
	 */
	public AllPai(int [] tsumikomi)
		throws Exception
	{
		this(true);

		int start = 10;

		for (int i=0;i<tsumikomi.length;i++)
		{
			boolean find = false;

			for (int j=size()-1 ; j>=0 && !find ; j--)
			{
				if (!(j >= start && j < start + i))
				{
					// 確定部分以外。交換可能な牌。

					if (tsumikomi[i] == get(j).kind)
					{
						// 欲しい牌。

						Pai p = get(start + i);
						set(start + i, get(j));
						set(j, p);

						find = true;
					}
				}
			}

			if (! find)
			{
				// 欲しい牌が見つからなかった。

				throw
					new Exception(
						String.format(
							"%d:%sはもうありません。積込牌を確認してください。",
							i,
							tsumikomi[i]));
			}
		}
	}

	/**
	 * ドラ牌を取得。
	 * @param ura true=裏ドラあり
	 * @return ドラ牌
	 */
	public ArrayList<Pai> getDora(boolean ura)
	{
		ArrayList<Pai> dora = new ArrayList<Pai>();

		for (int i = 0; i < rinshanIndex + 1 ; i++)
		{
			dora.add(get(8 - i * 2));

			if (ura)
			{
				// 裏ドラあり。

				dora.add(get(9 - i * 2));
			}
		}

		return dora;
	}

	/**
	 * 残りツモ牌数を取得。
	 * @return 残りツモ牌数
	 */
	public int getNokori()
	{
		return size() - tsumoIndex;
	}

	/**
	 * １枚ツモ。
	 * @return ツモ牌
	 */
	public Pai getTsumohai()
	{
		Pai pai = get(tsumoIndex);

		tsumoIndex++;

		return pai;
	}

	/**
	 * 嶺上牌を取得。
	 * @return 嶺上牌
	 */
	public Pai getRinshanpai()
	{
		Pai pai = get(14 - rinshanIndex - 1);

		rinshanIndex++;

		return pai;
	}

	/**
	 * 牌とランダム値のペアデータ同士の比較を行うオブジェクト。
	 * @author kumagai
	 */
	class RandomValueComparator
		implements Comparator<PaiAndRandomValue>
	{
		/**
		 * 牌とランダム値のペアデータ同士の比較を行う。
		 */
		public int compare
			(PaiAndRandomValue value1, PaiAndRandomValue value2)
		{
			if (value1.randomValue > value2.randomValue)
			{
				// 大きい。

				return 1;
			}
			else if (value1.randomValue < value2.randomValue)
			{
				// 小さい。

				return -1;
			}
			else
			{
				// 同じ。

				return 0;
			}
		}
	}
}
