package deque;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {
    @Test
    public void addAndGet(){
        ArrayDeque<Integer> testArrayDeque = new ArrayDeque<>();
        for(int i = 0;i < 8;i++){
            testArrayDeque.addLast(i);
        }
        assertEquals(0,(int)testArrayDeque.get(0));
    }
}
