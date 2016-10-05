package kumagai.Maajan.logic;

import java.util.*;

/**
 * 国士無双判定。
 */
public class KokushimusouCheck
{
	/**
	 * 国士無双判定。
	 * @param tehai 手牌
	 * @return 待ち牌
	 */
	static public ArrayList<Integer> getMachi(ArrayList<Pai> tehai)
	{
		ArrayList<Integer> yaochuu1 = new ArrayList<Integer>();
		yaochuu1.add(PaiKind.一萬.ordinal());
		yaochuu1.add(PaiKind.九萬.ordinal());
		yaochuu1.add(PaiKind.一筒.ordinal());
		yaochuu1.add(PaiKind.九筒.ordinal());
		yaochuu1.add(PaiKind.一索.ordinal());
		yaochuu1.add(PaiKind.九索.ordinal());
		yaochuu1.add(PaiKind.東.ordinal());
		yaochuu1.add(PaiKind.南.ordinal());
		yaochuu1.add(PaiKind.西.ordinal());
		yaochuu1.add(PaiKind.北.ordinal());
		yaochuu1.add(PaiKind.白.ordinal());
		yaochuu1.add(PaiKind.發.ordinal());
		yaochuu1.add(PaiKind.中.ordinal());

		ArrayList<Integer> yaochuu2 = new ArrayList<Integer>(yaochuu1);

		for (int i=0 ; i<tehai.size() ; i++)
		{
			if (yaochuu1.contains(tehai.get(i).kind))
			{
				// 幺九牌である。

				yaochuu2.remove(new Integer(tehai.get(i).kind));
			}
			else
			{
				// 幺九牌ではない。

				return new ArrayList<Integer>();
			}
		}

		if (yaochuu2.size() == 1)
		{
			// あと１個。

			return yaochuu2;
		}
		else if (yaochuu2.size() == 0)
		{
			// 全部ある。

			return yaochuu1;
		}
		else
		{
			// まだ達していない。

			return new ArrayList<Integer>();
		}
	}
}
