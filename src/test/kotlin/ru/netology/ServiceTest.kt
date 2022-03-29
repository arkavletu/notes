package ru.netology

import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class ServiceTest {
    @Before
    fun clear() {
        Service.emptySingleton()
    }


    @Test
    fun findById() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val note1 = Note("none", "second")
        Service.add(note1)

        val result = Service.findById(1)
        assertNotNull(result)
    }

    @Test
    fun findByIdNull() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val note1 = Note("none", "second")
        Service.add(note1)

        val result = Service.findById(4)
        assertNull(result)
    }


    @Test
    fun add() {
        val noteTest = Note("title", "text")
        val result = Service.add(noteTest)
        assertTrue(result)
    }

    @Test
    fun addFalse() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val result = Service.add(noteTest)
        assertFalse(result)
    }

    @Test
    fun parentsStackAndCommentsStack() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)
        val commentTest2 = Comment("none", commentTest, null)
        Service.add(commentTest2)
        val resultParent = commentTest.parentsStack.isNotEmpty()
        val resultComments = noteTest.commentsStack.contains(commentTest)
        val resultParentForParentCommentNotNull = (commentTest2.parentsStack.size == 2)
        assertTrue(resultParent && resultComments && resultParentForParentCommentNotNull)
    }

    @Test
    fun printNotDeleted() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, null)
        Service.add(commentTest)
        Service.remove(1)

        val result = Service.printNotDeleted()

        assertTrue(result.size == 1)
    }


    @Test
    fun remove() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)
        Service.remove(1)
        val result = Service.wallObjects[0].deleted
        val commentDelete = Service.wallObjects[1].deleted
        assertTrue(result && commentDelete)
    }

    @Test(expected = WrongIdException::class)
    fun removeFalse() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val note1 = Note("none", "second")
        Service.add(note1)
        Service.remove(1)
        Service.remove(1)
    }

    @Test
    fun restore() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)
        Service.remove(1)
        Service.restore(1)
        val result = Service.wallObjects[0].deleted
        assertFalse(result)
    }

    @Test(expected = WrongIdException::class)
    fun restoreFalse() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)
        Service.restore(4)
    }

    @Test
    fun edit() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val newNote = Note("new title", "new text")
        Service.edit(1, newNote)
        val result = Service.wallObjects[0] == newNote
        val idNotChanged = noteTest.id == 1
        assertTrue(result && idNotChanged)
    }

    @Test(expected = WrongIdException::class) // и на удаленном еще
    fun editFalse() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val newNote = Note("new title", "new text")
        Service.edit(2, newNote)

    }

    @Test(expected = WrongIdException::class) // и на удаленном еще
    fun editDeleted() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)
        Service.remove(1)
        val newNote = Note("new title", "new text")
        Service.edit(1, newNote)

    }

    @Test
    fun getFilteredByOwnerId() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)
        Service.remove(2)
        val result = Service.getFilteredByOwnerId(0)
        assertTrue(result.size == 1)
    }

    @Test(expected = WrongIdException::class)
    fun getFilteredByOwnerIdError() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)

        Service.getFilteredByOwnerId(5)

    }

    @Test
    fun seeComments() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)

        val result = Service.seeComments(1)

        assertTrue(result.isNotEmpty())
    }

    @Test(expected = WrongIdException::class)
    fun seeCommentsError() {
        val noteTest = Note("title", "text")
        Service.add(noteTest)
        val commentTest = Comment("none", null, noteTest)
        Service.add(commentTest)

        Service.seeComments(5)

    }
}