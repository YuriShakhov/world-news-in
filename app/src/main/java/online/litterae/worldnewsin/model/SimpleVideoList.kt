package online.litterae.worldnewsin.model

import online.litterae.worldnewsin.model.SimpleVideo
import java.io.Serializable

class SimpleVideoList : Serializable {
    var items: List<SimpleVideo> = ArrayList<SimpleVideo>()

    val size: Int
        get() = items.size

    fun getVideo(position: Int): SimpleVideo {
        return items[position]
    }
}