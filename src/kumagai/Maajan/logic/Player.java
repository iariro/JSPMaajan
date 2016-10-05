package kumagai.Maajan.logic;

import java.util.*;

/**
 * プレイヤー。
 */
public abstract class Player
{
	protected boolean valid = true;
	protected boolean menzen = true;

	public boolean naki = true;
	public boolean riichi = false;
	public int riichiJun = 0;
	public boolean nakiOnRiichi = false;

	public final String name;
	public final HashMap<Integer, Player> taacha;
	public final PaiKind jikaze;
	public ArrayList<Pai> tehai;
	public ArrayList<Naki> nakiList;
	public ArrayList<Integer> machi;
	public ArrayList<Pai> sutehai;
	public TenpaiType tenpaiType;
	public MachiPattern machiPattern;

	/**
	 * プレイ続行状態か＝Breakしてないかを取得。
	 * @return true=プレイ続行状態／false=プレイ続行状態ではない
	 */
	public boolean isValid()
	{
		return valid;
	}

	/**
	 * 門前であるかを取得。
	 * @return true=門前である／false=門前ではない
	 */
	public boolean isMenzen()
	{
		return menzen;
	}

	/**
	 * 門前であるかを設定。デバッグ用。
	 * @param menzen true=門前である／false=門前ではない
	 */
	public void setMenzen(boolean menzen)
	{
		this.menzen = menzen;
	}

	/**
	 * 各種プレイヤー情報を初期化しプレイヤーオブジェクトを構築。
	 * @param name 名前
	 * @param jikaze 自風
	 */
	public Player(String name, PaiKind jikaze)
	{
		this.name = name;
		this.jikaze = jikaze;
		this.taacha = new HashMap<Integer, Player>();
		this.nakiList = new ArrayList<Naki>();
		this.tehai = new ArrayList<Pai>();
		this.sutehai = new ArrayList<Pai>();
	}

	/**
	 * 和がるかをプレイヤーに問い合わせる。
	 * @return true=和がる／false=和がらない
	 */
	public abstract boolean promptAgari() throws InterruptedException;

	/**
	 * 和がりか槓かをプレイヤーに問い合わせる。
	 * @param agari true=和がれる／false=できない
	 * @param kan true=槓できる／false=できない
	 * @return 実行するコマンド
	 */
	public abstract Command promptAgariOrKan(boolean agari, boolean kan);

	/**
	 * 打牌。
	 * @param tsumo ツモ牌
	 * @return 打牌する牌の手牌中のインデックス
	 */
	public abstract int dahai(Pai tsumo) throws Exception;

	/**
	 * ツモ時の処理。
	 * @param tsumohai ツモ牌
	 * @param canRiichi true=リーチできる／できない
	 * @param canKan true=槓できる／できない
	 * @param canAgari true=和がれる／できない
	 * @return ツモに対するコマンド
	 */
	public abstract Command onTsumo(Pai tsumohai, boolean canRiichi, boolean canKan, boolean canAgari) throws Exception;

	/**
	 * 他家打牌時。
	 * @param direction 打牌したプレイヤー
	 * @param pai 打った牌
	 * @return 実行するコマンド
	 */
	public abstract Command onTaachaDahai(int direction, Pai pai) throws Exception;

	/**
	 * 他家小明槓時。
	 * @param direction 打牌したプレイヤー
	 * @param pai 打った牌
	 * @return 実行するコマンド
	 */
	public abstract Command onTaachaShouminkan(int direction, Pai pai) throws InterruptedException;

	/**
	 * 局開始時。
	 */
	public void onGameStart()
	{
		// 何もしない。
	}

	/**
	 * 鳴いた後。
	 */
	public void onAfterNaki()
	{
		// 何もしない。
	}

	/**
	 * ドラ牌をカウントする。
	 * @param tsumohai ツモ牌（大明槓の場合のみ）
	 * @return 打牌インデックス
	 */
	public abstract int dahaiAfterNaki(Pai tsumohai) throws Exception;

	/**
	 * ドラ牌をカウントする。
	 * @param dora ドラ牌
	 * @return ドラ牌の数
	 */
	public abstract int countDora(Pai dora);

	/**
	 * 局終了時処理。
	 */
	public void terminateKyoku()
	{
		// 何もしない。
	}

