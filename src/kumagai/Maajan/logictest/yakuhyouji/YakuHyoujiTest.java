package kumagai.Maajan.logictest.yakuhyouji;

import java.util.*;
import junit.framework.*;
import kumagai.Maajan.logic.*;

public class YakuHyoujiTest
	extends TestCase
{
	public void test1()
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

		PaiList dora = new PaiList(new PaiKind [] { PaiKind.二萬 });

		YakuCollection yakuCollection =
			new YakuCollection(player, 3, 80, true, PaiKind.四萬.ordinal(), dora, PaiKind.東, null);

		ScoreData yakuStringCollection =
			new ScoreData(false, yakuCollection, true, true);

		for (int i = 0; i < yakuStringCollection.size() ; i++)
		{
			System.out.println(
				yakuStringCollection.get(i)[0] +
				" " +
				yakuStringCollection.get(i)[1]);
		}

		Assert.assertEquals(5, yakuStringCollection.size());
		Assert.assertEquals("門前清自模和", yakuStringCollection.get(0)[0]);
		Assert.assertEquals("１翻", yakuStringCollection.get(0)[1]);
		Assert.assertEquals("平和", yakuStringCollection.get(1)[0]);
		Assert.assertEquals("１翻", yakuStringCollection.get(1)[1]);
		Assert.assertEquals("一盃口", yakuStringCollection.get(2)[0]);
		Assert.assertEquals("１翻", yakuStringCollection.get(2)[1]);
		Assert.assertEquals("三色同順", yakuStringCollection.get(3)[0]);
		Assert.assertEquals("２翻", yakuStringCollection.get(3)[1]);
		Assert.assertEquals("ドラ２", yakuStringCollection.get(4)[0]);
		Assert.assertEquals("", yakuStringCollection.get(4)[1]);
	}

	public void test2()
	{
		ArrayList<String> array = new ArrayList<String>();
		array.add("abc");
		String [] array2 = array.toArray(new String[]{});
		System.out.println(array2.length);
		assertEquals("abc", array2[0]);
	}
}
