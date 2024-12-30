import kotlin.time.measureTime

fun main() {

    fun findChars(input: List<String>, char: Char): List<Pair<Int, Int>> {
        return input.flatMapIndexed { yIdx, row ->
            var index = row.indexOf(char, 0)
            var indexes = emptyList<Pair<Int, Int>>()
            while (index != -1) {
                indexes = indexes + Pair(index, yIdx)
                index = row.indexOf(char, index + 1)
            }
            indexes
        }
    }

    fun testDirection(input: List<String>, start: Pair<Int, Int>, moveAction: (Pair<Int, Int>) -> Pair<Int, Int>, depth: Int): Boolean {
        val newCoords = moveAction(start);
        if (newCoords.first < 0 || newCoords.first > input[0].length - 1 ||
            newCoords.second < 0 || newCoords.second > input.size - 1) {
            return false
        }
        return if (input[newCoords.second][newCoords.first] == "XMAS"[depth]) {
            if (depth == 3) {
                true
            } else {
                testDirection(input, newCoords, moveAction, depth + 1)
            }
        } else {
            false
        }
    }

    fun testDirection2(input: List<String>, start: Pair<Int, Int>, moveAction: (Pair<Int, Int>) -> Pair<Int, Int>, findChar: Char): Boolean {
        val newCoords = moveAction(start);
        if (newCoords.first < 0 || newCoords.first > input[0].length - 1 ||
            newCoords.second < 0 || newCoords.second > input.size - 1) {
            return false
        }
        return input[newCoords.second][newCoords.first] == findChar
    }

    val moveUp: Pair<Int, Int>.() -> Pair<Int, Int> = {
        Pair(this.first, this.second - 1)
    }
    val moveDown: Pair<Int, Int>.() -> Pair<Int, Int> = {
        Pair(this.first, this.second + 1)
    }
    val moveLeft: Pair<Int, Int>.() -> Pair<Int, Int> = {
        Pair(this.first - 1, this.second)
    }
    val moveRight: Pair<Int, Int>.() -> Pair<Int, Int> = {
        Pair(this.first + 1, this.second)
    }
    val moveUpLeft: Pair<Int, Int>.() -> Pair<Int, Int> = {
        Pair(this.first - 1, this.second - 1)
    }
    val moveUpRight: Pair<Int, Int>.() -> Pair<Int, Int> = {
        Pair(this.first - 1, this.second + 1)
    }
    val moveDownLeft: Pair<Int, Int>.() -> Pair<Int, Int> = {
        Pair(this.first + 1, this.second - 1)
    }
    val moveDownRight: Pair<Int, Int>.() -> Pair<Int, Int> = {
        Pair(this.first + 1, this.second + 1)
    }


    fun part1(input: List<String>): Int {
        val starts = findChars(input, 'X')

        return starts.flatMap{ listOf(
            testDirection(input, it, moveUp, 1),
            testDirection(input, it, moveDown, 1),
            testDirection(input, it, moveLeft, 1),
            testDirection(input, it, moveRight, 1),
            testDirection(input, it, moveUpLeft, 1),
            testDirection(input, it, moveUpRight, 1),
            testDirection(input, it, moveDownLeft, 1),
            testDirection(input, it, moveDownRight, 1),
        )}.count{ it }
    }

    fun part2(input: List<String>): Int {
        val starts = findChars(input, 'A')

        return starts.map{
            val masForward = (testDirection2(input, it, moveUpRight, 'S') && testDirection2(input, it, moveDownLeft, 'M'))
                || (testDirection2(input, it, moveUpRight, 'M') && testDirection2(input, it, moveDownLeft, 'S'))

            val masBackward = (testDirection2(input, it, moveUpLeft, 'S') && testDirection2(input, it, moveDownRight, 'M'))
                    || (testDirection2(input, it, moveUpLeft, 'M') && testDirection2(input, it, moveDownRight, 'S'));

            masForward && masBackward
        }.count{ it }
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    val input = readInput("Day04")
    val part1Time = measureTime {
        part1(input).println()
    }
    println("Part 1 took: $part1Time")
    val part2Time = measureTime {
        part2(input).println()
    }
    println("Part 2 took: $part2Time")
}
