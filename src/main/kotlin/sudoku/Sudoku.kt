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

        if (newGrid == grid) newGrid else solveSudoku(newGrid)
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
    val regionStartingIndex = ((startingColumn * ROW_LENGTH) + startingRow) * 3

    val regionTopRow = (regionStartingIndex..(regionStartingIndex + 2)).toList()
    val regionIndices =
            regionTopRow +
            regionTopRow.map { it + ROW_LENGTH } +
            regionTopRow.map { it + (2* ROW_LENGTH) }

    return grid.filterIndexed { index, _ ->
        regionIndices.contains(index)
    }
}
