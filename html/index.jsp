<%@ page contentType="text/html; charset=UTF-8" %>

<%@ taglib uri="/struts-tags" prefix="s" %>

<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>麻雀</title>
	</head>

	<body>
		<s:form action="machihantei1">
			<select name="data">
				<option value="1@101" selected>1@101</option>
				<option value="1@102">1@102</option>
				<option value="1@103">1@103</option>
				<option value="1@104">1@104</option>
				<option value="1@105">1@105</option>
				<option value="1@106">1@106</option>
				<option value="1@107">1@107</option>
				<option value="2@201">2@201</option>
				<option value="2@202">2@202</option>
				<option value="2@203">2@203</option>
				<option value="2@204">2@204</option>
				<option value="2@205">2@205</option>
				<option value="2@206">2@206</option>
				<option value="2@207">2@207</option>
				<option value="3@301">3@301</option>
				<option value="3@302">3@302</option>
				<option value="3@303">3@303</option>
				<option value="3@304">3@304</option>
				<option value="3@305">3@305</option>
				<option value="3@306">3@306</option>
				<option value="3@307">3@307</option>
				<option value="3@308">3@308</option>
				<option value="3@309">3@309</option>
				<option value="3@310">3@310</option>
				<option value="3@311">3@311</option>
				<option value="3@312">3@312</option>
				<option value="3@313">3@313</option>
				<option value="3@314">3@314</option>
				<option value="3@315">3@315</option>
				<option value="3@316">3@316</option>
				<option value="3@317">3@317</option>
				<option value="3@318">3@318</option>
				<option value="3@319">3@319</option>
				<option value="3@320">3@320</option>
				<option value="4@401">4@401</option>
				<option value="4@402">4@402</option>
				<option value="4@403">4@403</option>
				<option value="4@404">4@404</option>
				<option value="4@405">4@405</option>
				<option value="4@406">4@406</option>
				<option value="4@407">4@407</option>
				<option value="4@408">4@408</option>
				<option value="4@409">4@409</option>
				<option value="4@410">4@410</option>
				<option value="4@411">4@411</option>
				<option value="4@412">4@412</option>
				<option value="4@413">4@413</option>
				<option value="5@501">5@501</option>
				<option value="5@502">5@502</option>
				<option value="5@503">5@503</option>
				<option value="5@504">5@504</option>
				<option value="5@505">5@505</option>
				<option value="5@506">5@506</option>
				<option value="5@507">5@507</option>
				<option value="5@508">5@508</option>
				<option value="5@509">5@509</option>
				<option value="5@510">5@510</option>
				<option value="6@601">6@601</option>
				<option value="6@602">6@602</option>
				<option value="6@603">6@603</option>
				<option value="6@604">6@604</option>
				<option value="6@605">6@605</option>
				<option value="7@701">7@701</option>
				<option value="7@702">7@702</option>
				<option value="7@703">7@703</option>
				<option value="7@704">7@704</option>
				<option value="8@801">8@801</option>
				<option value="8@802">8@802</option>
				<option value="8@803">8@803</option>
				<option value="8@804">8@804</option>
				<option value="9@901">9@901</option>
				<option value="Chiitoitsu@101">Chiitoitsu@101</option>
				<option value="Chiitoitsu@102">Chiitoitsu@102</option>
				<option value="Chiitoitsu@103">Chiitoitsu@103</option>
				<option value="Kokushimusou@101">Kokushimusou@101</option>
				<option value="Kokushimusou@102">Kokushimusou@102</option>
				<option value="Kokushimusou@103">Kokushimusou@103</option>
			</select>
			<s:submit value="判定" />
		</s:form>

		<a href="userselect.action">userselect</a>
	</body>
</html>
