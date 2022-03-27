package ru.netology
// tests, readme (-guid)
object Service {
    internal var wallObjects = mutableListOf<WallObject<*>>()

    fun printNotDeleted(){
        wallObjects.forEach { if (!it.deleted) println(it) }
    }


    fun findById(id: Int): WallObject<*>? {          // find and return id
        return wallObjects.firstOrNull { it.id == id }
    }

    fun add(element:  WallObject<*>): Boolean {
        if (findById(element.id) == null) {
            element.createId(wallObjects.size)
            wallObjects.add(element)

            if(element is Comment && element.parentId != 0){
                element.parentsStack += element.parentId
            }
            if(element is Comment && element.parentNote is Note) {
                element.parentNote.commentsStack += element
            }
            if(element is Comment && element.parentComment is Comment && element.parentsStack.isNotEmpty()) {
                element.parentsStack += element.parentComment.parentsStack
            }
            return true
        }
        return false
    }

    fun remove(id: Int) { // CHECK
        val objId: Int = wallObjects.indexOf(findById(id))
        if (objId >= 0 && !wallObjects[objId].deleted) {
            wallObjects[objId].deleted = true
            for (obj in wallObjects){
                if (obj is Comment && obj.parentsStack.contains(wallObjects[objId].id)){
                    obj.deleted = true
                }
            }
        }else throw WrongIdException()
    }

    fun restore(id: Int){
        val objId: Int = wallObjects.indexOf(findById(id))
        if (objId >= 0 && wallObjects[objId].deleted){

                !wallObjects[objId].deleted

        } else throw WrongIdException()
    }

    fun <T>edit(id: Int, obj: T){
        val index: Int = wallObjects.indexOf(findById(id))
        if(index >= 0) {
            val x: T = wallObjects[index]
            if (!x.deleted) {
                obj.date = x.date
                obj.id = x.id
                wallObjects[index] = obj
            }
        }else throw WrongIdException()
    }

    fun getFilteredByOwnerId(id: Int){
      val result = wallObjects.filter { it.ownerId == id }
        println(result)
    }

    fun seeComments(id: Int){ //null
        val index: Int = wallObjects.indexOf(findById(id))

        if(index >= 0 && !wallObjects[index].deleted){
            val obj: WallObject<*> = wallObjects[index]
            obj.commentsStack.forEach { if (!it.deleted) println(it) }
        }
    }
}