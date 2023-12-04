fun main() {
    fun toNaiveCalibrationValue(input: String): Int {

        val digit1 = input.first { it.isDigit() }
        val digit2 = input.last { it.isDigit() }

        return "$digit1$digit2".toInt()
    }

    fun toAwareCalibrationValue(input: String): Int {

        val finder = AdvancedDigitSearch()

        val digit1 = finder.findFirstDigit(input)
        val digit2 = finder.findLastDigit(input)

        return "$digit1$digit2".toInt()
    }

    fun part1(input: List<String>): Int = input.sumOf { toNaiveCalibrationValue(it) }
    fun part2(input: List<String>): Int = input.sumOf { toAwareCalibrationValue(it) }

    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}


class AdvancedDigitSearch {

    companion object {

        private val numberWordRepresentations = listOf("zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine")
        private val forwardRootNode = Node()
        private val reverseRootNode = Node()


        private fun initRootNode(node: Node, words: List<String>) {
            for (i in words.indices) {
                var currentNode = node
                for (j in 0..<words[i].length) {
                    currentNode = currentNode.childNodes.getOrPut(words[i][j]) { Node() }
                    if (j == words[i].length - 1) {
                        currentNode.digit = i
                    }

                }
            }
        }

        init {
            initRootNode(forwardRootNode, numberWordRepresentations)
            initRootNode(reverseRootNode, numberWordRepresentations.map { it.reversed() })
        }
    }

    private fun findDigit(node: Node, input: String): Int? {
        for(i in input.indices) {
            if(input[i].isDigit()) return input[i].digitToInt()
            var currentNode: Node? = node
            var offset = 0

            while(currentNode != null) {
                currentNode = currentNode.childNodes.getOrDefault(input[i + offset], null)
                if (currentNode?.digit != null) return currentNode.digit
                offset++
            }
        }
        return null
    }


    fun findFirstDigit(input: String): Int? = findDigit(forwardRootNode, input)

    fun findLastDigit(input: String): Int? = findDigit(reverseRootNode, input.reversed())


    data class Node(var digit: Int? = null, val childNodes: MutableMap<Char, Node> = mutableMapOf())
}
