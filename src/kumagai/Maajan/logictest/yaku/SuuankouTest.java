package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class SuuankouTest
	extends TestCase
{
	public void test1()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.一萬,
					PaiKind.二筒, PaiKind.二筒,
					PaiKind.三筒, PaiKind.三筒, PaiKind.三筒,
					PaiKind.七筒, PaiKind.七筒, PaiKind.七筒,
					PaiKind.九索, PaiKind.九索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, true, PaiKind.二筒.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.門前清自模和, yakuCollection.get(0));
		Assert.assertEquals(YakuType.四暗刻, yakuCollection.get(1));
	}
}
