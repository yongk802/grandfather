package com.yong.grandfather;

public class Main {
    public static void main(String[] args) {
        C c = new C();
        // let's call this instance of c's doThat() method,
        // which is really calling A's doThat() method through reflection
        c.doThat();
    }
}
