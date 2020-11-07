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

    // TODO() refactor below

    val regionIndices = when {
        columnIndex <= 2 && rowIndex <= 2 -> listOf(0,1,2,9,10,11,18,19,20)
        columnIndex <= 5 && rowIndex <= 2 -> listOf(3,4,5,12,13,14,21,22,23)
        columnIndex <= 8 && rowIndex <= 2 -> listOf(6,7,8,15,16,17,24,25,26)
        columnIndex <= 2 && rowIndex <= 5 -> listOf(27,28,29,36,37,38,45,46,47)
        columnIndex <= 5 && rowIndex <= 5 -> listOf(30,31,32,39,40,41,48,49,50)
        columnIndex <= 8 && rowIndex <= 5 -> listOf(33,34,35,42,43,44,51,52,53)
        columnIndex <= 2 && rowIndex <= 8 -> listOf(54,55,56,63,64,65,72,73,74)
        columnIndex <= 5 && rowIndex <= 8 -> listOf(57,58,59,66,67,68,75,76,77)
        else -> listOf(60,61,62,69,70,71,78,79,80)
    }

    return grid.filterIndexed { index, _ ->
        regionIndices.contains(index)
    }
}
