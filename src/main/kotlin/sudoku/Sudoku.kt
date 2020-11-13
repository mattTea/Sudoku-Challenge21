package sudoku

const val ROW_LENGTH = 9

fun solveSudoku(grid: List<Int>): List<Int> {
    val gridWithAllPossibleValues = calculateAllPossibleValuesInGrid(grid)

    val newGrid = gridWithAllPossibleValues.map { if (it.size == 1) it.single() else 0 }

    val indexesWithPossibleValues = calculateGuesses(gridWithAllPossibleValues)

    // loop through Pairs in indexesWithPossibleValues calling makeGuess()
    // if any come back with a different grid than was passed in call solveSudoku
    // make sure suss out the base case for this recursion


    return if (gridWithAllPossibleValues.all { it.size == 1 }) {
        gridWithAllPossibleValues.flatten()
    } else {
        if (newGrid == grid) newGrid else solveSudoku(newGrid)
    }
}

fun calculateAllPossibleValuesInGrid(grid: List<Int>): List<List<Int>> {
    return grid.mapIndexed { index, it ->
        if (it == 0) calculatePossibleValues(grid, index) else listOf(it)
    }
}

fun calculatePossibleValues(grid: List<Int>, index: Int): List<Int> {
    val rowIndex = index / ROW_LENGTH
    val columnIndex = index % ROW_LENGTH

    return possibleRowValues(grid, rowIndex)
        .subtract(columnValues(grid, columnIndex))
        .subtract(regionValues(grid, columnIndex, rowIndex))
        .toList()
}

fun possibleRowValues(grid: List<Int>, rowIndex: Int): List<Int> {
    val possibleValues = listOf(1, 2, 3, 4, 5, 6, 7, 8, 9)

    return possibleValues.subtract(
        grid.subList(
            (rowIndex * ROW_LENGTH),
            (((rowIndex + 1) * ROW_LENGTH))
        )
    ).toList()
}

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
                regionTopRow.map { it + (2 * ROW_LENGTH) }

    return grid.filterIndexed { index, _ ->
        regionIndices.contains(index)
    }
}

//fun createGrid(grid: List<Int>, gridIndex: Int, value: Int): List<Int> =
//    grid.mapIndexed { index, it -> if (index == gridIndex) value else it }
//
//fun createPossibleGrids(
//    grid: List<Int>,
//    pairOfIndexAndPossibleValues: Pair<Int, List<Int>>
//): List<List<Int>> =
//    pairOfIndexAndPossibleValues.second.map { createGrid(grid, pairOfIndexAndPossibleValues.first, it) }

fun calculateGuesses(gridWithPossibleValues: List<List<Int>>): List<Pair<Int, List<Int>>> =
    gridWithPossibleValues.mapIndexedNotNull { index, possibleValues ->
        if (possibleValues.size > 1) Pair(index, possibleValues) else null
    }

fun makeGuess(grid: List<Int>, index: Int, possibleValues: List<Int>): List<Int> {
    val startingGridWithAllPossibleValues = calculateAllPossibleValuesInGrid(grid)

    val listOfGrids = possibleValues.map { possibleValue ->
        grid.mapIndexed { gridIndex, originalValue ->
            if (gridIndex == index) possibleValue else originalValue
        }
    }

    val listOfSuccessfulGrids = listOfGrids.mapNotNull { gridGuess ->
        val guess = calculateAllPossibleValuesInGrid(gridGuess).flatten()

        if (guess != startingGridWithAllPossibleValues.flatten()) guess else null
    }

    return if (listOfSuccessfulGrids.isNotEmpty()) listOfSuccessfulGrids.first() else grid
    // try changing this to list of successful grids being returned
}