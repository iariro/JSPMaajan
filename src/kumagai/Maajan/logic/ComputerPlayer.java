package kumagai.Maajan.logic;

import java.util.*;

/**
 * コンピュータプレイヤー。
 * @author kumagai
 */
public class ComputerPlayer
	extends Player
{
	private final int wait;

	/**
	 * コンピュータプレイヤーオブジェクトを構築する。
	 * @param jikaze 自風
	 * @param wait ウエイト（ミリ秒）
	 */
	public ComputerPlayer(PaiKind jikaze, int wait)
	{
		super("コンピュータ", jikaze);

		this.wait = wait;
	}

	/**
	 * 和がるかをプレイヤーに問い合わせる。
	 * @return true=和がる／false=和がらない
	 * @see kumagai.Maajan.logic.Player#promptAgari()
	 */
	public boolean promptAgari()
	{
		return true;
	}

	/**
	 * 和がりか槓かをプレイヤーに問い合わせる。
	 * @param agari true=和がれる／false=できない
	 * @param kan true=槓できる／false=できない
	 * @return 実行するコマンド
	 * @see kumagai.Maajan.logic.Player#promptAgariOrKan(boolean, boolean)
	 */
	public Command promptAgariOrKan(boolean agari, boolean kan)
	{
		if (agari)
		{
			// 和がれる。

			return new AgariCommand(PlayerDirection.Jibun.ordinal());
		}
		else
		{
			// 和がれない。

			return null;
		}
	}

	/**
	 * ドラ牌をカウントする。
	 * @param tsumohai ツモ牌（大明槓の場合のみ）
	 * @return 打牌インデックス
	 * @see kumagai.Maajan.logic.Player#dahaiAfterNaki(kumagai.Maajan.logic.Pai)
	 */
	public int dahaiAfterNaki(Pai tsumohai)
	{
		// TODO: 実装
		return 0;
	}

	/**
	 * ドラ牌をカウントする。
	 * @param dora ドラ牌
	 * @return ドラ牌の数
	 * @see kumagai.Maajan.logic.Player#countDora(kumagai.Maajan.logic.Pai)
	 */
	public int countDora(Pai dora)
	{
		int count = 0;

		for (int i=0 ; i<nakiList.size() ; i++)
		{
			for (int j=0 ; j<nakiList.get(i).size() ; j++)
			{
				if (dora.equals2(nakiList.get(i).get(j)))
				{
					// ドラ牌である。

					count++;
				}
			}
		}

		return count;
	}

	/**
	 * ツモ時の処理。
	 * @param tsumohai ツモ牌
	 * @param canRiichi true=リーチできる／できない
	 * @param canKan true=槓できる／できない
	 * @param canAgari true=和がれる／できない
	 * @return ツモに対するコマンド
	 * @see kumagai.Maajan.logic.Player#onTsumo(kumagai.Maajan.logic.Pai, boolean, boolean, boolean)
	 */
	public Command onTsumo
		(Pai tsumohai, boolean canRiichi, boolean canKan, boolean canAgari)
	{
		if (machi != null)
		{
			// 待ち情報はある。

			if (machi.contains(tsumohai.kind))
			{
				// 待ち牌である。

				return new AgariCommand(PlayerDirection.Jibun.ordinal());
			}
		}

		return null;
	}

	/**
	 * 他家打牌時。
	 * @param direction 打牌したプレイヤー
	 * @param pai 打った牌
	 * @return 実行するコマンド
	 * @see kumagai.Maajan.logic.Player#onTaachaDahai(int, kumagai.Maajan.logic.Pai)
	 */
	public Command onTaachaDahai(int direction, Pai pai)
	{
		if (machi != null)
		{
			// 待ち牌はある。

			if (machi.contains(pai.kind))
			{
				// 待ち牌である。

				return new AgariCommand(direction);
			}
			else
			{
				// 待ち牌ではない。

				return null;
			}
		}
		else
		{
			// 待ち牌はない。

			return null;
		}
	}

	/**
	 * 他家小明槓時。
	 * @param direction 打牌したプレイヤー
	 * @param pai 小明槓した牌
	 * @return 実行するコマンド
	 * @see kumagai.Maajan.logic.Player#onTaachaShouminkan(int, kumagai.Maajan.logic.Pai)
	 */
	public Command onTaachaShouminkan(int direction, Pai pai)
	{
		if (machi != null)
		{
			// 待ちはある。

			if (machi.contains(pai.kind))
			{
				// 待ち牌である。

				// 必ず和了。
				return new AgariCommand(direction);
			}
		}

		return null;
	}

	/**
	 * 打牌。
	 * @param tsumo ツモ牌
	 * @return 打牌する牌の手牌中のインデックス
	 * @see kumagai.Maajan.logic.Player#dahai(kumagai.Maajan.logic.Pai)
	 */
	public int dahai(Pai tsumo) throws Exception
	{
		Thread.sleep(wait * 5);

		int [] relativeCount = new int [tehai.size() + 1];

		ArrayList<Pai> tehaiAndTsumo = new ArrayList<Pai>(tehai);

		if (tsumo != null)
		{
			// ツモ牌はある。

			tehaiAndTsumo.add(tsumo);
		}

		for (int i = 0; i < tehaiAndTsumo.size(); i++)
		{
			for (int j = 0; j < tehaiAndTsumo.size(); j++)
			{
				if (i != j)
				{
					// 調べる牌自身ではない。

					if (tehaiAndTsumo.get(i).isShuupai())
					{
						// 数牌。

						if (tehaiAndTsumo.get(i).isSameColor(tehaiAndTsumo.get(j)))
						{
							// 同じ柄。

							if (tehaiAndTsumo.get(i).kind > tehaiAndTsumo.get(j).kind)
							{
								// 小さい数の牌。

								if (tehaiAndTsumo.get(i).kind - tehaiAndTsumo.get(j).kind <= 2)
								{
									// 近い牌。

									relativeCount[i]++;

									if (tehaiAndTsumo.get(i).kind - tehaiAndTsumo.get(j).kind <= 1)
									{
										// 隣の数の牌。

										relativeCount[i]++;
									}
								}
							}
							else if (tehaiAndTsumo.get(i).kind < tehaiAndTsumo.get(j).kind)
							{
								// 大きい数の牌。

								if (tehaiAndTsumo.get(j).kind - tehaiAndTsumo.get(i).kind <= 2)
								{
									// 近い牌。

									relativeCount[i]++;

									if (tehaiAndTsumo.get(j).kind - tehaiAndTsumo.get(i).kind <= 2)
									{
										// 隣の数の牌。

										relativeCount[i]++;
									}
								}
							}
							else
							{
								// 同種の牌。

								relativeCount[i]++;
							}
						}
					}
					else
					{
						// 字牌。

						if (tehaiAndTsumo.get(i).equals2(tehaiAndTsumo.get(j)))
						{
							// 同種の牌。

							relativeCount[i]++;
						}
					}
				}
			}
		}

		int index = 0;
		int min = relativeCount[index];

		for (int i = 0; i < tehaiAndTsumo.size(); i++)
		{
			if (min > relativeCount[i])
			{
				// より孤立した牌。

				min = relativeCount[i];
				index = i;
			}
		}

		return index;
	}
}
