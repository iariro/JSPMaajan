package kumagai.Maajan.struts2;

import kumagai.Maajan.logic.*;

/**
 * JSP表示用待ち情報。
 * @author kumagai
 */
public class MachiElementForJsp
{
	public MachiElement machi;
	public String [] machihaiFileName;
	public String type;

	/**
	 * 指定の値をメンバーに割り当てる。
	 * @param machi 待ち情報
	 */
	public MachiElementForJsp(MachiElement machi)
	{
		this.machi = machi;

		machihaiFileName = new String [machi.size()];

		for (int i=0 ; i<machi.size() ; i++)
		{
			boolean valid = true;

			if (i == 0)
			{
				// １枚目。

				valid = !machi.over1;
			}

			if (i == 1)
			{
				// ２枚目。

				valid = !machi.over2;
			}

			machihaiFileName[i] = PaiImage.getFileName(machi.get(i), valid);
		}

		switch (machi.type)
		{
			case Tanki:
				type = "単騎待ち";
				break;
			case Ryanmen:
				type = "両面待ち";
				break;
			case Shanpon:
				type = "シャンポン待ち";
				break;
			case Kanchan:
				type = "嵌張待ち";
				break;
			case Penchan:
				type = "辺張待ち";
				break;
		}
	}
}
