# ORISGame Client

1. Игрок соединяется с сервером по определенному IP адресу и номеру порта, после соединения отправляет сообщение:
{"command":"start","clientName":"Иван"}\n
2. В ответ сервер отправляет:
{"status":"start","message":"Привет, Иван","startPoint":[1,4],"raiting":[]}\n
3. Далее игрок отправляет направление следующего хода, (считаем, что в начале игрок стоит в указанной точке входа):
{"command":"direction","direction":"d"}\n
Допустимые направления: u, d, r, l
4. Сервер отвечает:
{"status":"go","result":"0"}\n — если в указанном направлении проход, тогда игрок перемещается;
{"status":"go","result":"1"}\n — если в указанном направлении стена, тогда игрок остается на месте;
{"status":"stop","result":"N", "min":"M","raiting":[{"name":"Вася","steps":"10","min":"9"},{"name":"Gtnz","steps":"15","min":"11"}]}\n — если игрок достиг выхода, N — количество сделанных шагов, M — минимально возможное число шагов.
5. В любой момент игрок может остановить игру, разорвав соединение, или направив сообщение {"command":"stop"}\n
Сервер отвечает {"status":"stop", "raiting":[{"name":"Вася","steps":"10","min":"9"},{"name":"Gtnz","steps":"15","min":"11"}]}\n и завершает соединение с клиентом.
