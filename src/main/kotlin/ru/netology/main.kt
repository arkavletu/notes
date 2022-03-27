package ru.netology


fun main(){
    val noteTest: WallObject<Note> = Note("title","text")
    Service.add(noteTest)
    val note1: WallObject<Note> = Note("none","second")
    Service.add(note1)
    Service.add(Comment("text",null,noteTest))

    //Service.edit(2,Note())



}
