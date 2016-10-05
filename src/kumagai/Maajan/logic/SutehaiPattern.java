package kumagai.Maajan.logic;

import java.util.*;;

/**
 * 捨て牌パターン。
 */
public class SutehaiPattern
	extends ArrayList<Integer>
{
	public int loopCount;

	/**
	 * 捨て牌パターンを構築。
	 * @param tehai 手牌
	 */
	public SutehaiPattern(ArrayList<Pai> tehai)
		throws Exception
	{
		ArrayList<Integer> checkPai = new ArrayList<Integer>();
		ArrayList<Integer> sutehai = new ArrayList<Integer>();

		for (int i=0 ; i<tehai.size() ; i++)
		{
			ArrayList<Pai> tehai2 = new ArrayList<Pai>(tehai);

			if (!checkPai.contains(tehai2.get(i).kind))
			{
				// まだチェックしてない牌。

				tehai2.remove(i);

				ArrayList<Integer> machi;

				// ４面子１雀頭判定。
				MachiPattern machi1 = new MachiPattern(tehai2, false);

				//if (machi1.Pattern != null)
				//{
					// 待ちパターンを生成した。

					loopCount += machi1.loopCount;
				//}

				machi = machi1.getMachi();

				// 七対子判定。
				if (machi.size() <= 0)
				{
					// ４面子１雀頭ではない。

					machi = ChiitoitsuCheck.getMachi(tehai2);
				}

				// 国士無双判定。
				if (machi.size() <= 0)
				{
					// 七対子でもない。

					machi = KokushimusouCheck.getMachi(tehai2);
				}

				if (machi.size() > 0)
				{
					// 聴牌する。

					sutehai.add(tehai.get(i).kind);
				}

				checkPai.add(tehai.get(i).kind);
			}
		}

		for (int i=0 ; i<tehai.size() ; i++)
		{
			if (sutehai.contains(tehai.get(i).kind))
			{
				// リーチ宣言牌である。

				add(i);
			}
		}
	}
}
