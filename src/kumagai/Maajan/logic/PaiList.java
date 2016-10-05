package kumagai.Maajan.logic;

import java.util.*;

/**
 * PaiKindの配列から構築可能な牌リスト。テスト用。
 */
public class PaiList
	extends ArrayList<Pai>
{
	/**
	 * PaiKindの配列から牌リストを構築する。
	 * @param paiArray 牌種類配列
	 */
	public PaiList(PaiKind [] paiArray)
	{
		for (int i = 0; i < paiArray.length; i++)
		{
			add(new Pai(paiArray[i].ordinal()));
		}
	}
}
