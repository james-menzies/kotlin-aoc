import kotlin.math.min

fun main() {

    fun part1(input: List<String>): Int = input.map {
        val game = ElfGame.fromString(it)
        if (game.isValid()) game.id else 0
    }.sum()


    fun part2(input: List<String>): Int = input.sumOf { ElfGame.fromString(it).getMinimumPower() }

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}


data class ElfGame(val id: Int, val rounds: List<Round>) {
    enum class Color { RED, GREEN, BLUE }
    data class Round(val color: Color, val number: Int)

    fun getMinimumPower(): Int {

        val minimumRequiredReference = mutableMapOf<Color, Int>()

        for (round in rounds) {
            val currentMinimum = minimumRequiredReference.getOrDefault(round.color, 0)
            if (round.number > currentMinimum) {
                minimumRequiredReference.put(round.color, round.number)
            }
        }

        return minimumRequiredReference.values.reduce { acc, i -> acc * i }

    }

    fun isValid(): Boolean {

        for (round in rounds) {

            if (limits.getOrDefault(round.color, 0) < round.number) return false
        }

        return true
    }


    companion object {

        val limits = mapOf(
            Color.RED to 12,
            Color.GREEN to 13,
            Color.BLUE to 14
        )

        private fun parseRound(input: String): Round {
            val tokens = input.split(" ")
            val (numberValue, colorValue) = tokens

            return Round(
                Color.valueOf(colorValue.uppercase()),
                numberValue.toInt()
            )
        }

        fun fromString(input: String): ElfGame {
            val tokens = input.split(": ", ", ", "; ")
            val id = tokens[0].split(" ")[1].toInt()

            return ElfGame(id, tokens.subList(1, tokens.size).map { parseRound(it) })
        }
    }
}
