package kumagai.Maajan.logic;

/**
 * 役データ。
 */
public class YakuData
{
	public final boolean yakuman;
	public final int menzenFan;
	public final int nakiFan;

	/**
	 * 役データを構築。
	 * @param yakuman true=役満／false=役満ではない
	 * @param menzenFan 門前の時の翻数
	 * @param nakiFan 鳴いた時の翻数
	 */
	public YakuData(boolean yakuman, int menzenFan, int nakiFan)
	{
		this.yakuman = yakuman;
		this.menzenFan = menzenFan;
		this.nakiFan = nakiFan;
	}
}
