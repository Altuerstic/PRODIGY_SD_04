import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SudokuSolver extends JFrame {

    private JTextField[][] puzzleFields;

    public SudokuSolver() {
        // Set up the main frame
        setTitle("Sudoku Solver");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create and place components in the frame
        JPanel panel = new JPanel(new GridLayout(9, 9, 2, 2));
        puzzleFields = new JTextField[9][9];

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                JTextField field = new JTextField(1);
                field.setHorizontalAlignment(JTextField.CENTER);
                puzzleFields[i][j] = field;
                panel.add(field);
            }
        }

        JButton solveButton = new JButton("Solve Sudoku");
        solveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                solveSudoku();
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(solveButton);

        add(panel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }

    private void solveSudoku() {
        int[][] sudokuGrid = new int[9][9];

        // Extract puzzle values from text fields
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                String value = puzzleFields[i][j].getText();
                sudokuGrid[i][j] = value.isEmpty() ? 0 : Integer.parseInt(value);
            }
        }

        // Solve Sudoku using the SudokuSolver.solveSudoku method
        if (SudokuSolver.solveSudoku(sudokuGrid)) {
            // Update text fields with solved values
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    puzzleFields[i][j].setText(String.valueOf(sudokuGrid[i][j]));
                }
            }
            JOptionPane.showMessageDialog(this, "Sudoku solved successfully!");
        } else {
            JOptionPane.showMessageDialog(this, "No solution exists for the given Sudoku puzzle.");
        }
    }

    private static boolean solveSudoku(int[][] grid) {
        int[] emptyCell = findEmptyCell(grid);

        if (emptyCell == null) {
            // No empty cell found, the puzzle is solved
            return true;
        }

        int row = emptyCell[0];
        int col = emptyCell[1];

        for (int num = 1; num <= 9; num++) {
            if (isSafe(grid, row, col, num)) {
                grid[row][col] = num;

                if (solveSudoku(grid)) {
                    // Move to the next empty cell
                    return true;
                }

                // If placing the current number doesn't lead to a solution, backtrack
                grid[row][col] = 0;
            }
        }

        // No number can be placed in the current cell, trigger backtracking
        return false;
    }

    private static boolean isSafe(int[][] grid, int row, int col, int num) {
        // Check if 'num' is not present in the current row and column
        for (int x = 0; x < 9; x++) {
            if (grid[row][x] == num || grid[x][col] == num) {
                return false;
            }
        }

        // Check if 'num' is not present in the current 3x3 subgrid
        int startRow = row - row % 3;
        int startCol = col - col % 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    private static int[] findEmptyCell(int[][] grid) {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (grid[i][j] == 0) {
                    return new int[]{i, j};
                }
            }
        }
        return null; // No empty cell found
    }

    public static void main(String[] args) {
        new SudokuSolver();
    }
}
