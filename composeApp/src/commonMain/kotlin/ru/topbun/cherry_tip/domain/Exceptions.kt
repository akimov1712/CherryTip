package ru.topbun.cherry_tip.domain

open class AppException: RuntimeException()

class ParseBackendResponseException : AppException()
class RequestTimeoutException : AppException()
class ClientException(val errorText: String) : AppException()
class ServerException(val errorText: String) : AppException()