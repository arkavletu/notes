package ru.netology



data class Note(
    var title: String,
    var text: String,
    val url: String = "www.myurl.ru",
    val isPrivate: Boolean = false,
    val isPrivateCommentAllowed: Boolean = false,
    var canComment: Boolean = true,
    override var ownerId: Int = 0
): WallObject<Note>("Note"){


    override var deleted: Boolean = false
    override var commentsStack: MutableList<WallObject<*>> = mutableListOf()
    override var comments: Int = commentsStack.size


    override fun toString(): String {
        return "\n$type id $id, owner id $ownerId\n$url\n$date\n${title.uppercase()}\n$text\n$comments comments"
    }
}