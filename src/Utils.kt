@file:Suppress("unused")

import java.io.File
import kotlin.math.abs

/**
 * Reads from the given input txt file as one string.
 */
fun readInput(name: String): String = File("src", "$name.txt")
    .readText()

/**
 * Reads lines from the given input txt file as integers.
 */
fun readInputAsInts(name: String): List<Int> = File("src", "$name.txt")
    .readLines()
    .map { it.toInt() }

/**
 * Reads from the given input txt file as strings split by the given delimiter.
 */
fun readInputSplitByDelimiter(name: String, delimiter: String): List<String> = File("src", "$name.txt")
    .readText()
    .split(delimiter)

/**
 * Reads lines from the given input txt file as strings. Can be used to read the input as a grid of characters.
 */
fun readInputAsStrings(name: String): List<String> = File("src", "$name.txt")
    .readLines()

/**
 * Adds a border with the given character to a grid. Useful when looking for neighbours of points in the input grid.
 */
fun addBorderToGrid(input: List<String>, borderCharacter: Char): List<String> {
    val emptyRow = borderCharacter.toString().repeat(input.first().length)
    val borderedInput = mutableListOf(emptyRow)
    for (line in input) {
        val borderedLine = "$borderCharacter$line$borderCharacter"
        borderedInput.add(borderedLine)
    }
    borderedInput.add(emptyRow)
    return borderedInput
}

fun distanceTo(a: Int, b: Int): Int = abs(a - b)

/**
 * The cleaner shorthand for printing output.
 */
fun Any?.println() = println(this)

/**
 * The cleaner shorthand for splitting strings and filtering non-empty values.
 */
fun CharSequence.splitIgnoreEmpty(vararg delimiters: String): List<String> {
    return this.split(*delimiters).filter {
        it.isNotEmpty()
    }
}