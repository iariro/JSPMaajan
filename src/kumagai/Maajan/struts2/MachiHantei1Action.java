package kumagai.Maajan.struts2;

import java.util.*;
import java.lang.reflect.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.Maajan.logic.*;

/**
 * 待ち判定アクション。
 * @author kumagai
 */
@Namespace("/maajan")
@Result(name="success", location="/maajan/machihantei1.jsp")
public class MachiHantei1Action
	extends MachiHanteiAction
{
	public String data;
	public String [] dataSplitted;
	public String mensuu;
	public String number;

	/**
	 * 待ち判定を行う。
	 * @return 処理結果文字列
	 * @throws Exception
	 */
	@Action("machihantei1")
	public String execute()
		throws Exception
	{
		dataSplitted = data.split("@");

		mensuu = dataSplitted[0];
		number = dataSplitted[1];

		Class<?> data =
			Class.forName(
				"kumagai.Maajan.logictest.tenpai.TenpaiData" +
				mensuu);

		Field field = data.getField("data" + number);
		PaiKind[][] tehaiAndMachi = (PaiKind[][])field.get(data);
		PaiKind [] tehai = tehaiAndMachi[0];

		ArrayList<Pai> tehai2 = new ArrayList<Pai>();

		for(int i=0 ; i<tehai.length ; i++)
		{
			tehai2.add(new Pai(tehai[i]));
		}

		hantei(tehai2);

		return "success";
	}
}
