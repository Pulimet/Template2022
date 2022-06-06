package net.alexandroid.template2022.repo.api

import net.alexandroid.template2022.db.dao.ApiDao
import net.alexandroid.template2022.db.model.api.Api
import net.alexandroid.template2022.ui.api.ApiCaller
import net.alexandroid.template2022.utils.logs.logI

class ApiRepo(private val apiDao: ApiDao, private val apiCaller: ApiCaller) {
    init {
        logI("init")
    }

    fun getAll() = apiDao.getApis()

    suspend fun addApi(api: Api) {
        apiDao.insert(api)
    }

    suspend fun deleteApi(api: Api) {
        apiDao.delete(api)
    }

    suspend fun editApi(api: Api, apiArgument: Api) {
        if (api.baseUrl != apiArgument.baseUrl) {
            apiDao.delete(apiArgument)
        }
        apiDao.insert(api)
    }

    suspend fun callFor(api: Api): ApiResult =
        try {
            ApiResult.Success(apiCaller.call(api))
        } catch (e: Exception) {
            ApiResult.Error(e)
        }
}