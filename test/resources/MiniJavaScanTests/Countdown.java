public class Countdown {
    public static void main(String[] args) {
        printCountdown(5);
    }

    public void printCountdown(int n) {
        int count = 0;
        while(count < n) {
            System.out.println(count);
            count = count + 1;
        }
        //Comment should be ignored, display should be identifier
        int display = 5;
        /*
        *** this comment should also be ignored
        // print should be identifier token as well
         */

        int print = -1;
    }
}