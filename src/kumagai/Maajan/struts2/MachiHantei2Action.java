package kumagai.Maajan.struts2;

import java.util.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.Maajan.logic.*;

/**
 * 待ち判定アクション。
 * @author kumagai
 */
@Namespace("/maajan")
@Result(name="success", location="/maajan/machihantei2.jsp")
public class MachiHantei2Action
	extends MachiHanteiAction
{
	public String tehai1;
	public String tehai2;
	public String tehai3;
	public String tehai4;
	public String tehai5;
	public String tehai6;
	public String tehai7;
	public String tehai8;
	public String tehai9;
	public String tehai10;
	public String tehai11;
	public String tehai12;
	public String tehai13;

	/**
	 * 待ち判定を行う。
	 * @return 処理結果文字列
	 * @throws Exception
	 */
	@Action("machihantei2")
	public String execute()
		throws Exception
	{
		String [] tehaiString =
			new String []
			{
				tehai1,	tehai2, tehai3, tehai4, tehai5,
				tehai6,	tehai7, tehai8, tehai9, tehai10,
				tehai11, tehai12, tehai13
			};

		ArrayList<Pai> tehai = new ArrayList<Pai>();

		for(int i=0 ; i<13 ; i++)
		{
			tehai.add(new Pai(Integer.valueOf(tehaiString[i])));
		}

		hantei(tehai);

		return "success";
	}
}
