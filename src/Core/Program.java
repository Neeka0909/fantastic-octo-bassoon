package Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Program {
	private static final String filePath = "./data.txt";
	public String userName;
	public String date;
	public int inputMonthlyBill;
	public int monthlyTarget;
	public int monthlyExpected;
	

	
	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Program.getData();
	}
	
	
	public void addUsage(int billValue) {
		this.inputMonthlyBill = billValue;
		
	}
	
	//addTimePeriod method renamed as addReadings
	public void addReadings(int meater, int days) {
		
	}
	
	public void save(Object monTgt) throws IOException {
		FileOutputStream fo = new FileOutputStream(filePath);
		try (ObjectOutputStream objWriter = new ObjectOutputStream(fo)) {
			objWriter.writeObject(monTgt);
			fo.close();
			objWriter.close();
		}
	}
	
	public static int getData() throws IOException, ClassNotFoundException {
		File dataFile = new File("./data.txt");
		FileInputStream fi = new FileInputStream(dataFile);
		try (ObjectInputStream objReader = new ObjectInputStream(fi)) {
			while (true) {
				int readData = (int) objReader.readObject();
				return readData;
			}
		}
	}
	
	public int expectedBill() {
		return 0;

	}
	
	public static int  addTimePeriod(String prevDate, String currDate) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		long difference_In_Time = 0;
		long difference_In_Days = 0;
		
			Date d1 = sdf.parse(prevDate);
			Date d2 = sdf.parse(currDate);
			
			difference_In_Time = d2.getTime() - d1.getTime() ;
			difference_In_Days = ((difference_In_Time/ (1000 * 60 * 60 * 24))% 365) +1;
			System.out.println(difference_In_Days);
			return (int) difference_In_Days;

	}

}
