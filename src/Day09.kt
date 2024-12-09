fun main() {
    fun getDisk(input: String): List<List<String>> {
        val disk = mutableListOf<List<String>>()
        var id = -1
        for (i in input.indices) {
            val number = input[i].code - 48
            if (i % 2 == 0) {
                id++
                if (number == 0) continue
                val file = id.toString()
                val files = mutableListOf<String>()
                for (n in 1..number) {
                    files.add(file)
                }
                disk.add(files)
            } else {
                if (number == 0) continue
                val freeSpace = "."
                val freeSpaces = mutableListOf<String>()
                for (n in 1..number) {
                    freeSpaces.add(freeSpace)
                }
                disk.add(freeSpaces)
            }
        }
        return disk
    }

    fun trimDisk(disk: List<List<String>>): List<List<String>> {
        while (disk.last().all { it == "." }) {
            return trimDisk(disk.dropLast(1))
        }
        return disk
    }

    fun moveFile(disk: List<List<String>>): List<List<String>> {
        val file = disk.last().last()
        val indexList = disk.indexOfFirst { it.contains(".") }
        val indexFreeSpace = disk[indexList].indexOfFirst { it == "." }
        val newList = disk[indexList].toMutableList()
        newList[indexFreeSpace] = file
        val newDisk = disk.toMutableList()
        newDisk[indexList] = newList
        newDisk[newDisk.size - 1] = newDisk[newDisk.size - 1].dropLast(1)
        return trimDisk(newDisk)
    }

    fun part1(input: String): Long {
        var disk = getDisk(input)
        while (disk.any { it.contains(".") }) {
            disk = moveFile(disk)
        }
        val map = disk.flatten().mapIndexed { i, it -> Pair(i.toLong(), it) }
        return map.fold(0L) { acc, it -> acc + (it.first * it.second.toLong()) }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    val input = readInput("Day09")
    part1(input).println()
//    part2(input).println()
}
