package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class RiichiTest
	extends TestCase
{
	public void testRiichi1リーチ()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.一索, PaiKind.一索,
					PaiKind.二索, PaiKind.三索, PaiKind.四索,
					PaiKind.東
				});

		player.riichi = true;
		player.riichiJun = 3;

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.東.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.リーチ, yakuCollection.get(0));
	}

	public void testRiichi2リーチツモ()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.一索, PaiKind.一索,
					PaiKind.二索, PaiKind.三索, PaiKind.四索,
					PaiKind.東
				});

		player.riichi = true;
		player.riichiJun = 3;

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, true, PaiKind.東.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.リーチ, yakuCollection.get(0));
		Assert.assertEquals(YakuType.門前清自模和, yakuCollection.get(1));
	}

	public void testRiichi3ダブルリーチ()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.一索, PaiKind.一索,
					PaiKind.二索, PaiKind.三索, PaiKind.四索,
					PaiKind.東
				});

		player.riichi = true;
		player.riichiJun = 1;

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.東.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.ダブルリーチ, yakuCollection.get(0));
	}

	public void testRiichi4リーチ一発()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.一索, PaiKind.一索, PaiKind.一索,
					PaiKind.二索, PaiKind.三索, PaiKind.四索,
					PaiKind.東
				});

		player.riichi = true;
		player.riichiJun = 3;

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.東.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.リーチ, yakuCollection.get(0));
		Assert.assertEquals(YakuType.一発, yakuCollection.get(1));
	}
}
