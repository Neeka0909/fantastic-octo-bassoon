package Core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class Elecricity extends Program implements Serializable{
	private static final long serialVersionUID = 6529685098267757690L;
	private static final String filePath = "./data.txt";
	public int monthlyTarget;
	public double monthlyTargetUnits;
	public static int[] eleFixedCost = {120,240,360,960,15};
	public String date;
	public double reading;
	public double totalUnits;
	public int totalDays;
	
	public Elecricity(String date, double reading) {
		this.date = date;
		this.reading = reading;
	}
	
	public Elecricity(Double totalUnits, int totalDays) {
		this.totalDays = totalDays;
		this.totalUnits = totalUnits;
	}
	
	
	
	public Elecricity(int monthlyTarget) {
		this.monthlyTarget = monthlyTarget;
	}

	public Elecricity() {
		System.out.println("Test");
	}

	public String getDate() {
		return date;
	}
	
	public double getReading() {
		return reading;
	}
	
	public void setMonthlyTarget(int monthlyTarget) {
		this.monthlyTarget = monthlyTarget;
	}
	
	public int getMonthlyTarget() {
		return monthlyTarget;
	}
	
	public int getMonthlyTargetUnits() {
		return monthlyTarget;
	}
	
	public double getTotalUnits() {
		return totalUnits;
	}
	
	public int getTotalDays() {
		return totalDays;
	}
	
	public void setTotalUnits(double d) {
		this.totalUnits = d;
	}
	
	public void setTotalDays(int dates) {
		this.totalDays = dates;
	}
	
	//main function
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		Elecricity el = new Elecricity();
		//el.save(500);
		//el.saveHistoryData("2022.11.09", 22563.65);
		el.saveHistoryData("01/11/2022", 22585.65);
		//System.out.println(el.dataOutput());
		System.out.println(el.unitCount(2500));
		System.out.println(Elecricity.currentUsage(90));
		el.saveReoprtData(0, 1);
	} 
	
	//data save method
	public void save(int tgt) throws IOException {
		File file = new File(filePath);
		ArrayList<Elecricity> dataSave = new ArrayList<Elecricity>();
		dataSave.add(new Elecricity(tgt));
		FileOutputStream fileOutput = new FileOutputStream(file);
		ObjectOutputStream objWriter = new ObjectOutputStream(fileOutput);
		
		for (Elecricity e : dataSave) {
			objWriter.writeObject(e);
			
		}
		System.out.println("Data Write Successfully");
		fileOutput.close();
		objWriter.close();
	}
	
	public void saveHistoryData(String date, double reading) throws IOException {
		File file = new File("./ebilllhistorydata.txt");
		ArrayList<Elecricity> dataSave = new ArrayList<Elecricity>();
		dataSave.add(new Elecricity(date, reading));
		FileOutputStream fileOutput = new FileOutputStream(file);
		ObjectOutputStream objWriter = new ObjectOutputStream(fileOutput);
		
		for (Elecricity e : dataSave) {
			objWriter.writeObject(e);
		}
		System.out.println("Data Write Successfully");
		fileOutput.close();
		objWriter.close();
	}
	
	public void saveReoprtData(double units, int days) throws IOException {
		File file = new File("./ebillreportdata.txt");
		ArrayList<Elecricity> dataSave = new ArrayList<Elecricity>();
		dataSave.add(new Elecricity(units, days));
		FileOutputStream fileOutput = new FileOutputStream(file);
		ObjectOutputStream objWriter = new ObjectOutputStream(fileOutput);
		
		for (Elecricity e : dataSave) {
			objWriter.writeObject(e);
		}
		System.out.println("report Data Write Successfully");
		fileOutput.close();
		objWriter.close();
	}
	
	//DataOutput method
	public int dataOutput() throws IOException, ClassNotFoundException{
		File file = new File(filePath);
		FileInputStream fileInput = new FileInputStream(file);
		try (ObjectInputStream objReader = new ObjectInputStream(fileInput)) {
			Elecricity dataRead = (Elecricity) objReader.readObject();
			int tgt = dataRead.getMonthlyTarget();
			return tgt;
		}
	}
	
	public static int currentUsage(int units) {
		int uCost = 0;
		if (units <= 30) {
			uCost = eleFixedCost[0];
			uCost = uCost + (8*units); 
		}else if (units <= 60) {
			uCost = eleFixedCost[1] + 240;
			uCost = uCost + ((units-30)*10);
			
		}else if (units <= 90) {
			uCost = eleFixedCost[2];
			uCost = uCost + (units*16);
			
		}else if (units <= 180) {
			uCost = eleFixedCost[3] + 1440;
			uCost = uCost + ((units-90)*50);
			
		}else if(units > 180) {
			uCost = eleFixedCost[4] + 5940;
			uCost = uCost + ((units-180)*75); 
			
		}		
		return uCost;
	}
	
	public int remainingUsage() {
		int rmnUsage = 0;
		
		return rmnUsage;
	}
	
	public double unitCount(int billValue) {
		double uCount = 0.00;
		int kwhCost = 0;
		 //30kwh limit
		if(billValue <= 360) { 
			kwhCost = billValue - eleFixedCost[0];
			uCount = (double) (kwhCost/8);
		//60kwh limit
		}else if(billValue <= 780) {
			kwhCost = billValue - eleFixedCost[1] - 240;
			uCount = (double) ((kwhCost/10)+30);
			
		}else if(780 < billValue && billValue < 1336) {
			uCount = 60;
			
		}else if(1336 <= billValue && billValue <= 1800) {
			kwhCost = billValue - eleFixedCost[2];
			uCount = (double) (kwhCost/16);
			
		}else if(1800 < billValue && billValue < 2450) {
			uCount = 90;
		
		}else if(2450 < billValue && billValue <= 6900) {
			kwhCost = billValue - eleFixedCost[3] - 1440;
			uCount = (double)((kwhCost/50)+90);
			
		}else if(billValue >= 7515) {
			kwhCost = billValue - eleFixedCost[4] - 5940;
			uCount = (double)((kwhCost/75)+181);
		}
			
		return uCount;
	}
	
}
