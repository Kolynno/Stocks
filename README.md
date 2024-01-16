**URLs:**
1) /db/main -  получить графики по promt
2) /db/update - обновить данные в SQL DB из готовых csv-файлов в папке data
3) /db/updateCSV - получить csv-файлы акций от MOEX и сохранить их в data
4) /db/updateDiv - обновить данные по дивидендам
5) /db/updateTemplate - обновить данные по текущему шаблону акции
6) /strategy/one - запустить стратегию 1
7) /table/main - таблица
8) /game/play - проиграть

**Управление**
1) Чтобы посмотреть график акций: 
   * Ввести /db/main
   * В promt указать название (например, "VKCO")
2) Чтобы обновить данные акций:
   * Ввести /db/updateCSV
   * Ввести /db/update
   * Ввести /db/updateTemplate
3) Чтобы обновить дивиденды
   * Ввести /db/updateDiv?ticker=VKCO
4) Чтобы посмотреть таблицу
   * Ввести /table/main
5) Чтобы создать excel по таблице
   * Ввести /table/main?createTable=true

***
User friendly вывод
Проще update 
***
Добавить по своему
***
Сколько в интервале 5,10,20 лет акция подряд росла/падала