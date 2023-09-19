public class RunningProgram {
    public void running() {
        try {
            Thread.sleep(3 * 1000);
            System.out.println("this is running program");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
        RunningProgram runningProgram = new RunningProgram();
        while (true) {
            runningProgram.running();
        }
    }
}
