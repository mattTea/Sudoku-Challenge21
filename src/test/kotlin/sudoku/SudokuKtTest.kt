package sudoku

import assertk.assertThat
import assertk.assertions.*
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

    @Test
    fun `should return map of index and possible values at index`() {
        val gridWithAllPossibleValues = multipleValueOptionsAtIndexThirtyFiveCorrectIsOne
            .mapIndexed { index, it ->
                if (index == 35) listOf(1,2)
                else if (index == 26 || index == 34) listOf(2)
                else listOf(it) }

        val result = createGuesses(gridWithAllPossibleValues)

        assertThat(result).containsOnly(Pair(35, listOf(1,2)))
    }

    @Test
    fun `should return true for valid value guess`() {
        val grid = hardPuzzle

        val result = isValid(grid, 11, 5)

        assertThat(result).isTrue()
    }

    @Test
    fun `should return false for invalid value guess`() {
        val grid = hardPuzzle

        val result = isValid(grid, 11, 9)

        assertThat(result).isFalse()
    }
}
