package kumagai.Maajan.logic;

import java.util.*;

/**
 * 人間のプレイヤー。
 */
public class HumanPlayer
	extends Player
{
	private final MessageQueue queue;
	private final MaajanUI ui;
	private final int wait;
	private final int playerIndex = 0;
	private int dahaiIndex = -1;

	/**
	 * 人間のプレイヤーオブジェクトを構築。
	 * @param jikaze 自風
	 * @param queue コマンドキュー
	 * @param ui ユーザーインターフェイス
	 * @param wait ウエイト（ミリ秒）
	 */
	public HumanPlayer
		(PaiKind jikaze, MessageQueue queue, MaajanUI ui, int wait)
	{
		super("あなた", jikaze);

		this.queue = queue;
		this.ui = ui;
		this.wait = wait;
	}

	/**
	 * 局開始時。初期化処理を行う。
	 */
	public void onGameStart()
	{
		ui.setButtonState(false, false, false, false, false, false);
		ui.setNakiButtonState(true);
	}

	/**
	 * 和がるかをプレイヤーに問い合わせる。
	 * ereturn true=和がる／false=和がらない
	 */
	public boolean promptAgari()
		throws InterruptedException
	{
		ui.setButtonState(true, false, false, false, true, true);

		Message command = queue.get();

		if (command != null)
		{
			// コマンドを受信した。

			if (command.type == MessageType.Agari)
			{
				// 和がりである。

				return true;
			}
		}

		return false;
	}

	/**
	 * 和がりか槓かをプレイヤーに問い合わせる。
	 * @param agari true=和がれる／false=できない
	 * @param kan true=槓できる／false=できない
	 * @return 実行するコマンド
	 */
	public Command promptAgariOrKan(boolean agari, boolean kan)
	{
		return new AgariCommand(PlayerDirection.Jibun.ordinal());
	}

	/**
	 * ドラ牌をカウントする。
	 * @param dora ドラ牌
	 * @return ドラ牌の個数
	 */
	public int countDora(Pai dora)
	{
		int count = 0;

		for (int i=0 ; i<tehai.size() ; i++)
		{
			if (dora.equals2(tehai.get(i)))
			{
				// ドラ牌である。

				count++;
			}
		}

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
	 * 局終了時処理。
	 */
	public void terminateKyoku()
	{
		if (dahaiIndex >= 0)
		{
			// 牌選択されている。

			ui.advancePai(playerIndex, dahaiIndex, false);
		}

		dahaiIndex = -1;
	}

	/**
	 * ツモ時の処理。
	 * @param tsumohai ツモ牌
	 * @param canRiichi true=リーチできる／できない
	 * @param canKan true=槓できる／できない
	 * @param canAgari true=和がれる／できない
	 * @return ツモに対するコマンド
	 */
	public Command onTsumo(Pai tsumohai, boolean canRiichi, boolean canKan,
		boolean canAgari)
		throws Exception
	{
		Command command;

		ui.setButtonState(
			canRiichi,
			false,
			false,
			canKan,
			canAgari,
			canAgari); // キャンセルは和がりについてのみ

		boolean loop1 = true;

		do
		{
			Message message = queue.get();

			if (message != null)
			{
				// メッセージあり。

				switch (message.type)
				{
					case Riichi:
						command = new Command(CommandType.Riichi);
						loop1 = false;
						break;

					case Agari:
						command =
							new AgariCommand(PlayerDirection.Jibun.ordinal());
						loop1 = false;
						break;

					case Kan:
						ArrayList<Pai> kanzai = getKanzai(tsumohai);

						if (kanzai.size() == 1)
						{
							// 槓できる牌は１個だけ。

							if (tehaiHasAnko(kanzai.get(0)))
							{
								// 暗刻がある。

								if (dahaiIndex >= 0)
								{
									// 牌選択されている。

									ui.advancePai
										(playerIndex, dahaiIndex, false);
								}

								dahaiIndex = -1;
								command = new AnkanCommand(kanzai.get(0));
							}
							else
							{
								// 暗刻はない。明刻があるものとする。

								command = new ShouminkanCommand(kanzai.get(0));
							}
						}
						else if (kanzai.size() > 1)
						{
							// 槓できる牌が複数ある。

							boolean loop2 = false;
							command = null;

							boolean [] highlight = new boolean [tehai.size()];

							for (int i=0 ; i<tehai.size() ; i++)
							{
								highlight[i] = kanzai.contains(tehai.get(i));
							}

							ui.highlightTehai(playerIndex, highlight);

							do
							{
								Message message2 = queue.get();

								if (message2 != null)
								{
									// メッセージあり。

									if (message2 instanceof DahaiMessage)
									{
										// 牌タッチである。

										Pai kanpai;

										int kanIndex =
											((DahaiMessage)message2).index;

										if (kanIndex < tehai.size() - 1)
										{
											// 手牌である。

											kanpai = tehai.get(kanIndex);
										}
										else
										{
											// ツモ牌である。

											kanpai = tsumohai;

											System.out.println("ツモ牌を槓");
										}

										if (kanzai.contains(kanpai))
										{
											// 槓できる牌である。

											if (tehaiHasAnko(kanpai))
											{
												// 暗刻がある。

												System.out.println(
													kanpai +
													"の槓が選ばれました");

												command =
													new AnkanCommand(kanpai);
											}
											else
											{
												// 暗刻はない。明刻があるものと
												// する。

												command =
													new ShouminkanCommand
														(kanpai);
											}
										}
										else
										{
											// 槓できる牌ではない。

											loop2 = true;
										}
									}
								}
							}
							while (loop2);

							ui.highlightTehai(playerIndex, null);
						}
						else
						{
							// 槓できる牌は１個もない。来ないはず。

							throw new Exception("槓できる牌は１個もない。");
						}

						loop1 = false;
						break;

					case Dahai:
						if (dahaiIndex != ((DahaiMessage)message).index)
						{
							// 直前の選択と異なる。

							if (dahaiIndex >= 0)
							{
								// 牌選択されている。

								ui.advancePai(playerIndex, dahaiIndex, false);
							}

							dahaiIndex = ((DahaiMessage)message).index;
							ui.advancePai(playerIndex, dahaiIndex, true);
							command = null;

							// 待ち判定。
							ArrayList<Pai> tehai2 = new ArrayList<Pai>(tehai);
							if (dahaiIndex < tehai.size())
							{
								tehai2.set(dahaiIndex, tsumohai);
								Collections.sort(tehai2, new PaiComparator());
							}

							// ４面子＋１雀頭かを判定。
							ArrayList<Integer> machi =
								new MachiPattern(tehai2, true).getMachi();

							if (machi.size() <= 0)
							{
								// ４面子＋１雀頭ではない。

								// 七対子かを判定。
								machi = ChiitoitsuCheck.getMachi(tehai2);
							}

							if (machi.size() <= 0)
							{
								// ４面子＋１雀頭・七対子ではない。

								// 国士無双かを判定。
								machi = KokushimusouCheck.getMachi(tehai2);
							}

							ui.displayMachi(playerIndex, machi);
						}
						else
						{
							// 直前の選択と同じ。

							ui.advancePai(playerIndex, dahaiIndex, false);
							dahaiIndex = -1;
							command =
								new DahaiCommand(((DahaiMessage)message).index);
							loop1 = false;
						}
						break;

					case Terminate:
						terminateKyoku();
						command = null;
						break;

					default:
						System.out.println(message);
						command = null;
						loop1 = false;
						break;
				}
			}
			else
			{
				// メッセージなし。

				loop1 = false;
				command = null;
			}
		}
		while (loop1);

		return command;
	}

	/**
	 * 鳴き後の打牌。
	 * @param tsumohai ツモ牌（大明槓の場合のみ）
	 * @return 打牌インデックス
	 */
	public int dahaiAfterNaki(Pai tsumohai)
		throws Exception
	{
		int dahaiIndex2;
		boolean loop = true;

		do
		{
			dahaiIndex2 = dahai(null);

			if (dahaiIndex2 >= 0)
			{
				// 打牌された。

				if (dahaiIndex >= 0)
				{
					// 直前の選択あり。

					ui.advancePai(playerIndex, dahaiIndex, false);
				}

				ui.advancePai(playerIndex, dahaiIndex2, true);

				if (dahaiIndex == dahaiIndex2)
				{
					// 直線の牌選択と同じ。

					loop = false;
					ui.advancePai(playerIndex, dahaiIndex, false);
				}
				else
				{
					// 直前の牌選択と異なる。

					ArrayList<Pai> tehai2 = new ArrayList<Pai>(tehai);

					if (tsumohai != null)
					{
						// ツモ牌あり。

						tehai2.add(tsumohai);
					}

					if (dahaiIndex2 < tehai2.size())
					{
						// ツモ切り以外。

						tehai2.remove(dahaiIndex2);
						ArrayList<Integer> machi =
							new MachiPattern(tehai2, true).getMachi();

						if (machi.size() <= 0)
						{
							// ４面子＋１雀頭ではない。

							// 七対子かを判定。
							machi = ChiitoitsuCheck.getMachi(tehai);
						}

						if (machi.size() <= 0)
						{
							// ４面子＋１雀頭・七対子ではない。

							// 国士無双かを判定。
							machi = KokushimusouCheck.getMachi(tehai);
						}

						ui.displayMachi(playerIndex, machi);
					}
				}

				dahaiIndex = dahaiIndex2;
			}
			else
			{
				// 打牌はキャンセルされた。

				loop = false;
			}
		}
		while (loop);

		dahaiIndex = -1;

		return dahaiIndex2;
	}

	/**
	 * 他家打牌時。
	 * @param direction 打牌したプレイヤー
	 * @param pai 打った牌
	 * @return 実行するコマンド
	 */
	public Command onTaachaDahai(int direction, Pai pai) throws Exception
	{
		boolean chii = false;
		boolean pon = false;
		boolean kan = false;
		boolean agari = false;
		boolean [] chiiFlag = new boolean [5];

		if (machi != null)
		{
			// 待ちはある。

			if (machi.contains(pai.kind))
			{
				// 待ち牌である。

				// 和がりかを問い合わせる。
				agari = true;
			}
		}

		if (naki)
		{
			// 鳴き有り。

			int count = 0;

			// 同じ牌をカウント。
			for (int i = 0; i < tehai.size(); i++)
			{
				if (tehai.get(i).equals2(pai))
				{
					// 同じ牌。

					count++;
				}
			}

			if (count >= 2)
			{
				// ２個以上ある。

				pon = true;

				if (count >= 3)
				{
					// ３個以上ある。

					// 槓
					kan = true;
				}
			}

			for (int i=0 ; i<tehai.size() ; i++)
			{
				if (tehai.get(i).isShuupai() && tehai.get(i).isSameColor(pai))
				{
					// 同じ柄の数牌。

					if (tehai.get(i).kind - pai.kind >= -2 && tehai.get(i).kind - pai.kind <= 2)
					{
						// ±２の範囲内。

						chiiFlag[tehai.get(i).kind - pai.kind + 2] = true;
					}
				}
			}

			chii =
				chiiFlag[0] && chiiFlag[1] ||
				chiiFlag[1] && chiiFlag[3] ||
				chiiFlag[3] && chiiFlag[4];
		}

		Command command = null;

		if (chii || pon || kan|| agari)
		{
			// いずれか有効にする。

			ui.setButtonState(false, chii, pon, kan, agari, true);

			// プレイヤーに問い合わせ。
			Message message = queue.get();

			if (message != null)
			{
				// メッセージあり。

				switch (message.type)
				{
					case Agari:
						command = new AgariCommand(direction);
						break;

					case Pon:
						command = new PonCommand(pai, direction);
						break;

					case Chii:
						if (chiiFlag[0] && chiiFlag[1] && !chiiFlag[3])
						{
							// 前２つに対してチー確定。

							command =
								new ChiiCommand(pai, direction, tehai, -2, -1);
						}
						else if (!chiiFlag[0] && chiiFlag[1] &&
								chiiFlag[3] && !chiiFlag[4])
						{
							// 前後１つに対してチー確定。

							command =
								new ChiiCommand(pai, direction, tehai, -1, 1);
						}
						else if (!chiiFlag[1] && chiiFlag[3] && chiiFlag[4])
						{
							// 後２つに対してチー確定。

							command =
								new ChiiCommand(pai, direction, tehai, 1, 2);
						}
						else
						{
							// 候補が複数ありうる。プレイヤーに問い合わせ。

							int chiiCount = 0;
							int chiiIndex1 = 0;
							int chiiIndex2 = 0;

							boolean loop = true;

							boolean [] highlight = new boolean [tehai.size()];

							for (int i=0 ; i<tehai.size() ; i++)
							{
								if (tehai.get(i).isShuupai())
								{
									// 数牌である。

									if (tehai.get(i).isSameColor(pai) &&
										tehai.get(i).kind == pai.kind - 1)
									{
										// １個前がある。

										highlight[i] = true;
									}

									if (tehai.get(i).isSameColor(pai) &&
										tehai.get(i).kind == pai.kind + 1)
									{
										// １個次がある。

										highlight[i] = true;
									}

									if (tehai.get(i).isSameColor(pai) &&
										tehai.get(i).kind == pai.kind - 2 &&
										chiiFlag[1])
									{
										// 連続する２個前がある。

										highlight[i] = true;
									}

									if (tehai.get(i).isSameColor(pai) &&
										tehai.get(i).kind == pai.kind + 2 &&
										chiiFlag[3])
									{
										// 連続する２個次がある。

										highlight[i] = true;
									}
								}
							}

							ui.highlightTehai(playerIndex, highlight);

							do
							{
								// 牌選択。
								Message message2 = queue.get();

								if (message2 != null)
								{
									// コマンドは来た。

									if (message2 instanceof DahaiMessage)
									{
										// 打牌である。

										DahaiMessage chiiMessage =
											(DahaiMessage)message2;

										if (chiiCount == 0)
										{
											// １個目。

											chiiIndex1 = chiiMessage.index;
											ui.advancePai
												(playerIndex, chiiIndex1, true);

											chiiCount++;
										}
										else if (chiiCount == 1)
										{
											// ２個目。

											chiiIndex2 = chiiMessage.index;
											ui.advancePai(playerIndex, chiiIndex2, true);

											if (tehai.get(chiiIndex1).kind + 2 == pai.kind && tehai.get(chiiIndex2).kind + 1 == pai.kind ||
												tehai.get(chiiIndex1).kind + 1 == pai.kind && tehai.get(chiiIndex2).kind + 2 == pai.kind ||
												tehai.get(chiiIndex1).kind + 1 == pai.kind && tehai.get(chiiIndex2).kind - 1 == pai.kind ||
												tehai.get(chiiIndex1).kind - 1 == pai.kind && tehai.get(chiiIndex2).kind + 1 == pai.kind ||
												tehai.get(chiiIndex1).kind - 1 == pai.kind && tehai.get(chiiIndex2).kind - 2 == pai.kind ||
												tehai.get(chiiIndex1).kind - 2 == pai.kind && tehai.get(chiiIndex2).kind - 1 == pai.kind)
											{
												// 有効。

												command = new ChiiCommand(pai, direction, chiiIndex1, chiiIndex2);
												loop = false;
											}
											else
											{
												// 不正。

												Thread.sleep(wait);
												chiiCount = 0;
											}

											ui.advancePai(playerIndex, chiiIndex1, false);
											ui.advancePai(playerIndex, chiiIndex2, false);
										}
									}
									else
									{
										// 牌選択以外のコマンド。

										loop = false;
									}
								}
								else
								{
									// コマンドは来ない。

									loop = false;
								}
							}
							while (loop);

							ui.highlightTehai(playerIndex, null);
						}
						break;

					case Kan:
						command = new DaiminkanCommand(pai, direction);
						break;

					case Terminate:
						terminateKyoku();
						command = null;
						break;
				}
			}
		}

		return command;
	}

	/**
	 * 他家小明槓時。
	 * @param direction 小明槓したプレイヤー
	 * @param pai 小明槓した牌
	 * @return 実行するコマンド
	 */
	public Command onTaachaShouminkan(int direction, Pai pai)
		throws InterruptedException
	{
		Command command = null;

		if (canAgari(pai))
		{
			// 和がれる。

			ui.setButtonState(false, false, false, false, true, true);

			// プレイヤーに問い合わせ。
			Message message = queue.get();

			if (message != null)
			{
				// メッセージあり。

				switch (message.type)
				{
					case Agari:
						command = new AgariCommand(direction);
						break;
				}
			}
		}

		return command;
	}

	/**
	 * 鳴いた後。ボタン初期化。
	 */
	public void onAfterNaki()
	{
		ui.setButtonState(false, false, false, false, false, false);
	}

	/**
	 * 打牌。
	 * @param tsumo ツモ牌／null＝ツモ牌なし チー・ポン時
	 * @return 打牌する牌のインデックス
	 */
	public int dahai(Pai tsumo)
		throws InterruptedException
	{
		int index = -1;

		for (boolean loop = true ; loop ; )
		{
			Message message = queue.get();

			if (message != null)
			{
				// キャンセルではない。

				if (message instanceof DahaiMessage)
				{
					// DahaiMessageである。

					DahaiMessage message2 = (DahaiMessage)message ;

					loop = false;
					index = message2.index;
				}
			}
			else
			{
				// キャンセルされた。

				valid = false;
				loop = false;
			}
		}

		return index;
	}

	/**
	 * 終了。
	 */
	public void breakPlay()
	{
		super.breakPlay();

		queue.breakQueue();
	}
}
