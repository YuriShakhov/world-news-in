package online.litterae.worldnewsin.model

import online.litterae.worldnewsin.utils.DateManager
import java.text.ParseException

class SimpleVideo {
    private val id: Id = Id()
    private val snippet: Snippet = Snippet()
    private val TITLE_MAX_LENGTH = 90

    val videoId: String
        get() = id.videoId

    val title: String
        get() {
            val title = snippet.title
            return if (title.length < TITLE_MAX_LENGTH) {
                title
            } else {
                title.substring(0, TITLE_MAX_LENGTH)
            }
        }

    val publishedAt: String
        get() = try {
            DateManager.formatDate(snippet.publishedAt)
        } catch (e: ParseException) {
            snippet.publishedAt
        }

    inner class Id {
        val kind: String = ""
        val videoId: String = ""
    }

    inner class Snippet {
        val publishedAt: String = ""
        val title: String = ""
    }
}