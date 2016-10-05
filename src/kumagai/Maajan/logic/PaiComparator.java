package kumagai.Maajan.logic;

import java.util.Comparator;

/**
 * Paiオブジェクト比較オブジェクト。
 * @author kumagai
 */
public class PaiComparator
	implements Comparator<Pai>
{
	/**
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(Pai arg0, Pai arg1)
	{
		return arg0.kind == arg1.kind ? 0 :
			arg0.kind > arg1.kind ? 1 : -1;
	}
}
