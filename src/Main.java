import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        String expression = scanner.nextLine();
        try{
            Calculator calculator = new Calculator(expression);
            System.out.println(calculator.run());
            System.out.println(calculator.showPostExp());
        }
        catch(InvalidExpressionException e){
            e.printStackTrace();
        }
        main(args);
    }
}
