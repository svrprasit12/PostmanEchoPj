package com.exam.PostmanEcho.api.application.service

import com.exam.PostmanEcho.api.application.port.`in`.ReadRequest
import com.exam.PostmanEcho.api.domain.Request
import com.exam.PostmanEcho.api.domain.RequestForm
import org.springframework.stereotype.Service
import java.util.*
import javax.servlet.http.HttpServletRequest

@Service
class ReadRequestService : ReadRequest {
    override fun sendBackGetRequest(
        request: HttpServletRequest,
        headers: Map<String, String>,
        args: Map<String, String>
    ): Request {
        return Request(args, headers, getUrl(request))
    }

    override fun sendBackPostApplicationJson(
        request: HttpServletRequest,
        headers: Map<String, String>,
        args: Map<String, String>,
        json: Map<String, String>
    ): RequestForm {
        return RequestForm(args,"", emptyMap(),json, headers,json, getUrl(request))
    }

    override fun sendBackPostFormUrlEncode(
        request: HttpServletRequest,
        headers: Map<String, String>,
        args: Map<String, String>
    ): RequestForm {
        var paramKey = request.queryString.split("&").map { it.split("=")[0] to it.split("=")[1] }.toMap()
        var form = args.minus(paramKey.keys)
        return RequestForm(paramKey,"", emptyMap(),form, headers,form, getUrl(request))
    }

    override fun sendBackPostMultiPartForm(request: HttpServletRequest, headers: Map<String, String>): RequestForm {
        var file:Map<String,String> = emptyMap()
        var form:Map<String,String> = emptyMap()
        request.parts.forEach {
            if(!it.submittedFileName.isNullOrEmpty()){
                var a =it.inputStream.readAllBytes()
                var f = it.contentType+";base64,"+ Base64.getEncoder().encodeToString(a)
                file += Pair(it.submittedFileName,f)
            }else{
                form += Pair(it.name,String(it.inputStream.readAllBytes()))
            }
        }
        var paramKey = request.queryString.split("&").map { it.split("=")[0] to it.split("=")[1] }.toMap()
        return RequestForm(paramKey,"", file,form, headers,form, getUrl(request))
    }

    fun getUrl(request: HttpServletRequest) :String{
        var param = ""
        if(!request.queryString.isNullOrEmpty()) param = "?"+request.queryString
        return request.requestURL.toString() + param
    }

}