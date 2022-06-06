package net.alexandroid.template2022.ui.api

import net.alexandroid.template2022.db.model.api.Api
import net.alexandroid.template2022.network.services.ApiCallerService

class ApiCaller(private val apiCallerService: ApiCallerService) {
    suspend fun call(api: Api) = apiCallerService.call(api.getUrl())
}