package kumagai.Maajan.logic;

import java.util.*;

/**
 * 幺九牌コレクション。
 */
public class YaochuuCollection
	extends ArrayList<Integer>
{
	/**
	 * 幺九牌のみのコレクションを構築。
	 * @param tehai 手牌
	 * @param tsumo ツモ牌
	 */
	public YaochuuCollection(ArrayList<Pai> tehai, Pai tsumo)
	{
		ArrayList<Pai> tehai2 = new ArrayList<Pai>(tehai);
		tehai2.add(tsumo);

		for (int i = 0; i < tehai2.size() ; i++)
		{
			if (!contains(tehai2.get(i).kind))
			{
				// まだ含まれていない。

				boolean add = false;

				if (tehai2.get(i).isShuupai())
				{
					// 数牌。

					if (tehai2.get(i).isRaotoupai())
					{
						// 老頭牌。

						add = true;
					}
				}
				else
				{
					// 字牌。

					add = true;
				}

				if (add)
				{
					// 追加。

					super.add(tehai2.get(i).kind);
				}
			}
		}
	}
}
