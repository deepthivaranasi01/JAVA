package calculator;

import java.util.EmptyStackException;

/**
 *The smartCalculator class extends the SimpleCalculator and it overrides  functionalities of Simple
 calculator.
 */
public class SmartCalculator extends SimpleCalculator {

  @Override
    protected boolean isItFirstElement(char ch) {

    return (expression.empty() && ch != '+');
  }

  @Override
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

  @Override
    protected void equalOperation(Character ch) {
    this.simpleAlreadyEnteredEqual = false;
    if (isItFirstElement(ch)) {
      throw new IllegalArgumentException("First element can't be operator");
    }
    if (this.alreadyEnteredEqual) {
      if (this.lastOperation != 'z') {
        this.input(this.lastOperation);
      }

      if (this.lastOperand != Long.MAX_VALUE) {
        String lastOperandString = Long.toString(this.lastOperand);
        for (int i = 0; i < lastOperandString.length(); i++) {
          expression.push(lastOperandString.charAt(i));
        }
      }
    }
    String currentStack = this.getResult();
    String topOperator = currentStack.substring(currentStack.length() - 1);
    if ( topOperator.equals("+") ) {
      String operandLString = (currentStack.substring(0,currentStack.lastIndexOf('+')));
      for (int i = 0; i < operandLString.length() ; i++) {
        expression.push(operandLString.charAt(i));
      }
      this.smartSecondOperation = true;
    }
    else if ( topOperator.equals("-") ) {
      String operandLString = (currentStack.substring(0,currentStack.lastIndexOf('-')));
      for (int i = 0; i < operandLString.length(); i++) {
        expression.push(operandLString.charAt(i));
      }
      this.smartSecondOperation = true;
    }
    else if ( topOperator.equals("*") ) {
      String operandLString = (currentStack.substring(0,currentStack.lastIndexOf('*')));
      for (int i = 0; i < operandLString.length();i++) {
        expression.push(operandLString.charAt(i));
      }
      this.smartSecondOperation = true;
    }
    currentStack = this.getResult();
    int pos1 = (Integer) expression.search('+');
    int pos2 = (Integer) expression.search('-');
    int pos3 = (Integer) expression.search('*');
    if ( (pos1 != -1 || pos2 != -1 || pos3 != -1) ) {
      performOperation();

    }
    this.alreadyEnteredEqual = true;
  }

}
