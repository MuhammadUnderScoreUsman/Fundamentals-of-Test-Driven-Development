package com.mohammadosman.unittest.domain

data class Note(
    val id: String,

    val title: String,

    val content: String,

    val timestamp: String
) {


    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Note

        if (id != other.id) return false
        if (title != other.title) return false
        if (content != other.content) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }


    companion object NoteFactory {
        fun createNote(
            id: String?,
            title: String?,
            content: String?,
            timestamp: String?
        ): Note {
            return Note(
                id = id ?: "",
                title = title ?: "",
                content = content ?: "",
                timestamp = timestamp ?: ""
            )
        }
    }

}
