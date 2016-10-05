package kumagai.Maajan.struts2;

import java.util.*;
import org.apache.struts2.convention.annotation.*;
import kumagai.Maajan.logic.*;

/**
 * ユーザー選択画面表示アクション。
 * @author kumagai
 */
@Namespace("/maajan")
@Result(name="success", location="/maajan/userselect.jsp")
public class UserSelectAction
{
	public ArrayList<IdAndName> allPai;

	/**
	 * 待ち判定を行う。
	 * @return 処理結果文字列
	 * @throws Exception
	 */
	@Action("userselect")
	public String execute()
		throws Exception
	{
		allPai = new ArrayList<IdAndName>();

		int id = 0;

		for (PaiKind pai : PaiKind.values())
		{
			allPai.add(new IdAndName(id, pai.toString()));
			id++;
		}

		return "success";
	}
}
