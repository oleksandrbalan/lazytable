@file:Suppress("UndocumentedPublicClass", "UndocumentedPublicProperty")

package eu.wewox.lazytable.data

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import java.util.Locale

/**
 * Pokemon domain model.
 */
data class Pokemon(
    val id: Int,
    val number: String,
    val name: String,
    val height: Float,
    val weight: Float,
    val imageUrl: String,
    val stats: Stats,
) {
    data class Stats(
        val health: Int,
        val attack: Int,
        val defence: Int,
        val specialAttack: Int,
        val specialDefence: Int,
        val speed: Int,
    )
}

/**
 * Loads pokemons from remote.
 *
 * @param range The range of pokemon numbers.
 */
suspend fun pokemons(range: IntRange = 1..30): List<Pokemon> = range.map { id ->
    ktorHttpClient.get("https://pokeapi.co/api/v2/pokemon/$id/")
        .body<PokemonDto>()
        .let { dto ->
            Pokemon(
                id = id,
                number = String.format(Locale.getDefault(), "#%04d", id),
                name = dto.name,
                height = dto.height.toFloat() * 10, // convert to CM
                weight = dto.weight * 0.01f, // convert to KG
                imageUrl = dto.imageUrl,
                stats = Pokemon.Stats(
                    health = dto.stat("hp"),
                    attack = dto.stat("attack"),
                    defence = dto.stat("defense"),
                    specialAttack = dto.stat("special-attack"),
                    specialDefence = dto.stat("special-defense"),
                    speed = dto.stat("speed"),
                ),
            )
        }
}

internal val ktorHttpClient = HttpClient {
    install(ContentNegotiation) {
        json(
            Json {
                ignoreUnknownKeys = true
            }
        )
    }

    install(DefaultRequest) {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }
}
