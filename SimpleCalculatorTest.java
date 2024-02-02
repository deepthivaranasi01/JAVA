package calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This class helps in testing the multiple cases of SimpleCalculator.
 */
public class SimpleCalculatorTest {
  Calculator calci = new SimpleCalculator();

  @Test (expected = IllegalArgumentException.class)
  public void testIllegalFirstElement() {
    calci.input('+');
    calci.input('1');
    calci.input('*');
    calci.input('2');
    calci.input('=');
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIllegalOperandsAndOperators() {
    calci.input('#');
    calci.input('&');
    calci.input('$');
    calci.input('^');
  }

  @Test (expected = IllegalArgumentException.class)
  public void testSimultaneousOperators() {
    calci.input('1');
    calci.input('1');
    calci.input('+');
    calci.input('-');
    calci.input('2');
    calci.input('=');
  }

  @Test
  public void testBlankString() {
    assertEquals("", calci.getResult());
  }

  @Test
  public void clearInput() {
    calci.input('1');
    calci.input('+');
    calci.input('2');
    calci.input('=');
    assertEquals("3", calci.getResult());
    calci.input('C');
    assertEquals("", calci.getResult());
  }


  @Test (expected = IllegalArgumentException.class)
  public void missingInputs() {
    calci.input('1');
    calci.input('2');
    calci.input('+');
    calci.input('=');
  }

  @Test (expected = IllegalArgumentException.class)
  public void testMissingSecondOperand() {
    calci.input('1');
    calci.input('2');
    calci.input('+');
    calci.input('=');
  }

  @Test (expected = IllegalArgumentException.class)
  public void operandOverFlow() {
    calci.input('1');
    calci.input('0');
    calci.input('+');
    calci.input('2');
    calci.input('2');
    calci.input('2');
    calci.input('2');
    calci.input('2');
    calci.input('2');
    calci.input('2');
    calci.input('2');
    calci.input('2');
    calci.input('2'); //Digits overflow, throws an exception.
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOperandOverflow() {
    calci.input('2');
    calci.input('1');
    calci.input('4');
    calci.input('7');
    calci.input('4');
    calci.input('8');
    calci.input('3');
    calci.input('6');
    calci.input('4');
    calci.input('7');
    calci.input('1');
    calci.input('1');
    calci.input('1');
  }

  @Test (expected = IllegalArgumentException.class)
  public void testOperandRetain() {
    calci.input('2');
    calci.input('1');
    calci.input('4');
    calci.input('7');
    calci.input('4');
    calci.input('8');
    calci.input('3');
    calci.input('6');
    calci.input('4');
    calci.input('7');
    calci.input('1');
    assertEquals("2147483647",calci.getResult()); //operand before overflow is retained.
  }

  @Test (expected = IllegalArgumentException.class)
  public void testNegativeInputs() {
    calci.input('-');
    calci.input('1');
    calci.input('+');
    calci.input('1');
  }

  @Test
  public void testSequence() {
    calci.input('1');
    calci.input('0');
    calci.input('+');
    calci.input('2');
    calci.input('-');
    calci.input('3');
    calci.input('=');
    assertEquals("9", calci.getResult());
  }

  @Test
  public void ValidOperatorsAndOperands() {
    assertEquals("",calci.getResult());
    calci.input('1');
    calci.input('2');
    calci.input('+');
    calci.input('2');
    calci.input('3');
    calci.input('=');
    assertEquals("35", calci.getResult());

    calci.input('=');
    assertEquals("35",calci.getResult());

    calci.input('*');
    calci.input('1');
    assertEquals("35*1",calci.getResult());
    calci.input('=');
    assertEquals("35",calci.getResult());
    calci.input('=');
    assertEquals("35",calci.getResult());
    calci.input('=');
    assertEquals("35",calci.getResult());
    calci.input('=');
    assertEquals("35",calci.getResult());
    calci.input('C');
    assertEquals("",calci.getResult()); // Gives out a empty string after a C.
    calci.input('1');
    assertEquals("1",calci.getResult());
    calci.input('0');
    assertEquals("10",calci.getResult());
    calci.input('-');
    assertEquals("10-",calci.getResult());
    calci.input('1');
    assertEquals("10-1",calci.getResult());
    calci.input('2');
    assertEquals("10-12",calci.getResult());
    calci.input('=');
    assertEquals("-2",calci.getResult()); // Can store a negative number.
    calci.input('=');
    calci.input('C');
    assertEquals("",calci.getResult());
    calci.input('2');
    calci.input('1');
    calci.input('4');
    calci.input('7');
    calci.input('4');
    calci.input('8');
    calci.input('3');
    calci.input('6');
    calci.input('4');
    calci.input('7');
    calci.input('+');
    calci.input('1');
    assertEquals("2147483647+1",calci.getResult());
    calci.input('=');
    assertEquals("0",calci.getResult()); //result is 0 if the result of arithmetic overflow
    calci.input('+');
    calci.input('2');
    calci.input('=');
    assertEquals("2",calci.getResult());
    calci.input('5');
    assertEquals("5",calci.getResult()); //number 2 getting replaced by number 5
  }

  @Test
  public void getResult() {
    calci.input('1');
    assertEquals("1",calci.getResult());
    calci.input('2');
    assertEquals("12",calci.getResult());
    calci.input('+');
    assertEquals("12+",calci.getResult());
    calci.input('2');
    assertEquals("12+2",calci.getResult());
  }
}
