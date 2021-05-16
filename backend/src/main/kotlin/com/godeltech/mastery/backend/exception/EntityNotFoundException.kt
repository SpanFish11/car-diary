package com.godeltech.mastery.backend.exception

class EntityNotFoundException : RuntimeException {

    constructor() : super()
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
    constructor(cause: Throwable) : super(cause)
    constructor(entity: String, id: Long) : super("Could not find any $entity with the ID $id")
}