package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class SanshokudoujunTest
	extends TestCase
{
	public void testSanshokudoujun1()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.一筒, PaiKind.二筒, PaiKind.三筒,
					PaiKind.一索, PaiKind.二索,
					PaiKind.五索, PaiKind.五索,
					PaiKind.七索, PaiKind.七索, PaiKind.七索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.三索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.三色同順, yakuCollection.get(0));
	}

	/**
	 * 鳴きの三色
	 */
	public void testSanshokudoujun2()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.五索, PaiKind.五索,
					PaiKind.七索, PaiKind.七索
				});

		player.nakiList.add(
			new Chii(
				PlayerDirection.Toimen.ordinal(),
				new Pai(PaiKind.一筒),
				new Pai(PaiKind.二筒),
				new Pai(PaiKind.三筒)));

		player.setMenzen(false);
		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.七索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.三色同順, yakuCollection.get(0));
	}

	/*
	 * 平和との複合
	 */
	public void testSanshokudoujun3()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.六萬, PaiKind.七萬, PaiKind.八萬,
					PaiKind.二筒, PaiKind.三筒,
					PaiKind.六筒, PaiKind.七筒, PaiKind.八筒,
					PaiKind.八筒, PaiKind.八筒,
					PaiKind.六索, PaiKind.七索, PaiKind.八索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.四筒.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(3, yakuCollection.size());
		Assert.assertEquals(YakuType.断幺, yakuCollection.get(0));
		Assert.assertEquals(YakuType.平和, yakuCollection.get(1));
		Assert.assertEquals(YakuType.三色同順, yakuCollection.get(2));
	}

	/**
	 * 平和・一盃口との複合
	 */
	public void testSanshokudoujun4()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.三萬, PaiKind.四萬,
					PaiKind.二筒, PaiKind.三筒, PaiKind.四筒,
					PaiKind.二索, PaiKind.三索, PaiKind.四索,
					PaiKind.九索, PaiKind.九索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.四萬.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(3, yakuCollection.size());
		Assert.assertEquals(YakuType.平和, yakuCollection.get(0));
		Assert.assertEquals(YakuType.一盃口, yakuCollection.get(1));
		Assert.assertEquals(YakuType.三色同順, yakuCollection.get(2));
	}
}
