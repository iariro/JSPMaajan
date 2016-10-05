package kumagai.Maajan.logictest.player;

import java.util.*;
import junit.framework.*;
import kumagai.Maajan.logic.*;

public class PlayerTest
	extends TestCase
{
	public void testGetKanzai1()
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.一萬, PaiKind.一萬,
					PaiKind.三萬, PaiKind.三萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬, PaiKind.五萬,
					PaiKind.七萬, PaiKind.七萬, PaiKind.七萬,
					PaiKind.東
				});

		List<Pai> kanzai = player.getKanzai(new Pai(PaiKind.南));
		Assert.assertEquals(1, kanzai.size());
		Assert.assertEquals(PaiKind.一萬.ordinal(), kanzai.get(0).kind);
	}

	public void testGetKanzai2()
	{
		Player player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬,
					PaiKind.三萬, PaiKind.三萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬, PaiKind.五萬,
					PaiKind.七萬, PaiKind.七萬, PaiKind.七萬,
					PaiKind.東
				});

		Pon pon = new Pon(PlayerDirection.Toimen.ordinal(), new Pai(PaiKind.一萬));

		player.nakiList.add(pon);

		List<Pai> kanzai = player.getKanzai(new Pai(PaiKind.南));
		Assert.assertEquals(1, kanzai.size());
		Assert.assertEquals(PaiKind.一萬.ordinal(), kanzai.get(0).kind);
	}

	public void testCountDora1()
	{
		ComputerPlayer player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬,
					PaiKind.三萬, PaiKind.三萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬, PaiKind.五萬,
					PaiKind.七萬, PaiKind.七萬, PaiKind.七萬,
					PaiKind.東
				});

		Assert.assertEquals(0, player.countDora(new Pai(PaiKind.七萬)));
	}

	public void testCountDora2()
	{
		ComputerPlayer player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬,
					PaiKind.三萬, PaiKind.三萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬, PaiKind.五萬,
					PaiKind.東
				});

		Pon pon = new Pon(PlayerDirection.Toimen.ordinal(), new Pai(PaiKind.七萬));
		player.nakiList.add(pon);

		Assert.assertEquals(3, player.countDora(new Pai(PaiKind.七萬)));
	}

	public void testCheckAnkanOnRiichi()
		throws Exception
	{
		ComputerPlayer player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.三萬, PaiKind.三萬, PaiKind.三萬,
					PaiKind.五萬,
					PaiKind.六萬, PaiKind.六萬, PaiKind.六萬,
					PaiKind.一索, PaiKind.二索, PaiKind.三索,
					PaiKind.七索, PaiKind.八索, PaiKind.九索,
				});

		player.updateMachi();

		Assert.assertFalse(player.checkAnkanOnRiichi(new Pai(PaiKind.三萬)));
	}

}
