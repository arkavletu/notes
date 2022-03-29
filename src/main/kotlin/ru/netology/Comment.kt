package ru.netology



data class Comment(
    val text: String,
    val parentComment:  Comment?,
    val parentNote: Note?,
    override var ownerId: Int = 0
    ): WallObject("Comment"){

    var parentsStack = mutableListOf<Int?>()
    override var commentsStack = mutableListOf<WallObject>()
    override var comments: Int = commentsStack.size

    override var deleted: Boolean = false

    var replyToUser: Int? = null
        set(value) {
            if (value != null) {
                if (value < 0) return
            } else field = value
        }
        get() = parentComment?.ownerId?: parentNote?.ownerId


    var replyToComment: Int? = null
        set(value) {
            if (value != null) {
                if (value < 0) return
            } else {
                field = value

            }

        }
        get() = parentComment?.id

    var parentId: Int? = 0
        get() = parentNote?.id?: parentComment?.id




    override fun toString(): String {
        return "\n$type id $id\nFrom user $ownerId\n$date\n$text\nto user $replyToUser\n" +
                "to comment $replyToComment\nparents stack ${parentsStack.toString()}"
    }
}