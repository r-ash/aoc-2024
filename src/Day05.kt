import kotlin.math.floor
import kotlin.time.measureTime

fun main() {
    fun part1(input: List<String>): Int {
        val rules: MutableList<Pair<Int, Int>> = mutableListOf()
        val pages: MutableList<List<Int>> = mutableListOf()
        var breakEncountered = false
        input.forEach{ line ->
            run {
                if (line == "") {
                    breakEncountered = true
                } else if (!breakEncountered) {
                    val characters = line.split("|")
                    rules.add(Pair(characters[0].toInt(), characters[1].toInt()))
                } else {
                    pages.add(line.split(",").map{ it.toInt() })
                }
            }
        }

        return pages.fold(0) {acc, page ->
            var valid = true
            var idx = 0
            while (valid && idx < rules.size) {
                val rule = rules[idx]
                val idx1 = page.indexOf(rule.first)
                val idx2 = page.indexOf(rule.second)
                if (idx1 >= 0 && idx2 >= 0 && idx1 > idx2) {
                    valid = false
                }
                idx++
            }

            if (valid) {
                acc + page[floor(page.size / 2.0).toInt()]
            } else {
                acc
            }
        }
    }

    fun findMiddleValue(page: List<Int>, rules: List<Pair<Int, Int>>): Int? {
        val targetNoBefore = (page.size - 1) / 2
        return page.find{test ->
            val pagesBeforeTest = rules.filter { it.second == test }
                .map { it.first }.count { page.indexOf(it) != -1 }
            pagesBeforeTest == targetNoBefore
        }
    }

    fun part2(input: List<String>): Int {
        val rules: MutableList<Pair<Int, Int>> = mutableListOf()
        val pages: MutableList<MutableList<Int>> = mutableListOf()
        var breakEncountered = false
        input.forEach { line ->
            run {
                if (line == "") {
                    breakEncountered = true
                } else if (!breakEncountered) {
                    val characters = line.split("|")
                    rules.add(Pair(characters[0].toInt(), characters[1].toInt()))
                } else {
                    pages.add(line.split(",").map { it.toInt() }.toMutableList())
                }
            }
        }

        return pages.fold(0) {acc, page ->
            var middleValue = -1
            for (rule in rules) {
                val idx1 = page.indexOf(rule.first)
                val idx2 = page.indexOf(rule.second)
                if (idx1 >= 0 && idx2 >= 0 && idx1 > idx2) {
                    val value = findMiddleValue(page, rules)
                    value?.let{ middleValue = value }
                }
            }

            if (middleValue != -1) {
                acc + middleValue
            } else {
                acc
            }
        }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
    check(part2(testInput) == 123)

    val input = readInput("Day05")
    val part1Time = measureTime {
        part1(input).println()
    }
    println("Part 1 took: $part1Time")
    val part2Time = measureTime {
        part2(input).println()
    }
    println("Part 2 took: $part2Time")
}
