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
            if (indexesAndOptions[indexesAndOptionsIndex].second.size > optionsIndex) {
                solveComplexSudoku(
                    grid = grid,
                    indexesAndOptions = indexesAndOptions,
                    indexesAndOptionsIndex = indexesAndOptionsIndex,
                    optionsIndex = optionsIndex + 1
                )
            } else {
                // find the value previously used
                val previousGuessPosition = indexesAndOptions[indexesAndOptionsIndex - 1].first

                val currentValueInPreviousGuessPosition = grid[previousGuessPosition]

                // see where that sits in the related options list for this index, get its index and increment by 1
                val listOfPossibleOptions = indexesAndOptions[indexesAndOptionsIndex - 1].second

                val currentValueOptionsIndex = listOfPossibleOptions.mapIndexedNotNull { index, option ->
                    if (option == currentValueInPreviousGuessPosition) index else null
                }.single()

//                if (currentValueOptionsIndex < indexesAndOptions[indexesAndOptionsIndex - 1].second.size) {
                solveComplexSudoku(
                    grid = createGrid(
                        grid = grid,
                        gridIndex = indexesAndOptions[indexesAndOptionsIndex].first,
                        value = indexesAndOptions[indexesAndOptionsIndex].second[optionsIndex]
                    ),
                    indexesAndOptions = indexesAndOptions,
                    indexesAndOptionsIndex = indexesAndOptionsIndex - 1,
                    optionsIndex = currentValueOptionsIndex + 1
                )
//                } else {
//                    // do the above else condition again - refactor out to a method to recall this
//                    // actually might need to move the "less than" if statement out
//                    // so that we either increment options index if we have another option,
//                    // or decrement the indexesWithPossibleValuesIndex
//                    emptyList()
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
