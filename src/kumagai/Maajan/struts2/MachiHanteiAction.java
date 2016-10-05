package kumagai.Maajan.struts2;

import java.util.*;
import kumagai.Maajan.logic.*;

/**
 * 待ち判定アクション基底部。
 * @author kumagai
 */
public class MachiHanteiAction
{
	public String [] tehaiFileName;
	public String tenpaiType = "-";
	public String [] machihaiFileName;
	public MachiElementForJsp [] machiCollection;

	/**
	 * 待ち判定とJSP用情報出力。
	 * @param tehai 手牌
	 */
	protected void hantei(ArrayList<Pai> tehai)
		throws Exception
	{
		tehaiFileName = new String [tehai.size()];

		for(int i=0 ; i<tehai.size() ; i++)
		{
			tehaiFileName[i] = PaiImage.getFileName(tehai.get(i).kind, true);
		}

		Player player = new ComputerPlayer(PaiKind.東, 0);

		player.tehai = tehai;
		player.updateMachi();

		if (player.tenpaiType == TenpaiType.Mentsu)
		{
			// 面子形。

			tenpaiType = "面子形";
		}
		else if (player.tenpaiType == TenpaiType.Chiitoitsu)
		{
			// 七対子。

			tenpaiType = "七対子";
		}
		else if (player.tenpaiType == TenpaiType.Kokushimusou)
		{
			// 国士無双。

			tenpaiType = "国士無双";
		}

		machihaiFileName = new String [player.machi.size()];

		for (int i=0 ; i<player.machi.size() ; i++)
		{
			machihaiFileName[i] =
				PaiImage.getFileName(player.machi.get(i), true);
		}

		int size = player.machiPattern.machiElementCollection.size();

		machiCollection = new MachiElementForJsp [size];

		for (int i=0 ; i<size ; i++)
		{
			machiCollection[i] =
				new MachiElementForJsp(
					player.machiPattern.machiElementCollection.get
						(size - i - 1));
		}
	}
}
