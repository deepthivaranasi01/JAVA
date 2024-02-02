package calculator;

import java.util.EmptyStackException;
import java.util.Stack;
import java.util.Arrays;

/**
 * SimpleCalculator implements the Calculator interface, and it has the methods input and getResult,
 in order to take the input and return the current string.
 */

public class SimpleCalculator implements Calculator {
  // Using protected  because, SmartCalculator extends the SimpleCalculator and uses the variables
  protected Stack<Character> expression;
  protected long lastOperand;
  protected char lastOperation;
  protected boolean alreadyEnteredEqual;
  protected boolean simpleAlreadyEnteredEqual;
  protected boolean smartSecondOperation;
  private boolean lastPerformedEqualOperation; //SmartCalculator doesn't use them hence private.
  private String operandStr; //SmartCalculator doesn't use them hence private.

  /**
   * The constructor SimpleCalculator helps in initializing the variables - expression, lastoperand
   * lastoperation, alreadyEnteredEqual, simpleAlreadyEnteredEqual, smartSecondOperation,
   * lastPerformedEqualOperation and operandstr.
   */

  public SimpleCalculator() {
    this.expression = new Stack<>();
    this.lastOperand = Long.MAX_VALUE;
    this.lastOperation = 'z';
    this.alreadyEnteredEqual = false;
    this.simpleAlreadyEnteredEqual = false;
    this.smartSecondOperation = false;
    this.lastPerformedEqualOperation = false;
    this.operandStr = "";
  }

  //SmartCalculator overrides the SimpleCalculator methods and hence the methods are protected.
  protected void equalOperation(Character ch) {
    if (isItFirstElement(ch)) {
      throw new IllegalArgumentException("First element can't be operator");
    }
    int pos1 = (Integer) expression.search('+');
    int pos2 = (Integer) expression.search('-');
    int pos3 = (Integer) expression.search('*');
    if (pos1  != -1 || pos2 != -1 || pos3 != -1) {
      performOperation();
    }
  }

  protected boolean isItFirstElement(char ch) {
    return expression.empty();
  }

  protected boolean isTopOperand(char ch) {
    char peek;

    try {
      peek = expression.peek();
    }
    catch (EmptyStackException e) {
      return false;
    }
    return (peek == '+' || peek == '-' || peek == '*');
  }

  //performOperation is used by SmartCalculator hence it is protected.
  protected boolean performOperation() {
    StringBuilder mathExpression = new StringBuilder();
    long  stackSize = expression.size();
    for (int i = 0 ; i < stackSize ; i++) {
      char c = expression.pop();
      mathExpression = mathExpression.append(c);
    }
    mathExpression = mathExpression.reverse();
    String correctedMathExpression = mathExpression.toString();

    if (correctedMathExpression.contains("+") && correctedMathExpression.lastIndexOf('+') > 0) {
      long total;
      String operandLString = (correctedMathExpression.substring(0,correctedMathExpression
              .lastIndexOf('+')));
      String operandRString = (correctedMathExpression.substring(correctedMathExpression
              .lastIndexOf('+') + 1));
      long operandL = Long.parseLong(operandLString);
      long operandR = Long.parseLong(operandRString);
      total = operandL + operandR;
      comparision(total, operandR);
      lastOperation = '+';
    }
    else if (correctedMathExpression.contains("-")
            && correctedMathExpression.lastIndexOf('-') > 0) {
      long total;
      String operandLString = (correctedMathExpression
               .substring(0,correctedMathExpression.lastIndexOf('-')));
      String operandRString = (correctedMathExpression.substring(correctedMathExpression
                 .lastIndexOf('-') + 1));
      long operandL = Long.parseLong(operandLString);
      long operandR = Long.parseLong(operandRString);
      total = operandL - operandR;
      comparision(total, operandR);
      lastOperation = '-';
    }
    else if (correctedMathExpression.contains("*")) {
      String operandLString = (correctedMathExpression
                 .substring( 0 , correctedMathExpression.lastIndexOf('*')));
      String operandRString = (correctedMathExpression.substring(correctedMathExpression
                 .lastIndexOf('*') + 1 ));
      long operandL = Long.parseLong(operandLString);
      long operandR = Long.parseLong(operandRString);
      long total;
      total = operandL * operandR;
      comparision(total, operandR);
      lastOperation = '*';
    }
    return false;
  }

  private void comparision(long total, long operandR) {
    if ( total > Integer.MAX_VALUE || total < Integer.MIN_VALUE ) {
      total = 0;
    }
    String totalString = Long.toString(total);
    for ( int i = 0 ; i < totalString.length() ; i++) {
      expression.push(totalString.charAt(i));
    }
    lastOperand = operandR;
  }

