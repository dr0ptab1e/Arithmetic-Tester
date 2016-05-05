import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ArithmeticTest {

    private String operand1;
    private String operand2;
    private String operation;
    private String result;

    public ArithmeticTest(String operand1, String operand2, String operation, String result) {
        this.operand1 = operand1;
        this.operand2 = operand2;
        this.operation = operation;
        this.result = result;
    }

    @org.junit.Test
    public void testOperation() {
        int parsedOperand1 = Integer.valueOf(operand1);
        int parsedOperand2 = Integer.valueOf(operand2);
        int parsedResult = Integer.valueOf(result);
        assertTrue(operation.matches("^[\\*\\/\\+\\-]$"));
        switch (operation.charAt(0)) {
            case '+':
                assertTrue(parsedOperand1 + parsedOperand2 == parsedResult);
                break;
            case '-':
                assertTrue(parsedOperand1 - parsedOperand2 == parsedResult);
                break;
            case '*':
                assertTrue(parsedOperand1 * parsedOperand2 == parsedResult);
                break;
            case '/':
                assertTrue(parsedOperand1 / parsedOperand2 == parsedResult);
                break;
        }
    }

    @Parameterized.Parameters
    public static Collection getInputParams() {
        URL url = ArithmeticTest.class.getResource("/operations");
        try {
            File inputFile = new File(url.getFile());
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            ArrayList<String[]> data = new ArrayList<String[]>();
            String line;
            while ((line = reader.readLine()) != null) {
                data.add(line.split(";"));
            }
            reader.close();
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
