package kumagai.Maajan.logic;

/**
 * ツモループの処理結果。
 * @author kumagai
 */
public class TsumoLoopResult
{
	public final boolean agari;
	public final int dahaiIndex;

	/**
	 * 指定の値を割り当てる。
	 * @param agari true=和がり／false=和がりではない
	 * @param dahaiIndex 打牌インデックス
	 */
	public TsumoLoopResult(boolean agari, int dahaiIndex)
	{
		this.agari = agari;
		this.dahaiIndex = dahaiIndex;
	}
}
