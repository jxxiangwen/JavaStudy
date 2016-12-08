package en.edu.shu;

/**
 * Created by jxxiangwen on 16-11-6.
 */
public class BaseSub extends Base {
    public void f() {
        System.out.println("Base f()");
    }

    public BaseSub() throws FirstException,SecondException {
    }

    class Test {
        class Testa {

        }
    }

    public static void main(String[] args) throws FirstException,SecondException{
        Base base = new BaseSub();
//        base.f();
    }
}