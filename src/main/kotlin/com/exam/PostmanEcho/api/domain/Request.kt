package com.exam.PostmanEcho.api.domain

data class Request (val args: Map<String,String>, val headers : Map<String,String>, val url: String)
