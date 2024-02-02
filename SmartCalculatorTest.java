package calculator;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

/**
 * This class helps us in testing different cases of SmartCalculator.
 */
public class SmartCalculatorTest {
  Calculator calci = new SmartCalculator();

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
    calci.input('1'); //Digits overflow, throws an exception.
    calci.input('1');
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIllegalFirstElement() {
    calci.input('-');
    calci.input('*');
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

  @Test
  public void testMissingSecondOperand() {
    calci.input('1');
    calci.input('2');
    calci.input('+');
    calci.input('=');
    assertEquals("24", calci.getResult());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testIllegalOperandsAndOperators() {
    calci.input('#');
    calci.input('&');
    calci.input('$');
    calci.input('^');
  }

  @Test
  public void testIgnorePlusAsFirst() {
    calci.input('C');
    calci.input('+');
    calci.input('1');
    calci.input('+');
    calci.input('1');
    calci.input('=');
    assertEquals("2", calci.getResult());
  }

  @Test
  public void testMultipleEquals() {
    calci.input('C');
    calci.input('+');
    calci.input('1');
    calci.input('+');
    calci.input('1');
    calci.input('=');
    assertEquals("2", calci.getResult());
    calci.input('=');
    calci.input('=');
    assertEquals("4", calci.getResult());
    calci.input('*');
    calci.input('2');
    calci.input('=');
    assertEquals("8", calci.getResult());
    calci.input('=');
    calci.input('=');
    assertEquals("32", calci.getResult());
    calci.input('-');
    calci.input('2');
    calci.input('=');
    assertEquals("30", calci.getResult());
    calci.input('=');
    calci.input('=');
    assertEquals("26", calci.getResult());
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
  public void testSimultaneousOperators() {
    calci.input('1');
    calci.input('1');
    calci.input('+');
    calci.input('-');
    calci.input('2');
    calci.input('=');
    assertEquals("9", calci.getResult());
  }

  @Test
  public void testSimultaneousOperators1() {
    calci.input('1');
    calci.input('1');
    calci.input('+');
    calci.input('-');
    calci.input('*');
    calci.input('2');
    calci.input('=');
    assertEquals("22", calci.getResult());
  }

  @Test
  public void validInputs() {
    assertEquals("",calci.getResult());
    calci.input('1');
    calci.input('2');
    calci.input('+');
    calci.input('2');
    calci.input('3');
    calci.input('=');
    assertEquals("35", calci.getResult());

    calci.input('-');
    calci.input('9');
    assertEquals("35-9",calci.getResult());

    calci.input('=');
    assertEquals("26",calci.getResult());

    calci.input('*');
    calci.input('1');
    assertEquals("26*1",calci.getResult());
    calci.input('=');
    assertEquals("26",calci.getResult());
    calci.input('=');
    assertEquals("26",calci.getResult());
    calci.input('=');
    assertEquals("26",calci.getResult());
    calci.input('=');
    assertEquals("26",calci.getResult());
    calci.input('C');
    assertEquals("",calci.getResult()); // Gives out an empty string after a C.
    calci.input('2');
    calci.input('+');
    calci.input('2');
    calci.input('=');
    calci.input('=');
    calci.input('=');
    // returns 8 after two consecutive =='s (Inputting == with + operator)
    assertEquals("8",calci.getResult());
    calci.input('C');
    calci.input('2');
    calci.input('*');
    calci.input('2');
    calci.input('=');
    calci.input('=');
    calci.input('=');
    // returns 16 after two consecutive =='s (Inputting == with * operator)
    assertEquals("16",calci.getResult());
    calci.input('C');
    calci.input('9');
    calci.input('-');
    calci.input('2');
    calci.input('=');
    calci.input('=');
    calci.input('=');
    // returns 3 after two consecutive =='s (Inputting == with - operator)
    assertEquals("3",calci.getResult());
    calci.input('C');
    calci.input('2');
    calci.input('+');
    calci.input('=');
    // returns 4 after 2+= operation
    assertEquals("4",calci.getResult());
    calci.input('C');
    calci.input('9');
    calci.input('-');
    calci.input('=');
    // returns 0 after 9-= operation
    assertEquals("0",calci.getResult());
    calci.input('C');
    calci.input('3');
    calci.input('*');
    calci.input('=');
    calci.input('=');
    // returns 27 after 3*== operation
    assertEquals("27",calci.getResult());
    calci.input('C');
    calci.input('4');
    calci.input('+');
    calci.input('-');
    calci.input('2');
    calci.input('=');
    // returns 2 after 4+-2, it ignores the first operator and retains the second
    assertEquals("2",calci.getResult());
    calci.input('C');
    calci.input('+');
    calci.input('4');
    calci.input('-');
    calci.input('2');
    calci.input('=');
    // In this case (+4-2=) '+' is ignored. (First operator is ignored).
    assertEquals("2",calci.getResult());
    calci.input('C');
    calci.input('2');
    calci.input('-');
    calci.input('*');
    calci.input('2');
    calci.input('=');
    // In this case (2-*2=) '-' is ignored. (First operator is ignored).
    assertEquals("4",calci.getResult());
    calci.input('C');
    calci.input('2');
    calci.input('*');
    calci.input('+');
    calci.input('2');
    calci.input('=');
    calci.input('=');
    calci.input('=');
    calci.input('=');
    // In this case (2*+2=) '*' is ignored. (First operator is ignored).
    assertEquals("10",calci.getResult());
    calci.input('C');
    calci.input('2');
    calci.input('-');
    calci.input('+');
    calci.input('2');
    calci.input('=');
    // In this case (2-+2=) '-' is ignored. (First operator is ignored).
    assertEquals("4",calci.getResult());
    calci.input('C');
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
    // Expression -> 10-12=-2=-14 (-12+-2=-14)
    assertEquals("-14",calci.getResult()); // Can store a negative number.
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
    calci.input('-');
    calci.input('2');
    calci.input('=');
    assertEquals("-2",calci.getResult()); // After overflow, it's 0, and it becomes 0-2=-2
    calci.input('5');
    assertEquals("5",calci.getResult()); //number -2 getting replaced by number 5
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


