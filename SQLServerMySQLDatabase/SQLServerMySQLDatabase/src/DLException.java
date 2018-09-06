import java.util.*;
import java.sql.Timestamp;
import java.io.*;

public class DLException extends Exception {
	
   Exception e;
	ArrayList<ArrayList<String>> exceptionList = new ArrayList<ArrayList<String>>();

	public DLException(Exception e){
		this.e = e;
      log();
	}

	public DLException(Exception e, ArrayList<ArrayList<String>> exceptionList){
		this.e = e;
		this.exceptionList = exceptionList;
      log();
	}
  
	public void log(){
      if(e.getStackTrace() != null){
		   StackTraceElement[] stackTrace = e.getStackTrace();
			ArrayList<String> stackTraceList = new ArrayList<String>();
			for (StackTraceElement ele : stackTrace){
            String element = ele.toString();
				stackTraceList.add(element);
			}
			exceptionList.add(stackTraceList);
		}
		try{
			Writer log = new FileWriter(new File("log.txt"), true);
			for (int outer = 0; outer <= exceptionList.size() - 1; outer++){
				for(int inner = 0; inner <= exceptionList.get(outer).size() - 1; inner++){
					Date date = new Date();
               String timestamp = new Timestamp(date.getTime()).toString();
					log.write(timestamp);
               log.write(" ");
					log.write(exceptionList.get(outer).get(inner));
               log.write("\n");
					log.flush();
				}
			}
			log.close();
		}
      catch(IOException ioe){ //do nothing
         return;
      }
	}
}