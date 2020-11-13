package sudoku

import assertk.assertThat
import assertk.assertions.containsExactly
import assertk.assertions.containsOnly
import assertk.assertions.isEqualTo
import org.junit.jupiter.api.Test

class SudokuKtTest {
    @Test
    fun `should return single possible row value`() {
        val grid = singleEmptyGridValueIsSixAtIndexFive

        val result = possibleRowValues(grid, 0)

        assertThat(result).containsExactly(6)
    }

    @Test
    fun `should return column values`() {
        val grid = singleEmptyGridValueIsSixAtIndexFive

        val result = columnValues(grid, 5)

        assertThat(result).containsExactly(0,2,5,4,7,1,9,3,8)
    }

    @Test
    fun `should calculate single empty value in first row in 9x9 grid`() {
        val grid = singleEmptyGridValueIsSixAtIndexFive

        val result = calculatePossibleValues(grid, 5)

        assertThat(result).containsOnly(6)
    }

    @Test
    fun `should solve puzzle with one empty grid position in first row`() {
        val input = singleEmptyGridValueIsSixAtIndexFive

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should calculate single empty value in second row in 9x9 puzzle`() {
        val grid = singleEmptyGridValueIsThreeAtIndexTwelve

        val result = calculatePossibleValues(grid, 12)

        assertThat(result).containsOnly(3)
    }

    @Test
    fun `should calculate 9x9 grid with one empty grid position in second row`() {
        val input = singleEmptyGridValueIsThreeAtIndexTwelve

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should calculate 9x9 grid with one empty grid position at end of row`() {
        val input = singleEmptyGridValueIsOneAtIndexThirtyFive

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should calculate 9x9 grid with two empty grid positions in separate rows and columns`() {
        val input = twoEmptyGridValues

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should return region values for second region`() {
        val grid = singleEmptyGridValueIsSixAtIndexFive

        val result = regionValues(grid, 5, 0)

        assertThat(result).containsExactly(8,4,0,3,9,2,7,1,5)
    }

    @Test
    fun `should return region values for ninth region`() {
        val grid = singleEmptyGridValueIsSixAtIndexFive

        val result = regionValues(grid, 8, 8)

        assertThat(result).containsExactly(2,8,3,5,9,6,7,1,4)
    }

    @Test
    fun `should return region values for fifth region`() {
        val grid = singleEmptyGridValueIsSixAtIndexFive

        val result = regionValues(grid, 5, 5)

        assertThat(result).containsExactly(6,5,4,2,8,7,9,3,1)
    }

    @Test
    fun `should calculate two possible options for empty grid position`() {
        val grid = multipleValueOptionsAtIndexThirtyFiveCorrectIsOne

        val result = calculatePossibleValues(grid, 35)

        assertThat(result).containsOnly(1,2)
    }

    @Test
    fun `should calculate 9x9 grid with three empty grid positions in adjacent positions`() {
        val input = multipleValueOptionsAtIndexThirtyFiveCorrectIsOne

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should calculate full simple, solvable puzzle`() {
        val input = simplePuzzle

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(simplePuzzleSolution)
    }

    @Test
    fun `should return zeros for grid positions that could contain more than one value`() {
        val input = puzzleWithMoreThanOneSolution

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(puzzleWithMoreThanOneSolutionOutput)
    }

//    @Test
//    fun `should calculate full hard, solvable puzzle`() {
//        val input = hardPuzzle
//
//        val result = solveSudoku(input)
//
//        assertThat(result).isEqualTo(hardPuzzleSolution)
//    }

//    @Test
//    fun `should create grid replacing index with indexed value`() {
//        val grid = multipleValueOptionsAtIndexThirtyFiveCorrectIsOne
//        val expected = listOf(
//            7, 2, 3, 8, 4, 6, 1, 5, 9,
//            6, 1, 5, 3, 9, 2, 4, 7, 8,
//            8, 4, 9, 7, 1, 5, 6, 3, 0,
//            3, 7, 8, 6, 5, 4, 9, 0, 1,
//            1, 9, 4, 2, 8, 7, 3, 6, 5,
//            2, 5, 6, 9, 3, 1, 8, 4, 7,
//            5, 6, 1, 4, 7, 9, 2, 8, 3,
//            4, 8, 7, 1, 2, 3, 5, 9, 6,
//            9, 3, 2, 5, 6, 8, 7, 1, 4
//        )
//
//        val result = createGrid(grid, 35, 1)
//
//        assertThat(result).isEqualTo(expected)
//    }
//
//    @Test
//    fun `should return list of possible grids`() {
//        val startingGrid = multipleValueOptionsAtIndexThirtyFiveCorrectIsOne
//        val possibleGrid1 = multipleValueOptionsAtIndexThirtyFiveCorrectIsOneCorrectOption1
//        val possibleGrid2 = multipleValueOptionsAtIndexThirtyFiveCorrectIsOneIncorrectOption2
//
//        val result = createPossibleGrids(startingGrid, Pair(35, listOf(1, 2)))
//
//        assertThat(result).containsOnly(possibleGrid1, possibleGrid2)
//    }

    @Test
    fun `should return map of index and possible values at index`() {
        val gridWithAllPossibleValues = multipleValueOptionsAtIndexThirtyFiveCorrectIsOne
            .mapIndexed { index, it ->
                if (index == 35) listOf(1,2)
                else if (index == 26 || index == 34) listOf(2)
                else listOf(it) }

        val result = calculateGuesses(gridWithAllPossibleValues)

        assertThat(result).containsOnly(Pair(35, listOf(1,2)))
    }

    @Test
    fun `should return a grid if guess is valid`() {
        val grid = multipleValueOptionsAtIndexThirtyFiveCorrectIsOne

        val result = makeGuess(grid, 35, listOf(1,2))

        assertThat(result).isEqualTo(solvedPuzzle)
    }
}
