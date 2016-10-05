package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class PinfuTest
	extends TestCase
{
	public void testPinfu1()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.五索, PaiKind.五索,
					PaiKind.七索, PaiKind.八索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.九索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.平和, yakuCollection.get(0));
	}

	public void testPinfu2()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.五索, PaiKind.五索,
					PaiKind.七索, PaiKind.九索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.八索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(0, yakuCollection.size());
	}

	public void testPinfu3()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.七索, PaiKind.八索,
					PaiKind.西, PaiKind.西
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.九索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.平和, yakuCollection.get(0));
	}

	public void testPinfu4()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.七索, PaiKind.八索,
					PaiKind.南, PaiKind.南
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.九索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(0, yakuCollection.size());
	}

	public void testPinfu5()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.七索, PaiKind.八索,
					PaiKind.東, PaiKind.東
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.九索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(0, yakuCollection.size());
	}

	public void testPinfu6()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.東, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.四萬, PaiKind.五萬,
					PaiKind.七萬, PaiKind.八萬, PaiKind.九萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.七索, PaiKind.八索, PaiKind.九索,
					PaiKind.南, PaiKind.南
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.三萬.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.平和, yakuCollection.get(0));
	}
}
