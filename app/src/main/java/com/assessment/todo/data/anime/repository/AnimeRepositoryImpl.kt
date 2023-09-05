package com.assessment.todo.data.anime.repository

import com.assessment.todo.data.anime.local.dao.AnimeDao
import com.assessment.todo.data.anime.remote.api.IAnimeApi
import com.assessment.todo.data.anime.remote.model.AnimeResponseModel
import com.assessment.todo.domain.anime.repository.IAnimeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AnimeRepositoryImpl @Inject constructor(private val animeApi: IAnimeApi, private val animeDao: AnimeDao) :
    IAnimeRepository {

    override suspend fun getAnimeListFromRemote():Flow<ArrayList<AnimeResponseModel.Data>>{

       return flow {

            if(animeDao.loadAnimes().size<1) {
                val response = animeApi.getAnimeList()
                if (response.isSuccessful) {
                    response.body()?.let { animeDao.insertAnimeRecord(it.data) }
                    response.body()?.data?.let { emit(it) }
                }
            }else{
                animeDao.loadAnimes()?.let { emit(it) }

            }
        }

    }

    override suspend fun getAnimeListFromDB(): Flow<ArrayList<AnimeResponseModel.Data>> {

        return flow {
             animeDao.loadAnimes()
        }

    }

    override suspend fun insertAnimeListInDB(data: ArrayList<AnimeResponseModel.Data>) {
        animeDao.insertAnimeRecord(data)
    }

    override suspend fun deleteAnimeListFromDB() {
        animeDao.deleteAllRecords()
    }
}