package com.arbelkilani.binge.tv.common.source.remote.pagingsource

import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.discover.data.mapper.TvMapper
import com.arbelkilani.binge.tv.feature.discover.domain.entities.TvEntity
import retrofit2.HttpException
import java.io.IOException
import java.util.*
import javax.inject.Inject

class AiringTodayPagingSource @Inject constructor(
    private val service: ApiService,
    private val tvMapper: TvMapper
) : TvEntityPagingSource(service, tvMapper) {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvEntity> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val timezone = TimeZone.getDefault().id
            val response = service.getAiringToday(position, timezone)
            val tvShows = response.results
                .filter { it.voteAverage > 5f }
                .map {
                    tvMapper.map(it)
                }.sortedByDescending { it.voteAverage }

            LoadResult.Page(
                data = tvShows,
                prevKey = if (position == STARTING_PAGE_INDEX) null else position - 1,
                nextKey = if (position > response.totalPages) null else position + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }
}