package stopWatch;

public class StopWatch {
	
	 private final long createdMillis = System.currentTimeMillis(); 
	 
	 /**
	  *Return the numbers of seconds from the instantiation of the class
	  */
	 public double getSeconds() {
	   long nowMillis = System.currentTimeMillis();
	   return ((nowMillis - this.createdMillis) / 1000.0);
	
	 }
}
