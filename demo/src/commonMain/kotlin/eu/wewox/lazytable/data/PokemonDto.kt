@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicProperty", "UndocumentedPublicFunction")

package eu.wewox.lazytable.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PokemonDto(
    val id: Int,
    val name: String,
    val height: Int,
    val weight: Int,
    val sprites: Sprites,
    val species: NameDto,
    val stats: List<Stat>,
) {
    @Serializable
    data class Sprites(
        val other: Other
    ) {
        @Serializable
        data class Other(
            @SerialName("official-artwork")
            val officialArtwork: Images
        ) {
            @Serializable
            data class Images(
                @SerialName("front_default")
                val frontDefault: String?
            )
        }
    }

    @Serializable
    data class Stat(
        @SerialName("base_stat")
        val value: Int,
        @SerialName("stat")
        val name: NameDto
    )

    val imageUrl: String by lazy {
        sprites.other.officialArtwork.frontDefault.orEmpty()
    }

    fun stat(name: String): Int =
        stats.find { it.name.value == name }?.value ?: 0
}

@Serializable
data class NameDto(
    @SerialName("name")
    val value: String
)
