package HW_7;

import HW_7.interfaces.AfterSuite;
import HW_7.interfaces.BeforeSuite;
import HW_7.interfaces.Test;

public class SomethingTest {

    @BeforeSuite
    public void beforeSuite() {
        System.out.println("Before Suite");
    }

    @Test(priority = 3)
    public void test3() {
        System.out.println("Test 3");
    }

    @Test(priority = 1)
    public void test1() {
        System.out.println("Test 1");
    }

    @Test(priority = 2)
    public void test2A() {
        System.out.println("Test 2A");
    }

    @Test(priority = 2)
    public void test2B() {
        System.out.println("Test 2B");
    }

    @AfterSuite
    public void afterSuite() {
        System.out.println("After Suite");
    }
}
