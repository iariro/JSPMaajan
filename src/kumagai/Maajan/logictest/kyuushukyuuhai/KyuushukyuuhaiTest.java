package kumagai.Maajan.logictest.kyuushukyuuhai;

import java.util.*;
import kumagai.Maajan.logic.*;
import junit.framework.*;

public class KyuushukyuuhaiTest
	extends TestCase
{
	public void test1()
	{
		ArrayList<Pai> tehai =
			new PaiList(
				new PaiKind[]
				{
					PaiKind.一萬, PaiKind.一萬, PaiKind.一萬,
					PaiKind.三萬, PaiKind.九萬, PaiKind.九萬,
					PaiKind.一筒, PaiKind.五筒, PaiKind.九筒,
					PaiKind.一索, PaiKind.九索, PaiKind.東,
					PaiKind.西
				});

		YaochuuCollection yaochuu =
			new YaochuuCollection(tehai, new Pai(PaiKind.白));
		Assert.assertEquals(9, yaochuu.size());
	}
}
