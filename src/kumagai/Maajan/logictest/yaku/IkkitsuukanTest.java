package kumagai.Maajan.logictest.yaku;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class IkkitsuukanTest
	extends TestCase
{
	public void testIkkitsuukan1()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.四萬, PaiKind.五萬, PaiKind.六萬,
					PaiKind.七萬, PaiKind.八萬, PaiKind.九萬,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.七索
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.七索.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.一気通貫, yakuCollection.get(0));
	}

	public void testIkkitsuukan2()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.四索, PaiKind.五索, PaiKind.六索,
					PaiKind.七索, PaiKind.八索, PaiKind.九索,
					PaiKind.東
				});

		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.東.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.一気通貫, yakuCollection.get(0));
	}

	public void testIkkitsuukan3()
		throws Exception
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.三萬,
					PaiKind.四萬, PaiKind.五萬, PaiKind.六萬,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.七索, PaiKind.七索
				});

		player.nakiList.add(
			new Chii(
				PlayerDirection.Toimen.ordinal(),
				new Pai(PaiKind.七萬),
				new Pai(PaiKind.八萬),
				new Pai(PaiKind.九萬)));

		player.setMenzen(false);
		player.updateMachi();

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, false, PaiKind.二萬.ordinal(), null, PaiKind.東, null);

		Assert.assertEquals(1, yakuCollection.size());
		Assert.assertEquals(YakuType.一気通貫, yakuCollection.get(0));
	}
}
