package kumagai.Maajan.logictest.player;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class ChechAnkanOnRiichiTest
	extends TestCase
{
	public void test1()
		throws Exception
	{
		ComputerPlayer player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.一萬,
					PaiKind.三萬, PaiKind.三萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬, PaiKind.五萬,
					PaiKind.七萬, PaiKind.七萬, PaiKind.七萬,
					PaiKind.東
				});

		player.updateMachi();

		Assert.assertTrue(player.checkAnkanOnRiichi(new Pai(PaiKind.一萬)));
	}

	public void test2()
		throws Exception
	{
		ComputerPlayer player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬, PaiKind.五萬,
					PaiKind.七萬, PaiKind.七萬, PaiKind.七萬,
					PaiKind.東, PaiKind.東
				});

		player.updateMachi();

		Assert.assertFalse(player.checkAnkanOnRiichi(new Pai(PaiKind.一萬)));
	}

	public void test3()
		throws Exception
	{
		ComputerPlayer player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.一萬,
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬, PaiKind.五萬,
					PaiKind.七萬, PaiKind.七萬, PaiKind.七萬,
					PaiKind.東, PaiKind.東
				});

		player.updateMachi();

		Assert.assertFalse(player.checkAnkanOnRiichi(new Pai(PaiKind.四萬)));
	}
}
