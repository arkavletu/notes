package ru.netology

import java.time.LocalDateTime

open class WallObject(
    var type: String
)
{
    open var deleted: Boolean = false
    open var ownerId: Int = 1
    var date: LocalDateTime = LocalDateTime.now()
    var id: Int = 0
    open var commentsStack = mutableListOf<WallObject>()
    open var comments: Int = 0
    fun createId(size: Int){
        this.id = size + 1
    }

}