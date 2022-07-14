package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE

  @Test
  public void testAddTestRemove(){
      AListNoResizing<Integer> alist = new AListNoResizing<>();
      BuggyAList<Integer> blist = new BuggyAList<>();
      alist.addLast(4);
      blist.addLast(4);
      alist.addLast(5);
      blist.addLast(5);
      assertEquals(alist.removeLast(),blist.removeLast());
  }

  @Test
    public void testRandom(){
      BuggyAList<Integer> L = new BuggyAList<>();

      int N = 1000;
      for (int i = 0; i < N; i += 1) {
          int operationNumber = StdRandom.uniform(0, 2);
          if (operationNumber == 0) {
              // addLast
              int randVal = StdRandom.uniform(0, 100);
              L.addLast(randVal);
              System.out.println("addLast(" + randVal + ")");
          } else if (operationNumber == 1) {
              // size
              int size = L.size();
              System.out.println("size: " + size);
          }
      }
  }
}
