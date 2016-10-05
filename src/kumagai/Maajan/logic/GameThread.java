package kumagai.Maajan.logic;

import java.util.*;
//import android.util.*;

/**
 * ゲーム進行メインスレッド。
 */
public class GameThread
	extends GameThreadBase
{
	/**
	 * 配牌など初期化処理を行いゲームスレッドオブジェクトを構築する。
	 * @param ui 人間プレイヤー用ユーザーインターフェイス
	 * @param wait ウエイト
	 * @param player1 プレイヤー１
	 * @param player2 プレイヤー２
	 * @param allPai すべての牌
	 */
	public GameThread
		(MaajanUI ui, int wait, Player player1, Player player2, AllPai allPai)
	{
		super(ui, wait, player1, player2, allPai);
	}

	/**
	 * 一局分のスレッド。
	 */
	@Override
	public void run()
	{
		try
		{
			Thread.sleep(1000);

			ui.displayDora(null, false);

			for (int i=0 ; i<players.size() ; i++)
			{
				players.get(i).onGameStart();
				ui.displayTehai(i, players.get(i));
				ui.clearSutehai(i);
			}

			// 配牌。
			for (int i=0 ; i<4 ; i++)
			{
				for (int j=0 ; j<players.size() ; j++)
				{
					for (int k=0 ; k<(i < 3 ? 4 : 1) ; k++)
					{
						players.get(j).tehai.add(allPai.getTsumohai());
					}

					ui.displayTehai(j, players.get(j));

					Thread.sleep(wait * 3);
				}
			}

			// 理牌。
			for (int i=0 ; i<players.size() ; i++)
			{
				Collections.sort(players.get(i).tehai, new PaiComparator());
				ui.displayTehai(i, players.get(i));
			}

			dora = allPai.getDora(false);
			dora2 = new DoraPaiCollection(dora);
			ui.displayDora(dora, false);

			if (players.countAllPlayerDora(dora2.get(0)) > 0)
			{
				// ドラが手牌にある。

				ui.flushDora(players, dora2, true);
			}

			jun = 1;

			while (loop)
			{
				for (int i = 0; i < players.size() && loop ; i++)
				{
					if (players.get(i).machi == null)
					{
						// まだ待ち判定はない。

						players.get(i).updateMachi();
					}

					ui.displayMachi(i, players.get(i).machi);

					if (allPai.getNokori() > 0)
					{
						// まだツモ牌がある。

						// ツモ。
						Pai tsumohai = allPai.getTsumohai();

						int dahaiIndex = -1;
						boolean agari;

						TsumoLoopResult tsumoLoopResult =
							tsumoLoop(i, tsumohai, dahaiIndex);

						agari = tsumoLoopResult.agari;
						dahaiIndex = tsumoLoopResult.dahaiIndex;

						boolean kan = false;
						Command command = null;

						if (! agari)
						{
							// 和がらない。

							if (dahaiIndex < 0)
							{
								// まだ打牌してない。

								// 打牌。
								dahaiIndex = players.get(i).dahai(tsumohai);
							}

							if (players.get(i).isValid())
							{
								// プレイ続行。

								Pai sutehai;

								if (dahaiIndex <= players.get(i).tehai.size() - 1)
								{
									// ツモ切りではない。

									// 牌差し替え。
									sutehai = players.get(i).tehai.get(dahaiIndex);
									players.get(i).tehai.set(dahaiIndex, tsumohai);
									Collections.sort(players.get(i).tehai, new PaiComparator());

									// 牌表示。
									ui.displayTehai(i, players.get(i));
								}
								else
								{
									// ツモ切りである。

									sutehai = tsumohai;
								}

								ui.displaySutehai
									(i, players.get(i).sutehai.size(), sutehai);
								players.get(i).sutehai.add(sutehai);

								// ツモ牌非表示。
								ui.displayTsumo(i, players.get(i), null);

								// 他家に打牌を通知。
								command = players.get(i).notifyDahai(sutehai);

								if (command != null)
								{
									// 何か実行する。

									ui.displayMachi(i, null);

									switch (command.type)
									{
										case Agari:
											loop = false;

											AgariCommand command4 =
												(AgariCommand)command;
											YakuType yaku = null;

											if (kan)
											{
												// 槓している。

												yaku = YakuType.嶺上開花;
												ui.displayMessage("ツモ");
											}
											else
											{
												// 槓していない。

												ui.displayMessage("ロン");
											}

											if (players.get(i).taacha.get(command4.direction).riichi)
											{
												// リーチをかけている。

												// 裏ドラ。
												dora = allPai.getDora(true);
												dora2 = new DoraPaiCollection(dora);
												ui.displayDora(dora, true);
											}

											players.agari(
												players.get(i).taacha.get(command4.direction),
												jun,
												allPai.getNokori(),
												kan,
												kan ? null : players.get(i),
												sutehai,
												dora2,
												bakaze,
												yaku);

											break;

										case Chii:
											ChiiCommand command1 = (ChiiCommand)command;

											players.notifyNaki();

											ui.blankSutehai(i, players.get(i).sutehai.size() - 1);

											// 順番抜かし。
											i = (i + 1) % players.size();

											ArrayList<Integer> kuikaePai =
												command1.getKuikaePai(players.get(i).tehai);

											players.get(i).chii(command1);

											ui.displayTehai(i, players.get(i));
											players.get(i).onAfterNaki();

											// さらに打牌。

											boolean chiiLoop = false;

											do
											{
												dahaiIndex = players.get(i).dahaiAfterNaki(null);
												if (dahaiIndex >= 0)
												{
													// 牌が指定された。

													chiiLoop = kuikaePai.contains(players.get(i).tehai.get(dahaiIndex).kind);

													if (chiiLoop)
													{
														// 食い替え。

														ui.displayMessage("食い替えはできません");
													}
												}
												else
												{
													// 牌が指定されなかった。

													chiiLoop = false;
												}
											}
											while (chiiLoop);

											if (players.get(i).isValid())
											{
												// プレイ続行。

												sutehai = players.get(i).tehai.get(dahaiIndex);
												players.get(i).tehai.remove(dahaiIndex);

												// 牌表示。
												ui.displayTehai
													(i, players.get(i));

												ui.displaySutehai(i, players.get(i).sutehai.size(), sutehai);
												players.get(i).sutehai.add(sutehai);

												// ツモ牌非表示。
												ui.displayTsumo(i, players.get(i), null);
											}
											break;

										case Pon:
											PonCommand command2 = (PonCommand)command;

											players.notifyNaki();

											players.get(i).taacha.get(command2.direction).pon(command2.pai, command2.direction);

											ui.blankSutehai(i, players.get(i).sutehai.size() - 1);

											// 順番抜かし。
											if (players.size() == 2)
											{
												// ２人打ち。

												i = (i + 1) % players.size();
											}
											else
											{
												// ４人打ち。

												i = (i + (int)command2.direction) % players.size();
											}

											ui.displayTehai(i, players.get(i));
											players.get(i).onAfterNaki();

											// さらに打牌。
											boolean ponLoop = false;

											do
											{
												dahaiIndex = players.get(i).dahaiAfterNaki(null);

												if (dahaiIndex >= 0)
												{
													// 牌が指定された。

													ponLoop = players.get(i).tehai.get(dahaiIndex) == command2.pai;

													if (ponLoop)
													{
														// 食い替え。

														ui.displayMessage("食い替えはできません");
													}
												}
												else
												{
													// 牌が指定されなかった。

													ponLoop = false;
												}
											}
											while (ponLoop);

											if (players.get(i).isValid())
											{
												// プレイ続行。

												sutehai = players.get(i).tehai.get(dahaiIndex);
												players.get(i).tehai.remove(dahaiIndex);

												// 牌表示。
												ui.displayTehai(i, players.get(i));

												ui.displaySutehai(i, players.get(i).sutehai.size(), sutehai);
												players.get(i).sutehai.add(sutehai);

												// ツモ牌非表示。
												ui.displayTsumo(i, players.get(i), null);
											}
											break;

										case Daiminkan:
											DaiminkanCommand command3 = (DaiminkanCommand)command;

											players.notifyNaki();

											ui.blankSutehai(i, players.get(i).sutehai.size() - 1);

											// 順番抜かし。
											i = (i + 1) % players.size();

											players.get(i).daiminkan(command3.pai, command3.direction);
											kan = true;

											ui.displayTehai(i, players.get(i));
											players.get(i).onAfterNaki();

											Thread.sleep(wait * 5);

											// 嶺上牌。
											tsumohai = allPai.getRinshanpai();
											dora = allPai.getDora(false);
											dora2 = new DoraPaiCollection(dora);
											ui.displayDora(dora, false);

											if (players.countAllPlayerDora(dora2.get(dora2.size() - 1)) > 0)
											{
												// ドラが手牌にある。

												ui.flushDora(players, dora2, true);
											}

											if (dora2.contains(tsumohai))
											{
												// ツモ牌はドラである。

												ui.flushTsumo(i, players.get(i), true);
											}

											// ツモループ。暗槓・小明槓をこれで処理。
											tsumoLoopResult =
												tsumoLoop(i, tsumohai, dahaiIndex);

											agari = tsumoLoopResult.agari;
											dahaiIndex = tsumoLoopResult.dahaiIndex;

											if (players.get(i).isValid())
											{
												// プレイ続行。

												if (dahaiIndex <= players.get(i).tehai.size() - 1)
												{
													// ツモ切りではない。

													// 牌差し替え。
													sutehai = players.get(i).tehai.get(dahaiIndex);
													players.get(i).tehai.set(dahaiIndex, tsumohai);
													Collections.sort(players.get(i).tehai, new PaiComparator());

													// 牌表示。
													ui.displayTehai(i, players.get(i));
												}
												else
												{
													// ツモ切りである。

													sutehai = tsumohai;
												}

												// 牌表示。
												ui.displayTehai(i, players.get(i));

												ui.displaySutehai(i, players.get(i).sutehai.size(), sutehai);
												players.get(i).sutehai.add(sutehai);

												// ツモ牌非表示。
												ui.displayTsumo(i, players.get(i), null);
											}

											break;

										default:
											throw new Exception("不正なコマンド " + command);
									}
								}

								if (! agari)
								{
									// まだ和がりではない。

									Thread.sleep(wait * 5);

									if (players.get(i).isValid())
									{
										// プレイ続行。

										players.get(i).onAfterNaki();
										ui.clearRiichiPai(i);
									}
								}
							}
							else
							{
								// プレイ中断。

								loop = false;
							}

							if (! agari)
							{
								// まだ和がりではない。

								players.get(i).updateMachi();
							}
						}
						else
						{
							// 和がり。

							loop = false;
						}
					}
					else
					{
						// もうツモ牌がない。

						ui.notifyAgari(0, null, null);
						loop = false;
					}
				}

				jun++;
			}
		}
		catch (Exception exception)
		{
			exception.printStackTrace();
		}

//		Log.i("GameThread", "スレッド終了");
	}
}
