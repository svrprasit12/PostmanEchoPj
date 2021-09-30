package com.exam.PostmanEcho.api.application.port.`in`

import com.exam.PostmanEcho.api.domain.Request
import com.exam.PostmanEcho.api.domain.RequestForm
import javax.servlet.http.HttpServletRequest

interface ReadRequest {

    fun sendBackGetRequest(request: HttpServletRequest, headers: Map<String,String>, args: Map<String, String>) : Request

    fun sendBackPostApplicationJson(request: HttpServletRequest, headers: Map<String,String>, args: Map<String, String>, json: Map<String,String>) : RequestForm

    fun sendBackPostFormUrlEncode(request: HttpServletRequest, headers: Map<String,String>, args: Map<String, String>) : RequestForm

    fun sendBackPostMultiPartForm(request: HttpServletRequest, headers: Map<String,String>) : RequestForm
}