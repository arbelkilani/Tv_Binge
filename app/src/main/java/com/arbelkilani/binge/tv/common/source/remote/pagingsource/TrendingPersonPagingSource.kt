package com.arbelkilani.binge.tv.common.source.remote.pagingsource

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.discover.data.mapper.PersonResponseMapper
import com.arbelkilani.binge.tv.feature.discover.domain.entities.PersonEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class TrendingPersonPagingSource @Inject constructor(
    private val service: ApiService,
    private val personResponseMapper: PersonResponseMapper
) : PagingSource<Int, PersonEntity>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
        private const val OFFSET = 20
        private const val TRENDING_TIME_WINDOW = "day" // Or "day"
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PersonEntity> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getTrendingPerson(TRENDING_TIME_WINDOW)
            val tvShows = response.results
                .map {
                    Log.i("TAG**", "response = $it")
                    personResponseMapper.map(it)
                }

            LoadResult.Page(
                data = tvShows,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position > response.totalPages) null else position + 1
            )
        } catch (exception: IOException) {
            Log.i("TAG**", "IOException = $exception")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i("TAG**", "HttpException = $exception")
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            Log.i("TAG**", "Exception = $exception")
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, PersonEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(OFFSET)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(OFFSET)
        }
    }
}