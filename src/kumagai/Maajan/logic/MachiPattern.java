package kumagai.Maajan.logic;

import java.util.*;

/**
 * 待ちパターン。
 * @author kumagai
 */
public class MachiPattern
	extends MentsuPattern
{
	public final boolean all;

	private final HashMap<Integer, Integer> paiCount;

	public int JudgeCount;
	public boolean[] machi = new boolean[AllPai.totalPaiNum];
	public MachiElementCollection machiElementCollection =
		new MachiElementCollection();

	/**
	 * 待ち牌のコレクションを構築する。
	 * @param tehai 手牌
	 * @param all true=すべて検索／false=初めの１個のみ検索する
	 */
	public MachiPattern(ArrayList<Pai> tehai, boolean all)
		throws Exception
	{
		this.all = all;

		// 牌の数をカウント。
		paiCount = new HashMap<Integer, Integer>();

		for (int i = 0; i < tehai.size(); i++)
		{
			if (!paiCount.containsKey(tehai.get(i).kind))
			{
				// 初めての牌。

				paiCount.put(tehai.get(i).kind, 1);
			}
			else
			{
				// すでに出ている牌。

				int count = paiCount.get(tehai.get(i).kind);
				paiCount.put(tehai.get(i).kind, count + 1);
			}
		}

		makeMentsu0(tehai, all);
	}

	/**
	 * @see kumagai.Maajan.logic.MentsuPattern#onFind3Mentsu(java.util.ArrayList, int[])
	 */
	@Override
	protected boolean onFind3Mentsu(ArrayList<Pai> tehai, int[] use)
		throws Exception
	{
		JudgeCount++;

		ArrayList<Pai> nokori = new ArrayList<Pai>();

		for (int i = 0; i < tehai.size(); i++)
		{
			if (use[i] <= 0)
			{
				// 面子確定してない牌である。

				nokori.add(tehai.get(i));
			}
		}

		if (nokori.size() == 4)
		{
			// 残り４枚。

			boolean dec = false, inc = false;

			if (nokori.get(0).equals2(nokori.get(1)) &&
				nokori.get(1).equals2(nokori.get(2)) &&
				nokori.get(2).kind + 1 == nokori.get(3).kind)
			{
				// 2223 == 22 + (1+)23(+4)
				// 1112 == 111 + 2(+2) == 11 + 12 (+3)
				// 8889 == 888 + 9(+9) == 88 + (7+)89

				if (nokori.get(3).isShuupai() && nokori.get(2).isSameColor(nokori.get(3)))
				{
					// 同じ柄の数牌である。

					// 端の牌ではない。
					dec = nokori.get(2).isChunchanpai();

					// 端の牌ではない。
					inc = nokori.get(3).isChunchanpai();

					machi[(int)nokori.get(3).kind] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(3).kind,
							paiCount));

					if (dec && inc)
					{
						// 両面待ち。

						machi[nokori.get(2).kind - 1] = true;
						machi[nokori.get(3).kind + 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Ryanmen,
								use,
								nokori,
								nokori.get(2).kind - 1,
								nokori.get(3).kind + 1,
								paiCount));
					}
					else if (dec)
					{
						// 辺張待ち。

						machi[(int)nokori.get(2).kind - 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Penchan,
								use,
								nokori,
								nokori.get(2).kind - 1,
								paiCount));
					}
					else if (inc)
					{
						// 辺張待ち。

						machi[nokori.get(3).kind + 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Penchan,
								use,
								nokori,
								nokori.get(3).kind + 1,
								paiCount));
					}
				}
			}
			else if (nokori.get(0).kind + 1 == nokori.get(1).kind &&
					nokori.get(1).kind == nokori.get(2).kind &&
					nokori.get(2).kind == nokori.get(3).kind)
			{
				// 2333 == (1+)23(+4) + 33
				// 1222 == (1+)1 + 222
				// 1222 == 12(+3) + 22
				// 8999 == (7+)89 + 99

				if (nokori.get(1).isShuupai() && nokori.get(0).isSameColor(nokori.get(1)))
				{
					// 同じ柄の数牌である。

					// 端の牌ではない。
					dec = nokori.get(0).isChunchanpai();

					// 端の牌ではない。
					inc = nokori.get(1).isChunchanpai();

					machi[(int)nokori.get(0).kind] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(0).kind,
							paiCount));

					if (dec && inc)
					{
						// 両面待ち。

						machi[(int)nokori.get(0).kind - 1] = true;
						machi[(int)nokori.get(1).kind + 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Ryanmen,
								use,
								nokori,
								nokori.get(0).kind - 1,
								nokori.get(1).kind + 1,
								paiCount));
					}
					else if (dec)
					{
						// 辺張待ち。

						machi[(int)nokori.get(0).kind - 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Penchan,
								use,
								nokori,
								nokori.get(0).kind - 1,
								paiCount));
					}
					else if (inc)
					{
						// 辺張待ち。

						machi[(int)nokori.get(1).kind + 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Penchan,
								use,
								nokori,
								nokori.get(1).kind + 1,
								paiCount));
					}
				}
			}
			else if (nokori.get(0).kind + 2 == nokori.get(1).kind &&
					nokori.get(1).kind == nokori.get(2).kind &&
					nokori.get(2).kind == nokori.get(3).kind)
			{
				// 1333 == 1(+1) + 333 == 1(+2+)3 + 33

				if (nokori.get(1).isShuupai() && nokori.get(0).isSameColor(nokori.get(1)))
				{
					// 同じ柄の数牌である。

					machi[(int)nokori.get(0).kind] = true;
					machi[(int)nokori.get(0).kind + 1] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(0).kind,
							paiCount));

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Kanchan,
							use,
							nokori,
							nokori.get(0).kind + 1,
							paiCount));
				}
			}
			else if (nokori.get(0).kind == nokori.get(1).kind &&
					nokori.get(1).kind == nokori.get(2).kind &&
					nokori.get(2).kind + 2 == nokori.get(3).kind)
			{
				// 1113 == 111 + 3(+3) == 11 + 1(+2+)3

				if (nokori.get(1).isShuupai() && nokori.get(2).isSameColor(nokori.get(3)))
				{
					// 同じ柄の数牌である。

					machi[(int)nokori.get(3).kind] = true;
					machi[(int)nokori.get(2).kind + 1] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(3).kind,
							paiCount));

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Kanchan,
							use,
							nokori,
							nokori.get(2).kind + 1,
							paiCount));
				}
			}
			else if (nokori.get(0).kind + 1 == nokori.get(1).kind &&
					nokori.get(1).kind == nokori.get(2).kind &&
					nokori.get(2).kind + 1 == nokori.get(3).kind)
			{
				// 1223

				if (nokori.get(3).isShuupai() && nokori.get(0).isSameColor(nokori.get(3)))
				{
					// 同じ柄の数牌である。

					machi[nokori.get(2).kind] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(2).kind,
							paiCount));
				}
			}
			else if (nokori.get(0).kind + 1 == nokori.get(1).kind &&
					nokori.get(1).kind + 1 == nokori.get(2).kind &&
					nokori.get(2).kind + 1 == nokori.get(3).kind)
			{
				// 1234

				if (nokori.get(3).isShuupai() && nokori.get(0).isSameColor(nokori.get(3)))
				{
					// 同じ柄の数牌である。

					machi[(int)nokori.get(0).kind] = true;
					machi[(int)nokori.get(3).kind] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(0).kind,
							paiCount));

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(3).kind,
							paiCount));
				}
			}
			else if (nokori.get(0).kind == nokori.get(1).kind &&
					nokori.get(1).kind == nokori.get(2).kind &&
					nokori.get(2).kind != nokori.get(3).kind)
			{
				// 1119

				machi[(int)nokori.get(3).kind] = true;

				machiElementCollection.addElement(
					new MachiElement(
						MachiType.Tanki,
						use,
						nokori,
						nokori.get(3).kind,
						paiCount));
			}
			else if (nokori.get(0).kind != nokori.get(1).kind &&
					nokori.get(1).kind == nokori.get(2).kind &&
					nokori.get(2).kind == nokori.get(3).kind)
			{
				// 1999

				machi[(int)nokori.get(0).kind] = true;

				machiElementCollection.addElement(
					new MachiElement(
						MachiType.Tanki,
						use,
						nokori,
						nokori.get(0).kind,
						paiCount));
			}
			else if (nokori.get(0).kind + 2 == nokori.get(1).kind &&
					nokori.get(2).kind == nokori.get(3).kind)
			{
				// 1399

				if (nokori.get(1).isShuupai() && nokori.get(0).isSameColor(nokori.get(1)))
				{
					// 同じ柄の数牌である。

					machi[nokori.get(0).kind + 1] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Kanchan,
							use,
							nokori,
							nokori.get(0).kind + 1,
							paiCount));
				}
			}
			else if (nokori.get(0).kind == nokori.get(1).kind &&
					nokori.get(2).kind + 2 == nokori.get(3).kind)
			{
				// 9913 == 99 + 1(+2+)3

				if (nokori.get(3).isShuupai() && nokori.get(2).isSameColor(nokori.get(3)))
				{
					// 同じ柄の数牌である。

					machi[(int)nokori.get(2).kind + 1] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Kanchan,
							use,
							nokori,
							nokori.get(2).kind + 1,
							paiCount));
				}
			}
			else if (nokori.get(0).kind + 1 == nokori.get(1).kind &&
					nokori.get(2).kind == nokori.get(3).kind)
			{
				// 2399

				if (nokori.get(1).isShuupai() && nokori.get(0).isSameColor(nokori.get(1)))
				{
					// 端の牌ではない。
					dec = nokori.get(0).isChunchanpai();

					// 端の牌ではない。
					inc = nokori.get(1).isChunchanpai();

					if (dec && inc)
					{
						// 両面待ち。

						machi[(int)nokori.get(0).kind - 1] = true;
						machi[(int)nokori.get(1).kind + 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Ryanmen,
								use,
								nokori,
								nokori.get(0).kind - 1,
								nokori.get(1).kind + 1,
								paiCount));
					}
					else if (dec)
					{
						// 辺張待ち。

						machi[(int)nokori.get(0).kind - 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Penchan,
								use,
								nokori,
								nokori.get(0).kind - 1,
								paiCount));
					}
					else if (inc)
					{
						// 辺張待ち。

						machi[(int)nokori.get(1).kind + 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Penchan,
								use,
								nokori,
								nokori.get(1).kind + 1,
								paiCount));
					}
				}
			}
			else if (nokori.get(0).kind == nokori.get(1).kind &&
					nokori.get(2).kind + 1 == nokori.get(3).kind)
			{
				// 9923

				if (nokori.get(3).isShuupai() && nokori.get(2).isSameColor(nokori.get(3)))
				{
					// 同じ柄の数牌である。

					// 端の牌ではない。
					dec = nokori.get(2).isChunchanpai();

					// 端の牌ではない。
					inc = nokori.get(3).isChunchanpai();

					if (dec && inc)
					{
						// 両面待ち。

						machi[(int)nokori.get(2).kind - 1] = true;
						machi[(int)nokori.get(3).kind + 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Ryanmen,
								use,
								nokori,
								nokori.get(2).kind - 1,
								nokori.get(3).kind + 1,
								paiCount));
					}
					else if (dec)
					{
						// 辺張待ち。

						machi[(int)nokori.get(2).kind - 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Penchan,
								use,
								nokori,
								nokori.get(2).kind - 1,
								paiCount));
					}
					else if (inc)
					{
						// 辺張待ち。

						machi[(int)nokori.get(3).kind + 1] = true;

						machiElementCollection.addElement(
							new MachiElement(
								MachiType.Penchan,
								use,
								nokori,
								nokori.get(3).kind + 1,
								paiCount));
					}
				}
			}
			else if (nokori.get(0).kind + 1 == nokori.get(1).kind &&
					nokori.get(1).kind + 1 == nokori.get(2).kind &&
					nokori.get(2).kind != nokori.get(3).kind)
			{
				// 1239

				if (nokori.get(2).isShuupai() && nokori.get(0).isSameColor(nokori.get(2)))
				{
					// 同じ柄の数牌である。

					machi[(int)nokori.get(3).kind] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(3).kind,
							paiCount));
				}
			}
			else if (nokori.get(1).kind + 1 == nokori.get(2).kind &&
					nokori.get(2).kind + 1 == nokori.get(3).kind)
			{
				// 9123

				if (nokori.get(3).isShuupai() && nokori.get(1).isSameColor(nokori.get(3)))
				{
					// 同じ柄の数牌である。

					machi[(int)nokori.get(0).kind] = true;

					machiElementCollection.addElement(
						new MachiElement(
							MachiType.Tanki,
							use,
							nokori,
							nokori.get(0).kind,
							paiCount));
				}
			}
			else if (nokori.get(0).kind == nokori.get(1).kind &&
					nokori.get(1).kind != nokori.get(2).kind &&
					nokori.get(2).kind == nokori.get(3).kind)
			{
				// 1199

				machi[(int)nokori.get(0).kind] = true;
				machi[(int)nokori.get(2).kind] = true;

				machiElementCollection.addElement(
					new MachiElement(
						MachiType.Shanpon,
						use,
						nokori,
						nokori.get(0).kind,
						nokori.get(2).kind,
						paiCount));
			}
		}

		if (all)
		{
			// すべて見つける。

			return true;
		}
		else
		{
			// １つでも見つければ終了とする。

			for (int i = 0; i < machi.length; i++)
			{
				if (machi[i])
				{
					// 見つけた。

					return false;
				}
			}

			return true;
		}
	}

	/**
	 * 待ち牌取得。
	 * @return 待ち牌
	 */
	public ArrayList<Integer> getMachi()
	{
		ArrayList<Integer> machi = new ArrayList<Integer>();

		for (int i = 0; i < this.machi.length; i++)
		{
			if (this.machi[i])
			{
				// 待ちである。

				if (paiCount.containsKey(i))
				{
					// 含まれている。

					if (paiCount.get(i) < 4)
					{
						// ４枚使われていない。

						machi.add(i);
					}
				}
				else
				{
					// 含まれていない。

					machi.add(i);
				}
			}
		}

		return machi;
	}
}
