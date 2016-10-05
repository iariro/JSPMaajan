package kumagai.Maajan.logictest.naki;

import junit.framework.*;
import java.util.*;
import kumagai.Maajan.logic.*;

public class ChiiTest
	extends TestCase
{
	public void testChii1()
	{
		ArrayList<Pai> tehai =
			new PaiList(
				new PaiKind []
				{
					PaiKind.一萬, PaiKind.二萬, PaiKind.三萬,
					PaiKind.五萬, PaiKind.五萬, PaiKind.六萬,
					PaiKind.八萬, PaiKind.九萬
				});

		ChiiCommand chii =
			new ChiiCommand(
				new Pai(PaiKind.七萬),
				PlayerDirection.Toimen.ordinal(),
				tehai,
				-2,
				-1);

		Assert.assertEquals(3, chii.index1);
		Assert.assertEquals(5, chii.index2);
	}
}
