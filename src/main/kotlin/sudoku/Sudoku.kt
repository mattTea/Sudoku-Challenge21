package sudoku

import kotlin.math.floor

const val ROW_LENGTH = 9

fun solveSudoku(input: List<Int>): List<Int> =
    input.mapIndexed { index, it ->
        if (it == 0) calculateValue(input, index) else it
    }

fun calculateValue(grid: List<Int>, index: Int): Int {
    val rowIndex = floor((index + 1) / ROW_LENGTH.toDouble()).toInt()
    val columnIndex = index - (rowIndex * ROW_LENGTH)

    return possibleRowValues(grid, rowIndex).subtract(columnValues(grid, columnIndex)).single()
}

fun possibleRowValues(grid: List<Int>, rowIndex: Int): List<Int> =
    listOf(1,2,3,4,5,6,7,8,9)
        .subtract(
            grid.subList(
                (rowIndex * ROW_LENGTH),
                (((rowIndex + 1) * ROW_LENGTH))
            )
        ).toList()

fun columnValues(grid: List<Int>, columnIndex: Int): List<Int> {
    val rowLengthMultiples = (grid.indices).filter { it % ROW_LENGTH == 0 }

    return grid.filterIndexed { index, _ ->
        rowLengthMultiples
            .map { it + columnIndex }
            .contains(index)
    }
}
