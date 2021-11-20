package kumagai.Maajan.logic;

import java.util.ArrayList;

/**
 * プレイヤーのコレクション。
 */
public class PlayerCollection
	extends ArrayList<Player>
{
	private final MaajanUI ui;

	/**
	 * プレイヤーオブジェクトを集約しコレクションを構築する。
	 * @param ui 画面オブジェクト
	 * @param players プレイヤーのコレクション
	 */
	public PlayerCollection(MaajanUI ui, Player [] players)
	{
		for (int i=0 ; i<players.length ; i++)
		{
			add(players[i]);
		}

		this.ui = ui;
	}

	/**
	 * 全プレイヤーのドラの枚数をカウントする。
	 * @param dora ドラ牌
	 * @return ドラの枚数
	 */
	public int countAllPlayerDora(Pai dora)
	{
		int count = 0;

		for (int i=0 ; i<size() ; i++)
		{
			count += get(i).countDora(dora);
		}

		return count;
	}

	/**
	 * 鳴きを全員に通知。リーチ一発消しのため。
	 */
	public void notifyNaki()
	{
		for (int i=0 ; i<size() ; i++)
		{
			if (get(i).riichi)
			{
				// リーチ中である。

				get(i).nakiOnRiichi = true;
			}
		}
	}

	/**
	 * 和がり時の役判定と精算。
	 * @param player プレイヤー
	 * @param jun 巡
	 * @param nokoriPaiNum 残り牌数
	 * @param tsumo true=ツモ和がり／false=ロン和がり
	 * @param houjuuPlayer ロン和がりの際放銃したプレイヤー
	 * @param tsumohai ツモ牌
	 * @param dora ドラ牌
	 * @param bakaze 場風牌
	 * @param yaku 特殊役
	 */
	public void agari(Player player, int jun, int nokoriPaiNum, boolean tsumo,
		Player houjuuPlayer, Pai tsumohai, ArrayList<Pai> dora, PaiKind bakaze,
		YakuType yaku)
		throws Exception
	{
		boolean furiten = false;

		for (int i=0 ; i<player.machi.size() ; i++)
		{
			for (int j=0 ; j<player.sutehai.size() ; j++)
			{
				furiten |= player.sutehai.get(j).kind == player.machi.get(i);
			}
		}

		YakuCollection yakuCollection =
			new YakuCollection(
				player,
				jun,
				nokoriPaiNum,
				tsumo,
				tsumohai.kind,
				dora,
				bakaze,
				yaku);

		ScoreData yakuStrings =
			new ScoreData(
				furiten && !tsumo,
				yakuCollection,
				player.isMenzen(),
				player.jikaze == bakaze);

		ui.displayYaku(yakuStrings);
		ui.notifyAgari(tsumo ? 1 : 2, player.name, null);

		if (tsumo)
		{
			// ツモ和がり。

			for (Player p : this)
			{
				if (p != player)
				{
					// 自分ではない。

					// p.MinusPoint();
				}
			}
		}
		else
		{
			// ロン和がり。

			// houjuuPlayer.MinusPoint();
		}
	}
}
