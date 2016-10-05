package kumagai.Maajan.logictest.pai;

import junit.framework.*;
import java.util.*;
import kumagai.Maajan.logic.*;

public class AllPaiTest
	extends TestCase
{
	public void testAllPai()
	{
		ArrayList<Pai> allPai = new AllPai(true);
		Assert.assertEquals(136, allPai.size());
	}

	public void testGetRandom1()
	{
		ArrayList<Pai> allPai = new AllPai(true);

		for (Pai pai : allPai)
		{
			System.out.print(pai.kind + " ");
		}
		System.out.println("");

		Assert.assertEquals(136, allPai.size());

		HashMap<Integer, Integer> paiNumTable = new HashMap<Integer, Integer>();

		for (int i=0 ; i<allPai.size() ; i++)
		{
			if (paiNumTable.containsKey(allPai.get(i).kind))
			{
				int count = paiNumTable.get(allPai.get(i).kind);
				paiNumTable.put(allPai.get(i).kind, count + 1);
			}
			else
			{
				paiNumTable.put(allPai.get(i).kind, 1);
			}
		}

		for (int i=0 ; i<34 ; i++)
		{
			Assert.assertEquals(4, (int)paiNumTable.get(i));
		}
	}
}
