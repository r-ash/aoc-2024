import kotlin.math.abs
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        val list1 = input.map{ it.split("   ").first().toInt() }
        val list2 = input.map{ it.split("   ").last().toInt() }

        return list1.sorted().zip(list2.sorted()).fold(0){acc, pair ->
            acc + abs(pair.first - pair.second)
        }
    }

    fun part2(input: List<String>): Int {
        val list1 = input.map{ it.split("   ").first().toInt() }
        val counts = input
            .map{ it.split("   ").last().toInt() }
            .groupingBy { it }.eachCount()

        return list1.fold(0) {acc, value ->
            acc + (value * counts.getOrDefault(value, 0))
        }
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
    check(part2(testInput) == 31)

    val input = readInput("Day01")
    val part1Time = measureTime {
        part1(input).println()
    }
    println("Part 1 took: $part1Time")
    val part2Time = measureTime {
        part2(input).println()
    }
    println("Part 2 took: $part2Time")
}
