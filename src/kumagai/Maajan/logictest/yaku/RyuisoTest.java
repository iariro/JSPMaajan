package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class RyuisoTest
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
					PaiKind.二索, PaiKind.二索, PaiKind.三索,
					PaiKind.三索, PaiKind.四索, PaiKind.四索,
					PaiKind.六索, PaiKind.六索, PaiKind.六索,
					PaiKind.八索, PaiKind.八索, PaiKind.八索,
					PaiKind.發
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.發.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(3, yakuCollection.size());
		Assert.assertEquals(YakuType.一盃口, yakuCollection.get(0));
		Assert.assertEquals(YakuType.混一色, yakuCollection.get(1));
		Assert.assertEquals(YakuType.緑一色, yakuCollection.get(2));
	}

	public void test2()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二索, PaiKind.二索, PaiKind.三索,
					PaiKind.三索, PaiKind.四索, PaiKind.四索,
					PaiKind.四索,
					PaiKind.六索, PaiKind.六索, PaiKind.六索,
					PaiKind.八索, PaiKind.八索, PaiKind.八索,
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.四索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(3, yakuCollection.size());
		Assert.assertEquals(YakuType.断幺, yakuCollection.get(0));
		Assert.assertEquals(YakuType.一盃口, yakuCollection.get(1));
		Assert.assertEquals(YakuType.清一色, yakuCollection.get(2));
	}
}
