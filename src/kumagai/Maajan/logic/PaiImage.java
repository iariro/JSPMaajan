package kumagai.Maajan.logic;

/**
 * 牌画像。
 * @author kumagai
 */
public class PaiImage
{
	/**
	 * 牌画像ファイル一覧。
	 */
	static private final String [] paiImageFileName =
	{
		"man_1.png", "man_2.png", "man_3.png",
		"man_4.png", "man_5.png", "man_6.png",
		"man_7.png", "man_8.png", "man_9.png",

		"pin_1.png", "pin_2.png", "pin_3.png",
		"pin_4.png", "pin_5.png", "pin_6.png",
		"pin_7.png", "pin_8.png", "pin_9.png",

		"sou_1.png", "sou_2.png", "sou_3.png",
		"sou_4.png", "sou_5.png", "sou_6.png",
		"sou_7.png", "sou_8.png", "sou_9.png",

		"kaze_ton.png", "kaze_nan.png", "kaze_sha.png", "kaze_pei.png",

		"sangen_hatu.png", "sangen_haku.png", "sangen_chun.png"
	};

	/**
	 * ファイル名取得。
	 * @param index 牌インデックス
	 * @param valid true=有効表示用／false=無効表示用
	 * @return ファイル名
	 */
	static public String getFileName(int index, boolean valid)
	{
		if (valid)
		{
			// 有効表示用。

			return paiImageFileName[index];
		}
		else
		{
			// 無効表示用。

			return paiImageFileName[index].replace(".png", "_gray.png");
		}
	}
}
