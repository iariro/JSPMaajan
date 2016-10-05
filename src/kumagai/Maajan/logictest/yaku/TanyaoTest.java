package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class TanyaoTest
	extends TestCase
{
	public void testTanyao1()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二萬, PaiKind.三萬, PaiKind.四萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.三索, PaiKind.三索, PaiKind.三索,
					PaiKind.四索, PaiKind.五索, PaiKind.六索,
					PaiKind.七索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.七索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.断幺, yakuCollection.get(0));
	}

	public void testTanyao2()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二萬, PaiKind.三萬, PaiKind.四萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.三索, PaiKind.三索, PaiKind.三索,
					PaiKind.四索, PaiKind.四索, PaiKind.七索, PaiKind.八索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.九索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(0, yakuCollection.size());
	}
}
