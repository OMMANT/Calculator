public class InvalidExpressionException extends Exception{
    public InvalidExpressionException(){}
    public InvalidExpressionException(String msg){
        super(msg + "\nPlease Check the expression again!");
    }
}