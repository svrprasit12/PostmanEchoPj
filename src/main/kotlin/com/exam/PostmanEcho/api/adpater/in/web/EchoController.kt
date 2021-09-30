package com.exam.PostmanEcho.api.adpater.`in`.web

import com.exam.PostmanEcho.api.application.service.ReadRequestService
import com.exam.PostmanEcho.api.domain.Request
import com.exam.PostmanEcho.api.domain.RequestForm
import com.exam.PostmanEcho.common.WebAdapter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import javax.servlet.http.HttpServletRequest

@WebAdapter
@RestController
class EchoController {

    @Autowired
    private lateinit var readRequestService: ReadRequestService

    @GetMapping("/get")
    fun get(request: HttpServletRequest, @RequestHeader headers: Map<String,String>, @RequestParam args: Map<String, String>): Request{
        println("GET")
        return readRequestService.sendBackGetRequest(request, headers,args)
    }

    @PostMapping("/post",consumes=["application/x-www-form-urlencoded"])
    fun postForm(request: HttpServletRequest, @RequestHeader headers: Map<String,String>, @RequestParam args: Map<String, String>
    ): RequestForm{
        println("POST:application/x-www-form-urlencoded")
        return readRequestService.sendBackPostFormUrlEncode(request, headers,args)
    }

    @PostMapping("/post",consumes=["application/json"])
    fun post(request: HttpServletRequest, @RequestHeader headers: Map<String,String>, @RequestParam args: Map<String, String>
             , @RequestBody json: Map<String,String>
    ) : RequestForm{
        println("POST:application/json")
        return readRequestService.sendBackPostApplicationJson (request,headers,args,json)
    }

    @PostMapping("/post",consumes=["multipart/form-data"])
    fun postData(request: HttpServletRequest, @RequestHeader headers: Map<String,String>
    ): RequestForm{
        println("POST:multipart/form-data")
        return readRequestService.sendBackPostMultiPartForm (request,headers)
    }

    @PutMapping("/put")
    fun put(request: HttpServletRequest, @RequestHeader headers: Map<String,String>, @RequestParam args: Map<String, String>, @RequestBody json: Map<String,String>): RequestForm{
        println("PUT:application/json")
        return readRequestService.sendBackPostApplicationJson (request,headers,args,json)
    }


}