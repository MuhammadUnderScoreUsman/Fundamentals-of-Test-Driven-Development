package com.mohammadosman.unittest.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test

class NoteTest {

    private val TimeOne = "2020-02"
    private val TimeTwo = "2020-19"
    /*
    Compare two notes
     */
    @Test
    fun isNoteEqual() {
        // Given
        val noteOne = Note("1", "Note 1", "This is note 1.", TimeOne)
        val noteTwo = Note("1", "Note 1", "This is note 1.", TimeOne)


        // When


        // Then
        assertEquals(noteOne, noteTwo)
    }
    /*
    * Compare notes with 2 different ids
    * */
    @Test
    fun isNotEquals_withDifferentId_returnFalse() {
        // Given
        val noteOne = Note("1", "Note 1", "This is note 1.", TimeOne)
        val noteTwo = Note("2", "Note 1", "This is note 1.", TimeOne)

        // When


        // Then
        assertNotEquals(noteOne, noteTwo)
    }

    /*
    * compare two notes with different timestamp
    * */

    @Test
    fun isNoteEqual_differentTimeStamps_returnTrue() {
        // Given
        val noteOne = Note("1", "Note 1", "This is note 1.", TimeOne)
        val noteTwo = Note("1", "Note 1", "This is note 1.", TimeTwo)

        // When

        // Then
        assertEquals(noteOne, noteTwo)
    }

    /*
   * compare two notes with different title
   * */
    @Test
    fun isNoteEqual_differentTitle_returnFalse() {// return false means if notes have different we are expecting notEqual
        // Given
        val noteOne = Note("1", "Note 1", "This is note 1.", TimeOne)
        val noteTwo = Note("1", "Note 2", "This is note 1.", TimeTwo)

        // When

        // Then
        assertNotEquals(noteOne, noteTwo)
    }

    /*
   * compare two notes with different content
   * */

    @Test
    fun isNoteEqual_differentContent_returnFalse() {// return false means if notes have different we are note expecting note equal
        // Given
        val noteOne = Note("1", "Note 1", "This is note 1.", TimeOne)
        val noteTwo = Note("1", "Note 1", "This is note 2.", TimeTwo)

        // When

        // Then
        assertNotEquals(noteOne, noteTwo)
    }


}