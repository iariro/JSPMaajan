package kumagai.Maajan.logic;

/**
 * 洗牌用の牌とランダム値の組。
 */
public class PaiAndRandomValue
{
	public final int pai;
	public final double randomValue;

	/**
	 * 牌とランダム値の組を構築。
	 * @param pai 牌
	 * @param randomValue ランダム値
	 */
	public PaiAndRandomValue(int pai, double randomValue)
	{
		this.pai = pai;
		this.randomValue = randomValue;
	}
}