	/**
	 * 対戦相手となる他家を登録する。
	 * @param direction 登録するプレイヤーの方向
	 * @param player プレイヤー
	 */
	public void addTaacha(PlayerDirection direction, Player player)
	{
		taacha.put(direction.ordinal(), player);
	}

	/**
	 * 待ち牌を判定・取得する。
	 */
	public void updateMachi()
		throws Exception
	{
		tenpaiType = null;

		// 待ち判定。
		// ４面子＋１雀頭かを判定。
		machiPattern = new MachiPattern(tehai, true);

		machi = machiPattern.getMachi();

		if (machi.size() > 0)
		{
			// ４面子＋１雀頭である。

			tenpaiType = TenpaiType.Mentsu;
		}
		else
		{
			// ４面子＋１雀頭ではない。

			// 七対子かを判定。
			machi = ChiitoitsuCheck.getMachi(tehai);

			if (machi.size() > 0)
			{
				// 七対子である。

				tenpaiType = TenpaiType.Chiitoitsu;
			}
			else
			{
				// ４面子＋１雀頭・七対子ではない。

				// 国士無双かを判定。
				machi = KokushimusouCheck.getMachi(tehai);

				if (machi.size() > 0)
				{
					// 国士無双である。

					tenpaiType = TenpaiType.Kokushimusou;
				}
			}
		}
	}

	/**
	 * リーチ宣言牌を判定・取得する。
	 * @param tsumohai ツモ牌
	 * @return リーチ宣言牌フラグ配列
	 */
	public boolean [] getRiichiSengenPai(Pai tsumohai) throws Exception
	{
		// リーチ宣言牌判定。
		ArrayList<Pai> tehai2 = new ArrayList<Pai>(tehai);

		int tsumoIndex = tehai2.size();

		for (int j = 0; j < tehai2.size(); j++)
		{
			if (tsumohai.kind < tehai2.get(j).kind)
			{
				// ツモ牌が収まる場所である。

				tsumoIndex = j;
				break;
			}
		}

		tehai2.add(tsumohai);
		Collections.sort(tehai2, new PaiComparator());

		SutehaiPattern sute = new SutehaiPattern(tehai2);

		if (sute.size() > 0)
		{
			// テンパイしている。

			boolean [] riichiSengenPai = new boolean [tehai2.size()];

			for (int j = 0; j < tehai2.size(); j++)
			{
				int k;

				if (j < tsumoIndex)
				{
					// ツモ牌が収まる位置より左。

					k = j;
				}
				else if (j > tsumoIndex)
				{
					// ツモ牌が収まる位置より右。

					k = j - 1;
				}
				else
				{
					// ツモ牌が収まる位置である。

					k = tehai2.size() - 1;
				}

				riichiSengenPai[k] = sute.contains(j);
			}

			return riichiSengenPai;
		}
		else
		{
			// テンパイしていない。

			return null;
		}
	}

	/**
	 * 和がりかを判定。
	 * @param tsumohai ツモ牌
	 * @return true=和がり／false=和がりではない
	 */
	public boolean canAgari(Pai tsumohai)
	{
		if (machi != null)
		{
			// 待ち情報はある。

			if (machi.contains(tsumohai.kind))
			{
				// 待ち牌である。

				return true;
			}
		}

		return false;
	}

	/**
	 * 槓できる牌を判定・取得する。
	 * @param tsumohai ツモ牌
	 * @return 槓できる牌のコレクション
	 */
	public ArrayList<Pai> getKanzai(Pai tsumohai)
	{
		ArrayList<Pai> kanzai = new ArrayList<Pai>();
		HashMap<Integer, Integer> paiCount = new HashMap<Integer, Integer>();

		paiCount.put(tsumohai.kind, 1);

		for (int i=0 ; i<tehai.size() ; i++)
		{
			if (paiCount.containsKey(tehai.get(i).kind))
			{
				// すでに出現している牌である。

				paiCount.put(
					tehai.get(i).kind,
					paiCount.get(tehai.get(i).kind) + 1);
			}
			else
			{
				// 初めて出現する牌である。

				paiCount.put(tehai.get(i).kind, 1);
			}
		}

		for (int i=0 ; i<nakiList.size() ; i++)
		{
			if (nakiList.get(i).type == MentsuType.Pon.ordinal())
			{
				// ポンである。

				for (int j=0 ; j<nakiList.get(i).size() ; j++)
				{
					if (paiCount.containsKey(nakiList.get(i).get(j).kind))
					{
						// すでに出現している牌である。

						paiCount.put(
							nakiList.get(i).get(j).kind,
							paiCount.get(nakiList.get(i).get(j).kind) + 1);
					}
					else
					{
						// 初めて出現する牌である。

						paiCount.put(nakiList.get(i).get(j).kind, 1);
					}
				}
			}
		}

		for (int pai : paiCount.keySet())
		{
			if (paiCount.get(pai) == 4)
			{
				// ４枚ある。

				kanzai.add(new Pai(pai));
			}
		}

		return kanzai;
	}

