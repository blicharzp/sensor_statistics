package com.ubs.opsit.interviews

import java.io.File

object FilenameCollector {
    def collect(path: String): List[String] = {
        try {
            new File(path).listFiles
                          .filter(_.isFile)
                          .toList
                          .map(_.toString)
                          .filter(_.endsWith(".csv"))
        } catch {
            case _: Throwable => List[String]()
        }
    }
}
