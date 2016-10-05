package kumagai.Maajan.logic;

import java.util.*;

/**
 * 待ち要素情報のコレクション。
 */
public class MachiElementCollection
	extends ArrayList<MachiElement>
{
	/**
	 * 待ち要素情報を追加。重複追加チェック付き。
	 * @param machi 待ち要素情報
	 */
	public void addElement(MachiElement machi)
	{
		boolean find = false;

		for (int i = 0; i < size() && !find;i++)
		{
			if (get(i).type == machi.type && get(i).size() == machi.size())
			{
				// 待ちのタイプ・個数ともに同じである。

				boolean equal = true;

				for (int j = 0; j < get(i).size() && equal; j++)
				{
					equal &= get(i).get(j) == machi.get(j);
				}

				find = equal;
			}
		}

		if (! find)
		{
			// 一致するものはなかった。

			super.add(machi);
		}
	}

	/**
	 * 内容比較。
	 * @param machiElementCollection 比較対象
	 * @return true=一致／false=不一致
	 */
	public boolean equals2(MachiElementCollection machiElementCollection)
	{
		boolean equal = true;

		if (size() == machiElementCollection.size())
		{
			// 待ち要素数は同じ。

			for (int i = 0; i < size(); i++)
			{
				for (int j = 0; j < machiElementCollection.size() && equal; j++)
				{
					if (get(i).type == machiElementCollection.get(j).type)
					{
						// 待ちのタイプは同じ。

						if (get(i).size() == machiElementCollection.get(j).size())
						{
							// 要素内の牌の数は同じ。

							for (int k = 0; k < get(i).size() && equal; k++)
							{
								equal = get(i).get(k) == machiElementCollection.get(j).get(k);
							}
						}
					}
				}
			}
		}
		else
		{
			// 待ち要素数は異なる。

			equal = false;
		}

		return equal;
	}

	/**
	 * 内容文字列化。
	 * @return 内容文字列
	 */
	public String toString()
	{
		StringBuffer builder = new StringBuffer();

		for (int i = 0; i < size(); i++)
		{
			if (i > 0)
			{
				// 区切りを入れる箇所である。

				builder.append("／");
			}

			builder.append(get(i).type + "：");

			for (int j = 0; j < get(i).size(); j++)
			{
				if (j > 0)
				{
					// 区切りを入れる箇所である。

					builder.append(" ");
				}

				builder.append(get(i).get(j));
			}
		}

		return builder.toString();
	}
}
