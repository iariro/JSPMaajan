package kumagai.Maajan.logictest.tenpai;

public class TenpaiPatternTest1
	extends TenpaiPatternTest
{
	public void test101嵌張()
		throws Exception
	{
		executeTest(TenpaiData1.data101);
	}

	public void test102辺張()
		throws Exception
	{
		executeTest(TenpaiData1.data102);
	}

	public void test103単騎待ち1()
		throws Exception
	{
		executeTest(TenpaiData1.data103);
	}

	public void test104単騎待ち2()
		throws Exception
	{
		executeTest(TenpaiData1.data104);
	}

	public void test105双石並の一面待ち()
		throws Exception
	{
		executeTest(TenpaiData1.data105);
	}

	public void test106両面の一面待ち()
		throws Exception
	{
		executeTest(TenpaiData1.data106);
	}

	public void test107ノベタンの変則形()
		throws Exception
	{
		executeTest(TenpaiData1.data107);
	}
}
