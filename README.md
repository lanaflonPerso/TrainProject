TrainProject
============

Тема даного проекту: <b>"Розробка системи управління залізницею"</b>.<br>
В програмі наявні наступні об’єкти:<br>
- Станції: {S1, S2, S3}<br>
- Потяги: {T1, T2, T3}<br>
- Світлофор: {L1, L2, L3}<br>
- Шлагбауми: {B1, B2}<br>
- Перемикач: {P1}<br>

Приймемо для даної системи швидкість потягів однакової і рівною одній клітинці за одиницю часу.<br>

<h2>Операції</h2>
<b>Операції, пов’язані з предметною областю:</b><br><br>
Потяг:<br>
- T_1 - Рух вперед<br>
- T_2 - Зупинка<br>
- T_3 - Рух назад<br>
- T_4 - Подача сигналу<br>
- T_5 - Відчинити двері<br>
- T_6 - Зачинити двері<br><br>
Світлофор:<br>
- L_1 - Переключитися на червоний<br>
- L_2 - Переключитися на зелений<br><br>
Шлагбаум:<br>
- B_1 - Відкрити дорогу<br>
- B_2 - Закрити дорогу<br><br>
Перемикач:<br>
- P_1 - Змінити положення<br><br>
Станція:<br>
- S_1 - Перевірити дороги у напрямку маршруту прибулого потяга<br><br>

<b>Операцій для реалізації алгоритму:</b><br><br>
Станція:<br>
- C_S_1 – якщо на станцію прибув потяг<br>
- C_S_2 – якщо дороги в напрямку маршруту прибулого потяга не завантажені<br><br>
Шлагбаум:<br>
- C_B_1 – якщо потяг прибув до контрольної точки перед шлагбаумом<br>
- C_B_2 – якщо потяг проїхав шлагбаум, досягнувши своїм останнім вагоном контрольної точки за шлагбаумом<br><br>
Світлофор:<br>
- C_L_1 - Якщо потяг прибув до контрольної точки перед світлофором<br>
- C_L_2 - Якщо світить зелений колір світлофора<br>
- C_L_3 - Якщо світить червоний колір світлофора<br><br>
Перемикач:<br>
- C_P_1 - Якщо для руху потяга необхідне переключення перемикача<br>
- C_P_2 - Якщо через перемикач не проїжджає інший потяг<br><br>
Додатково:<br>
- C_A_1 - Поки на дорозі немає перешкод<br>
- C_A_2 - Якщо на шляху поїзда є перешкода<br>
<h2>Переривання</h2>
1. Перешкода на залізничному шляху (людина, тварина, автомобіль, нерухомий вантаж).<br>
- якщо перешкодою є не нерухомий вантаж, то подати попереджувальний звуковий сигнал;<br>
- зупинити потяг, якщо перешкода не відреагувала на  сигнал;<br>
- усунути перешкоду;<br>
- продовжити рух.<br>
2. Технічні неполадки потяга.<br>
- припинити рух;<br>
- усунути неполадки;<br>
- відновити рух потяга.<br>

<h2>Алгоритми</h2>
<b>Станція</b><br>
- Якщо досягнуто станцію – C_S_1<br>
-- Зупинити рух потяга – T_2<br>
-- Перевірити дороги у напрямку маршруту прибулого потяга – S_1<br>
---- Відчинити двері потяга – T_5 (паралельно з S_1)<br>
---- Зачинити двері потяга – T_6 (паралельно з S_1)<br>
-- Якщо дороги у напрямку маршруту прибулого потяга не завантажені – C_S_2<br>
---- Рух потяга вперед – T_1<br><br>
<b>Перемикач</b><br>
- Якщо потяг прибув до контрольної точки перед світлофором – C_L_1<br>
-- Якщо світлофор зелений – C_L_2<br>
---- Рух потяга вперед – T_1<br>
-- Якщо світлофор червоний – C_L_3<br>
---- Увімкнути червоний колір для 2 інших світлофорів – L_1<br>
---- Змінити положення перемикача – P_1<br>
---- Увімкнути зелений колір для 2 інших світлофорів – L_2<br><br>

<b>Шлагбаум</b><br>
- Якщо потягом досягнута контрольна точка перед шлагбаумом – C_B_1<br>
-- Закрити дорогу – B_2<br>
-- Якщо досягнута контрольна точка після шлагбаума – C_B_2<br>
---- Відкрити дорогу – B_1<br>
