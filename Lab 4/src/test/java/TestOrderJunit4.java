import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

//@FixMethodOrder(MethodSorters.JVM)
//@FixMethodOrder(MethodSorters.DEFAULT)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestOrderJunit4 {

    @Test
    public void Testcase_3() {
        System.out.println("Testcase_3 executes");
    }
    @Test
    public void Testcase_1() {
        System.out.println("Testcase_1 executes");
    }
    @Test
    public void Testcase_2() {
        System.out.println("Testcase_2 executes ");
    }
}
