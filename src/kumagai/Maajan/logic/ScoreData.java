package kumagai.Maajan.logic;

import java.util.*;

/**
 * 点数データ。
 */
public class ScoreData
	extends ArrayList<String []>
{
	static private final YakuDataCollection yakuDataCollection =
		new YakuDataCollection();

	public int fu;
	public int totalFan;
	public int ten;
	public String mangan;
	public boolean furiten;
	public boolean yakuman;

	/**
	 * 表示用翻数を取得。
	 * @return 表示用翻数
	 */
	public int getTotalFanForDisplay()
	{
		return totalFan - 2;
	}

	/**
	 * 点数データ構築。
	 * @param furiten フリテン
	 * @param yakuCollection 役情報
	 * @param menzen true=門前／false=鳴きあり
	 * @param oya true=親／false=子
	 */
	public ScoreData(boolean furiten, YakuCollection yakuCollection,
		boolean menzen, boolean oya)
	{
		this.furiten = furiten;

		if (! furiten)
		{
			// フリテンではない。

			totalFan = 2;

			yakuman = false;
			int yakumanIndex = 0;

			for (int i = 0; i < yakuCollection.size() ; i++)
			{
				if (yakuDataCollection.get(yakuCollection.get(i)).yakuman)
				{
					// 役満である。

					yakuman = true;
					yakumanIndex = i;
				}
			}

			if (yakuman)
			{
				// 役満である。

				add(
					new String []
					{
						yakuCollection.get(yakumanIndex).toString(),
						new String()
					});
			}
			else
			{
				// 役満ではない。

				for (int i = 0; i < yakuCollection.size() ; i++)
				{
					int fan;

					if (menzen)
					{
						// 門前。

						fan = yakuDataCollection.get(yakuCollection.get(i)).menzenFan;
					}
					else
					{
						// 鳴きあり。

						fan = yakuDataCollection.get(yakuCollection.get(i)).nakiFan;
					}

					add(
						new String []
						{
							yakuCollection.get(i).toString(),
							new ZenkakuNum(fan).toSanyousuujiString() + "翻"
						});

					totalFan += fan;
				}

				if (yakuCollection.doraCount > 0)
				{
					// ドラあり。

					totalFan += yakuCollection.doraCount;

					add(
						new String []
						{
							"ドラ" + new ZenkakuNum(yakuCollection.doraCount).toSanyousuujiString(),
							new String()
						});
				}
			}

			fu = yakuCollection.fu;

			if (yakuman)
			{
				// 役満。

				ten = 8000;
				mangan = "役満";
			}
			else
			{
				// 役満ではない。

				if ((totalFan == 5 && fu >= 70) ||
					(totalFan == 6 && fu >= 40) ||
					(totalFan == 7))
				{
					// 満貫。

					ten = 2000;
					mangan = "満貫";
				}
				else if (totalFan == 8 || totalFan == 9)
				{
					// 跳満。

					ten = 3000;
					mangan = "跳満";
				}
				else if (totalFan >= 10 && totalFan <= 12)
				{
					// 倍満。

					ten = 4000;
					mangan = "倍満";
				}
				else if (totalFan >= 13)
				{
					// 三倍満。

					ten = 6000;
					mangan = "三倍満";
				}
				else
				{
					// 満貫未満。

					ten = fu;

					for (int i=0 ; i<totalFan ; i++)
					{
						ten *= 2;
					}
				}
			}

			if (oya)
			{
				// 親。

				ten *= 6;
			}
			else
			{
				// 子。

				ten *= 4;
			}

			if (ten % 100 > 0)
			{
				// 端数がある。

				ten += 100 - ten % 100;
			}
		}
	}
}
