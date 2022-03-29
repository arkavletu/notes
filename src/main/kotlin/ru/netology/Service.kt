package ru.netology

object Service {
    internal var wallObjects = mutableListOf<WallObject>()

    fun findById(id: Int): WallObject? {
        return wallObjects.firstOrNull { it.id == id }
    }

    fun <T : WallObject> add(element: T): Boolean {
        if (findById(element.id) == null) {
            element.createId(wallObjects.size)
            wallObjects.add(element)
            when (element) {
                is Comment -> {
                    element.parentsStack += element.parentId
                    element.parentNote?.commentsStack?.plusAssign(element)
                    if (element.parentComment != null) element.parentsStack += element.parentComment.parentsStack
                }
            }
            return true
        }
        return false
    }


    fun printNotDeleted(): List<WallObject> {
        val notDeleted = mutableListOf<WallObject>()
        wallObjects.forEach { if (!it.deleted) notDeleted += it }
        if (notDeleted.isEmpty()) println("Empty. Create a note!")
        return notDeleted
    }


    fun remove(id: Int) {
        val objId: Int? = wallObjects.indexOf(findById(id))
        if (objId != null) {
            if (objId >= 0 && !wallObjects[objId].deleted) {
                wallObjects[objId].deleted = true
                for (obj in wallObjects) {
                    if (obj is Comment && obj.parentsStack.contains(wallObjects[objId].id)) {
                        obj.deleted = true
                    }
                }
            } else throw WrongIdException()
        } else throw WrongIdException()
    }

    fun restore(id: Int) {
        val objId: Int? = wallObjects.indexOf(findById(id))
        if (objId != null) {
            if (objId >= 0 && wallObjects[objId].deleted) {

                wallObjects[objId].deleted = false

            } else throw WrongIdException()
        } else throw WrongIdException()
    }

    fun <T : WallObject> edit(id: Int, obj: T) {
        val index: Int? = wallObjects.indexOf(findById(id))
        if (index != null && index >= 0) {
            val x: WallObject = wallObjects[index] // element to edit
            if (!x.deleted) {
                obj.date = x.date
                obj.id = x.id
                wallObjects[index] = obj
            } else throw WrongIdException()
        } else throw WrongIdException()
    }

    fun getFilteredByOwnerId(id: Int): List<WallObject> {
        val filtered = (wallObjects.filter { it.ownerId == id && !it.deleted })
        if (filtered.isEmpty()) throw WrongIdException()
        return wallObjects
    }

    fun seeComments(id: Int): List<WallObject> {
        val index: Int? = wallObjects.indexOf(findById(id))
        val comments = mutableListOf<WallObject>()
        if (index != null) {
            if (index >= 0 && !wallObjects[index].deleted) {
                comments += wallObjects[index].commentsStack.filter { !it.deleted }
                if (comments.isEmpty()) println("No comments")
            } else throw WrongIdException()
        } else throw WrongIdException()
        return comments
    }

    fun emptySingleton() {
        wallObjects.clear()
    }

}