	/**
	 * リーチ中に指定のツモ牌で暗槓が可能かをチェック。
	 * @param tsumo ツモ牌
	 * @return true=暗槓可能／false=不能
	 */
	public boolean checkAnkanOnRiichi(Pai tsumo)
		throws Exception
	{
		ArrayList<Pai> tehai2 = new ArrayList<Pai>(tehai);

		int count = 0;

		for (int i=tehai2.size()-1 ; i>=0 ; i--)
		{
			if (tehai2.get(i).equals2(tsumo))
			{
				// ツモ牌と同じ牌。

				tehai2.remove(i);
				count++;
			}
		}

		boolean canKan;

		if (count == 3)
		{
			// ツモ牌と同じ牌は３枚。

			MachiPattern machiPattern = new MachiPattern(tehai2, true);

			canKan =
				this.machiPattern.machiElementCollection.equals2
					(machiPattern.machiElementCollection);

			if (!canKan)
			{
				System.out.println(
					this.machiPattern.machiElementCollection + "と\r\n" +
					machiPattern.machiElementCollection);
			}
		}
		else
		{
			// ツモ牌と同じ牌は３枚ではない。

			canKan = false;
		}

		return canKan;
	}

	/**
	 * 打牌を他家に通知。
	 * @param pai 打牌した牌
	 * @return コマンド
	 */
	public Command notifyDahai(Pai pai)
		throws Exception
	{
		for (int i=1 ; i<4 ; i++)
		{
			if (taacha.containsKey(i))
			{
				// 相手にいる。

				Command command = taacha.get(i).onTaachaDahai(i, pai);

				// TODO: 全員に問い合わせ、優先順位に従って処理する必要がある。

				if (command != null)
				{
					// コマンドはある。

					return command;
				}
			}
		}

		return null;
	}

	/**
	 * 小明槓を他家に通知。
	 * @param pai 小明槓する牌
	 * @return コマンド
	 */
	public Command notifyShouminkan(Pai pai) throws InterruptedException
	{
		for (int d=PlayerDirection.Shimocha.ordinal() ;
			d<=PlayerDirection.Kamicha.ordinal() ;
			d++)
		{
			if (taacha.containsKey(d))
			{
				// 相手にいる。

				Command command = taacha.get(d).onTaachaShouminkan(d, pai);

				if (command != null)
				{
					// 槍槓。

					return command;
				}
			}
		}

		return null;
	}

	/**
	 * 手牌に指定の牌の暗刻または４枚があるかを取得。
	 * @param pai チェックする牌
	 * @return true=ある／false=ない
	 */
	public boolean tehaiHasAnko(Pai pai)
	{
		int count = 0;

		for (int i=0 ; i<tehai.size() ; i++)
		{
			if (tehai.get(i).equals2(pai))
			{
				// 指定の牌。

				count++;
			}
		}

		return count >= 3;
	}

	/**
	 * チーを実行する。
	 * @param command チーコマンド
	 */
	public void chii(ChiiCommand command)
	{
		Pai pai2 = tehai.get(command.index1);
		Pai pai3 = tehai.get(command.index2);

		tehai.remove(command.index2);
		tehai.remove(command.index1);

		nakiList.add(
			new Chii(
				command.direction,
				command.pai,
				pai2,
				pai3));

		menzen = false;
	}

	/**
	 * ポンを実行。
	 * @param pai ポンする牌
	 * @param direction ポンする牌を捨てたプレイヤーの方向
	 */
	public void pon(Pai pai, int direction)
		throws Exception
	{
		int count = 0;

		for (int i=tehai.size()-1 ; i>=0 && count < 2 ; i--)
		{
			if (tehai.get(i).equals2(pai))
			{
				// ポンする牌である。

				count++;
				tehai.remove(i);
			}
		}

		if (count == 2)
		{
			// 対象は２枚である。

			nakiList.add(new Pon(direction, pai));

			menzen = false;
		}
		else
		{
			// 対象は２枚ではない。

			throw new Exception("無効なポン");
		}
	}

