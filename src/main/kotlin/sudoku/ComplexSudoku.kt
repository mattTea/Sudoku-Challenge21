package sudoku

fun solveComplexSudoku(
    grid: Grid,
    indexesAndOptions: List<Pair<Int, List<Int>>>,
    indexesAndOptionsIndex: Int = 0,
    optionsIndex: Int = 0
): Grid {
    return if (grid.none { it == 0 }) {
        grid
    } else {
        val optionIsValid = isValid(
            grid = grid,
            index = indexesAndOptions[indexesAndOptionsIndex].first,
            value = indexesAndOptions[indexesAndOptionsIndex].second[optionsIndex]
        )

        if (optionIsValid) {
            solveComplexSudoku(
                grid = createGrid(
                    grid = grid,
                    gridIndex = indexesAndOptions[indexesAndOptionsIndex].first,
                    value = indexesAndOptions[indexesAndOptionsIndex].second[optionsIndex]
                ),
                indexesAndOptions = indexesAndOptions,
                indexesAndOptionsIndex = indexesAndOptionsIndex + 1,
                optionsIndex = 0
            )
        } else {
            if (indexesAndOptions[indexesAndOptionsIndex].second.size - 1 > optionsIndex) {
                solveComplexSudoku(
                    grid = grid, // TODO <- what grid to pass in here - original or amended?
                    indexesAndOptions = indexesAndOptions,
                    indexesAndOptionsIndex = indexesAndOptionsIndex,
                    optionsIndex = optionsIndex + 1
                )
            } else {
                backtrack(
                    grid = grid, // TODO <- what grid to pass in here - original or amended?
                    indexesAndOptions = indexesAndOptions,
                    indexesAndOptionsIndex = indexesAndOptionsIndex,
                    optionsIndex = optionsIndex
                )
            }
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

fun backtrack(
    grid: Grid,
    indexesAndOptions: List<Pair<Int, List<Int>>>,
    indexesAndOptionsIndex: Int,
    optionsIndex: Int,
    backtrackPositions: Int = 1
): Grid {
    val previousGuessPosition = indexesAndOptions[indexesAndOptionsIndex - backtrackPositions].first
    val currentValueInPreviousGuessPosition = grid[previousGuessPosition]

    val listOfPossibleOptions = indexesAndOptions[indexesAndOptionsIndex - backtrackPositions].second
    val currentValueOptionsIndex = listOfPossibleOptions.mapIndexedNotNull { index, option ->
        if (option == currentValueInPreviousGuessPosition) index else null
    }.single()

    // if currentValueOptionsIndex is less than available indices at that indexesAndOptionsIndex
    return if (currentValueOptionsIndex < indexesAndOptions[indexesAndOptionsIndex - backtrackPositions].second.size - 1) {
        solveComplexSudoku(
            grid = createGrid(
                grid = grid,
                gridIndex = indexesAndOptions[indexesAndOptionsIndex].first,
                value = indexesAndOptions[indexesAndOptionsIndex].second[optionsIndex]
            ),
            indexesAndOptions = indexesAndOptions,
            indexesAndOptionsIndex = indexesAndOptionsIndex - backtrackPositions,
            optionsIndex = currentValueOptionsIndex + 1
        )
    } else {
        backtrack(
            grid = grid, // TODO <- what grid to pass in here - original or amended?
            indexesAndOptions = indexesAndOptions,
            indexesAndOptionsIndex = indexesAndOptionsIndex,
            optionsIndex = optionsIndex,
            backtrackPositions = backtrackPositions + 1
        )
    }
}
