package kumagai.Maajan.logictest.pai;

import junit.framework.*;
import kumagai.Maajan.logic.*;

public class PaiTest
	extends TestCase
{
	public void test01Shuupai()
	{
		Assert.assertTrue(new Pai(0).isShuupai());
		Assert.assertTrue(new Pai(11).isShuupai());
		Assert.assertTrue(new Pai(26).isShuupai());
		Assert.assertFalse(new Pai(31).isShuupai());
	}

	public void test2Jihai()
	{
		Assert.assertFalse(new Pai(0).isJihai());
		Assert.assertFalse(new Pai(11).isJihai());
		Assert.assertFalse(new Pai(26).isJihai());
		Assert.assertTrue(new Pai(31).isJihai());
	}

	public void test3Chunchanpai()
		throws Exception
	{
		Assert.assertFalse(new Pai(0).isChunchanpai());
		Assert.assertTrue(new Pai(11).isChunchanpai());
		Assert.assertFalse(new Pai(26).isChunchanpai());
		Assert.assertFalse(new Pai(31).isChunchanpai());
	}

	public void test4Yaochuupai()
	{
		Assert.assertTrue(new Pai(0).isYaochuupai());
		Assert.assertFalse(new Pai(11).isYaochuupai());
		Assert.assertTrue(new Pai(26).isYaochuupai());
		Assert.assertTrue(new Pai(31).isYaochuupai());
	}

	public void test5Number()
		throws Exception
	{
		Assert.assertEquals(1, new Pai(0).getNumber());
		Assert.assertEquals(3, new Pai(11).getNumber());
		Assert.assertEquals(9, new Pai(26).getNumber());
	}

	public void test6Number()
		throws Exception
	{
		try
		{
			new Pai(31).getNumber();

			Assert.fail();
		}
		catch (Exception exception)
		{
		}
	}

	public void test7IsSameNumberInNextColor()
	{
		Assert.assertTrue(new Pai(2).isSameNumberInNextColor(new Pai(11)));
		Assert.assertFalse(new Pai(2).isSameNumberInNextColor(new Pai(12)));
		Assert.assertFalse(new Pai(2).isSameNumberInNextColor(new Pai(20)));

		Assert.assertTrue(new Pai(11).isSameNumberInNextColor(new Pai(20)));
	}

	public void test8IsSameColor()
		throws Exception
	{
		Assert.assertTrue(new Pai(2).isSameColor(new Pai(8)));
		Assert.assertFalse(new Pai(2).isSameColor(new Pai(11)));
	}
}
