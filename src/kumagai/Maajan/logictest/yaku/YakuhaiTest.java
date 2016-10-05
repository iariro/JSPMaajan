package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class YakuhaiTest
	extends TestCase
{
	public void testYakuhai1自風牌()
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
					PaiKind.四索, PaiKind.四索,
					PaiKind.南, PaiKind.南
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.南.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.自風牌, yakuCollection.get(0));
	}

	public void testYakuhai2場風牌()
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
					PaiKind.四索, PaiKind.四索,
					PaiKind.東, PaiKind.東
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.東.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.場風牌, yakuCollection.get(0));
	}

	public void testYakuhai3自風牌場風牌()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.東, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二萬, PaiKind.三萬, PaiKind.四萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.三索, PaiKind.三索, PaiKind.三索,
					PaiKind.四索, PaiKind.四索,
					PaiKind.東, PaiKind.東
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.東.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.自風牌, yakuCollection.get(0));
		Assert.assertEquals(YakuType.場風牌, yakuCollection.get(1));
	}

	public void testYakuhai4白()
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
					PaiKind.四索, PaiKind.四索,
					PaiKind.白, PaiKind.白
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.白.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.白, yakuCollection.get(0));
	}

	public void testYakuhai5白發()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二萬, PaiKind.三萬, PaiKind.四萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.三索, PaiKind.三索,
					PaiKind.白, PaiKind.白, PaiKind.白,
					PaiKind.發, PaiKind.發
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.發.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.白, yakuCollection.get(0));
		Assert.assertEquals(YakuType.發, yakuCollection.get(1));
	}

	public void testYakuhai6發中()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二萬, PaiKind.三萬, PaiKind.四萬,
					PaiKind.四筒, PaiKind.五筒, PaiKind.六筒,
					PaiKind.三索, PaiKind.三索,
					PaiKind.發, PaiKind.發
				});

		player.updateMachi();

		player.nakiList.add(new Pon(PlayerDirection.Toimen.ordinal(), new Pai(PaiKind.中)));

		YakuCollection yakuCollection =
			new YakuCollection(player, 5, 80, false, PaiKind.發.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.發, yakuCollection.get(0));
		Assert.assertEquals(YakuType.中, yakuCollection.get(1));
	}
}
