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

***

*ROADMAP_mini*
1) ~~Автозагрузка данных в SQL~~
2) ~~Автозагрузка данных CSV~~
3) ~~Первоначальный вид графиков~~
4) ~~Рефакторинг~~
5) ~~Загрузка дивидендов~~
6) **Обзор дивидендов**
7) Таблица с данными



Добавить столбцы (
w
c
percent
ВОЗМОЖНО percent per day
)

Проиграть...