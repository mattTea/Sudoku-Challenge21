package sudoku

import assertk.assertThat
import assertk.assertions.containsExactly
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

        val result = calculateValue(grid, 5)

        assertThat(result).isEqualTo(6)
    }

    @Test
    fun `should calculate 9x9 grid with one empty grid position in first row`() {
        val input = singleEmptyGridValueIsSixAtIndexFive

        val result = solveSudoku(input)

        assertThat(result).isEqualTo(solvedPuzzle)
    }

    @Test
    fun `should calculate single empty value in second row in 9x9 grid`() {
        val grid = singleEmptyGridValueIsThreeAtIndexTwelve

        val result = calculateValue(grid, 12)

        assertThat(result).isEqualTo(3)
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
}
