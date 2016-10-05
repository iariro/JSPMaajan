package kumagai.Maajan.logic;

/**
 * 全角文字による文字列化可能な数字。
 */
public class ZenkakuNum
{
	static private final char[] kansuuji =
		new char[] { '〇', '一', '二', '三', '四', '五', '六', '七', '八', '九' };

	static private final String[] ketaStringArray =
		new String[] { "十", "百", "千", "万" };

	private final int num;

	/**
	 * 数値を割り当てる。
	 * @param num 数値
	 */
	public ZenkakuNum(int num)
	{
		this.num = num;
	}

	/**
	 * 算用数字による文字列化。
	 * @return 算用数字による文字列
	 */
	public String toSanyousuujiString()
	{
		String ret = new String();

		for (int num = this.num; num > 0; num /= 10)
		{
			ret = (char)('０' + num % 10) + ret;
		}

		return ret;
	}

	/**
	 * 漢数字による文字列化。
	 * @return 漢数字による文字列
	 */
	public String toKansuujiString()
	{
		String ret = new String();
		int keta = 0;

		for (int num = this.num; num > 0; num /= 10)
		{
			String ketaString = new String();

			if (keta >= 1)
			{
				// ２桁目以降。

				ketaString = ketaStringArray[keta - 1];
			}

			String suujiString = new String();

			if (((num % 10) >= 1) && (keta == 0 || keta >= 4) || (num % 10 >= 2))
			{
				// １桁目か４桁目以降、または二桁目以降で一より大きな数。

				suujiString += kansuuji[num % 10];
			}

			ret = suujiString + ketaString + ret;
			keta++;
		}

		return ret;
	}
}
