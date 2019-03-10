package com.ubs.opsit.interviews

object Main {
    def main(args: Array[String]): Unit = args match {
        case Array(dirpath, _*) => process(dirpath)
        case _ => println("Provide valid path")
    }

    private def process(dirpath: String): Unit = {
        val filepaths = FilenameCollector.collect(dirpath)
        val measurements = FileParser.process(filepaths)
        Presenter.present(measurements)
    }
}
