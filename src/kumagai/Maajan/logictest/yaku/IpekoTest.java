package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class IpekoTest
	extends TestCase
{
	public void testIpeko1()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.二萬,
					PaiKind.二萬, PaiKind.三萬, PaiKind.三萬,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.五索, PaiKind.五索,
					PaiKind.七索, PaiKind.七索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.七索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.一盃口, yakuCollection.get(0));
	}

	public void testIpeko2()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.一索, PaiKind.一索, PaiKind.二索,
					PaiKind.二索, PaiKind.三索,
					PaiKind.五索, PaiKind.五索,
					PaiKind.七索, PaiKind.七索, PaiKind.七索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.三索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.一盃口, yakuCollection.get(0));
	}

	public void testIpeko3()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.五筒, PaiKind.五筒,
					PaiKind.一索, PaiKind.一索, PaiKind.二索,
					PaiKind.二索, PaiKind.三索, PaiKind.三索,
					PaiKind.七索, PaiKind.八索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.九索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.平和, yakuCollection.get(0));
		Assert.assertEquals(YakuType.一盃口, yakuCollection.get(1));
	}

	public void testIpeko4()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.二萬,
					PaiKind.二萬, PaiKind.三萬, PaiKind.三萬,
					PaiKind.七筒,
					PaiKind.四索, PaiKind.四索, PaiKind.五索,
					PaiKind.五索, PaiKind.六索, PaiKind.六索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.七筒.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.二盃口, yakuCollection.get(0));
	}

	public void testIpeko5()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬,
					PaiKind.二萬, PaiKind.二萬,
					PaiKind.三萬, PaiKind.三萬,
					PaiKind.五索, PaiKind.五索,
					PaiKind.六索, PaiKind.六索,
					PaiKind.七索,
					PaiKind.一筒, PaiKind.一筒
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.七索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.平和, yakuCollection.get(0));
		Assert.assertEquals(YakuType.二盃口, yakuCollection.get(1));
	}
}
