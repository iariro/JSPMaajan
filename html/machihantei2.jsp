<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<title>麻雀待ち判定</title>
		<meta http-equiv=Content-Style-Type content=text/css>
		<link media=all href="hatena.css" type=text/css rel=stylesheet>
	</head>

	<body>
		<div class=hatena-body>
		<div class=main>

		<h1>待ち判定</h1>
		<div class=day>
		<h2>ユーザー指定</h2>

		<div class=body>
		<h3>手牌</h3>
		<div class=section>
			<s:iterator value="tehaiFileName">
				<image src="image/<s:property />">
			</s:iterator>
			<br>
		</div>
		<h3>待ちタイプ</h3>
		<div class=section>
		<s:property value="tenpaiType" />
		</div>

		<h3>待ち牌</h3>
		<div class=section>
			<s:iterator value="machihaiFileName">
				<img src="image/<s:property />">
			</s:iterator>
		</div>

		<h3>待ち牌内訳</h3>
		<div class=section>
			<s:iterator value="machiCollection">
				<s:iterator value="machihaiFileName">
					<img src="image/<s:property />">
				</s:iterator>
				の<s:property value="type" /><br>
			</s:iterator>
		</div>
		</div>
		</div>
		</div>
		</div>

	</body>
</html>
