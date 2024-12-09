fun main() {
    val freeSpace = "."

    fun charToInt(c: Char): Int {
        return c.code - 48
    }

    fun getDisk(input: String): List<List<String>> {
        val disk = mutableListOf<List<String>>()
        for (i in input.indices) {
            val number = charToInt(input[i])
            if (number == 0) continue
            val block = (1..number).map { if (i % 2 == 0) (i / 2).toString() else freeSpace }
            disk.add(block)
        }
        return disk
    }

    fun trimDisk(disk: List<List<String>>): List<List<String>> {
        while (disk.last().all { it == freeSpace }) {
            return trimDisk(disk.dropLast(1))
        }
        return disk
    }

    fun moveFile(disk: List<List<String>>): List<List<String>> {
        val file = disk.last().last()
        val indexBlockWithFreeSpace = disk.indexOfFirst { it.contains(freeSpace) }
        val indexFreeSpace = disk[indexBlockWithFreeSpace].indexOfFirst { it == freeSpace }

        val newBlock = disk[indexBlockWithFreeSpace].toMutableList()
        newBlock[indexFreeSpace] = file
        val oldBlock = disk.last().toMutableList().dropLast(1)

        val newDisk = disk.toMutableList()
        newDisk[indexBlockWithFreeSpace] = newBlock
        newDisk[newDisk.size - 1] = oldBlock

        return trimDisk(newDisk)
    }

    fun moveBlock(disk: List<List<String>>, fileNumber: Int): List<List<String>> {
        val indexFileBlockToMove = disk.indexOfLast { it.contains(fileNumber.toString()) }
        var sizeFileBlockToMove = disk[indexFileBlockToMove].size
        val indexFreeSpace = disk.indexOfFirst { it.count { c -> c == freeSpace } >= sizeFileBlockToMove }

        if (indexFreeSpace == -1 || indexFreeSpace > indexFileBlockToMove) return disk

        val newBlock = mutableListOf<String>()
        disk[indexFreeSpace].forEach {
            if (sizeFileBlockToMove == 0 || it != freeSpace) {
                newBlock.add(it)
            } else {
                newBlock.add(fileNumber.toString())
                sizeFileBlockToMove--
            }
        }

        val oldBlock = mutableListOf<String>()
        disk[indexFileBlockToMove].forEach {
            if (it == fileNumber.toString()) {
                oldBlock.add(freeSpace)
            } else {
                oldBlock.add(it)
            }
        }

        val newDisk = disk.toMutableList()
        newDisk[indexFreeSpace] = newBlock
        newDisk[indexFileBlockToMove] = oldBlock
        return newDisk
    }

    fun part1(input: String): Long {
        var disk = getDisk(input)
        while (disk.any { it.contains(freeSpace) }) {
            disk = moveFile(disk)
        }
        return disk
            .flatten()
            .mapIndexed { i, it -> Pair(i.toLong(), it) }
            .fold(0L) { acc, it -> acc + (it.first * it.second.toLong()) }
    }

    fun part2(input: String): Long {
        var disk = getDisk(input)
        val maxFileNumber = disk.flatten().max().toInt()
        (maxFileNumber downTo 0).forEach {
            disk = moveBlock(disk, it)
        }
        return disk
            .flatten()
            .mapIndexed { i, it -> if (it == freeSpace) Pair(i.toLong(), "0") else Pair(i.toLong(), it) }
            .fold(0L) { acc, it -> acc + (it.first * it.second.toLong()) }
    }

    val input = readInput("Day09")
    part1(input).println()
    part2(input).println()
}
