# Calculator
## What
This is simple calculator, written in Java.   
It calculate input expression.   
ex) 1 + 2 * (3 / 4 + 5.7)^2 = 84.205

**Operators**
   * <code>+</code> Addition
   * <code>-</code> Subtraction
   * <code>*</code> Multiplication
   * <code>/</code> Division
   * <code>^ or **</code> Exponent
   * <code>%</code> Modulus

***Operator Priority***
   1. Brackets <code>() {} []</code>   
      * *This support all kinds of bracket*
      * *All kinds of bracket play the same role*
         * [(1 + 2) * {3 + 4}]^5 <=> ((1+2)*(3+4))^5
  2. Exponent <code>** or ^</code>   
  3. Multiplication <code>*</code> Division <code>/</code>   
  3. Addition <code>+</code> Subtraction <code>-</code>
  4. Modulus <code>%</code>
  
  ## Characteristics
  * Bracket error detection   
    This detect the bracket matching error   
       ex) (1 + 2 => __ERROR__   
       ex) (1 + [2 - 3)] => __ERROR__