  private void processDigit(char ch) {

    if (lastPerformedEqualOperation) {
      lastPerformedEqualOperation = false;
      expression.clear();
      lastOperand = 'z';
      lastOperation = 'z';
      alreadyEnteredEqual = false;
      this.operandStr = "";
      simpleAlreadyEnteredEqual = false;
    }

    // handle digit case
    if (this.alreadyEnteredEqual) {
      lastOperand = 'z';
      lastOperation = 'z';
      alreadyEnteredEqual = false;
      this.operandStr = "";
    }
    try {
      Integer isOverflow = Integer.parseInt(this.operandStr + ch);
      this.operandStr = this.operandStr + ch;
    }
    catch (Exception numberFormatException) {
      throw new IllegalArgumentException("Digits Overflow");
    }

    expression.push(ch);
  }
  /**
   * The input method takes in a character and performs operations on it.
   * @param ch is the character input that is being taken by the method
   * @return is a Calculator object.
   * @throws IllegalArgumentException when invalid arguments are given as inputs.
   */

  @Override
  public Calculator input(char ch) throws IllegalArgumentException {

    switch (ch) {
      case '-' :
      case '+':
      case '*':

        lastPerformedEqualOperation = false;
        if (simpleAlreadyEnteredEqual) {
          break;
        }
        if (isItFirstElement(ch)) {
          throw new IllegalArgumentException("First element can't be operator");
        }
        if (isTopOperand(ch) ) {
          if (this instanceof SmartCalculator) {
            expression.pop();
            expression.push(ch);
          }
          else {
            throw new IllegalArgumentException("Simultaneous operators are not allowed");
          }
          break;
        }
        //2,1+2,3
        int pos1 = (Integer) expression.search('+');
        int pos2 = (Integer) expression.search('-');
        int pos3 = (Integer) expression.search('*');

        String currentStack = this.getResult();
        pos1 = (Integer) currentStack.lastIndexOf('+');
        pos2 = (Integer) currentStack.lastIndexOf('-');
        pos3 = (Integer) currentStack.lastIndexOf('*');
        if (smartSecondOperation) {
          break;
        }
        if (pos1 == -1 && pos2 == -1 && pos3 == -1 ) {
          currentStack = this.getResult();
        }
        else {
          currentStack = this.getResult();
          if (Character.isDigit(expression.peek())) {
            if (currentStack.charAt(0) != '-'
                      && currentStack.charAt(0) != '+') {
              if ( pos1 > 0 || pos2 > 0) {
                performOperation();
              }

            }
          }
        }
        expression.push(ch);
        this.operandStr = "";
        break;
      case '=':
        if (isItFirstElement(ch)) {
          throw new IllegalArgumentException("First element can't be '=' operator");
        }

        if ((isTopOperand('+') || isTopOperand('-') || isTopOperand('*'))
                  && (this instanceof SmartCalculator) && (!smartSecondOperation )) {
          equalOperation(ch);
          smartSecondOperation = false;
          break;
        }
        else if ( (isTopOperand('+') || isTopOperand('-') || isTopOperand('*'))
                  && !(this instanceof SmartCalculator)) {
          throw new IllegalArgumentException("Second Operand missing");

        }

        currentStack = this.getResult();
        pos1 = (Integer) currentStack.lastIndexOf('+');
        pos2 = (Integer) currentStack.lastIndexOf('-');
        pos3 = (Integer) currentStack.lastIndexOf('*');

        if (currentStack.charAt(0) != '-'
                && currentStack.charAt(0) != '+') {
          if (Character.isDigit(expression.peek())) {
            equalOperation(ch);
          }
        }
        else if (currentStack.charAt(0) == '-'
                || currentStack.charAt(0) == '+') {

          if (Character.isDigit(expression.peek()) && pos1 != -1 ) {

            if (pos1 > 0 || alreadyEnteredEqual) {
              equalOperation(ch);
            }
          }
          if (Character.isDigit(expression.peek()) && pos2 != -1 ) {

            if (pos2 > 0 || alreadyEnteredEqual) {
              equalOperation(ch);
            }
          }
          if (Character.isDigit(expression.peek()) && pos3 != -1 ) {

            if (pos3 > 0 || alreadyEnteredEqual) {
              equalOperation(ch);
            }
          }
        }

        this.operandStr = "";
        if (this instanceof SimpleCalculator) {
          simpleAlreadyEnteredEqual = false;
          lastPerformedEqualOperation = true;
        }
        break;
      case '0':
        processDigit('0');
        break;
      case '1':
        processDigit('1');
        break;
      case '2':
        processDigit('2');
        break;
      case '3':
        processDigit('3');
        break;
      case '4':
        processDigit('4');
        break;
      case '5':
        processDigit('5');
        break;
      case '6':
        processDigit('6');
        break;
      case '7':
        processDigit('7');
        break;
      case '8':
        processDigit('8');
        break;
      case '9':
        processDigit('9');
        break;
      case 'C':
        expression.clear();
        lastOperand = 'z';
        lastOperation = 'z';
        alreadyEnteredEqual = false;
        this.operandStr = "";
        simpleAlreadyEnteredEqual = false;
        break;
      default:
        throw new IllegalArgumentException("Invalid inputs");

    }
    return (Calculator) this;
  }

  /**
   *method will give out the current status of the expression.
   *@return the expression that is currently present.
   */
  @Override
  public String getResult() {
    String currentStack = Arrays.toString(expression.toArray());
    return currentStack.replace('[',' ').replace(']',' ')
            .replace(',',' ').replaceAll("\\s", "");
  }

}
