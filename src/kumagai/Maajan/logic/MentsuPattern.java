package kumagai.Maajan.logic;

import java.util.*;

/**
 * 面子パターン。
 * @author kumagai
 */
public abstract class MentsuPattern
{
	private boolean skip;
	private boolean loop = true;

	public int loopCount;

	/**
	 * 面子検索。
	 * @param tehai 手牌
	 * @param skip true=枝切りする／false=しない
	 */
	public void makeMentsu0(ArrayList<Pai> tehai, boolean skip)
		throws Exception
	{
		this.skip = skip;

		int[] use1 = new int[tehai.size()];
		int[] use2 = new int[tehai.size()];

		makeMentsu(tehai, use1, use2, new ArrayList<Integer>(), 0, 1, 0);
	}

	/**
	 * ３面子確定時イベント。
	 * @param tehai 手牌
	 * @param use 牌確定フラグ
	 * @return true=継続／false=検索処理打ち切り
	 * @throws Exception
	 */
	protected abstract boolean onFind3Mentsu(ArrayList<Pai> tehai, int [] use)
		throws Exception;

	/**
	 * 再帰的に面子を検索する。
	 * @param tehai 手牌
	 * @param use1 チェック配列
	 * @param use2 面子確定配列
	 * @param mentsu 面子候補インデックス配列
	 * @param mentsuCount 確定した面子の数
	 * @param level 再帰呼び出しの深さ
	 * @param start チェック開始インデックス
	 */
	private void makeMentsu(ArrayList<Pai> tehai, int[] use1, int[] use2,
		List<Integer> mentsu, int mentsuCount, int level, int start)
		throws Exception
	{
		loopCount++;

		if (level >= tehai.size())
		{
			// すべての牌を使用した。

			return;
		}

		if (level == tehai.size() - 3 * 1 + 1)
		{
			// １３枚の場合level=11、１４枚の場合level=12。

			if ((13 - tehai.size()) / 3 + mentsuCount < 3)
			{
				// まだ２面子しか確定していない。

				return;
			}
		}
		else if (level == tehai.size() - 3 * 2 + 1)
		{
			// １３枚の場合level=8、１４枚の場合level=9。

			if ((13 - tehai.size()) / 3 + mentsuCount < 2)
			{
				// まだ１面子しか確定していない。

				return;
			}
		}
		else if (level == tehai.size() - 3 * 3 + 1)
		{
			// １３枚の場合level=5、１４枚の場合level=8。

			if ((13 - tehai.size()) / 3 + mentsuCount < 1)
			{
				// まだ１面子も確定していない。

				return;
			}
		}

		ArrayList<Integer> mentsu2 = null;
		int ppai = -1;

		for (int i = start; i < use1.length && loop ; i++)
		{
			if (use1[i] == 0 && (!skip || tehai.get(i).kind != ppai))
			{
				// まだ使われていない。

				if (mentsu.size() == 0)
				{
					// １個目。

					use1[i] = level;
					mentsu.add(i);
					makeMentsu(tehai, use1, use2, mentsu, mentsuCount, level + 1, i + 1);
					mentsu.remove(mentsu.size() - 1);
					use1[i] = 0;
				}
				else if (mentsu.size() == 1)
				{
					// ２個目。

					use1[i] = level;

					if (tehai.get(mentsu.get(0)).kind == tehai.get(i).kind ||
						(tehai.get(i).kind < 26 && tehai.get(mentsu.get(0)).kind + 1 == tehai.get(i).kind))
					{
						// 対子または搭子。

						mentsu.add(i);
						makeMentsu(tehai, use1, use2, mentsu, mentsuCount, level + 1, i + 1);
						mentsu.remove(mentsu.size() - 1);
					}

					use1[i] = 0;
				}
				else if (mentsu.size() == 2)
				{
					// ３個目。

					boolean kakutei = false;

					if (tehai.get(mentsu.get(0)).kind == tehai.get(mentsu.get(1)).kind &&
						tehai.get(mentsu.get(1)).kind == tehai.get(i).kind)
					{
						// 刻子。

						kakutei = true;
					}
					else if (tehai.get(i).isShuupai() &&
							tehai.get(mentsu.get(0)).kind + 1 == tehai.get(mentsu.get(1)).kind &&
							tehai.get(mentsu.get(1)).kind + 1 == tehai.get(i).kind)
					{
						// 順子。

						if (tehai.get(mentsu.get(0)).isSameColor(tehai.get(i)))
						{
							// 同じ柄。

							kakutei = true;
						}
					}

					if (skip)
					{
						// 間引きOn。

						if (kakutei)
						{
							// 面子確定。

							for (int j=i+1 ; j<use1.length && kakutei ; j++)
							{
								if (use1[j] > 0)
								{
									// その面子よりも右側ですでに面子が確定して
									// いる。

									kakutei = false;
								}
							}
						}

						if (kakutei)
						{
							// 面子確定。

							if (mentsu2 != null)
							{
								// すでに確定した別の面子がある。

								if (mentsu2.contains(mentsu.get(0)) ||
									mentsu2.contains(mentsu.get(1)) ||
									mentsu2.contains(i))
								{
									// すでに使われている。

									kakutei = false;
								}
							}
						}
					}

					use1[i] = level;

					if (kakutei)
					{
						// 面子確定。

						mentsu.add(i);
						mentsu2 = new ArrayList<Integer>(mentsu);

						// ３面子以内である。
						// 「2223」を「3」単騎待ちとせず「22 + (1+)23(+4)」とす
						// るために確定は３面子以内に収めることとしている。

						use2[mentsu.get(0)] = level - 2;
						use2[mentsu.get(1)] = level - 1;
						use2[mentsu.get(2)] = level;

						if ((13 - tehai.size()) / 3 + mentsuCount < 2)
						{
							// ２面子以内である。

							makeMentsu(tehai, use1, use2, new ArrayList<Integer>(), mentsuCount + 1, level + 1, 0);
						}
						else
						{
							// ３面子を越えた。

							loop = onFind3Mentsu(tehai, use2);
						}

						use2[mentsu.get(0)] = 0;
						use2[mentsu.get(1)] = 0;
						use2[mentsu.get(2)] = 0;

						mentsu.remove(mentsu.size() - 1);
					}

					use1[i] = 0;
				}

				ppai = tehai.get(i).kind;
			}
		}
	}
}
