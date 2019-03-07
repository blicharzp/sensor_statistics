package com.ubs.opsit.interviews

import scalaz.stream._
import scalaz.concurrent._


object FileParser {
    def parse(filepaths: List[String]): Unit = { 
        filepaths foreach parseFile
    }

    private def parseFile(filepath: String): Unit = {
        println(filepath)
        val mapResult = io.linesR(filepath)
                          .drop(1)
                          .map(row => row.split(":"))
                          .runLog
                          .run
        println(mapResult)
    }


}
