package kumagai.Maajan.logic;

import java.util.*;

/**
 * プレイヤー操作用メッセージキュー。
 */
public class MessageQueue
	extends ArrayList<Message>
{
	private boolean valid;

	/**
	 * メッセージキューを構築。
	 */
	public MessageQueue()
	{
		valid = true;
	}

	/**
	 * メッセージを投入。通知も行う。
	 * @param message メッセージ
	 */
	public synchronized void put(Message message)
	{
		add(message);
		notify();
	}

	/**
	 * メッセージ取得。メッセージがない場合は待機する。
	 * @return メッセージ／null=メッセージ無し
	 */
	public synchronized Message get()
		throws InterruptedException
	{
		while (size() <= 0 && valid)
		{
			// データがない場合。

			// 待機。
			wait();
		}

		if (size() > 0 && valid)
		{
			// 待ったらデータがあった場合。

			Message command = get(0);
			remove(0);

			return command;
		}
		else
		{
			// 待ってもデータがない場合。

			return null;
		}
	}

	/**
	 * キューイングの停止。
	 */
	public synchronized void breakQueue()
	{
		valid = false;
		notify();
	}
}
