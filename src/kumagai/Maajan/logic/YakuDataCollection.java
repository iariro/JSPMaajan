package kumagai.Maajan.logic;

import java.util.*;

/**
 * 役データコレクション。
 */
public class YakuDataCollection
	extends HashMap<YakuType, YakuData>
{
	/**
	 * 役データコレクションを構築。
	 */
	public YakuDataCollection()
	{
		put(YakuType.リーチ,		new YakuData(false, 1, 0));
		put(YakuType.一発,			new YakuData(false, 1, 0));
		put(YakuType.門前清自模和,	new YakuData(false, 1, 0));
		put(YakuType.平和,			new YakuData(false, 1, 0));
		put(YakuType.断幺,			new YakuData(false, 1, 1));
		put(YakuType.一盃口,		new YakuData(false, 1, 0));
		put(YakuType.自風牌,		new YakuData(false, 1, 1));
		put(YakuType.場風牌,		new YakuData(false, 1, 1));
		put(YakuType.白,			new YakuData(false, 1, 1));
		put(YakuType.發,			new YakuData(false, 1, 1));
		put(YakuType.中,			new YakuData(false, 1, 1));
		put(YakuType.海底撈魚,		new YakuData(false, 1, 1));
		put(YakuType.河底撈月,		new YakuData(false, 1, 1));
		put(YakuType.嶺上開花,		new YakuData(false, 1, 1));
		put(YakuType.槍槓,			new YakuData(false, 1, 1));
		put(YakuType.三色同順,		new YakuData(false, 2, 1));
		put(YakuType.一気通貫,		new YakuData(false, 2, 1));
		put(YakuType.対々和,		new YakuData(false, 2, 2));
		put(YakuType.三暗刻,		new YakuData(false, 2, 2));
		put(YakuType.七対子,		new YakuData(false, 2, 0));
		put(YakuType.混全帯幺九,	new YakuData(false, 2, 1));
		put(YakuType.三色同刻,		new YakuData(false, 2, 2));
		put(YakuType.三槓子,		new YakuData(false, 2, 2));
		put(YakuType.ダブルリーチ,	new YakuData(false, 2, 0));
		put(YakuType.混一色,		new YakuData(false, 3, 2));
		put(YakuType.純全帯幺九,	new YakuData(false, 3, 2));
		put(YakuType.二盃口,		new YakuData(false, 3, 0));
		put(YakuType.混老頭,		new YakuData(false, 2, 2));
		put(YakuType.小三元,		new YakuData(false, 2, 2));
		put(YakuType.清一色,		new YakuData(false, 6, 5));
		put(YakuType.四暗刻,		new YakuData(true, 0, 0));
		put(YakuType.大三元,		new YakuData(true, 0, 0));
		put(YakuType.字一色,		new YakuData(true, 0, 0));
		put(YakuType.緑一色,		new YakuData(true, 0, 0));
		put(YakuType.国士無双,		new YakuData(true, 0, 0));
		put(YakuType.九蓮宝燈,		new YakuData(true, 0, 0));
		put(YakuType.四喜和,		new YakuData(true, 0, 0));
		put(YakuType.清老頭,		new YakuData(true, 0, 0));
		put(YakuType.四槓子,		new YakuData(true, 0, 0));
		put(YakuType.天和,			new YakuData(true, 0, 0));
		put(YakuType.地和,			new YakuData(true, 0, 0));
	}
}
