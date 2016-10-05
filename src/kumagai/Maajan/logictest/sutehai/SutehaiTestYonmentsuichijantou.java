package kumagai.Maajan.logictest.sutehai;

public class SutehaiTestYonmentsuichijantou
	extends SutehaiTest
{
	public void test01()
		throws Exception
	{
		executeTest(SutehaiData.data101);
	}

	public void test02()
		throws Exception
	{
		executeTest(SutehaiData.data102);
	}

	public void test03()
		throws Exception
	{
		executeTest(SutehaiData.data103);
	}

	public void test04()
		throws Exception
	{
		executeTest(SutehaiData.data104);
	}

	public void test05()
		throws Exception
	{
		executeTest(SutehaiData.data105);
	}

	public void test06()
		throws Exception
	{
		executeTest(SutehaiData.data106);
	}

	public void test07()
		throws Exception
	{
		executeTest(SutehaiData.data107);
	}

	public void test08()
		throws Exception
	{
		executeTest(SutehaiData.data108);
	}

	public void test09()
		throws Exception
	{
		executeTest(SutehaiData.data109);
	}

	public void test10()
		throws Exception
	{
		executeTest(SutehaiData.data110);
	}

	public void test11()
		throws Exception
	{
		executeTest(SutehaiData.data111);
	}

	public void test12()
		throws Exception
	{
		executeTest(SutehaiData.data112);
	}

	public void test13()
		throws Exception
	{
		executeTest(SutehaiData.data113);
	}

	public void test14()
		throws Exception
	{
		executeTest(SutehaiData.data114);
	}

	public void test15()
		throws Exception
	{
		executeTest(SutehaiData.data115);
	}

	public void test16()
		throws Exception
	{
		executeTest(SutehaiData.data116);
	}

	public void test17()
		throws Exception
	{
		executeTest(SutehaiData.data117);
	}

	public void test18()
		throws Exception
	{
		executeTest(SutehaiData.data118);
	}

	public void test19()
		throws Exception
	{
		executeTest(SutehaiData.data119);
	}

	public void test20()
		throws Exception
	{
		executeTest(SutehaiData.data120);
	}

	public void test21()
		throws Exception
	{
		executeTest(SutehaiData.data121);
	}

	public void test22()
		throws Exception
	{
		executeTest(SutehaiData.data122);
	}

	/*
	public void 捨て牌Test23()
		throws Exception
	{
		ExecuteTest(SutehaiData.SutehaiData.data123);

		ComputerPlayer player = new ComputerPlayer(PaiKind.南, 0);

		player.tehai = new PaiList(SutehaiData.SutehaiData.data123.Tehai);
		player.tehai.RemoveAt(13);
		boolean [] sutehai = player.GetRiichiSengenPai(new Pai(PaiKind.四索));

		Assert.assertEquals(
			new boolean []
				{
					false, false, false,
					false, false, false,
					false, false,
					true, true,
					false, false, false,
					true
				},
			sutehai);
	}
	*/
}
