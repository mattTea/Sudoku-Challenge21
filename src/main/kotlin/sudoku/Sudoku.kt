package sudoku

import kotlin.math.floor

fun solveSudoku(input: List<Int>): List<Int> {
    return input.mapIndexed { index, it ->
        if (it != 0) it
        else calculateValue(input, index)
    }
}

fun calculateValue(grid: List<Int>, columnIndex: Int): Int {
    val allowedValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)
    val rowSplitMap = mapOf(
        0 to Pair(0, 8),
        1 to Pair(9, 17),
        2 to Pair(18, 26),
        3 to Pair(27, 35),
        4 to Pair(36, 44),
        5 to Pair(45, 53),
        6 to Pair(54, 62),
        7 to Pair(63, 71),
        8 to Pair(72, 80),
    )
    val rowNumber = floor((columnIndex + 1) / 9.0).toInt()

    val possibleRowValues = allowedValues.subtract(
        grid.subList(rowSplitMap[rowNumber]?.first!!, rowSplitMap[rowNumber]?.second!!)
    )

    val possibleColumnValues = columnValues(grid, columnIndex)

    return possibleRowValues.subtract(possibleColumnValues).single()
}

fun columnValues(grid: List<Int>, columnIndex: Int): List<Int> {
    val columnMultiples = (grid.indices).filter { it % 9 == 0 }

    return grid.filterIndexed { index, _ ->
        columnMultiples.map { it + columnIndex }.contains(index)
    }
}
