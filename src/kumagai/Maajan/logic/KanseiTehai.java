package kumagai.Maajan.logic;

import java.util.*;

/**
 * 完成手牌。
 */
class KanseiTehai
	extends ArrayList<Pai>
{
	public final List<MentsuType> mentsuType;
	public MachiType machiType;

	/**
	 * 待ち状態の牌と当たり牌から完成手牌オブジェクトを構築する。
	 * @param player 和がったプレイヤー
	 * @param atarihai 当たり牌
	 */
	public KanseiTehai(Player player, Pai atarihai)
	{
		int matchCount = 0;

		mentsuType = new ArrayList<MentsuType>();

		for (MachiElement machiElement
			: player.machiPattern.machiElementCollection)
		{
			if (machiElement.contains(atarihai.kind))
			{
				// 当たり牌を含む待ち。

				machiType = machiElement.type;

				if (matchCount == 0)
				{
					// １個目。

					for (int i=0 ; i<machiElement.use.length ; i++)
					{
						int index = -1;
						for (int j=0 ; j<machiElement.use.length && index < 0 ; j++)
						{
							if (machiElement.use[j] == i + 1)
							{
								// 一致する。

								index = j;
							}
						}

						if (index >= 0)
						{
							// 確定面子分。

							add(player.tehai.get(index));
						}
					}

					switch (machiElement.type)
					{
						case Tanki:
							if (machiElement.nokori.get(0).equals2(atarihai))
							{
								// 先頭に対する単騎待ち。

								add(machiElement.nokori.get(1));
								add(machiElement.nokori.get(2));
								add(machiElement.nokori.get(3));
							}
							else if (machiElement.nokori.get(1).equals2(atarihai))
							{
								// 真ん中に対する単騎待ち。

								add(machiElement.nokori.get(0));
								add(machiElement.nokori.get(2));
								add(machiElement.nokori.get(3));
							}
							else if (machiElement.nokori.get(3).equals2(atarihai))
							{
								// 末尾に対する単騎待ち。

								add(machiElement.nokori.get(0));
								add(machiElement.nokori.get(1));
								add(machiElement.nokori.get(2));
							}

							add(atarihai);
							add(atarihai);
						break;

						case Kanchan:
							if (machiElement.nokori.get(0).equals2(machiElement.nokori.get(1)))
							{
								// 前半が頭。後半が嵌張。

								add(machiElement.nokori.get(2));
								add(atarihai);
								add(machiElement.nokori.get(3));

								add(machiElement.nokori.get(0));
								add(machiElement.nokori.get(1));
							}
							else if (machiElement.nokori.get(2).equals2(machiElement.nokori.get(3)))
							{
								// 後半が頭。前半が嵌張。

								add(machiElement.nokori.get(0));
								add(atarihai);
								add(machiElement.nokori.get(1));

								add(machiElement.nokori.get(2));
								add(machiElement.nokori.get(3));
							}
							break;

						case Penchan:
						case Ryanmen:
							if (machiElement.nokori.get(0).equals2(machiElement.nokori.get(1)))
							{
								// 前半が頭。後半が嵌張。

								if (atarihai.kind + 1 == machiElement.nokori.get(2).kind)
								{
									// 両面の前側。

									add(atarihai);
									add(machiElement.nokori.get(2));
									add(machiElement.nokori.get(3));
								}
								else
								{
									// 両面の後ろ側。

									add(machiElement.nokori.get(2));
									add(machiElement.nokori.get(3));
									add(atarihai);
								}

								add(machiElement.nokori.get(0));
								add(machiElement.nokori.get(1));
							}
							else if (machiElement.nokori.get(2).equals2(machiElement.nokori.get(3)))
							{
								// 後半が頭。前半が嵌張。

								if (atarihai.kind + 1 == machiElement.nokori.get(0).kind)
								{
									// 両面の前側。

									add(atarihai);
									add(machiElement.nokori.get(0));
									add(machiElement.nokori.get(1));
								}
								else
								{
									// 両面の後ろ側。

									add(machiElement.nokori.get(0));
									add(machiElement.nokori.get(1));
									add(atarihai);
								}

								add(machiElement.nokori.get(2));
								add(machiElement.nokori.get(3));
							}
							break;
						case Shanpon:
							if (atarihai.equals2(machiElement.nokori.get(0)))
							{
								// 前半について当たり。

								add(machiElement.nokori.get(0));
								add(machiElement.nokori.get(1));
								add(atarihai);
								add(machiElement.nokori.get(2));
								add(machiElement.nokori.get(3));
							}
							else
							{
								// 後半について当たり。

								add(machiElement.nokori.get(2));
								add(machiElement.nokori.get(3));
								add(atarihai);
								add(machiElement.nokori.get(0));
								add(machiElement.nokori.get(1));
							}
							break;
					}

					for (int i=0 ; i<size() ; i++)
					{
						if (i % 3 == 2)
						{
							// 面子の最後の牌。

							if (get(i - 2).equals2(get(i - 1)) &&
								get(i - 1).equals2(get(i)))
							{
								// 暗刻。

								mentsuType.add(MentsuType.Anko);
							}
							else if (get(i - 2).isShuntsu(get(i - 1), get(i)))
							{
								// 順子。

								mentsuType.add(MentsuType.Shuntsu);
							}
						}
					}
				}
				else
				{
					// ２個目以降。

					System.out.println("■２個目以降の待ち■");
				}

				matchCount++;
			}
		}
	}
}
