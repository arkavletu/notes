package ru.netology


fun main() {

    val noteTest = Note("title", "text")
    Service.add(noteTest)
    val commentTest = Comment("none", null, null)
    Service.add(commentTest)
    println(Service.seeComments(1))


}
