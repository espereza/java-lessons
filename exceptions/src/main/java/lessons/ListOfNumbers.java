package lessons;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * <H1>Catching and Handling Exceptions</H1>
 * <p>
 *     This class creates an ArrayList that contains 10 Integer elements with sequential
 *     values 0 through 9.
 * </p>
 */
public class ListOfNumbers {

    private static final int SIZE = 10;

    private List<Integer> list;

    public ListOfNumbers() {
        list = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            list.add(i);
        }
    }

    /**
     * Writes the list of numbers into a text file called OutFile.txt
     */
    public void writeList() {
        /*
         Checked exception.

         The FileWriter constructor throws IOException if the file cannot be opened,
         which MUST be caught.
         */
        PrintWriter out = null;

        try {
            System.out.println("Entered try statement");
            out = new PrintWriter(new FileWriter("OutFile.txt"));
            for (int i = 0; i < SIZE; i++) {
              /*
               Unchecked exception.

               The get(int) method throws IndexOutOfBoundsException if the value of its argument is
               too small or too large, which you MAY consider catching.
               */
              out.println("Value at: " + i + " = " + list.get(i));
            }
        } catch (IndexOutOfBoundsException e) {
            System.err.println("IndexOutOfBoundsException: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        } finally {
            if (out != null) {
                System.out.println("Closing PrintWriter");
                out.close();
            } else {
                System.out.println("PrintWriter not open");
            }
        }
    }

}
