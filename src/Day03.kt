fun main() {
    fun part1(input: List<String>): Int {
        val re = Regex("mul\\((\\d+),(\\d+)\\)")
        return re.findAll(input[0])
            .fold(0){ acc, value -> acc + value.groupValues[1].toInt() * value.groupValues[2].toInt() }
    }

    fun part2(input: List<String>): Int {
        val re = Regex("(mul\\(\\d+,\\d+\\)|do\\(\\)|don't\\(\\))")
        val mulRe = Regex("mul\\((\\d+),(\\d+)\\)")
        var mulEnabled = true
        return re.findAll(input[0]).fold(0) { acc, value ->
            val command = value.groupValues[1]
            var increment = 0
            if (command.take(3) == "mul" && mulEnabled) {
                val mulMatch = mulRe.find(command)
                if (mulMatch != null) {
                    increment = mulMatch.groupValues[1].toInt() * mulMatch.groupValues[2].toInt()
                }
            } else if (command == "do()") {
                mulEnabled = true
            } else if (command == "don't()") {
                mulEnabled = false
            }
            acc + increment
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
    val testInput2 = readInput("Day03_test2")
    check(part2(testInput2) == 48)

    val input = readInput("Day03")
    part1(input).println()
    part2(input).println()
}
