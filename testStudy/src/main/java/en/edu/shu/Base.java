package en.edu.shu;

/**
 * Created by jxxiangwen on 16-11-6.
 */
class FirstException extends Exception{}
class SecondException extends Exception{}
public class Base {
    public Base() throws FirstException{

    }
    private void f(){
        System.out.println("Base f()");
    }
}
