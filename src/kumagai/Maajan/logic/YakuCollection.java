package kumagai.Maajan.logic;

import java.util.*;

/**
 * 役のコレクション。
 */
public class YakuCollection
	extends ArrayList<YakuType>
{
	private final static ArrayList<Integer> ryuisoList;

	static
	{
		ryuisoList = new ArrayList<Integer>();

		ryuisoList.add(PaiKind.二索.ordinal());
		ryuisoList.add(PaiKind.三索.ordinal());
		ryuisoList.add(PaiKind.四索.ordinal());
		ryuisoList.add(PaiKind.六索.ordinal());
		ryuisoList.add(PaiKind.八索.ordinal());
		ryuisoList.add(PaiKind.發.ordinal());
	}

	public int doraCount = 0;
	public int fu;

	/**
	 * 役判定を行い役コレクションを構築する。
	 * @param player プレイヤー
	 * @param jun 巡
	 * @param nokori 残り牌数
	 * @param tsumo true=ツモ和がり／false=ロン和がり
	 * @param atarihai 当たり牌
	 * @param dora ドラ牌
	 * @param bakaze 場風牌
	 * @param yaku 役
	 */
	public YakuCollection(Player player, int jun, int nokori, boolean tsumo,
		int atarihai, ArrayList<Pai> dora, PaiKind bakaze, YakuType yaku) throws Exception
	{
		Pai atarihai2 = new Pai(atarihai);

		KanseiTehai kanseiTehai = new KanseiTehai(player, new Pai(atarihai));

		ArrayList<Pai> shuntsuAtama = new ArrayList<Pai>();
		ArrayList<Pai> ankoAtama = new ArrayList<Pai>();
		ArrayList<Pai> koutsuAtama = new ArrayList<Pai>();

		for (int i=0 ; i<kanseiTehai.mentsuType.size() ; i ++)
		{
			if (kanseiTehai.mentsuType.get(i) == MentsuType.Shuntsu)
			{
				// 順子である。

				shuntsuAtama.add(kanseiTehai.get(3 * i));
			}
			else if (kanseiTehai.mentsuType.get(i) == MentsuType.Anko)
			{
				// 暗刻である。

				ankoAtama.add(kanseiTehai.get(3 * i));
				koutsuAtama.add(kanseiTehai.get(3 * i));
			}
		}

		for (int i=0 ; i<player.nakiList.size() ; i++)
		{
			if (player.nakiList.get(i).type == MentsuType.Chii.ordinal())
			{
				// チーである。

				Chii chii = (Chii)player.nakiList.get(i);

				shuntsuAtama.add(chii.getMin());
			}
			else if (player.nakiList.get(i).type == MentsuType.Pon.ordinal() ||
					player.nakiList.get(i).type == MentsuType.Ankan.ordinal() ||
					player.nakiList.get(i).type == MentsuType.Daiminkan.ordinal() ||
					player.nakiList.get(i).type == MentsuType.Shouminkan.ordinal())
			{
				// ポン・槓である。

				koutsuAtama.add(player.nakiList.get(i).get(0));
			}
		}

		Collections.sort(shuntsuAtama, new PaiComparator());

		if (player.riichi)
		{
			// リーチをかけている。

			if (player.riichiJun == 1)
			{
				// １巡目でリーチをかけている。

				add(YakuType.ダブルリーチ);
			}
			else
			{
				// １巡目より後でリーチをかけている。

				add(YakuType.リーチ);
			}

			if (player.riichiJun == jun ||
				(player.riichiJun + 1 == jun && tsumo))
			{
				// リーチをかけた巡内または次の順のツモで和がり。

				if (! player.nakiOnRiichi)
				{
					// リーチ中に鳴き無し。

					add(YakuType.一発);
				}
			}
		}

		if (player.isMenzen() && tsumo)
		{
			// 門前であり自模である。

			add(YakuType.門前清自模和);
		}

		boolean tanyao = atarihai2.isChunchanpai();

		for (int i=0 ; i<player.tehai.size() && tanyao ; i++)
		{
			tanyao = player.tehai.get(i).isChunchanpai();
		}

		for (int i=0 ; i<player.nakiList.size() && tanyao ; i++)
		{
			for (int j=0 ; j<player.nakiList.get(i).size() && tanyao ; j++)
			{
				tanyao = player.nakiList.get(i).get(j).isChunchanpai();
			}
		}

		if (tanyao)
		{
			// 断幺。

			add(YakuType.断幺);
		}

		if (player.tenpaiType == TenpaiType.Mentsu && player.isMenzen())
		{
			// 門前である。

			if (kanseiTehai.machiType == MachiType.Ryanmen)
			{
				// 両面待ちである。

				if (shuntsuAtama.size() == 4)
				{
					// ４面子とも順子である。

					if (kanseiTehai.get(12).isShuupai() ||
						(kanseiTehai.get(12).isKazehai() &&
						kanseiTehai.get(12).kind != bakaze.ordinal() &&
						kanseiTehai.get(12).kind != player.jikaze.ordinal()))
					{
						// 頭は数牌または客風牌である。

						add(YakuType.平和);
					}
				}
			}
		}

		if (player.tenpaiType == TenpaiType.Mentsu && player.isMenzen())
		{
			// 門前である。

			int ipeko = 0;

			for (int i=0 ; i<shuntsuAtama.size() - 1 ; i++)
			{
				for (int j=i+1 ; j<shuntsuAtama.size() ; j++)
				{
					if (shuntsuAtama.get(i).equals2(shuntsuAtama.get(j)))
					{
						// 内容一致。

						ipeko++;
					}
				}
			}

			if (ipeko == 1)
			{
				// 一致する順子は１個。

				add(YakuType.一盃口);
			}
			else if (ipeko == 2)
			{
				// 一致する順子は２個。

				add(YakuType.二盃口);
			}
		}

		HashMap<Integer, Boolean> kazehai = new HashMap<Integer, Boolean>();
		HashMap<Integer, Boolean> sangenpai = new HashMap<Integer, Boolean>();

		boolean find = false;

		for (int i=0 ; i<koutsuAtama.size() && !find ; i++)
		{
			find = koutsuAtama.get(i).kind == PaiKind.東.ordinal();
		}
		kazehai.put(PaiKind.東.ordinal(), find);

		find = false;
		for (int i=0 ; i<koutsuAtama.size() && !find ; i++)
		{
			find = koutsuAtama.get(i).kind == PaiKind.南.ordinal();
		}
		kazehai.put(PaiKind.南.ordinal(), find);

		find = false;
		for (int i=0 ; i<koutsuAtama.size() && !find ; i++)
		{
			find = koutsuAtama.get(i).kind == PaiKind.西.ordinal();
		}
		kazehai.put(PaiKind.西.ordinal(), find);

		find = false;
		for (int i=0 ; i<koutsuAtama.size() && !find ; i++)
		{
			find = koutsuAtama.get(i).kind == PaiKind.北.ordinal();
		}
		kazehai.put(PaiKind.北.ordinal(), find);

		find = false;
		for (int i=0 ; i<koutsuAtama.size() && !find ; i++)
		{
			find = koutsuAtama.get(i).kind == PaiKind.白.ordinal();
		}
		sangenpai.put(PaiKind.白.ordinal(), find);

		find = false;
		for (int i=0 ; i<koutsuAtama.size() && !find ; i++)
		{
			find = koutsuAtama.get(i).kind == PaiKind.發.ordinal();
		}
		sangenpai.put(PaiKind.發.ordinal(), find);

		find = false;
		for (int i=0 ; i<koutsuAtama.size() && !find ; i++)
		{
			find = koutsuAtama.get(i).kind == PaiKind.中.ordinal();
		}
		sangenpai.put(PaiKind.中.ordinal(), find);

		if (kazehai.get(player.jikaze.ordinal()))
		{
			// 自風牌。

			add(YakuType.自風牌);
		}

		if (kazehai.get(bakaze.ordinal()))
		{
			// 場風牌。

			add(YakuType.場風牌);
		}

		if (sangenpai.get(PaiKind.白.ordinal()))
		{
			// 白。

			add(YakuType.白);
		}

		if (sangenpai.get(PaiKind.發.ordinal()))
		{
			// 發。

			add(YakuType.發);
		}

		if (sangenpai.get(PaiKind.中.ordinal()))
		{
			// 中。

			add(YakuType.中);
		}

		if (nokori == 0)
		{
			if (tsumo)
			{
				// 海底撈魚。

				add(YakuType.海底撈魚);
			}
			else
			{
				// 河底撈月。

				add(YakuType.河底撈月);
			}
		}

		if (yaku != null)
		{
			// 役あり。嶺上開花と槍槓がありうる。

			add(yaku);
		}

		boolean sanshokudoujun = false;
		boolean ikkitsuukan = false;

		if (shuntsuAtama.size() >= 3)
		{
			// 順子・チーが３つ以上ある。

			sanshokudoujun =
				shuntsuAtama.get(0).isSameNumberInNextColor(shuntsuAtama.get(1)) &&
				shuntsuAtama.get(1).isSameNumberInNextColor(shuntsuAtama.get(2));

			ikkitsuukan =
				shuntsuAtama.get(0).getNumber() == 1 &&
				shuntsuAtama.get(0).kind + 3 == shuntsuAtama.get(1).kind &&
				shuntsuAtama.get(1).kind + 3 == shuntsuAtama.get(2).kind;

			if (shuntsuAtama.size() == 4)
			{
				// 順子・チーが４つある。

				if (! sanshokudoujun)
				{
					// 三色同順未確定。

					sanshokudoujun =
						(shuntsuAtama.get(0).isSameNumberInNextColor(shuntsuAtama.get(1)) &&
						shuntsuAtama.get(1).isSameNumberInNextColor(shuntsuAtama.get(2))) ||

						(shuntsuAtama.get(0).isSameNumberInNextColor(shuntsuAtama.get(1)) &&
						shuntsuAtama.get(1).isSameNumberInNextColor(shuntsuAtama.get(3))) ||

						(shuntsuAtama.get(0).isSameNumberInNextColor(shuntsuAtama.get(2)) &&
						shuntsuAtama.get(2).isSameNumberInNextColor(shuntsuAtama.get(3))) ||

						(shuntsuAtama.get(1).isSameNumberInNextColor(shuntsuAtama.get(2)) &&
						shuntsuAtama.get(2).isSameNumberInNextColor(shuntsuAtama.get(3)));
				}

				if (! ikkitsuukan)
				{
					// 一気通貫未確定。

					ikkitsuukan =
						(int)shuntsuAtama.get(1).getNumber() == 1 &&
						shuntsuAtama.get(1).kind + 3 == shuntsuAtama.get(2).kind &&
						shuntsuAtama.get(2).kind + 3 == shuntsuAtama.get(3).kind;
				}
			}
		}

		if (sanshokudoujun)
		{
			// 三色同順。

			add(YakuType.三色同順);
		}

		if (ikkitsuukan)
		{
			// 一気通貫。

			add(YakuType.一気通貫);
		}

		if (koutsuAtama.size() == 4)
		{
			// 暗刻・明刻４つ。

			if (player.isMenzen())
			{
				// 門前。

				if (tsumo)
				{
					// ツモ和がり。

					add(YakuType.四暗刻);
				}
				else
				{
					// ロン和がり。

					if (kanseiTehai.machiType == MachiType.Tanki)
					{
						// 単騎待ちである。

						add(YakuType.四暗刻);
					}
					else
					{
						// 単騎待ちではない。

						add(YakuType.対々和);
						add(YakuType.三暗刻);
					}
				}
			}
			else
			{
				// 鳴きあり。

				add(YakuType.対々和);

				if (ankoAtama.size() == 3)
				{
					// 暗刻３つ。

					add(YakuType.三暗刻);
				}
			}
		}
		else if (ankoAtama.size() == 3)
		{
			// 暗刻３つ。

			add(YakuType.三暗刻);
		}

		if (player.tenpaiType == TenpaiType.Chiitoitsu)
		{
			// 七対子。

			add(YakuType.七対子);
		}

		boolean raotou = true;
		boolean jihai = false;
		boolean hatsu = false;

		for (int i=0 ; i<player.tehai.size() ; i++)
		{
			if (player.tehai.get(i).isShuupai())
			{
				// 数牌である。

				raotou &= player.tehai.get(i).isRaotoupai();
			}
			else
			{
				// 字牌である。

				jihai = true;
				hatsu |= player.tehai.get(i).kind == PaiKind.發.ordinal();
			}
		}

		for (int i=0 ; i<player.nakiList.size() ; i++)
		{
			for (int j=0 ; j<player.nakiList.get(i).size() ; j++)
			{
				if (player.nakiList.get(i).get(j).isShuupai())
				{
					// 数牌である。

					raotou &= player.nakiList.get(i).get(j).isRaotoupai();
				}
				else
				{
					// 字牌である。

					jihai = true;
					hatsu |= player.nakiList.get(i).get(j).kind == PaiKind.發.ordinal();
				}
			}
		}

		for (int i=0 ; i<player.nakiList.size() ; i++)
		{
			if (player.nakiList.get(i).get(0).isJihai())
			{
				// 字牌である。

				jihai = true;
				hatsu = true;
			}
		}

		if (raotou)
		{
			// 数牌は老頭牌のみ。

			if (jihai)
			{
				// 字牌を含む。

				add(YakuType.混老頭);
			}
			else
			{
				// 字牌を含まない。

				add(YakuType.清老頭);
			}
		}

		if (player.tenpaiType == TenpaiType.Mentsu)
		{
			// 門前である。

			boolean chanta = true;

			for (int i=0 ; i<koutsuAtama.size() && chanta ; i++)
			{
				chanta &= koutsuAtama.get(i).isRaotoupai();
			}

			for (int i=0 ; i<shuntsuAtama.size() && chanta ; i++)
			{
				chanta &= shuntsuAtama.get(i).isChantaShuntsuAtama();
			}

			chanta &=
				kanseiTehai.get(12 - 3 * player.nakiList.size()).isRaotoupai();

			chanta &=
				kanseiTehai.get(13 - 3 * player.nakiList.size()).isRaotoupai();

			if (chanta)
			{
				// 数牌の面子は老頭牌を含む。

				if (jihai)
				{
					// 字牌を含む。

					add(YakuType.混全帯幺九);
				}
				else
				{
					// 字牌を含まない。

					add(YakuType.純全帯幺九);
				}
			}
		}

		boolean sanshokudoukou = false;

		if (koutsuAtama.size() >= 3)
		{
			// ３つ以上。

			if (koutsuAtama.get(0).isSameNumberInNextColor(koutsuAtama.get(1)) &&
				koutsuAtama.get(1).isSameNumberInNextColor(koutsuAtama.get(2)))
			{
				// すべて一致。

				sanshokudoukou = true;
			}

			if (koutsuAtama.size() == 4)
			{
				// ４つ。

				if (koutsuAtama.get(1).isSameNumberInNextColor(koutsuAtama.get(2)) &&
					koutsuAtama.get(2).isSameNumberInNextColor(koutsuAtama.get(3)))
				{
					// すべて一致。

					sanshokudoukou = true;
				}
			}
		}

		if (sanshokudoukou)
		{
			// 三色同刻。

			add(YakuType.三色同刻);
		}

		int kanCount = 0;

		for (int i=0 ; i<player.nakiList.size() ; i++)
		{
			if (player.nakiList.get(i).type == MentsuType.Ankan.ordinal() ||
				player.nakiList.get(i).type == MentsuType.Daiminkan.ordinal() ||
				player.nakiList.get(i).type == MentsuType.Shouminkan.ordinal())
			{
				// 槓である。

				kanCount++;
			}
		}

		if (kanCount == 3)
		{
			// 三槓子。

			add(YakuType.三槓子);
		}
		else if (kanCount == 4)
		{
			// 四槓子。

			add(YakuType.四槓子);
		}

		ArrayList<Integer> colors = new ArrayList<Integer>();

		for (int i=0 ; i<player.tehai.size() ; i++)
		{
			if (player.tehai.get(i).isShuupai())
			{
				// 数牌である。

				if (! colors.contains(player.tehai.get(i).getIchi()))
				{
					// 初出である。

					colors.add(player.tehai.get(i).getIchi());
				}
			}
		}

		for (int i=0 ; i<player.nakiList.size() ; i++)
		{
			for (int j=0 ; j<player.nakiList.get(i).size() ; j++)
			{
				if (player.nakiList.get(i).get(j).isShuupai())
				{
					// 数牌である。

					if (! colors.contains(player.nakiList.get(i).get(j).getIchi()))
					{
						// 初出である。

						colors.add(player.nakiList.get(i).get(j).getIchi());
					}
				}
			}
		}

		if (colors.size() == 1)
		{
			// 数牌は一色のみである。

			if (jihai)
			{
				// 字牌あり。

				add(YakuType.混一色);
			}
			else
			{
				// 字牌なし。

				add(YakuType.清一色);
			}
		}
		else if (colors.size() == 0)
		{
			// 数牌なし。

			add(YakuType.字一色);
		}

		int sangenpaiCount = 0;

		for (boolean exist : sangenpai.values())
		{
			if (exist)
			{
				// 存在する。

				sangenpaiCount++;
			}
		}

		if (sangenpaiCount == 2)
		{
			// 三元牌は２個。

			if (kanseiTehai.get(12 - 3 * player.nakiList.size()).isSangenpai() &&
				kanseiTehai.get(13 - 3 * player.nakiList.size()).isSangenpai())
			{
				// 頭が三元牌。

				add(YakuType.小三元);
			}
		}
		else if (sangenpaiCount == 3)
		{
			// 三元牌は３個。

			add(YakuType.大三元);
		}

		if (kazehai.get(PaiKind.東.ordinal()) &&
			kazehai.get(PaiKind.南.ordinal()) &&
			kazehai.get(PaiKind.西.ordinal()) &&
			kazehai.get(PaiKind.北.ordinal()))
		{
			// 全風牌がある。

			add(YakuType.四喜和);
		}

		if (player.tenpaiType == TenpaiType.Kokushimusou)
		{
			// 国士無双。

			add(YakuType.国士無双);
		}

		if (player.machi.size() == 9)
		{
			// 待ち牌９個。

			add(YakuType.九蓮宝燈);
		}

		boolean ryuiso = true;

		for (int i=0 ; i<player.tehai.size() ; i++)
		{
			ryuiso &= ryuisoList.contains(player.tehai.get(i).kind);
		}

		for (int i=0 ; i<player.nakiList.size() ; i++)
		{
			for (int j=0 ; j<player.nakiList.get(i).size() ; j++)
			{
				ryuiso &= ryuisoList.contains(player.nakiList.get(i).get(0).kind);
			}
		}

		if (ryuiso && hatsu)
		{
			// 緑一色。

			add(YakuType.緑一色);
		}

		if (jun == 0)
		{
			// 初巡。

			if (player.jikaze == bakaze)
			{
				// 親。

				add(YakuType.天和);
			}
			else
			{
				// 子。

				add(YakuType.地和);
			}
		}

		if (dora != null)
		{
			// ドラあり。デバッグ用の条件。

			for (int i=0 ; i<dora.size() ; i++)
			{
				for (int j=0 ; j<player.tehai.size() ; j++)
				{
					if (player.tehai.get(j).equals2(dora.get(i)))
					{
						// ドラ牌と一致する。

						doraCount++;
					}
				}

				for (int j=0 ; j<player.nakiList.size() ; j++)
				{
					for (int k=0 ; k<player.nakiList.get(j).size() ; k++)
					{
						if (player.nakiList.get(j).get(k).equals2(dora.get(i)))
						{
							// ドラ牌と一致する。

							doraCount++;
						}
					}
				}

				if (atarihai2.equals2(dora.get(i)))
				{
					// ドラ牌と一致する。

					doraCount++;
				}
			}
		}

		// 符の計算。
		if (player.tenpaiType == TenpaiType.Mentsu)
		{
			// 四面子一雀頭。

			fu = 20;

			if (! contains(YakuType.平和))
			{
				// 平和は含まない。

				if (player.isMenzen())
				{
					// 門前。

					if (tsumo)
					{
						// ツモ和がり。

						fu += 2;
					}
					else
					{
						// ロン和がり。

						fu += 10;
					}
				}

				HashMap<Integer, Integer> chunchanFu =
					new HashMap<Integer, Integer>();
				chunchanFu.put(MentsuType.Pon.ordinal(), 2);
				chunchanFu.put(MentsuType.Anko.ordinal(), 4);
				chunchanFu.put(MentsuType.Daiminkan.ordinal(), 8);
				chunchanFu.put(MentsuType.Shouminkan.ordinal(), 8);
				chunchanFu.put(MentsuType.Ankan.ordinal(), 16);

				HashMap<Integer, Integer> yaochuuFu =
					new HashMap<Integer, Integer>();
				yaochuuFu.put(MentsuType.Pon.ordinal(), 4);
				yaochuuFu.put(MentsuType.Anko.ordinal(), 8);
				yaochuuFu.put(MentsuType.Daiminkan.ordinal(), 16);
				yaochuuFu.put(MentsuType.Shouminkan.ordinal(), 16);
				yaochuuFu.put(MentsuType.Ankan.ordinal(), 32);

				for (int i=0 ; i<player.nakiList.size() ; i++)
				{
					if (player.nakiList.get(i).type == MentsuType.Pon.ordinal() ||
						player.nakiList.get(i).type == MentsuType.Daiminkan.ordinal() ||
						player.nakiList.get(i).type == MentsuType.Shouminkan.ordinal() ||
						player.nakiList.get(i).type == MentsuType.Ankan.ordinal())
					{
						// 明刻・槓。

						if (player.nakiList.get(i).get(0).isChunchanpai())
						{
							// 中張牌。

							fu += chunchanFu.get(player.nakiList.get(i).type);
						}
						else
						{
							// 幺九牌。

							fu += yaochuuFu.get(player.nakiList.get(i).type);
						}
					}
				}

				for (int i=0 ; i<ankoAtama.size() ; i++)
				{
					if (ankoAtama.get(i).isChunchanpai())
					{
						// 中張牌。

						fu += chunchanFu.get(MentsuType.Anko.ordinal());
					}
					else
					{
						// 数牌。

						fu += yaochuuFu.get(MentsuType.Anko.ordinal());
					}
				}

				Pai atama = kanseiTehai.get(12 - 3 * player.nakiList.size());

				if (atama.isSangenpai() || atama.kind == bakaze.ordinal() || atama.kind == player.jikaze.ordinal())
				{
					// 役牌。

					fu += 2;
				}

				if (kanseiTehai.machiType == MachiType.Tanki ||
					kanseiTehai.machiType == MachiType.Kanchan)
				{
					// 単騎・嵌張・辺張待ち。

					fu += 2;
				}
				else if (kanseiTehai.machiType == MachiType.Ryanmen)
				{
					// 両面待ち。

					if (atarihai2.getNumber() == 3 || atarihai2.getNumber() == 7)
					{
						// 和がり牌は３か７、つまり辺張待ち。

						fu += 2;
					}
				}

				if (fu % 10 > 0)
				{
					// 端数あり。

					fu += 10 - fu % 10;
				}
			}
		}
		else if (player.tenpaiType == TenpaiType.Chiitoitsu)
		{
			// 七対子。

			fu = 25;
		}
	}
}
