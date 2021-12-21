package com.listsample.data.exception

class EmptyResultException(message: String? = "Not found result") : Exception(message)
class NetworkException(message: String? = "Network Exception") : Exception(message)