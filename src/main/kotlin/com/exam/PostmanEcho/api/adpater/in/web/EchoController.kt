package com.exam.PostmanEcho.api.adpater.`in`.web

import com.exam.PostmanEcho.api.domain.Request
import com.exam.PostmanEcho.api.domain.RequestForm
import com.exam.PostmanEcho.common.WebAdapter
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.servlet.http.HttpServletRequest

@WebAdapter
@RestController
class EchoController {

    @GetMapping("/get")
    fun get(request: HttpServletRequest, @RequestHeader headers: Map<String,String>, @RequestParam args: Map<String, String>): Request{
        println("GET")
        var param = ""
        if(!request.queryString.isNullOrEmpty()) param = "?"+request.queryString
        return Request(args, headers, request.requestURL.toString() + param)
    }

    @PostMapping("/post",consumes=["application/x-www-form-urlencoded"])
    fun postForm(request: HttpServletRequest, @RequestHeader headers: Map<String,String>, @RequestParam args: Map<String, String>
    ): RequestForm{
        println("POST:application/x-www-form-urlencoded")
        var param = ""
        if(!request.queryString.isNullOrEmpty()) param = "?"+request.queryString
        var paramKey = request.queryString.split("&").map { it.split("=")[0] to it.split("=")[1] }.toMap()
        var form = args.minus(paramKey.keys)
        return RequestForm(paramKey,"", emptyMap(),form, headers,form, request.requestURL.toString() + param)
    }

    @PostMapping("/post",consumes=["application/json"])
    fun post(request: HttpServletRequest, @RequestHeader headers: Map<String,String>, @RequestParam args: Map<String, String>
             , @RequestBody json: Map<String,String>
    ) : RequestForm{
        println("POST:application/json")
        var param = ""
        if(!request.queryString.isNullOrEmpty()) param = "?"+request.queryString
        return RequestForm(args,"", emptyMap(),json, headers,json, request.requestURL.toString() + param)
    }

    @PostMapping("/post",consumes=["multipart/form-data"])
    fun postData(request: HttpServletRequest, @RequestHeader headers: Map<String,String>
    ): RequestForm{
        println("POST:multipart/form-data")
        var param = ""
        if(!request.queryString.isNullOrEmpty()) param = "?"+request.queryString
        var file:Map<String,String> = emptyMap()
        var form:Map<String,String> = emptyMap()
        request.parts.forEach {
            if(!it.submittedFileName.isNullOrEmpty()){
                var a =it.inputStream.readAllBytes()
                var f = it.contentType+";base64,"+Base64.getEncoder().encodeToString(a)
                file += Pair(it.submittedFileName,f)
            }else{
                form += Pair(it.name,String(it.inputStream.readAllBytes()))
            }
        }
        var paramKey = request.queryString.split("&").map { it.split("=")[0] to it.split("=")[1] }.toMap()
        return RequestForm(paramKey,"", file,form, headers,form, request.requestURL.toString() + param)
    }

    @PutMapping("/put")
    fun put(request: HttpServletRequest, @RequestHeader headers: Map<String,String>, @RequestParam args: Map<String, String>, @RequestBody json: Map<String,String>): RequestForm{
        println("PUT:application/json")
        var param = ""
        if(!request.queryString.isNullOrEmpty()) param = "?"+request.queryString
        return RequestForm(args,"", emptyMap(),json, headers,json, request.requestURL.toString() + param)
    }


}