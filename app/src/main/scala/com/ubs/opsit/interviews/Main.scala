package com.ubs.opsit.interviews


object Main {
    def main(args: Array[String]): Unit = {
        args foreach (arg => println(FilenameCollector.collect(arg)))
        // args foreach (arg => println(timeConverter.convertTime(arg)))
    }
}

case object FilenameCollector {
    def collect(path: String): List[String] = {
        List(path)
    }
}
