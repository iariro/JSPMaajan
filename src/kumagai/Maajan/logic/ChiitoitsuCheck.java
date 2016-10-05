package kumagai.Maajan.logic;

import java.util.*;

/**
 * 七対子の待ち判定。
 * @author kumagai
 */
public class ChiitoitsuCheck
{
	/**
	 * 七対子の待ち判定。
	 * @param tehai 手牌
	 * @return 待ち牌
	 */
	static public ArrayList<Integer> getMachi(List<Pai> tehai)
	{
		ArrayList<Integer> ret = new ArrayList<Integer>();

		HashMap<Integer, Integer> toitsu = new HashMap<Integer, Integer>();

		for (int i=0 ; i<tehai.size() ; i++)
		{
			if (toitsu.containsKey(tehai.get(i).kind))
			{
				// すでにある牌。

				toitsu.put
					(tehai.get(i).kind, toitsu.get(tehai.get(i).kind) + 1);
			}
			else
			{
				// 初めての牌。

				toitsu.put(tehai.get(i).kind, 1);
			}
		}

		if (toitsu.size() == 7)
		{
			int count = 0;

			for (Integer pai : toitsu.keySet())
			{
				if (toitsu.get(pai) == 2)
				{
					// 対子。

					count++;
				}
			}

			if (count == 6)
			{
				// 対子は６個。

				for (Integer pai : toitsu.keySet())
				{
					if (toitsu.get(pai) == 1)
					{
						// 残りの１個。

						ret.add(pai);
					}
				}
			}
		}

		return ret;
	}
}
