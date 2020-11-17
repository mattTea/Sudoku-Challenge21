package sudoku

fun solveComplexSudoku(
    grid: Grid,
    indexesWithPossibleValues: List<Pair<Int, List<Int>>>,
    indexesWithPossibleValuesIndex: Int = 0,
    optionsIndex: Int = 0
): Grid {

    return if (grid.none { it == 0 }) {
        grid
    } else {
        val optionIsValid = isValid(
            grid = grid,
            index = indexesWithPossibleValues[indexesWithPossibleValuesIndex].first,
            value = indexesWithPossibleValues[indexesWithPossibleValuesIndex].second[optionsIndex]
        )

        if (optionIsValid) {
            solveComplexSudoku(
                grid = createGrid(
                    grid = grid,
                    gridIndex = indexesWithPossibleValues[indexesWithPossibleValuesIndex].first,
                    value = indexesWithPossibleValues[indexesWithPossibleValuesIndex].second[optionsIndex]
                ),
                indexesWithPossibleValues = indexesWithPossibleValues,
                indexesWithPossibleValuesIndex = indexesWithPossibleValuesIndex + 1,
                optionsIndex = 0
            )
        } else {
            solveComplexSudoku(
                grid = grid,
                indexesWithPossibleValues = indexesWithPossibleValues,
                indexesWithPossibleValuesIndex = indexesWithPossibleValuesIndex,
                optionsIndex = optionsIndex + 1
            )
        }
    }
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
