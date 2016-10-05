package kumagai.Maajan.logic;

import java.util.*;

/**
 * ゲーム進行メインスレッド基底部。
 */
public abstract class GameThreadBase
	extends Thread
{
	protected final int wait;
	protected final MaajanUI ui;
	protected final PlayerCollection players;
	protected final AllPai allPai;
	protected final PaiKind bakaze;

	protected int jun = 1;
	protected boolean loop;
	protected ArrayList<Pai> dora;
	protected ArrayList<Pai> dora2;

	/**
	 * 初期化処理を行いゲームスレッドオブジェクトを構築する。
	 * @param ui 人間プレイヤー用ユーザーインターフェイス
	 * @param wait ウエイト
	 * @param player1 プレイヤー１
	 * @param player2 プレイヤー２
	 * @param allPai すべての牌
	 */
	public GameThreadBase
		(MaajanUI ui, int wait, Player player1, Player player2, AllPai allPai)
	{
		this.ui = ui;
		this.wait = wait;
		this.players =
			new PlayerCollection(ui, new Player [] { player1, player2 } );
		this.allPai = allPai;
		this.bakaze = PaiKind.東;

		players.get(0).addTaacha(PlayerDirection.Toimen, players.get(1));
		players.get(1).addTaacha(PlayerDirection.Toimen, players.get(0));

		loop = true;
	}

	/**
	 * スレッド終了。
	 */
	public void stopThread()
	{
		for (int i=0 ; i<players.size() ; i++)
		{
			players.get(i).breakPlay();
		}

		loop = false;
	}

	/**
	 * リーチ・和了・打牌・暗槓・小明槓を行うツモ時のループ処理。
	 * @param playerIndex プレイヤーインデックス
	 * @param tsumohai ツモ牌
	 * @param dahaiIndex 打牌する牌のインデックス
	 * @return 和了の有無と打牌する牌のインデックス
	 * @throws Exception
	 */
	protected TsumoLoopResult tsumoLoop
		(int playerIndex, Pai tsumohai, int dahaiIndex)
		throws Exception
	{
		boolean kan = false;
		boolean agari = false;
		boolean flush = false;
		boolean doRiichi = false;
		boolean tsumoLoop = false;

		do
		{
			ui.displayTsumo(playerIndex, players.get(playerIndex), tsumohai);
			ui.displayNokoriPaiNum(allPai.getNokori());

			if (dora2.contains(tsumohai))
			{
				// ツモ牌はドラである。

				if (! flush)
				{
					// まだフラッシュしていない。

					ui.flushTsumo(playerIndex, players.get(playerIndex), true);
					flush = true;
				}
			}

			// 槓チェック。
			boolean canKan;

			if (players.get(playerIndex).riichi)
			{
				// リーチ中である。

				canKan = players.get(playerIndex).checkAnkanOnRiichi(tsumohai);
			}
			else
			{
				// リーチ中ではない。

				List<Pai> kanzai = players.get(playerIndex).getKanzai(tsumohai);

				canKan = kanzai.size() > 0;
			}

			// 和がりチェック。
			boolean canAgari = players.get(playerIndex).canAgari(tsumohai);

			// リーチチェック。
			boolean canRiichi = false;
			boolean [] riichiSengenPai =
				players.get(playerIndex).getRiichiSengenPai(tsumohai);

			if (riichiSengenPai != null)
			{
				// リーチ宣言牌はある。

				if (players.get(playerIndex).isMenzen() &&
					! players.get(playerIndex).riichi)
				{
					// 門前かつリーチ状態ではない。

					canRiichi = true;
				}

				ui.displayRiichiPai(playerIndex, riichiSengenPai);
			}

			Player player = players.get(playerIndex);

			Command command =
				player.onTsumo(tsumohai, canRiichi, canKan, canAgari);

			if (command != null)
			{
				// キャンセルはされなかった。

				switch (command.type)
				{
					case Riichi:
						if (doRiichi)
						{
							// リーチボタン押下状態。

							ui.highlightTehai(playerIndex, null);
							doRiichi = false;
						}
						else
						{
							// 初期状態。

							ui.highlightTehai(playerIndex, riichiSengenPai);
							doRiichi = true;
						}
						tsumoLoop = true;
						break;

					case Agari:
						if (! player.canAgari(tsumohai))
						{
							// まだ和がれない。

							throw new Exception("まだ和がれません");
						}

						player.terminateKyoku();

						ui.displayMessage("ツモ");

						YakuType yaku = null;

						if (kan)
						{
							// 槓している。

							yaku = YakuType.嶺上開花;
						}

						if (player.riichi)
						{
							// リーチをかけている。

							// 裏ドラ。
							dora = allPai.getDora(true);
							dora2 = new DoraPaiCollection(dora);
							ui.displayDora(dora, true);
						}

						players.agari(
							player,
							jun,
							allPai.getNokori(),
							true,
							null,
							tsumohai,
							dora2,
							bakaze,
							yaku);

						agari = true;
						tsumoLoop = false;
						break;

					case Dahai:
						dahaiIndex = ((DahaiCommand)command).index;

						if (doRiichi)
						{
							// リーチをかける。

							if (riichiSengenPai[dahaiIndex])
							{
								// リーチ宣言牌。

								ui.highlightTehai(playerIndex, null);
								ui.displayMessage("リーチ");
								ui.setNakiButtonState(false);
								player.naki = false;
								player.riichi = true;
								player.riichiJun = jun;

								tsumoLoop = false;
								kan = false;
							}
							else
							{
								// リーチ宣言牌ではない。

								tsumoLoop = true;
							}
						}
						else
						{
							// リーチをかけない。

							if (player.riichi)
							{
								// リーチ状態。

								if (dahaiIndex == player.tehai.size())
								{
									// ツモ切り。

									dahaiIndex = ((DahaiCommand)command).index;
									tsumoLoop = false;
									kan = false;
								}
								else
								{
									// ツモ切り以外。

									tsumoLoop = true;
								}
							}
							else
							{
								// リーチ状態ではない。

								dahaiIndex = ((DahaiCommand)command).index;
								tsumoLoop = false;
								kan = false;
							}
						}
						break;

					case Ankan:
						ui.displayTsumo(playerIndex, player, null);
						AnkanCommand ankanCommand = (AnkanCommand)command;
						player.ankan(ankanCommand.pai, tsumohai);

						ui.displayTehai(playerIndex, player);
						player.onAfterNaki();

						ui.displayMachi(playerIndex, player.machi);

						Thread.sleep(wait * 5);

						// 嶺上牌。
						tsumohai = allPai.getRinshanpai();
						dora = allPai.getDora(false);
						dora2 = new DoraPaiCollection(dora);
						ui.displayDora(dora, false);
						flush = false;

						if (players.countAllPlayerDora(dora2.get(dora2.size() - 1)) > 0)
						{
							// ドラが手牌にある。

							ui.flushDora(players, dora2, true);
						}

						kan = true;
						tsumoLoop = true;
						break;

					case Shouminkan:
						ui.displayTsumo(playerIndex, player, null);
						ShouminkanCommand shouminkanCommand =
							(ShouminkanCommand)command;
						player.shouminkan(shouminkanCommand.pai, tsumohai);

						ui.displayTehai(playerIndex, player);
						player.onAfterNaki();
						Command command2 =
							player.notifyShouminkan(shouminkanCommand.pai);

						if (command2 == null)
						{
							// 槍槓はない。

							ui.displayMachi(playerIndex, player.machi);

							Thread.sleep(wait * 5);

							// 嶺上牌。
							tsumohai = allPai.getRinshanpai();
							dora = allPai.getDora(false);
							dora2 = new DoraPaiCollection(dora);
							ui.displayDora(dora, false);
							flush = false;

							if (players.countAllPlayerDora(dora2.get(dora2.size() - 1)) > 0)
							{
								// ドラが手牌にある。

								ui.flushDora(players, dora2, true);
							}

							kan = true;
							tsumoLoop = true;
						}
						else
						{
							// 槍槓。

							AgariCommand command3 = (AgariCommand)command2;

							loop = false;
							agari = true;
							tsumoLoop = false;
							ui.displayMessage("ロン");

							players.agari(
								player.taacha.get(command3.direction),
								jun,
								allPai.getNokori(),
								false,
								player,
								shouminkanCommand.pai,
								dora2,
								bakaze,
								YakuType.槍槓);
						}
						break;
				}
			}
			else
			{
				// キャンセル。

				tsumoLoop = false;
			}
		}
		while (tsumoLoop);

		return new TsumoLoopResult(agari, dahaiIndex);
	}
}
