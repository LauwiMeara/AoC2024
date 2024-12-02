import kotlin.math.abs

fun main() {
    fun levelsAreDescending(numbers: Pair<Int, Int>): Boolean {
        return numbers.first - numbers.second > 0
    }

    fun isSmallDifference(numbers: Pair<Int, Int>): Boolean {
        return abs(numbers.first - numbers.second) in 1..3
    }

    fun isSafeReport(report: List<Int>): Boolean {
        val zippedReport = report.zipWithNext()
        if (!zippedReport.all { levelsAreDescending(it) } && !zippedReport.all { !levelsAreDescending(it) }) {
            return false
        }
        if (zippedReport.any { !isSmallDifference(it) }) {
            return false
        }
        return true
    }

    fun isSafeReportPart2(report: List<Int>): Boolean {
        if (isSafeReport(report)) {
            return true
        }

        val zippedReport = report.zipWithNext()
        // The report is descending if half or more of the pairs is descending
        val reportIsDescending = zippedReport
            .map { if (it.first - it.second > 0) 1 else 0 }
            .reduce { acc, it -> acc + it } > (zippedReport.size / 2)
        val indexFirstNotSafePair = zippedReport.indexOfFirst {
            (reportIsDescending && !levelsAreDescending(it)) ||
                    (!reportIsDescending && levelsAreDescending(it)) ||
                    !isSmallDifference(it)
        }

        // Check if the report is safe if the first of the pair is removed
        var newReport = report.toMutableList()
        newReport.removeAt(indexFirstNotSafePair)
        if (isSafeReport(newReport)) {
            return true
        }

        // Check if the report is safe if the second of the pair is removed
        newReport = report.toMutableList()
        newReport.removeAt(indexFirstNotSafePair + 1)
        return isSafeReport(newReport)
    }

    fun part1(input: List<List<Int>>): Int {
        return input.map { if (isSafeReport(it)) 1 else 0 }.reduce { acc, it -> acc + it }
    }

    fun part2(input: List<List<Int>>): Int {
        return input.map { if (isSafeReportPart2(it)) 1 else 0 }.reduce { acc, it -> acc + it }
    }

    val input = readInputAsStrings("Day02").map { line -> line.split(" ").map { it.toInt() } }
    part1(input).println()
    part2(input).println()
}
