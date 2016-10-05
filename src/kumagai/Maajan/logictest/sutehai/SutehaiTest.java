package kumagai.Maajan.logictest.sutehai;

import java.util.*;
import junit.framework.*;
import kumagai.Maajan.logic.*;

public abstract class SutehaiTest
	extends TestCase
{
	protected void executeTest(TehaiAndIndexies data)
		throws Exception
	{
		ArrayList<Pai> tehai = new ArrayList<Pai>();

		for (int i = 0; i < data.tehai.length; i++)
		{
			tehai.add(new Pai(data.tehai[i].ordinal()));
		}

		ArrayList<Integer> sute1 = new ArrayList<Integer>();

		for (int i=0 ; i< data.indexies.length ; i++)
		{
			sute1.add(data.indexies[i]);
		}

		Collections.sort(
			tehai,
			new Comparator<Pai>()
			{
				public int compare(Pai pai1, Pai pai2)
				{
					if (pai1.kind > pai2.kind)
					{
						return 1;
					}
					else if (pai1.kind < pai2.kind)
					{
						return -1;
					}
					else
					{
						return 0;
					}
				}
			});

		long time1 = System.currentTimeMillis();
		SutehaiPattern sute2 = new SutehaiPattern(tehai);
		long span = System.currentTimeMillis() - time1;

		System.out.println(
			sute2.loopCount +
			"pattern " +
			span +
			"msec");

		Assert.assertEquals(sute1.size(), sute2.size());

		for (int i = 0; i < sute1.size(); i++)
		{
			Assert.assertEquals(sute1.get(i), sute2.get(i));
		}
	}

}
