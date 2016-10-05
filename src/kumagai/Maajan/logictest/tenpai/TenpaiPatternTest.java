package kumagai.Maajan.logictest.tenpai;

import java.util.*;
import junit.framework.*;
import kumagai.Maajan.logic.*;

public abstract class TenpaiPatternTest
	extends TestCase
{
	protected void executeTest(PaiKind [][] data)
		throws Exception
	{
		executeTest(data, true);
	}

	protected void executeTest(PaiKind [][] data, boolean dump)
		throws Exception
	{
		ArrayList<Pai> data1 = new ArrayList<Pai>();

		for (int i = 0; i < data[0].length; i++)
		{
			data1.add(new Pai(data[0][i].ordinal()));
		}

		ArrayList<Integer> data2 = new ArrayList<Integer>();

		for (int i=0 ; i<data[1].length ; i++)
		{
			data2.add(data[1][i].ordinal());
		}

		if (dump)
		{
			for (int i=0;i<data1.size() ; i++)
			{
				if (i > 0)
				{
					System.out.print(" ");
				}

				System.out.print(data1.get(i).kind);
			}

			System.out.println();
		}

		long time1 = System.currentTimeMillis();
		MachiPattern machiPattern = new MachiPattern(data1, true);
		List<Integer> machi = machiPattern.getMachi();
		long span = System.currentTimeMillis() - time1;

		if (machi.size() > 0)
		{
			System.out.println(
				"loopCount=" +
				machiPattern.loopCount +
				" " +
				span +
				"msec");

			if (dump)
			{
				/*
				for (int i=0;i<machi.Count ; i++)
				{
					if (i > 0)
					{
						Debug.Write(" ");
					}

					Debug.Write(machi[i]);
				}

				Debug.WriteLine(" ");
				*/

				System.out.println(machiPattern.machiElementCollection + "待ち");
			}
		}
		else
		{
			if (machi.size() <= 0)
			{
				// ４面子＋１雀頭ではない。

				machi = ChiitoitsuCheck.getMachi(data1);
			}

			if (machi.size() <= 0)
			{
				// ４面子＋１雀頭・七対子ではない。

				machi = KokushimusouCheck.getMachi(data1);
			}

			if (machi.size() > 0)
			{
				System.out.println(machi + "待ち");
			}
			else
			{
				System.out.println("待ちなし");
			}
		}

		Assert.assertEquals(data2.size(), machi.size());

		for (int i = 0; i < data2.size(); i++)
		{
			Assert.assertEquals(data2.get(i), machi.get(i));
		}

		if (!(this instanceof TenpaiPatternTestChiitoitsu) &&
			!(this instanceof TenpaiPatternTestKokushimusou))
		{
			for (int i=0 ; i<machi.size() ; i++)
			{
				boolean find = false;

				for (MachiElement machiElement : machiPattern.machiElementCollection)
				{
					find |= machiElement.contains(machi.get(i));
				}

				Assert.assertTrue(find);
			}
		}
	}
}
