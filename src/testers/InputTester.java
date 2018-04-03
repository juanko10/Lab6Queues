package testers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Scanner;

import queue.Queue;
import queue.SLLQueue;

public class InputTester {

	public static void main(String[] args) throws NumberFormatException, IOException {
		
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
}
