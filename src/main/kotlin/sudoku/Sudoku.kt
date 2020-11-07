package sudoku

const val ROW_LENGTH = 9

fun solveSudoku(grid: List<Int>): List<Int> {
    val sudokuTry = grid.mapIndexed { index, it ->
        if (it == 0) calculateValue(grid, index) else listOf(it)
    }

    return if (sudokuTry.all { it.size == 1 }) {
        sudokuTry.flatten()
    } else {
        val newGrid = sudokuTry
            .toMutableList()
            .map { if (it.size > 1) 0 else it.single() }

        solveSudoku(newGrid)
    }
}

fun calculateValue(grid: List<Int>, index: Int): List<Int> {
    val rowIndex = index / ROW_LENGTH
    val columnIndex = index % ROW_LENGTH

    return possibleRowValues(grid, rowIndex)
        .subtract(columnValues(grid, columnIndex))
        .subtract(regionValues(grid, columnIndex, rowIndex))
        .toList()
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

fun regionValues(grid: List<Int>, columnIndex: Int, rowIndex: Int): List<Int> {
    val startingRow = columnIndex / 3
    val startingColumn = rowIndex / 3
    val regionStartingIndex = ((startingColumn * 9) + startingRow) * 3

    val regionIndices = listOf(
        regionStartingIndex,
        regionStartingIndex + 1,
        regionStartingIndex + 2,
        regionStartingIndex + (1 * 9),
        regionStartingIndex + (1 * 9) + 1,
        regionStartingIndex + (1 * 9) + 2,
        regionStartingIndex + (2 * 9),
        regionStartingIndex + (2 * 9) + 1,
        regionStartingIndex + (2 * 9) + 2,
    )

    return grid.filterIndexed { index, _ ->
        regionIndices.contains(index)
    }
}
