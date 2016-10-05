package kumagai.Maajan.logic;

import java.util.*;

/**
 * 待ちの要素情報。
 */
public class MachiElement
	extends ArrayList<Integer>
{
	public final MachiType type;
	public final ArrayList<Pai> nokori;
	public final int [] use;
	public boolean over1;
	public boolean over2;

	/**
	 * 待ちの要素情報を構築。
	 * @param type 鳴きタイプ
	 * @param use 確定面子配列
	 * @param nokori 残り牌
	 * @param pai 待ち牌配列
	 * @param paiCount 牌カウント
	 */
	public MachiElement(MachiType type, int [] use, ArrayList<Pai> nokori,
		int pai, HashMap<Integer, Integer> paiCount)
	{
		this.type = type;
		this.nokori = nokori;
		this.use = new int [use.length];

		add(pai);

		if (paiCount.containsKey(pai))
		{
			// 含まれている。

			over1 = paiCount.get(pai) >= 4;
		}

		for (int i=0;i<use.length;i++)
		{
			this.use[i] = use[i];
		}
	}

	/**
	 * オブジェクトを構築。
	 * @param type 待ちの種別
	 * @param use 牌使用フラグ
	 * @param nokori 残り牌
	 * @param pai1 牌１
	 * @param pai2 牌２
	 * @param paiCount 牌カウント
	 */
	public MachiElement(MachiType type, int [] use, ArrayList<Pai> nokori,
		int pai1, int pai2, HashMap<Integer, Integer> paiCount)
	{
		this.type = type;
		this.nokori = nokori;
		this.use = new int [use.length];

		add(pai1);
		add(pai2);

		if (paiCount.containsKey(pai1))
		{
			// 含まれている。

			over1 = paiCount.get(pai1) >= 4;
		}

		if (paiCount.containsKey(pai2))
		{
			// 含まれている。

			over2 = paiCount.get(pai2) >= 4;
		}

		for (int i=0;i<use.length;i++)
		{
			this.use[i] = use[i];
		}
	}

	/**
	 * 待ちの要素情報を構築。
	 * @return 文字列化した内容
	 */
	public String toString()
	{
		StringBuffer builder = new StringBuffer();

		for (int i=0 ; i<size() ; i++)
		{
			if (i > 0)
			{
				// ２個目以降。

				builder.append("，");
			}

			builder.append(get(i));
		}

		builder.append("の");
		builder.append(type);
		builder.append("待ち");

		return builder.toString();
	}
}
