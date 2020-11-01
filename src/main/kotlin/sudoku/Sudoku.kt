package sudoku

import kotlin.math.floor

const val ROW_LENGTH = 9

fun solveSudoku(input: List<Int>): List<Int> {
    return input.mapIndexed { index, it ->
        if (it != 0) it
        else calculateValue(input, index)
    }
}

fun calculateValue(grid: List<Int>, index: Int): Int {
    val allowedValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val rowIndex = floor((index + 1) / ROW_LENGTH.toDouble()).toInt()
    val columnIndex = index - (rowIndex * ROW_LENGTH)

    val possibleRowValues = allowedValues.subtract(
        grid.subList(
            (rowIndex * ROW_LENGTH),
            (((rowIndex + 1) * ROW_LENGTH) - 1)
        )
    )

    val possibleColumnValues = columnValues(grid, columnIndex)

    return possibleRowValues.subtract(possibleColumnValues).single()
}

fun columnValues(grid: List<Int>, columnIndex: Int): List<Int> {
    val columnMultiples = (grid.indices).filter { it % ROW_LENGTH == 0 }

    return grid.filterIndexed { index, _ ->
        columnMultiples.map { it + columnIndex }.contains(index)
    }
}
