package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class ToitoihouTest
	extends TestCase
{
	public void testToitoihou1()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.四萬, PaiKind.四萬, PaiKind.四萬,
					PaiKind.七萬, PaiKind.七萬, PaiKind.七萬,
					PaiKind.一索,
					PaiKind.七索, PaiKind.七索, PaiKind.七索
				});

		player.nakiList.add(
			new Pon(PlayerDirection.Toimen.ordinal(), new Pai(PaiKind.一萬)));

		player.setMenzen(false);
		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.一索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.対々和, yakuCollection.get(0));
		Assert.assertEquals(YakuType.三暗刻, yakuCollection.get(1));
	}

	public void testToitoihou2()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.一萬,
					PaiKind.四萬, PaiKind.四萬, PaiKind.四萬,
					PaiKind.七萬, PaiKind.七萬, PaiKind.七萬,
					PaiKind.一索, PaiKind.一索,
					PaiKind.七索, PaiKind.七索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.七索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.対々和, yakuCollection.get(0));
		Assert.assertEquals(YakuType.三暗刻, yakuCollection.get(1));
	}

	public void testToitoihou3()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.二筒, PaiKind.二筒, PaiKind.二筒,
					PaiKind.五筒, PaiKind.五筒,
					PaiKind.八筒, PaiKind.八筒, PaiKind.八筒,
					PaiKind.七索, PaiKind.七索
				});

		player.nakiList.add(
			new Kan(PlayerDirection.Jibun.ordinal(), MentsuType.Ankan.ordinal(), new Pai(PaiKind.北)));

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 1, 94, false, PaiKind.五筒.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(2, yakuCollection.size());
		Assert.assertEquals(YakuType.対々和, yakuCollection.get(0));
		Assert.assertEquals(YakuType.三暗刻, yakuCollection.get(1));

		ScoreData score = new ScoreData(false, yakuCollection, true, true);

		Assert.assertEquals(2, score.size());
		Assert.assertEquals("対々和", score.get(0)[0]);
		Assert.assertEquals("２翻", score.get(0)[1]);
		Assert.assertEquals("三暗刻", score.get(1)[0]);
		Assert.assertEquals("２翻", score.get(1)[1]);
	}
}
