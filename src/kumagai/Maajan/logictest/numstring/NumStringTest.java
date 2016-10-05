package kumagai.Maajan.logictest.numstring;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class NumStringTest
	extends TestCase
{
	public void test10()
	{
		Assert.assertEquals("１２３", new ZenkakuNum(123).toSanyousuujiString());
	}

	public void test11()
	{
		Assert.assertEquals("１０", new ZenkakuNum(10).toSanyousuujiString());
	}

	public void test20()
	{
		Assert.assertEquals("五", new ZenkakuNum(5).toKansuujiString());
	}

	public void test30()
	{
		Assert.assertEquals("十五", new ZenkakuNum(15).toKansuujiString());
	}

	public void test31()
	{
		Assert.assertEquals("十", new ZenkakuNum(10).toKansuujiString());
	}

	public void test32()
	{
		Assert.assertEquals("十一", new ZenkakuNum(11).toKansuujiString());
	}

	public void test40()
	{
		Assert.assertEquals("二十五", new ZenkakuNum(25).toKansuujiString());
	}

	public void test50()
	{
		Assert.assertEquals("百二十三", new ZenkakuNum(123).toKansuujiString());
	}

	public void test60()
	{
		Assert.assertEquals("千二百三十四", new ZenkakuNum(1234).toKansuujiString());
	}

	public void test70()
	{
		Assert.assertEquals("一万二千三百四十五", new ZenkakuNum(12345).toKansuujiString());
	}
}
