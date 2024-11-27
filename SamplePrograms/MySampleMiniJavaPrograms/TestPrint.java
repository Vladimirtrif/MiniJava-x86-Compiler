class TestPrint {
    public static void main(String[] a){
        System.out.println((new BasicTests()).runTests());
    }
}

class BasicTests {

    public int runTests() {
        System.out.println(10);
        System.out.println(11 + 9);
        System.out.println(40 - 10);
        System.out.println(25 * 2);
        System.out.println(true);
        return 0;
    }

}
