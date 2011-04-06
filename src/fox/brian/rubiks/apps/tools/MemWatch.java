package fox.brian.rubiks.apps.tools;

public class MemWatch {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println("Memory Statistics");
		while (true) {
			System.out.printf(
				"%8.1f M    %8.1f M%n",
				Runtime.getRuntime().freeMemory() / (1024.0 * 1024),
				Runtime.getRuntime().totalMemory() / (1024.0 * 1024)
			);
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
