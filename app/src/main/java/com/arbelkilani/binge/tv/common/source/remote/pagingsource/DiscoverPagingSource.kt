package com.arbelkilani.binge.tv.common.source.remote.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.arbelkilani.binge.tv.common.domain.entity.ProviderEntity
import com.arbelkilani.binge.tv.common.source.remote.ApiService
import com.arbelkilani.binge.tv.feature.discover.data.mapper.TvResponseMapper
import com.arbelkilani.binge.tv.feature.discover.domain.entity.TvEntity
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

open class DiscoverPagingSource @Inject constructor(
    private val service: ApiService,
    private val tvResponseMapper: TvResponseMapper,
    private val queryMap: Map<String, String?>
) : PagingSource<Int, TvEntity>() {

    companion object {
        const val STARTING_PAGE_INDEX = 1
        const val OFFSET = 20
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TvEntity> {
        val position = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.discover(page = position, options = queryMap)
            val tvShows = response.results
                .filterNot {
                    it.backdrop.isNullOrEmpty() && it.poster.isNullOrEmpty()
                }.filterNot {
                    it.voteCount == 0
                }
                .map {
                    tvResponseMapper.map(it)
                }

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

    override fun getRefreshKey(state: PagingState<Int, TvEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(OFFSET)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(OFFSET)
        }
    }

    /*private suspend fun getTvProviders(id: Int): List<ProviderEntity>? {
        val result = service.getTvWatchProviders(id).result.filterKeys { key ->
            key == country
        }.map { map ->
            map.value
        }.firstOrNull()
        return result?.let { providerResponseMapper.map(it) }
    }*/

}