	/**
	 * 暗槓を実行する。
	 * @param kanpai 暗槓する牌
	 * @param tsumohai ツモ牌
	 */
	public void ankan(Pai kanpai, Pai tsumohai) throws Exception
	{
		int count = 0;

		if (tsumohai.equals2(kanpai))
		{
			// ツモ牌は槓する牌である。

			count++;
		}

		for (int i=tehai.size()-1 ; i>=0 && count < 4 ; i--)
		{
			if (tehai.get(i).equals2(kanpai))
			{
				// 槓する牌である。

				count++;
				tehai.remove(i);
			}
		}

		if (count == 4)
		{
			// 対象は３枚である。

			if (!tsumohai.equals2(kanpai))
			{
				// ツモ牌は槓する牌ではない。

				tehai.add(tsumohai);
				Collections.sort(tehai, new PaiComparator());
			}

			nakiList.add(
				new Kan(
					PlayerDirection.Jibun.ordinal(),
					MentsuType.Ankan.ordinal(),
					kanpai));

			// 待ちを更新。
			updateMachi();
		}
		else
		{
			// 対象は３枚ではない。

			throw new Exception("無効な槓" + count);
		}
	}

	/**
	 * 小明槓を実行。
	 * @param kanpai 槓する牌
	 * @param tsumohai ツモ牌
	 */
	public void shouminkan(Pai kanpai, Pai tsumohai) throws Exception
	{
		int index = -1;

		if (!tsumohai.equals2(kanpai))
		{
			// ツモ牌は槓する牌ではない。

			for (int i=0 ; i<tehai.size() ; i++)
			{
				if (tehai.get(i).equals2(kanpai))
				{
					// 槓する牌である。

					index = i;
				}
			}

			if (index < 0)
			{
				// 手牌にも槓する牌なし。

				throw new Exception("無効な槓");
			}
		}

		for (int i=0 ; i<nakiList.size() ; i++)
		{
			if (nakiList.get(i).type == MentsuType.Pon.ordinal() &&
				nakiList.get(i).get(0).equals2(kanpai))
			{
				// 指定の牌のポンである。

				if (!tsumohai.equals2(kanpai))
				{
					// ツモ牌は槓する牌ではない。

					tehai.set(index, tsumohai);
					Collections.sort(tehai, new PaiComparator());
				}

				nakiList.get(i).type = MentsuType.Shouminkan.ordinal();
				nakiList.get(i).add(kanpai);

				// 待ちを更新。
				updateMachi();

				return;
			}
		}

		throw new Exception("無効な槓");
	}

	/**
	 * 大明槓を実行する。
	 * @param pai 大明槓する牌
	 * @param direction 槓する牌を捨てたプレイヤーの方向
	 */
	public void daiminkan(Pai pai, int direction)
		throws Exception
	{
		int count = 0;

		for (int i=tehai.size()-1 ; i>=0 && count < 3 ; i--)
		{
			if (tehai.get(i).equals2(pai))
			{
				// 槓する牌。

				count++;
				tehai.remove(i);
			}
		}

		if (count == 3)
		{
			// 対象は３枚である。

			nakiList.add(new Kan(direction, MentsuType.Daiminkan.ordinal(), pai));

			menzen = false;
		}
		else
		{
			// 対象は３枚ではない。

			throw new Exception("不正な槓");
		}
	}

	/**
	 * 終了。
	 */
	public void breakPlay()
	{
		valid = false;
	}

	/**
	 * 手牌，鳴き牌を文字列化。
	 * @return 手牌，鳴き牌一覧
	 */
	public String dump()
	{
		StringBuilder builder = new StringBuilder();

		for (int i=0 ; i<tehai.size() ; i++)
		{
			if (i > 0)
			{
				// ２回目以降。

				builder.append(" ");
			}

			builder.append(tehai.get(i).kind);
		}

		for (int i=0 ; i<nakiList.size() ; i++)
		{
			builder.append("／" + nakiList.get(i).type + ":");

			for (int j=0 ; j<nakiList.get(i).size() ; j++)
			{
				if (j > 0)
				{
					// ２回目以降。

					builder.append(" ");
				}

				builder.append(nakiList.get(i).get(j).kind);
			}
		}

		return builder.toString();
	}
}
