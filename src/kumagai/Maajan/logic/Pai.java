package kumagai.Maajan.logic;

/**
 * 牌オブジェクト。
 * @author kumagai
 */
public class Pai
{
	public final int kind;
	public final boolean akadora;

	/**
	 * 数牌であるかを取得。
	 * @return true=数牌である／false=数牌ではない
	 */
	public boolean isShuupai()
	{
		return kind <= 26;
	}

	/**
	 * 字牌であるかを取得。
	 * @return true=字牌である／false=字牌ではない
	 */
	public boolean isJihai()
	{
		return kind >= 27;
	}

	/**
	 * 中張牌であるかを取得。
	 * @return true=中張牌である／false=中張牌ではない
	 * @throws Exception
	 */
	public boolean isChunchanpai()
		throws Exception
	{
		if (isShuupai())
		{
			// 数牌である。

			return getNumber() >= 2 && getNumber() <= 8;
		}
		else
		{
			// 数牌ではない。

			return false;
		}
	}

	/**
	 * 数牌１・７であるかを取得。
	 * @return true=数牌１・７である／false=数牌１・７ではない
	 * @throws Exception
	 */
	public boolean isChantaShuntsuAtama()
		throws Exception
	{
		if (isShuupai())
		{
			// 数牌である。

			return getNumber() == 1 || getNumber() == 7;
		}
		else
		{
			// 数牌ではない。

			throw new Exception("数牌ではありません。");
		}
	}

	/**
	 * 幺九牌であるかを取得。
	 * @return true=幺九牌である／false=幺九牌ではない
	 */
	public boolean isYaochuupai()
	{
		if (isShuupai())
		{
			// 数牌である。

			return
				kind ==  0 ||
				kind ==  9 ||
				kind == 18 ||
				kind ==  8 ||
				kind == 17 ||
				kind == 26;
		}
		else
		{
			// 字牌である。

			return true;
		}
	}

	/**
	 * 風牌であるかを取得。
	 * @return true=風牌である／false=風牌ではない
	 */
	public boolean isKazehai()
	{
		return kind >= 27 && kind <= 30;
	}

	/**
	 * 三元牌であるかを取得。
	 * @return true=三元牌である／false=三元牌ではない
	 */
	public boolean isSangenpai()
	{
		return kind >= 31;
	}

	/**
	 * 老頭牌であるかを取得。
	 * @return true=老頭牌である／false=老頭牌ではない
	 */
	public boolean isRaotoupai()
	{
		if (isShuupai())
		{
			// 数牌である。

			return
				kind ==  0 ||
				kind ==  9 ||
				kind == 18 ||
				kind ==  8 ||
				kind == 17 ||
				kind == 26;
		}
		else
		{
			// 字牌である。

			return false;
		}
	}

	/**
	 * 数牌の数を１～９で取得。
	 * @return 数牌の数
	 * @throws Exception
	 */
	public int getNumber()
		throws Exception
	{
		if (isShuupai())
		{
			// 数牌である。

			return kind % 9 + 1;
		}
		else
		{
			// 字牌である。

			throw new Exception("数牌ではありません。");
		}
	}

	/**
	 * 数牌の１を取得。
	 * @return 数牌の１
	 * @throws Exception
	 */
	public int getIchi()
		throws Exception
	{
		if (isShuupai())
		{
			// 数牌である。

			return kind - kind % 9;
		}
		else
		{
			// 字牌である。

			throw new Exception("数牌ではありません。");
		}
	}

	/**
	 * 牌情報を構築。
	 * @param kind 牌種別
	 */
	public Pai(int kind)
	{
		this.kind = kind;
		this.akadora = false;
	}

	/**
	 * 牌情報を構築。
	 * @param kind 牌種別
	 */
	public Pai(PaiKind kind)
	{
		this.kind = kind.ordinal();
		this.akadora = false;
	}

	/**
	 * 次の色の同じ数字かを判定。
	 * @param pai 牌
	 * @return true=次の色の同じ数字
	 */
	public boolean isSameNumberInNextColor(Pai pai)
	{
		if (kind <= 17)
		{
			// 一萬～九筒である。

			return kind + 9 == pai.kind;
		}
		else
		{
			// 一索以降である。

			return false;
		}
	}

	/**
	 * 順子であるかを判定。
	 * @param pai1 牌１
	 * @param pai2 牌２
	 * @return true=順子である／false=順子ではない
	 */
	public boolean isShuntsu(Pai pai1, Pai pai2)
	{
		return kind + 1 == pai1.kind && pai1.kind + 1 == pai2.kind;
	}

	/**
	 * 同じ色の数牌かを判定。
	 * @param pai 比較対象
	 * @return true=同じ色／false=同じ色ではない
	 */
	public boolean isSameColor(Pai pai)
		throws Exception
	{
		if (isShuupai())
		{
			// 数牌である。

			return (int)kind / 9 == (int)pai.kind / 9;
		}
		else
		{
			// 数牌ではない。

			throw new Exception("数牌ではありません");
		}
	}

	/**
	 * 比較。
	 * @param obj 比較対象
	 * @return 1/0/-1/
	 */
	public int compareTo(Object obj)
	{
		Pai pai = (Pai)obj;

		if (kind > pai.kind)
		{
			// 大きい。

			return 1;
		}
		else if (kind < pai.kind)
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

	/**
	 * 内容を比較。
	 * @param pai 比較対象
	 * @return true=同じ／false=異なる
	 */
	public boolean equals2(Pai pai)
	{
		return kind == pai.kind;
	}

	/**
	 * ハッシュコード取得。
	 * @return ハッシュコード
	 */
	public int getHashCode()
	{
		return 0;//super.GetHashCode();
	}

	/**
	 * ドラ牌取得。
	 * @return ドラ牌
	 */
	public Pai getDora()
	{
		if (isShuupai())
		{
			// 数牌。

			return new Pai(kind - (kind % 9) + (kind + 1) % 9);
		}
		else if (isKazehai())
		{
			// 風牌。

			return new Pai(27 + (kind + 1 - 27) % 4);
		}
		else
		{
			// 三元牌。

			return new Pai(31 + (kind + 1 - 31) % 3);
		}
	}
}
