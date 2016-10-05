package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class ChiitoitsuTest
	extends TestCase
{
	public void testChiitoitsu1()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二萬, PaiKind.二萬,
					PaiKind.五萬, PaiKind.五萬,
					PaiKind.八筒, PaiKind.八筒,
					PaiKind.一索,
					PaiKind.九索, PaiKind.九索,
					PaiKind.西, PaiKind.西,
					PaiKind.中, PaiKind.中
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.一索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.七対子, yakuCollection.get(0));
	}

	/**
	 * 断幺との複合
	 */
	public void testChiitoitsu2()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二萬, PaiKind.二萬,
					PaiKind.五萬, PaiKind.五萬,
					PaiKind.八筒, PaiKind.八筒,
					PaiKind.二索,
					PaiKind.四索, PaiKind.四索,
					PaiKind.六索, PaiKind.六索,
					PaiKind.八索, PaiKind.八索,
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.二索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.断幺, yakuCollection.get(0));
		Assert.assertEquals(YakuType.七対子, yakuCollection.get(1));
	}

	/**
	 * 混老頭との複合
	 */
	public void testChiitoitsu3()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬,
					PaiKind.九萬, PaiKind.九萬,
					PaiKind.九筒, PaiKind.九筒,
					PaiKind.一索,
					PaiKind.九索, PaiKind.九索,
					PaiKind.東, PaiKind.東,
					PaiKind.西, PaiKind.西,
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.一索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.七対子, yakuCollection.get(0));
		Assert.assertEquals(YakuType.混老頭, yakuCollection.get(1));
	}

	/**
	 * 混一色との複合
	 */
	public void testChiitoitsu4()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬,
					PaiKind.三萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬,
					PaiKind.七萬,
					PaiKind.九萬, PaiKind.九萬,
					PaiKind.東, PaiKind.東,
					PaiKind.西, PaiKind.西,
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.七萬.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.七対子, yakuCollection.get(0));
		Assert.assertEquals(YakuType.混一色, yakuCollection.get(1));
	}
}
