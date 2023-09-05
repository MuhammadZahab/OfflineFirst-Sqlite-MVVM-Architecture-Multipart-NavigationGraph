package com.assessment.todo.data.anime.local

object ContractClass {

            const val TABLE_NAME = "anime"
            const val _ID = "_id"
            const val TITLE_COLUMN = "title"
            const val ENGLISH_TITLE_COLUMN = "english_title"

            const val CREATE_ANIME_TABLE = "CREATE TABLE $TABLE_NAME (" +
                    "$_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$TITLE_COLUMN VARCHAR(256), " +
                    "$ENGLISH_TITLE_COLUMN VARCHAR(256)" +
                    ")"


}