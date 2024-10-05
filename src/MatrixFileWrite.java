import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class MatrixFileWrite {
    //how to write a matrix to a file 
    public static void writeMatrixFile(String filePath, int[][] matrix) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int[] row : matrix) {
                for (int element : row) {
                    writer.write(element + " ");
                }
                writer.newLine();
            }
        }
    }

//reads a matrix from a file
public static int[][] readMatrixFile(String filePath) throws IOException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        int row = 0;
        int cols = 0;
        String line;

        //determines the size of the matrix 
        while ((line = reader.readLine()) != null) {
            row++;
            String[] elements = line.split(" ");
            if (cols == 0) {
                cols = elements.length;
            }
        }
        //creates a matrix to store values
        int[][] matrix = new int[row][cols];

        //reads the values into the martrix
        try (BufferedReader resetReader = new BufferedReader(new FileReader(filePath))){
            int currentRow = 0;

            while ((line = resetReader.readLine()) != null) {
                String[] elements = line.split(" ");
                for (int i= 0; i < elements.length; i++) {
                    matrix[currentRow][i] = Integer.parseInt(elements[i]);
                }
                currentRow++;
            }
        }
            return matrix;
    }
}

//multiply two matrices
public static int[][] multiplyMatrices(int[][] matrix1, int[][] matrix2) {
    int row1 = matrix1.length;
    int cols1 = matrix1[0].length;
    int row2 = matrix2.length;
    int cols2 = matrix2[0].length;

    if (cols1 != row2) {
        throw new IllegalArgumentException("Error, the number of columns in the first matrix must be equal to the number of rows in the second matrix");
    }

    int[][] result = new int[row1][cols2];

    for (int i = 0; i < row1; i++ ) {
        for (int k = 0; k < cols2; k++) {
            result[i][k] = 0;
            
            for (int x = 0; x < cols1; x++) {
                result[i][k] += matrix1[i][x] * matrix2[x][k];
            }
        }
    }
    return result;
}

//main method 
public static void main(String[] args) {
    try {
        //pre-define file names
        String file1 = "matrix1.txt";
        String file2 = "matrix2.txt";

        //read the matrices from the files
        int[][] matrix1 = readMatrixFile(file1);
        int[][] matrix2 = readMatrixFile(file2);

        int[][] resultMatrix = multiplyMatrices(matrix1, matrix2);

        writeMatrixFile("matrix3.txt", resultMatrix);
        System.out.println("Results of the matrices multiplication can be viewed on matrix3.txt");
    }catch (IOException e) {
        System.err.println("An IOException has occured: " +e.getMessage());
    }catch (IllegalArgumentException e) {
        System.err.println("An IllegalException has occured: " +e.getMessage());
    }

    }
}
