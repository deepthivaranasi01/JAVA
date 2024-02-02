/**
 * This package helps in storing of the interface, classes - Simple and Smart Calculator.
 */

package calculator;

/**
 * The interface Calculator has two methods - input method of return type calculator and getResult
 method of return type string.
 */

public interface Calculator {
  /**
   * The input method takes in a character and performs operations on it.
   * @param ch is the character input that is being taken by the method
   * @return is a Calculator object.
   * @throws IllegalArgumentException when invalid arguments are given as inputs.
   */
  public Calculator input(char ch) throws IllegalArgumentException;

  /**
   *method will give out the current status of the expression.
   *@return the expression that is currently present.
   */
  public String getResult();
}
