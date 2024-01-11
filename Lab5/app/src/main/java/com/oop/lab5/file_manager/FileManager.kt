package com.oop.lab5.file_manager

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.oop.lab5.R
import com.oop.lab5.tooltip.Tooltip
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.FileWriter

class FileManager(private val context: Context) {
    private lateinit var fileExtension: String
    private lateinit var path: String
    private lateinit var drawingsDir: File
    private lateinit var currentFile: File

    private lateinit var createFileDialog: CreateFileDialog
    private lateinit var filesDialog: FilesDialog

    private lateinit var onCreateFileListener: (String) -> String
    private lateinit var onOpenFileListener: (String, String) -> Unit
    private lateinit var onSaveFileListener: () -> String
    private lateinit var onDeleteFileListener: (String, String?) -> Unit

    fun onCreate(startListener: (String) -> Unit) {
        val root = context.getExternalFilesDir(null)
        val drawingsDirName = context.getString(R.string.drawings_dir_name)
        drawingsDir = File(root, drawingsDirName)
        if (!drawingsDir.exists()) drawingsDir.mkdirs()
        path = drawingsDir.absolutePath
        fileExtension = context.getString(R.string.file_extension)
        val fileName = getDefaultFileName()
        currentFile = File(path, fileName)
        startListener(fileName)

        createFileDialog = CreateFileDialog()
        createFileDialog.setFileCreationListeners({}, ::createFile)

        filesDialog = FilesDialog()
        filesDialog.setOnFileListeners(::openFile, ::deleteFile)
    }

    fun files(manager: FragmentManager) {
        filesDialog.display(manager, drawingsDir.list())
    }

    fun save() {
        val str = onSaveFileListener()
        val writer = FileWriter(currentFile)
        writer.append(str)
        writer.flush()
        writer.close()
        Tooltip(context)
            .create("Малюнок збережено у файлі ${currentFile.name}")
            .display()
    }

    fun saveAs(manager: FragmentManager) {
        createFileDialog.display(manager, getShortFileName(getDefaultFileName()))
    }

    private fun getShortFileName(fileName: String): String {
        return fileName.removeSuffix(fileExtension)
    }

    private fun getShortFileNames(): List<String>? {
        return drawingsDir.list()?.map {
            getShortFileName(it)
        }
    }

    private fun getDefaultFileName(): String {
        val nameStart = context.getString(R.string.default_short_file_name)
        var nameEnd = 1
        var name = "$nameStart$nameEnd"
        val shortFileNames = getShortFileNames()
        if (shortFileNames != null) {
            while (name in shortFileNames) {
                nameEnd++
                name = "$nameStart$nameEnd"
            }
        }
        return name + fileExtension
    }

    private fun openFile(fileName: String) {
        val serializedDrawing = StringBuilder()
        currentFile = File(path, fileName)
        val bufferReader = BufferedReader(FileReader(currentFile))
        var text: String? = bufferReader.readLine()
        while (text != null) {
            serializedDrawing.append("$text\n")
            text = bufferReader.readLine()
        }
        bufferReader.close()
        onOpenFileListener(fileName, serializedDrawing.toString())
    }

    private fun createFile(shortFileName: String): Pair<Boolean, String> {
        val shortFileNames = getShortFileNames()
        return if (shortFileName == "") {
            false to "Порожнє ім'я"
        } else if (
            shortFileNames != null &&
            shortFileNames.contains(getShortFileName(shortFileName))
            ) {
            false to "Використане ім'я"
        } else {
            val fileName = shortFileName + fileExtension
            currentFile = File(path, fileName)
            val str = onCreateFileListener(fileName)
            val writer = FileWriter(currentFile)
            writer.append(str)
            writer.flush()
            writer.close()
            true to "Малюнок збережено у файлі $fileName"
        }
    }

    private fun deleteFile(fileName: String) {
        val file = File(path, fileName)
        file.delete()
        onDeleteFileListener(fileName,
            if (file.name != currentFile.name) null
            else getDefaultFileName()
        )
    }

    fun setOnFileListeners(
        createListener: (String) -> String,
        openListener: (String, String) -> Unit,
        saveListener: () -> String,
        deleteListener: (String, String?) -> Unit
    ) {
        onCreateFileListener = createListener
        onOpenFileListener = openListener
        onSaveFileListener = saveListener
        onDeleteFileListener = deleteListener
    }
}
