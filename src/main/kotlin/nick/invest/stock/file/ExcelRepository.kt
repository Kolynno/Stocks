package nick.invest.stock.file

import nick.invest.stock.controllers.StockChange
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import java.io.FileOutputStream

class ExcelRepository {
        fun createTable(lines: List<StockChange>){
            // Создаем новую книгу Excel
            val workbook = XSSFWorkbook()
            // Создаем лист
            val sheet = workbook.createSheet("StockChangeData")

            // Заголовок
            val headerRow = sheet.createRow(0)
            val headers = arrayOf("Тикер", "Название", "Капитализация, млрд. руб", "Дата", "Цена закрытия, руб",
                "Изменение цены за 16 дней, %", "Окно на сегодня (W)", "Просмотр назад (C)",
                "Общее кол-во появления данной комбинации W и C за весь период акции", "Процент (P)")
            for ((index, header) in headers.withIndex()) {
                val cell = headerRow.createCell(index, CellType.STRING)
                cell.setCellValue(header)
            }

            // Добавляем данные
            for ((rowIndex, stockChange) in lines.withIndex()) {
                val row = sheet.createRow(rowIndex + 1) // +1, так как первая строка уже занята заголовком
                row.createCell(0, CellType.STRING).setCellValue(stockChange.ticker)
                row.createCell(1, CellType.STRING).setCellValue(stockChange.name)
                row.createCell(2, CellType.NUMERIC).setCellValue(stockChange.capitalization.toDouble())
                row.createCell(3, CellType.STRING).setCellValue(stockChange.date)
                row.createCell(4, CellType.NUMERIC).setCellValue(stockChange.close.toDouble())
                row.createCell(5, CellType.NUMERIC).setCellValue(stockChange.change16d.toDouble())
                row.createCell(6, CellType.NUMERIC).setCellValue(stockChange.window.toDouble())
                row.createCell(7, CellType.NUMERIC).setCellValue(stockChange.count.toDouble())
                row.createCell(8, CellType.NUMERIC).setCellValue(stockChange.total.toDouble())
                row.createCell(9, CellType.NUMERIC).setCellValue(stockChange.percent.toDouble() * 100)
            }

            // Сохраняем книгу в файл
            val date = lines[1].date
            val fileOut = FileOutputStream("src/main/resources/data/Табличка Программы за $date.xlsx")
            workbook.write(fileOut)
            fileOut.close()
        }
}