package com.ubs.opsit.interviews

object Main {
    def main(args: Array[String]): Unit = args match {
        case Array(path, _*) => FileParser.parse(FilenameCollector.collect(path))
        case _ => println("Provide valid path")
    }
}
