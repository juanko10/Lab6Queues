import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

public class main {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//                                              Exercise 1 Tester                                           //
//////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    public class QueueTester {
//        public  void main(String Args[]){
//
//            ArrayQueue<Integer> QA1 = new ArrayQueue<>();
//            Queue<Integer> QA = QA1;
//
//            SLLQueue<Integer> QS1 = new SLLQueue<>();
//            Queue<Integer> QS = QS1;
//
//
//            testQ(QA);
//            testQ(QS);
//
//        }
//
//        public  <E> void testQ(Queue<Integer> s){
//            System.out.println("Testing Queue:");
//            Integer[] a = { 3, 2, 4, 55, 5, 1, 1, 17, 9, 3, 0, 4, 5, 7, 17, 28, 0, 9, 10, 40, 21, 22, 5, 6, -1};
//            for (int n : a) {
//                try {
//                    if (n == 0)
//                        s.showReverse();
//                    else if (n % 2 == 1)
//                        s.enqueue(n);
//                    else
//                        System.out.println("Extracting element: " + s.dequeue());
//                }
//                catch (Exception e) {
//                    System.out.println(e);
//                }
//            }
//            s.showReverse();
//            System.out.println("Testing Ended");
//        }
//    }
//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public class Jobs {
        private int pid;            // id of this job
        private int arrivalTime;    // arrival time of this job
        private int remainingTime;  // remaining service time for this job
        private int departureTime;  // time when the service for this job is completed
        public Jobs(int id, int at, int rt) {
            pid = id;
            arrivalTime = at;
            remainingTime = rt;
        }
        public int getDepartureTime() {
            return departureTime;
        }
        public void setDepartureTime(int departureTime) {
            this.departureTime = departureTime;
        }
        public int getPid() {
            return pid;
        }
        public int getArrivalTime() {
            return arrivalTime;
        }
        public int getRemainingTime() {
            return remainingTime;
        }

        /**
         * Registers an update of serviced received by this job.
         * @param q the time of the service being registered.
         */
        public void isServed(int q) {
            if (q < 0) return;   // only register positive value of q
            remainingTime -= q;
        }

        /**
         * Generates a string that describes this job; useful for printing
         * information about the job.
         */
        public String toString() {
            return "PID = " + pid +
                    "  Arrival Time = " + arrivalTime +
                    "  Remaining Time = " + remainingTime +
                    "  Departure Time = " + departureTime;
        }
    }

