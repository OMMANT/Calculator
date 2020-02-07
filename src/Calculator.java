import java.util.*;

public class Calculator{
    private String exp;
    private Queue<Datum> postExp;
    private static final Dictionary<Character, Integer> priority = new Hashtable();
    private static final int NUMBER_OF_BRACKET = 3;

    static{
        priority.put('(', 6);
        priority.put('{', 5);
        priority.put('[', 4);
        priority.put('*', 1);
        priority.put('/', 1);
        priority.put('%', 1);
        priority.put('^', 2);
        priority.put('+', 3);
        priority.put('-', 3);
    }

    public Calculator(String expression){
        exp = expression;
    }

    public static String run(String s){
        Calculator calculator = new Calculator(s);
        String rString = null;
        try{
            rString = calculator.run();
        }
        catch(InvalidExpressionException e){
            e.printStackTrace();
            return null;
        }
        return rString;
    }

    public String run() throws InvalidExpressionException{
        if(exp.equals(""))
            throw new InvalidExpressionException("This expression is empty!");
        //refine input string
        exp = exp.strip().replace(" ", "");
        //Check the expression has valid bracket match
        if(isValidBracket()){
            makePostorder();
            Stack<Datum> numberStack = new Stack();
            if(postExp.isEmpty())
                throw new InvalidExpressionException("Invalid expression! Nothing to calculate!");
            while(!postExp.isEmpty()){
                Datum datum = postExp.remove();
                if(datum.isOperator){
                    double[] operand = new double[2];
                    for(int i = 0; i < operand.length; i++){
                        if(numberStack.empty())
                            throw new InvalidExpressionException("Invalid expression! number need " + i);
                        operand[i] = numberStack.pop().data;
                    }
                    switch((char)datum.data){
                        case '+':
                            numberStack.push(new Datum(operand[1] + operand[0]));
                            break;
                        case '-':
                            numberStack.push(new Datum(operand[1] - operand[0]));
                            break;
                        case '*':
                            numberStack.push(new Datum(operand[1] * operand[0]));
                            break;
                        case '/':
                            numberStack.push(new Datum(operand[1] / operand[0]));
                            break;
                        case '^':
                            numberStack.push(new Datum(Math.pow(operand[1], operand[0])));
                            break;
                        default:
                            throw new InvalidExpressionException("Invalid Expression! invalid operator detected!");
                    }
                }
                else numberStack.push(datum);
            }
            double answer = numberStack.pop().data;
            if(numberStack.isEmpty())
                return answer + "";
            else throw new InvalidExpressionException("Invalid Expression! not enough operator");
        }
        throw new InvalidExpressionException("Invalid Bracket!");
    }

    private boolean isValidBracket(){
        char[] lBrackets = {'(', '{', '['}, rBrackets = {')', '}', ']'};
        int[] lCount = new int[3], rCount = new int[3];
        for(char c : exp.toCharArray()){
            for(int i = 0; i < NUMBER_OF_BRACKET; i++){
                if(c == lBrackets[i])
                    lCount[i]++;
                else if(c == rBrackets[i])
                    rCount[i]++;
            }
        }
        for(int i = 0; i < NUMBER_OF_BRACKET; i++)
            if(lCount[i] != rCount[i])
                return false;
        return true;
    }
    private void makePostorder() throws InvalidExpressionException{
        char[] expression = exp.toCharArray();
        postExp = new LinkedList<Datum>();
        Stack<Datum> operStack = new Stack<Datum>();
        //Do the job sequencially
        for(int i = 0; i < expression.length; i++){
            String temp = "";
            //push if it is number
            if(isNumber(expression[i])){
                while(i < expression.length && isNumber(expression[i]))
                    temp += expression[i++];
                i--;
                try{
                    postExp.add(new Datum(Double.parseDouble(temp)));
                }catch(java.lang.NumberFormatException e){
                    throw new InvalidExpressionException("Invalid number input");
                }
            }
            //push conditionally
            else{
                if(expression[i] == '(' || expression[i] == '{' || expression[i] == '[')
                    operStack.push(new Datum(expression[i]));
                else if(expression[i] == ')'){
                    while(!(operStack.peek().data == '(' || operStack.empty()))
                        postExp.add(operStack.pop());
                    operStack.pop();
                }
                else if (expression[i] == '}') {
                    while (!(operStack.peek().data == '{' || operStack.empty()))
                        postExp.add(operStack.pop());
                    operStack.pop();
                }
                else if (expression[i] == ']') {
                    while (!(operStack.peek().data == '[' || operStack.empty()))
                        postExp.add(operStack.pop());
                    operStack.pop();
                }
                else{
                    while (!operStack.empty() && priority.get((char)operStack.peek().data) <= priority.get(expression[i]))
                        postExp.add(operStack.pop());
                    operStack.push(new Datum(expression[i]));
                }
            }
        }
        //Pop remains
        while(!operStack.empty())
            postExp.add(operStack.pop());
    }
    private boolean isNumber(char c){
        if('0' <= c && c <= '9')
            return true;
        else if(c == '.')
            return true;
        else return false;
    }
    public String showPostExp(){
        String str = "";
        try {
            makePostorder();
        }catch(Exception e){
            ;
        }
        Iterator<Datum> iterator = postExp.iterator();
        while(iterator.hasNext()){
            Datum datum = iterator.next();
            if(datum.isOperator)
                str += (char)datum.data + " ";
            else
                str += datum.data + " ";
        }
        return str;
    }

    private class Datum{
        double data;
        boolean isOperator;
        public Datum(double d){
            data = d;
            isOperator = false;
        }
        public Datum(int i){
            data = i;
            isOperator = false;
        }
        public Datum(char c){
            data = (int)c;
            isOperator = true;
        }
    }
}