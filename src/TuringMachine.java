import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class TuringMachine {

	/**
	 * @param args
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {

		// read first line from input file
		// contains $Program$string, and program lines are separated by #
		File f = new File("Pw.txt");
		Scanner s = new Scanner(f);
		String inputTape = s.nextLine();

		String[] progParts = inputTape.split("\\$");
		String program = progParts[1];
		String workTape = "S" + progParts[2];
		workTape += "E";

		String[] progLines = program.split("\\#");
		System.out.println(Arrays.toString(progLines));

		String[] labels = new String[progLines.length];
		String[] lines = new String[progLines.length];
		for (int i = 0; i < progLines.length; i++) {
			String[] x = progLines[i].split(":");
			labels[i] = x[0];
			lines[i] = x[1];
		} // end for
		System.out.println(Arrays.toString(labels));
		System.out.println(Arrays.toString(lines));

		boolean test = runMachine(labels, lines, workTape);
		System.out.println(test);

	} // end main()

	/*
	 * runMachine = run universal turing machine
	 * lab = array of program line labels
	 * lin = array of program lines
	 * wT = work tape
	 */
	private static boolean runMachine(String[] lab, String[] lin, String wT) {
		
		boolean result = false;
		boolean isDone = false;
		int lineNum = 0;
		int head = 0;
		
		while (!isDone) {
			
			char type = lin[lineNum].charAt(0);
			
			switch (type) {
			
			case 'M':
				if (lin[lineNum].charAt(1) == 'R') {
					head++;
					if (head >= wT.length()) {
						wT += "-";
					} // end if
				} else if (lin[lineNum].charAt(1) == 'L') {
					head--;
				} // end if
				lineNum++;
				break;
				
			case 'W':
				if ((head > 0) && (head < wT.length() - 1)) {
					wT = wT.substring(0, head) + lin[lineNum].charAt(1)
							+ wT.substring(head + 1, wT.length());
				} else if (head < 0) {
					wT = lin[lineNum].charAt(1)
							+ wT.substring(head + 1, wT.length());
				} else if (head >= wT.length() - 1) {
					wT = wT.substring(0, head) + lin[lineNum].charAt(1);
				} // end if
				lineNum++;
				break;
				
			case 'I':
				String lbls = lin[lineNum].substring(2);
				String[] swtchLbls = lbls.split(",");
				int index = 0;
				String label = "";
				if (wT.charAt(head) == lin[lineNum].charAt(1)) {
					label = swtchLbls[0];
				} else {
					label = swtchLbls[1];
				} // end if
				while (!lab[index].equals(label)) {
					index++;
				} // end while
				lineNum = index;
				break;
				
			case 'R':
				if (lin[lineNum].charAt(1) == 'Y') {
					result = true;
					isDone = true;
				} else if (lin[lineNum].charAt(1) == 'N') {
					result = false;
					isDone = true;
				} // end if
				break;
				
			} // end switch
			
		} // end while
		
		return result;
		
	} // end runMachine()

} // end class
