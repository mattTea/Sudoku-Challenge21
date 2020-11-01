package sudoku

import kotlin.math.floor

fun solveSudoku(input: List<Int>): List<Int> {
    return input.mapIndexed { index, it ->
        if (it != 0) it
        else calculateValue(input, index)
    }
}

fun calculateValue(grid: List<Int>, index: Int): Int {
    val allowedValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    val rowNumber = floor((index + 1) / 9.0).toInt()

    val possibleRowValues = allowedValues.subtract(
        grid.subList(
            (rowNumber * 9),
            (((rowNumber + 1) * 9) - 1)
        )
    )

    val possibleColumnValues = columnValues(grid, index)

    return possibleRowValues.subtract(possibleColumnValues).single()
}

fun columnValues(grid: List<Int>, columnIndex: Int): List<Int> {
    val columnMultiples = (grid.indices).filter { it % 9 == 0 }

    return grid.filterIndexed { index, _ ->
        columnMultiples.map { it + columnIndex }.contains(index)
    }
}
