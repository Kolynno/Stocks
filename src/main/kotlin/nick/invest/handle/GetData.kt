package nick.invest.handle

import java.io.File

class GetData {

    companion object{
       fun getFileNames(): Array<out File> {
           val folder = File("src/main/resources/data10min/")
           val files = folder.listFiles()
           return files!!
       }
    }

}