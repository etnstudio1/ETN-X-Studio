package com.etnstudio.user.utils

import com.etnstudio.user.data.models.ItemType
import com.etnstudio.user.data.models.MediaItem
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import java.time.Instant
import javax.inject.Inject

class JsonNormalizer @Inject constructor(
    private val json: Json
) {
    fun normalize(raw: String): List<MediaItem> {
        val element = json.parseToJsonElement(raw)
        val items = mutableListOf<MediaItem>()

        val array = element.jsonObject["items"]?.jsonArray
            ?: if (element is kotlinx.serialization.json.JsonArray) element
            else return emptyList()

        array.forEach { itemElement ->
            val obj = itemElement.jsonObject
            val id = obj["id"]?.jsonPrimitive?.content ?: return@forEach
            val name = obj["title"]?.jsonPrimitive?.content ?: obj["name"]?.jsonPrimitive?.content ?: "Untitled"
            val typeStr = obj["type"]?.jsonPrimitive?.content ?: "video"
            val type = when (typeStr.lowercase()) {
                "folder" -> ItemType.FOLDER
                "video" -> ItemType.VIDEO
                else -> ItemType.UNKNOWN
            }
            val url = obj["url"]?.jsonPrimitive?.content ?: obj["link"]?.jsonPrimitive?.content
            val thumb = obj["thumb"]?.jsonPrimitive?.content ?: obj["thumbnail"]?.jsonPrimitive?.content
            val isLocked = obj["isLocked"]?.jsonPrimitive?.boolean ?: false
            val lockHash = obj["lockHash"]?.jsonPrimitive?.content
            val expiry = obj["lockExpiry"]?.jsonPrimitive?.content?.let { Instant.parse(it) }
            val remoteFav = obj["fav"]?.jsonPrimitive?.boolean ?: false
            val children = obj["children"]?.jsonArray?.map { it.jsonPrimitive.content } ?: emptyList()

            items.add(MediaItem(
                id = id,
                name = name,
                type = type,
                url = url,
                thumbnail = thumb,
                parentId = null,
                children = children,
                isLocked = isLocked,
                lockHash = lockHash,
                lockExpiry = expiry,
                remoteFavorite = remoteFav,
                localFavorite = false
            ))
        }
        return items
    }
}
