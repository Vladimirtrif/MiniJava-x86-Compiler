class TestPrint {
    public static void main(String[] a){
        System.out.println((new BasicTests()).runTests(17));
    }
}

class BasicTests {
    public int runTests(int n) {
        System.out.println(n);
        return 0;
    }
}
