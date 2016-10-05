package kumagai.Maajan.logictest.dora;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class DoraTest
	extends TestCase
{
	public void test1()
	{
		Assert.assertEquals(PaiKind.二萬.ordinal(), new Pai(PaiKind.一萬).getDora().kind);
	}

	public void test2()
	{
		Assert.assertEquals(PaiKind.一萬.ordinal(), new Pai(PaiKind.九萬).getDora().kind);
	}

	public void test3()
	{
		Assert.assertEquals(PaiKind.二索.ordinal(), new Pai(PaiKind.一索).getDora().kind);
	}

	public void test4()
	{
		Assert.assertEquals(PaiKind.一索.ordinal(), new Pai(PaiKind.九索).getDora().kind);
	}

	public void test5()
	{
		Assert.assertEquals(PaiKind.南.ordinal(), new Pai(PaiKind.東).getDora().kind);
	}

	public void test6()
	{
		Assert.assertEquals(PaiKind.東.ordinal(), new Pai(PaiKind.北).getDora().kind);
	}

	public void test7()
	{
		Assert.assertEquals(PaiKind.發.ordinal(), new Pai(PaiKind.白).getDora().kind);
	}

	public void test8()
	{
		Assert.assertEquals(PaiKind.白.ordinal(), new Pai(PaiKind.中).getDora().kind);
	}
}
