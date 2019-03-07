package com.ubs.opsit.interviews

object FileParser {
    def parse(filenames: List[String]): Unit = { 
        filenames foreach parseFile
    }

    private def parseFile(filename: String): Unit = {
        println(filename)
    }

}
