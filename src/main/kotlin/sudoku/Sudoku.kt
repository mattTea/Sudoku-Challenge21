package sudoku

typealias Grid = List<Int>

const val ROW_LENGTH = 9

fun solveSudoku(grid: Grid): Grid {
    val gridWithAllPossibleValues = calculateAllPossibleValuesInGrid(grid)
    val newGrid = gridWithAllPossibleValues.map { if (it.size == 1) it.single() else 0 }

//    val indexesWithPossibleValues = createGuesses(gridWithAllPossibleValues)

    if (gridWithAllPossibleValues.all { it.size == 1 }) return gridWithAllPossibleValues.flatten()

    return if (newGrid == grid) newGrid else solveSudoku(newGrid)
}

fun calculateAllPossibleValuesInGrid(grid: Grid): List<List<Int>> {
    return grid.mapIndexed { index, it ->
        if (it == 0) calculatePossibleValues(grid, index) else listOf(it)
    }
}

fun calculatePossibleValues(grid: Grid, index: Int): List<Int> {
    val rowIndex = index / ROW_LENGTH
    val columnIndex = index % ROW_LENGTH

    return possibleRowValues(grid, rowIndex)
        .subtract(columnValues(grid, columnIndex))
        .subtract(regionValues(grid, columnIndex, rowIndex))
        .toList()
}

fun possibleRowValues(grid: Grid, rowIndex: Int): List<Int> {
    val possibleValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    return possibleValues.subtract(
        grid.subList(
            (rowIndex * ROW_LENGTH),
            (((rowIndex + 1) * ROW_LENGTH))
        )
    ).toList()
}

fun columnValues(grid: Grid, columnIndex: Int): List<Int> {
    val rowLengthMultiples = (grid.indices).filter { it % ROW_LENGTH == 0 }

    return grid.filterIndexed { index, _ ->
        rowLengthMultiples
            .map { it + columnIndex }
            .contains(index)
    }
}

fun regionValues(grid: Grid, columnIndex: Int, rowIndex: Int): List<Int> {
    val startingRow = columnIndex / 3
    val startingColumn = rowIndex / 3
    val regionStartingIndex = ((startingColumn * ROW_LENGTH) + startingRow) * 3

    val regionTopRow = (regionStartingIndex..(regionStartingIndex + 2)).toList()

    val regionIndices =
        regionTopRow +
                regionTopRow.map { it + ROW_LENGTH } +
                regionTopRow.map { it + (2 * ROW_LENGTH) }

    return grid.filterIndexed { index, _ ->
        regionIndices.contains(index)
    }
}

fun createGuesses(gridWithPossibleValues: List<List<Int>>): List<Pair<Int, List<Int>>> =
    gridWithPossibleValues.mapIndexedNotNull { index, possibleValues ->
        if (possibleValues.size > 1) Pair(index, possibleValues) else null
    }

fun isValid(grid: Grid, index: Int, value: Int): Boolean {
    val rowIndex = index / ROW_LENGTH
    val columnIndex = index % ROW_LENGTH

    val rowValues = grid.subList(
        (rowIndex * ROW_LENGTH),
        (((rowIndex + 1) * ROW_LENGTH))
    )

    val columnValues = columnValues(grid, columnIndex)
    val regionValues = regionValues(grid, columnIndex, rowIndex)

    return !(rowValues + columnValues + regionValues).contains(value)
}

fun createGrid(grid: List<Int>, gridIndex: Int, value: Int): List<Int> =
    grid.mapIndexed { index, it -> if (index == gridIndex) value else it }

fun bruteForceSolveSudoku(
    grid: Grid,
    indexesWithPossibleValues: List<Pair<Int, List<Int>>>,
    indexesWithPossibleValuesIndex: Int = 0,
    optionsIndex: Int = 0
): Grid {
    val optionIsValid = isValid(
        grid = grid,
        index = indexesWithPossibleValues[indexesWithPossibleValuesIndex].first,
        value = indexesWithPossibleValues[indexesWithPossibleValuesIndex].second[optionsIndex]
    )

    return if (optionIsValid) {
        createGrid(
            grid = grid,
            gridIndex = indexesWithPossibleValues[indexesWithPossibleValuesIndex].first,
            value = indexesWithPossibleValues[indexesWithPossibleValuesIndex].second[optionsIndex]
        )
    } else {
        bruteForceSolveSudoku(
            grid = grid,
            indexesWithPossibleValues = indexesWithPossibleValues,
            indexesWithPossibleValuesIndex = indexesWithPossibleValuesIndex,
            optionsIndex = optionsIndex + 1
        )
    }
}