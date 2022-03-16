package com.example.bravetestproject

import com.example.bravetestproject.btcassetlist.LogEntry
import java.io.File
import java.io.FileWriter

object Logger {

    private const val FILE_NAME = "BraveLogs.txt"

    fun log(entry: LogEntry) {
        val dir = File(BraveApp.getAppContext().filesDir, "logs")
        if (!dir.exists()) {
            dir.mkdir()
        }

        try {
            val file = File(dir, FILE_NAME)
            val writer = FileWriter(file)
            writer.append(entry.log)
            writer.flush()
            writer.close()
        } catch (e: Exception) {
        }
    }
}