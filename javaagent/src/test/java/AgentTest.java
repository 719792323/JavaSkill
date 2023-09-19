public class AgentTest {
    public void foo() {
        System.out.println("bar");
    }

    public static void main(String[] args) {
       while (true){
           System.out.println("test");
           new AgentTest().foo();
           try {
               Thread.sleep(3000);
           } catch (InterruptedException e) {
               throw new RuntimeException(e);
           }
       }
    }
}
