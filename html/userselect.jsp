<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>麻雀</title>
	</head>

	<body>

		<s:form action="machihantei2" theme="simple">
			<s:select name="tehai1" list="allPai" listKey="id" listValue="name" value="0" />
			<s:select name="tehai2" list="allPai" listKey="id" listValue="name" value="0" />
			<s:select name="tehai3" list="allPai" listKey="id" listValue="name" value="0" />
			<s:select name="tehai4" list="allPai" listKey="id" listValue="name" value="1" />
			<s:select name="tehai5" list="allPai" listKey="id" listValue="name" value="2" />
			<s:select name="tehai6" list="allPai" listKey="id" listValue="name" value="3" />
			<s:select name="tehai7" list="allPai" listKey="id" listValue="name" value="4" />
			<s:select name="tehai8" list="allPai" listKey="id" listValue="name" value="5" />
			<s:select name="tehai9" list="allPai" listKey="id" listValue="name" value="6" />
			<s:select name="tehai10" list="allPai" listKey="id" listValue="name" value="7" />
			<s:select name="tehai11" list="allPai" listKey="id" listValue="name" value="8" />
			<s:select name="tehai12" list="allPai" listKey="id" listValue="name" value="8" />
			<s:select name="tehai13" list="allPai" listKey="id" listValue="name" value="8" />
			<s:submit value="判定" />
		</s:form>

	</body>
</html>