//    public class InputTester {

    public void main(String[] args) throws NumberFormatException, IOException {

            int t = 0, depTime = 0, arrival = 0;
            double averageTime, value;
            String[] split = new String[2];
            SLLQueue<Jobs> QS1 = new SLLQueue<>();
            Queue<Jobs> inputQueue = QS1;
            SLLQueue<Jobs> QS2 = new SLLQueue<>();
            Queue<Jobs> processingQueue = QS2;
            ArrayList<Jobs> terminatedJobs = new ArrayList<Jobs>();
            BufferedReader reader = null;
            String line = null;
            Scanner scanner = null;
            reader = new BufferedReader(new FileReader("input6.csv"));
            while((line = reader.readLine()) != null) {
                scanner = new Scanner(line);
                while(scanner.hasNext()) {
                    String data = scanner.next();
                    split = data.split(",");
                    inputQueue.enqueue(new Jobs(Integer.parseInt(split[0]), Integer.parseInt(split[1]), t));
                }
            }
            while(!inputQueue.isEmpty() || !processingQueue.isEmpty()) {
                if(!processingQueue.isEmpty()) {
                    int tempA = processingQueue.first().getArrivalTime();
                    int tempD = processingQueue.first().getDepartureTime() - 1;
                    int tempR = tempD - tempA ;
                    if(tempD == 0) {
                        depTime += t;
                        terminatedJobs.add(processingQueue.dequeue());
                    }
                    else {
                        processingQueue.dequeue();
                        processingQueue.enqueue(new Jobs(tempA, tempD, tempR));
                    }
                }
                if(!inputQueue.isEmpty() && inputQueue.first().getArrivalTime() == t) {
                    arrival += inputQueue.first().getArrivalTime();
                    processingQueue.enqueue(inputQueue.dequeue());
                }
                t++;
            }
            value = (double)(depTime - arrival)/terminatedJobs.size();
            averageTime = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP).doubleValue();
            System.out.println("Average time is system: " + averageTime);
            reader.close();
            scanner.close();
        }
  //  }

    public class SLLQueue<E> implements Queue<E> {

        private Node<E> first, last;   // references to first and last node
        private int size;

        private class Node<E> {   // Inner class for nodes.
            private E element;
            private Node<E> next;
            public Node(E e) {
                this.element = e;
            }
            public E getElement() {
                return element;
            }
            public void setElement(E element) {
                this.element = element;
            }
            public Node<E> getNext() {
                return next;
            }
            public void setNext(Node<E> next) {
                this.next = next;
            }
        }

        public SLLQueue() {           // initializes instance as empty queue
            first = last = null;
            size = 0;
        }
        public int size() {
            return size;
        }
        public boolean isEmpty() {
            return size == 0;
        }
        public E first() {
            if (isEmpty()) return null;
            return first.getElement();
        }
        public E dequeue() {
            if (isEmpty()) return null;
            E temp = first.getElement();
            first = first.getNext();
            size--;
            return temp;
        }
        public void enqueue(E e) {
            if (size == 0)
                first = last = new Node<E>(e);
            else {
                Node<E> temp = new Node<E>(e);
                last.setNext(temp);
                last = temp;
            }
            size++;
        }



        //JUST FOR TESTING
        @Override
        public void showReverse() {
            if (size == 0)
                System.out.println("Queue is empty.");
            else
                recSR(first);
        }
        private void recSR(Node<E> f) {
            if (f != null) {
                recSR(f.getNext());
                System.out.println(f.getElement());
            }
        }

    }

    public interface Queue<E> {
        int size();
        boolean isEmpty();
        E first();
        E dequeue();
        void enqueue(E e);
        //just for testing
        void showReverse();
    }

    public  class ArrayQueue<E> implements Queue<E> {

        private final static int INITCAP = 4;
        private E[] elements;
        private int first, size;
        public ArrayQueue() {
            elements = (E[]) new Object[INITCAP];
            first = 0;
            size = 0;
        }
        public int size() {
            return size;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public E first() {
            if (isEmpty()) return null;
            return elements[first];
        }

        public E dequeue() {
            if (isEmpty()) return null;
            E etr = elements[first];
            elements[first] = null;
            first = (first+1) % elements.length;
            size--;
            // Check if number of available positions in the array exceed 3/4
            // of its total length. If so, and if the current capacity is not
            // less than 2*INITCAP, shrink the internal array to 1/2 of its
            // current length (the capacity of the queue).
            if (elements.length >= 2*INITCAP && size < elements.length/4)
                changeCapacity(elements.length/2);
            return etr;
        }

        public void enqueue(E e) {
            if (size == elements.length)   // check capacity, double it if needed
                changeCapacity(2*size);
            elements[(first+size)%elements.length] = e;
            size++;
        }

        private void changeCapacity(int newCapacity) {
            // PRE: newCapacity >= size
            E[] temp = (E[]) new Object[newCapacity];
            for (int i=0; i<size; i++) {
                int srcIndex = (first + i) % elements.length;
                temp[i] = elements[srcIndex];
                elements[srcIndex] = null;
            }
            elements = temp;
            first = 0;
        }

        //JUST FOR TESTING
        @Override
        public void showReverse() {
            if (size == 0)
                System.out.println("Queue is empty.");
            else
                recSR(first);
        }
        private void recSR(int f) {
            if (elements[f] != null) {
                recSR(f+1);
                System.out.println(elements[f]);
            }
        }
    }
}
