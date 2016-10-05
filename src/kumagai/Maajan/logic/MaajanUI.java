package kumagai.Maajan.logic;

import java.util.*;

/**
 * ユーザーインターフェイス。
 */
public interface MaajanUI
{
	/**
	 * 手牌表示。
	 * @param playerIndex プレイヤーのインデックス
	 * @param player プレイヤー
	 */
	void displayTehai(int playerIndex, Player player);

	/**
	 * 手牌ハイライト化。
	 * @param playerIndex プレイヤーのインデックス
	 * @param highlight ハイライト化フラグ
	 */
	void highlightTehai(int playerIndex, boolean [] highlight);

	/**
	 * ツモ牌表示。
	 * @param playerIndex プレイヤーインデックス
	 * @param player プレイヤー
	 * @param pai ツモ牌／null＝消去
	 */
	void displayTsumo(int playerIndex, Player player, Pai pai);

	/**
	 * ドラ表示。
	 * @param dora ドラ牌
	 * @param uradora true=裏ドラあり
	 */
	void displayDora(ArrayList<Pai> dora, boolean uradora);

	/**
	 * ドラ牌フラッシュ。
	 * @param players プレイヤーの配列
	 * @param dora ドラ牌
	 * @param flush true=色を変える/false=元に戻す
	 */
	void flushDora(List<Player> players, List<Pai> dora, boolean flush);

	/**
	 * ツモ牌フラッシュ。
	 * @param playerIndex プレイヤーインデックス
	 * @param player プレイヤー
	 * @param flush true=色を変える/false=元に戻す
	 */
	void flushTsumo(int playerIndex, Player player, boolean flush);

	/**
	 * 牌前進後退。
	 * @param playerIndex プレイヤーのインデックス
	 * @param paiIndex 牌インデックス
	 * @param push true=前進/false=後退
	 */
	void advancePai(int playerIndex, int paiIndex, boolean push);

	/**
	 * 残り牌数表示。
	 * @param num 残り牌数
	 */
	void displayNokoriPaiNum(int num);

	/**
	 * 捨て牌表示。
	 * @param playerIndex プレイヤーインデックス
	 * @param index 捨て牌インデックス
	 * @param pai 捨て牌
	 */
	void displaySutehai(int playerIndex, int index, Pai pai);

	/**
	 * 捨て牌グレー化。
	 * @param playerIndex プレイヤーインデックス
	 * @param paiIndex 捨て牌インデックス
	 */
	void blankSutehai(int playerIndex, int paiIndex);

	/**
	 * 捨て牌クリア。
	 * @param playerIndex プレイヤーインデックス
	 */
	void clearSutehai(int playerIndex);

	/**
	 * ボタン状態更新。
	 * @param riichi true=リーチ可能／不能
	 * @param chii true=チー可能／不能
	 * @param pon true=ポン可能／不能
	 * @param kan true=槓可能／不能
	 * @param agari true=和了可能／不能
	 * @param cancel true=キャンセル可能／不能
	 */
	void setButtonState(boolean riichi, boolean chii, boolean pon, boolean kan,
		boolean agari, boolean cancel);

	/**
	 * 鳴きボタン状態更新。
	 * @param enable true=有効化／false=無効化
	 */
	void setNakiButtonState(boolean enable);

	/**
	 * リーチ宣言牌表示。
	 * @param playerIndex プレイヤーインデックス
	 * @param riichiSengenPai リーチ宣言牌
	 */
	void displayRiichiPai(int playerIndex, boolean [] riichiSengenPai);

	/**
	 * リーチ宣言牌クリア。
	 * @param playerIndex プレイヤーインデックス
	 */
	void clearRiichiPai(int playerIndex);

	/**
	 * 待ち牌表示。
	 * @param playerIndex プレイヤーインデックス
	 * @param machi 待ち牌のコレクション
	 */
	void displayMachi(int playerIndex, List<Integer> machi);

	/**
	 * 和がりを通知。
	 * @param type 1=ツモ和がり／2=ロン和がり
	 * @param name プレイヤー名
	 * @param message メッセージ
	 */
	void notifyAgari(int type, String name, String message);

	/**
	 * メッセージ表示。
	 * @param message メッセージ
	 */
	void displayMessage(String message);

	/**
	 * 役表示。
	 * @param yaku 役文字列
	 */
	void displayYaku(ScoreData yaku);
}
