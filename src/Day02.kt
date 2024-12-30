import kotlin.math.absoluteValue

fun main() {
    fun isLineSafe(numbers: List<Int>): Boolean {
        var safe = true
        var increases = false
        var decreases = false
        for (pair in numbers.windowed(2)) {
            if (pair[0] < pair[1]) {
                increases = true
            } else if (pair[0] > pair[1]) {
                decreases = true
            } else {
                increases = true
                decreases = true
            }
            safe = safe && ((pair[0] - pair[1]).absoluteValue <= 3) && !(increases && decreases)
        }
        return safe
    }

    fun part1(input: List<String>): Int {
        return input.count { line -> isLineSafe(line.split(" ").map { it.toInt() }) }
    }

    fun part2(input: List<String>): Int {
        return input.count { line ->
            val numbers = line.split(" ").map { it.toInt() }
            var safe = isLineSafe(numbers)
            if (!safe) {
                for (i in 0..numbers.lastIndex) {
                    safe = isLineSafe(numbers.toMutableList().apply { removeAt(i) })
                    if (safe) break
                }
            }
            safe
        }
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
