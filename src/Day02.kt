import kotlin.math.abs

fun main() {
    fun levelsAreDescending(numbers: Pair<Int, Int>): Boolean {
        return numbers.first - numbers.second > 0
    }

    fun isGradual(numbers: Pair<Int, Int>): Boolean {
        return abs(numbers.first - numbers.second) in 1..3
    }

    fun isSafeReport(report: List<Int>): Int {
        val zippedReport = report.zipWithNext()
        if (!zippedReport.all { levelsAreDescending(it) } && !zippedReport.all { !levelsAreDescending(it) }) {
            return 0
        }
        if (zippedReport.any { !isGradual(it) }) {
            return 0
        }
        return 1
    }

    fun isSafeReportPart2(report: List<Int>): Int {
        if (isSafeReport(report) == 1) {
            return 1
        }

        val zippedReport = report.zipWithNext()
        // The report is descending if half or more of the pairs is descending
        val reportIsDescending = zippedReport.map { if (it.first - it.second > 0) 1 else 0 }
            .reduce { acc, it -> acc + it } > (zippedReport.size / 2)
        val indexFirstNotSafePair = zippedReport.indexOfFirst {
            (reportIsDescending && !levelsAreDescending(it)) || (!reportIsDescending && levelsAreDescending(it)) || !isGradual(
                it
            )
        }

        // Check if the report is safe if the first of the pair is removed
        var newReport = report.toMutableList()
        newReport.removeAt(indexFirstNotSafePair)
        if (isSafeReport(newReport) == 1) {
            return 1
        }

        // Check if the report is safe if the second of the pair is removed
        newReport = report.toMutableList()
        newReport.removeAt(indexFirstNotSafePair + 1)
        if (isSafeReport(newReport) == 1) {
            return 1
        }

        return 0
    }

    fun part1(input: List<List<Int>>): Int {
        return input.fold(0) { acc, it -> acc + isSafeReport(it) }
    }

    fun part2(input: List<List<Int>>): Int {
        return input.fold(0) { acc, it -> acc + isSafeReportPart2(it) }
    }

    val input = readInputAsStrings("Day02").map { line -> line.split(" ").map { it.toInt() } }
    part1(input).println()
    part2(input).println()
}
