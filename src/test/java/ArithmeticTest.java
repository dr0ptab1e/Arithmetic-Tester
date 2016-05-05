import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import ru.yandex.qatools.allure.annotations.Step;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

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

    @Test
    public void testOperation() {
        Integer parsedOperand1=null;
        Integer parsedOperand2=null;
        Integer parsedResult=null;
        try{
            parsedOperand1 = Integer.valueOf(operand1);
            parsedOperand2 = Integer.valueOf(operand2);
            parsedResult = Integer.valueOf(result);
        }
        catch (NumberFormatException e) {
            fail("Wrong number: "+e.getMessage().substring(e.getMessage().indexOf('"'),e.getMessage().lastIndexOf('"')+1));
        }
        assertTrue("Wrong operation sign: \""+operation+"\"",operation.matches("^[\\*/\\+\\-]$"));
        checkExpression(parsedOperand1,parsedOperand2,operation.charAt(0),parsedResult);
    }

    @Step("Check whether {0}{2}{1}={3}")
    private void checkExpression(int operand1, int operand2, char operation, int result){
        switch (operation) {
            case '+':
                assertTrue("Sum is wrong",operand1 + operand2 == result);
                break;
            case '-':
                assertTrue("Difference is wrong",operand1 - operand2 == result);
                break;
            case '*':
                assertTrue("Product is wrong",operand1 * operand2 == result);
                break;
            case '/':
                assertTrue("Divisor is zero",operand2!=0);
                assertTrue("Quotient is wrong",operand1 / operand2 == result);
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
