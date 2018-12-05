package tests.second;

import annotations.After;
import annotations.Before;
import annotations.Test;

public class MySecondTest {

    public MySecondTest() {
        System.out.println("Call of the constructor  " + this.getClass().getSimpleName());
    }

    @Before
    public void before1(){
        System.out.println("Before1 " + this.getClass().getSimpleName());
    }

    @Before
    public void before2(){
        System.out.println("Before2 " + this.getClass().getSimpleName());
    }

    @Test
    public void testOne(){
        System.out.println("testOne " + this.getClass().getSimpleName());
    }

    @Test
    public void testTwo(){
        System.out.println("testTwo " + this.getClass().getSimpleName());
    }

    @After
    public void after(){
        System.out.println("After " + this.getClass().getSimpleName() + "\n----------------");
    }

}
