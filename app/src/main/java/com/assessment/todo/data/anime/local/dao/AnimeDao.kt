package com.assessment.todo.data.anime.local.dao

import android.content.ContentValues
import android.content.Context
import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import com.assessment.todo.data.anime.local.ContractClass
import com.assessment.todo.data.anime.local.DatabaseOpenHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class AnimeDao @Inject constructor(private var databaseOpenHelper: DatabaseOpenHelper) {


    val contractClass = ContractClass
    val animes = ArrayList<AnimeResponseModel.Data>()

     fun deleteAllRecords() {

         CoroutineScope(Dispatchers.IO).launch {
             val database = databaseOpenHelper.writableDatabase



             database.delete(contractClass.TABLE_NAME, null, null)

         }


    }


    fun insertAnimeRecord(list: ArrayList<AnimeResponseModel.Data>) {



        val database = databaseOpenHelper.writableDatabase



               for (i in 0 until list!!.size) {
                   var data = list[i]
                   val values = ContentValues()
                   values.put(contractClass.TITLE_COLUMN, data?.title)
                   values.put(contractClass.ENGLISH_TITLE_COLUMN, data?.title_english)
                   database.insert(contractClass.TABLE_NAME, null, values)

               }





    }


    fun loadAnimes(): ArrayList<AnimeResponseModel.Data> {


            val database = databaseOpenHelper.readableDatabase


            val columns = arrayOf(
                contractClass._ID,
                contractClass.TITLE_COLUMN,
                contractClass.ENGLISH_TITLE_COLUMN
            )

            val result = database.query(
                contractClass.TABLE_NAME,
                columns,
                null, null, null, null, null
            )


            val taskIdIndex = result.getColumnIndex(contractClass._ID)
            val taskColumnIndex = result.getColumnIndex(contractClass.TITLE_COLUMN)
            val taskCompletedIndex = result.getColumnIndex(contractClass.ENGLISH_TITLE_COLUMN)

            if (animes.size != 0) {
                animes.clear()
            }

            while (result.moveToNext()) {
                val id = result.getInt(taskIdIndex)
                val taskDone = result.getString(taskColumnIndex)
                val taskCompleted = result.getString(taskCompletedIndex)

                val task = AnimeResponseModel.Data(taskDone, taskCompleted)
                animes.add(task)
            }
            result.close()


        return animes

    }

    fun closeDatabase(context: Context) {
        val database = DatabaseOpenHelper(context)
        database.close()
    }
}
