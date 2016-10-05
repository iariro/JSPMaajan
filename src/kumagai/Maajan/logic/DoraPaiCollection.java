package kumagai.Maajan.logic;

import java.util.*;

/**
 * ドラ牌コレクション。
 * @author kumagai
 */
public class DoraPaiCollection
	extends ArrayList<Pai>
{
	/**
	 * ドラ表示牌からドラ牌コレクションを構築する。
	 * @param dora ドラ表示牌
	 */
	public DoraPaiCollection(List<Pai> dora)
	{
		for (int i = 0; i < dora.size(); i++)
		{
			add(dora.get(i).getDora());
		}
	}
}
