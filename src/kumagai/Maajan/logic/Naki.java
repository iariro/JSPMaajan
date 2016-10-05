package kumagai.Maajan.logic;

import java.util.*;

/**
 * 鳴き情報。
 */
public class Naki
	extends ArrayList<Pai>
{
	public int type;
	public final int direction;

	/**
	 * 鳴き情報を構築。
	 * @param type 鳴き種別
	 * @param direction 鳴く牌を捨てたプレイヤーの方向
	 */
	public Naki(int type, int direction)
	{
		this.type = type;
		this.direction = direction;
	}
}
