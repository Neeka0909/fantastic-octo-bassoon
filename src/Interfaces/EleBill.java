package Interfaces;

import Core.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;  
import java.util.Date; 

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JRadioButton;

public class EleBill {

	
	private JFrame frameEbill;
	private JTextField UnitReadingEntryMeterInput;
	private JTextField UnitReadingEntryTimeInput;
	private JTextField monTgtCost;
	private JTextField monTgtUnits;
	
	private static String currentDate;
	private int timePeriod;
	private double totalUnitCount;
	private int totalDateCount;
	
	
	public int getTimePeriod() {
		return timePeriod;
	}
	
	public void setTimePeriod(int period) {
		this.timePeriod = period;
	}
	
	public double getTotalUnitCount() {
		return totalUnitCount;
	}

	public void setTotalUnitCount(double totalUnitCount) {
		this.totalUnitCount = totalUnitCount;
	}

	public int getTotalDateCount() {
		return totalDateCount;
	}

	public void setTotalDateCount(int totalDateCount) {
		this.totalDateCount = totalDateCount;
	}
	
	public static String getCurrentDate() {
		return currentDate;
	}
	public static void setCurrentDate(String date) {
		currentDate = date;
	}

	
	public static void main(String[] args) throws ClassNotFoundException, IOException {
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EleBill window = new EleBill();
					window.frameEbill.setVisible(true);
				} catch (Exception e9) {
					e9.printStackTrace();
				}
			}
		});
		
		
	}


	public EleBill() throws ClassNotFoundException, IOException {
		
		initialize();
	}


	public void initialize() throws ClassNotFoundException, IOException {
		Elecricity el = new Elecricity();
		
		frameEbill = new JFrame();
		frameEbill.setResizable(false);
		frameEbill.getContentPane().setForeground(new Color(191, 0, 191));
		frameEbill.setBounds(100, 100, 810, 537);
		frameEbill.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JLabel lblNewLabel = new JLabel("Electricity Bill");
		lblNewLabel.setBounds(10, 11, 151, 27);
		lblNewLabel.setForeground(new Color(0, 0, 0));
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 22));
		
		JLabel ebLableMonthlyTargetCost = new JLabel("Monthly Target Cost (Rs)");
		ebLableMonthlyTargetCost.setBounds(43, 63, 171, 19);
		ebLableMonthlyTargetCost.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel ebLableMonthlyTargetUnits = new JLabel("Monthly Target Units");
		ebLableMonthlyTargetUnits.setBounds(43, 93, 154, 19);
		ebLableMonthlyTargetUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		
		monTgtCost = new JTextField();
		monTgtCost.setHorizontalAlignment(SwingConstants.CENTER);
		monTgtCost.setFont(new Font("Arial", Font.PLAIN, 14));
		
				//Appear save parameter on start
				monTgtCost.setText(Integer.toString(el.dataOutput()));
				monTgtCost.setBounds(268, 64, 86, 20);
				frameEbill.getContentPane().add(monTgtCost);
				monTgtCost.setColumns(10);
						
				monTgtUnits = new JTextField();
				monTgtUnits.setEditable(false);
				monTgtUnits.setHorizontalAlignment(SwingConstants.CENTER);
						
				//LoadData on start
				double tgtUnits = el.unitCount(el.dataOutput());
				System.out.println(tgtUnits);
				monTgtUnits.setText(Double.toString(tgtUnits));
				monTgtUnits.setFont(new Font("Arial", Font.PLAIN, 14));
				monTgtUnits.setColumns(10);
				monTgtUnits.setBounds(268, 94, 86, 20);
				frameEbill.getContentPane().add(monTgtUnits);
				
		
		
		
		
		JLabel lblReadingEntry = new JLabel("Last Meter Reading ");
		lblReadingEntry.setBounds(24, 170, 173, 19);
		lblReadingEntry.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel UnitReadingEntryMeter = new JLabel("Meater Reading (Units)");
		UnitReadingEntryMeter.setBounds(44, 345, 173, 17);
		UnitReadingEntryMeter.setFont(new Font("Arial", Font.PLAIN, 16));
		
		UnitReadingEntryMeterInput = new JTextField();
		UnitReadingEntryMeterInput.setFont(new Font("Arial", Font.PLAIN, 14));
		UnitReadingEntryMeterInput.setHorizontalAlignment(SwingConstants.CENTER);
		UnitReadingEntryMeterInput.setBounds(255, 344, 86, 20);
		UnitReadingEntryMeterInput.setColumns(10);
		
		UnitReadingEntryTimeInput = new JTextField();
		UnitReadingEntryTimeInput.setEditable(true);
		UnitReadingEntryTimeInput.setForeground(new Color(0, 0, 0));
		UnitReadingEntryTimeInput.setFont(new Font("Arial", Font.PLAIN, 14));
		UnitReadingEntryTimeInput.setHorizontalAlignment(SwingConstants.CENTER);
		UnitReadingEntryTimeInput.setBounds(255, 372, 86, 20);
		UnitReadingEntryTimeInput.setColumns(10);
		
		JLabel lblDateValue = new JLabel("test");
		lblDateValue.setHorizontalAlignment(SwingConstants.CENTER);
		lblDateValue.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDateValue.setBounds(35, 248, 86, 19);
		frameEbill.getContentPane().add(lblDateValue);
		
		JLabel mReading = new JLabel("test");
		mReading.setHorizontalAlignment(SwingConstants.CENTER);
		mReading.setFont(new Font("Arial", Font.PLAIN, 16));
		mReading.setBounds(186, 248, 121, 19);
		frameEbill.getContentPane().add(mReading);
		
		//History part data load
		File file = new File("./ebilllhistorydata.txt");
		FileInputStream fin = new FileInputStream(file);
		ObjectInputStream objReader = new ObjectInputStream(fin);
		ArrayList<Elecricity> tData = new ArrayList<Elecricity>();
		try {
			while (true) {
				Elecricity std = (Elecricity) objReader.readObject();
				tData.add(std);
			}
		} catch (Exception e3) {
			
		}
		objReader.close();
		fin.close();
		for (Elecricity e : tData) {
			lblDateValue.setText(e.getDate());
			EleBill.setCurrentDate(e.getDate());
			mReading.setText(Double.toString(e.getReading()));
		}
		
		JRadioButton autoCalc = new JRadioButton("Calculate time period based on Current date.");		
		autoCalc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				if(autoCalc.isSelected()) {
					UnitReadingEntryTimeInput.setText(formatter.format(date));
					UnitReadingEntryTimeInput.setEditable(false);
					
				}else {
					UnitReadingEntryTimeInput.setText(null);
					UnitReadingEntryTimeInput.setEditable(true);
				}
			}
		});
		autoCalc.setFont(new Font("Arial", Font.PLAIN, 12));
		autoCalc.setBounds(35, 409, 307, 23);
		frameEbill.getContentPane().add(autoCalc);

		
		
		JLabel UnitReadingEntryTime = new JLabel("Time Period (Days)");
		UnitReadingEntryTime.setBounds(43, 373, 136, 17);
		UnitReadingEntryTime.setFont(new Font("Arial", Font.PLAIN, 16));
		
		
		
		JLabel lblReport = new JLabel("Report");
		lblReport.setBounds(458, 30, 59, 19);
		lblReport.setFont(new Font("Arial", Font.BOLD, 16));
		
		JLabel lblXcCostrs = new JLabel("Usage Upto Current Date (Rs) :");
		lblXcCostrs.setBounds(395, 60, 225, 19);
		lblXcCostrs.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblUsageUptoCurrent = new JLabel("Usage Upto Current Date (Units) :");
		lblUsageUptoCurrent.setBounds(395, 93, 233, 19);
		lblUsageUptoCurrent.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblCurrentUsageRemainingUnits = new JLabel("2500");
		lblCurrentUsageRemainingUnits.setBounds(663, 125, 121, 19);
		lblCurrentUsageRemainingUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblCurrentUsageUnits = new JLabel("2500");
		lblCurrentUsageUnits.setBounds(663, 93, 97, 19);
		lblCurrentUsageUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblRemainingUnits = new JLabel("Remaining Units :");
		lblRemainingUnits.setBounds(395, 125, 147, 19);
		lblRemainingUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel lblCurrentUsageCost = new JLabel("2500");
		lblCurrentUsageCost.setBounds(663, 63, 97, 19);
		lblCurrentUsageCost.setFont(new Font("Arial", Font.PLAIN, 16));
		
		JLabel monExpectedUnits = new JLabel("2500");
		monExpectedUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		monExpectedUnits.setBounds(663, 324, 54, 19);
		frameEbill.getContentPane().add(monExpectedUnits);
		
		JLabel monExpectedUnitCost = new JLabel("2500");
		monExpectedUnitCost.setFont(new Font("Arial", Font.PLAIN, 16));
		monExpectedUnitCost.setBounds(663, 354, 54, 19);
		frameEbill.getContentPane().add(monExpectedUnitCost);
		
		JLabel savingCost = new JLabel("2500");
		savingCost.setFont(new Font("Arial", Font.PLAIN, 16));
		savingCost.setBounds(663, 386, 54, 19);
		frameEbill.getContentPane().add(savingCost);
		
		JLabel avgDailyUsage = new JLabel("0");
		avgDailyUsage.setFont(new Font("Arial", Font.PLAIN, 16));
		avgDailyUsage.setBounds(663, 294, 54, 19);
		frameEbill.getContentPane().add(avgDailyUsage);
		
		//report part data load
				File reportFile = new File("./ebillreportdata.txt");
				FileInputStream fileIn = new FileInputStream(reportFile);
				ObjectInputStream objectReader = new ObjectInputStream(fileIn);
				ArrayList<Elecricity> rData = new ArrayList<Elecricity>();
				try {
					while (true) {
						Elecricity std = (Elecricity) objectReader.readObject();
								rData.add(std);
							}
				} catch (Exception e1) {
							
				}
				objectReader.close();
				fileIn.close();
				
				for(Elecricity r :  rData) {
					System.out.println(r.getTotalUnits());
					System.out.println(r.getTotalDays());
					el.setTotalUnits(r.getTotalUnits());
					el.setTotalDays(r.getTotalDays());
				}
		
				lblCurrentUsageCost.setText(Integer.toString(Elecricity.currentUsage((int)el.getTotalUnits())));
				lblCurrentUsageUnits.setText(Double.toString(el.getTotalUnits()));
				avgDailyUsage.setText(Double.toString((el.getTotalUnits()/el.getTotalDays())));
				lblCurrentUsageRemainingUnits.setText(Double.toString(Double.parseDouble(monTgtUnits.getText()) - el.getTotalUnits()));		
				double expUnits = Double.parseDouble(avgDailyUsage.getText()) * 30;
				monExpectedUnits.setText(Double.toString(expUnits));
				monExpectedUnitCost.setText(Integer.toString(Elecricity.currentUsage((int)expUnits)));
				savingCost.setText(Integer.toString(Integer.parseInt(monTgtCost.getText()) - Integer.parseInt(monExpectedUnitCost.getText())));
				
				
		JButton addRecode = new JButton("Add Recode");		
		addRecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ea) {
				Elecricity e1 = new Elecricity();
				SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				Date date = new Date();
				double usageUnit = Double.parseDouble(UnitReadingEntryMeterInput.getText()) - Double.parseDouble(mReading.getText());
				int days;
				double units;
				if (autoCalc.isSelected()) {
					 try {
						days = Program.addTimePeriod(lblDateValue.getText(), formatter.format(date));
						el.setTotalDays(el.getTotalDays()+ days);
						units = el.getTotalUnits() + usageUnit;
						el.setTotalUnits(units);
						el.saveReoprtData(units, el.getTotalDays());
						e1.saveHistoryData(formatter.format(date), Double.parseDouble(UnitReadingEntryMeterInput.getText()));
						mReading.setText(UnitReadingEntryMeterInput.getText());
						lblDateValue.setText(formatter.format(date));
						JOptionPane.showMessageDialog(null, "Data saved.");
					} catch (NumberFormatException e2) {
						JOptionPane.showMessageDialog(null, "Error 1! ");
					} catch (IOException e2) {
						JOptionPane.showMessageDialog(null, "Error 2! ");
					} catch (ParseException e2) {
						e2.printStackTrace();
					} 
				}else {
					try {
						e1.saveHistoryData(formatter.format(date), Double.parseDouble(UnitReadingEntryMeterInput.getText()));
						lblDateValue.setText(formatter.format(date));
						units = el.getTotalUnits() + usageUnit;
						el.setTotalUnits(units);
						el.setTotalDays(Integer.parseInt(UnitReadingEntryTimeInput.getText()));
						el.saveReoprtData(units,el.getTotalDays() );
						mReading.setText(UnitReadingEntryMeterInput.getText());
						lblDateValue.setText(formatter.format(date));
					} catch (NumberFormatException | IOException e2) {
						JOptionPane.showMessageDialog(null, "Error! ");
					}
				}
				lblCurrentUsageCost.setText(Integer.toString(Elecricity.currentUsage((int)el.getTotalUnits())));
				lblCurrentUsageUnits.setText(Double.toString(el.getTotalUnits()));
				avgDailyUsage.setText(Double.toString((el.getTotalUnits()/el.getTotalDays())));
				lblCurrentUsageRemainingUnits.setText(Double.toString(Double.parseDouble(monTgtUnits.getText()) - el.getTotalUnits()));		
				double expUnits = Double.parseDouble(avgDailyUsage.getText()) * 30;
				monExpectedUnits.setText(Double.toString(expUnits));
				monExpectedUnitCost.setText(Integer.toString(Elecricity.currentUsage((int)expUnits)));
				savingCost.setText(Integer.toString(Integer.parseInt(monTgtCost.getText()) - Integer.parseInt(monExpectedUnitCost.getText())));
				
				
			}
			
			
		});
		
		
		addRecode.setBounds(221, 439, 121, 25);
		addRecode.setFont(new Font("Arial", Font.BOLD, 14));
		
		JTextArea txtrAdvice = new JTextArea();
		txtrAdvice.setWrapStyleWord(true);
		txtrAdvice.setBounds(395, 192, 322, 75);
		txtrAdvice.setFont(new Font("Arial", Font.PLAIN, 14));
		txtrAdvice.setText("Advice ....");
		txtrAdvice.setEditable(false);
		txtrAdvice.setLineWrap(true);
		frameEbill.getContentPane().setLayout(null);
		frameEbill.getContentPane().add(addRecode);
		
		JLabel lblReadingEntry_1 = new JLabel("Unit Reading Entry");
		lblReadingEntry_1.setBounds(24, 305, 173, 19);
		lblReadingEntry_1.setFont(new Font("Arial", Font.BOLD, 16));
		
		
		JLabel lblMonthelyExpectedUnits = new JLabel("Monthely Expected Units :");
		lblMonthelyExpectedUnits.setFont(new Font("Arial", Font.PLAIN, 16));
		lblMonthelyExpectedUnits.setBounds(395, 324, 207, 19);
		frameEbill.getContentPane().add(lblMonthelyExpectedUnits);
		
		
		
		JLabel lblAverageDailyUsage = new JLabel("Monthely Expected Cost :");
		lblAverageDailyUsage.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAverageDailyUsage.setBounds(395, 354, 207, 19);
		frameEbill.getContentPane().add(lblAverageDailyUsage);
		
		
		
		JLabel lblAverageDailyUsage_1 = new JLabel("Average Daily Usage :");
		lblAverageDailyUsage_1.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAverageDailyUsage_1.setBounds(395, 294, 207, 19);
		frameEbill.getContentPane().add(lblAverageDailyUsage_1);
		
		JLabel lblSavingextraCost = new JLabel("Saving/Extra Cost :");
		lblSavingextraCost.setFont(new Font("Arial", Font.PLAIN, 16));
		lblSavingextraCost.setBounds(395, 386, 207, 19);
		frameEbill.getContentPane().add(lblSavingextraCost);
		

		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				double units = el.unitCount(Integer.parseInt(monTgtCost.getText()));
				monTgtUnits.setText(Double.toString(units));
				int tgtValue = Integer.parseInt(monTgtCost.getText());
				try {
					el.save(tgtValue);
					frameEbill.repaint();;
					JOptionPane.showMessageDialog(null, "Data saved.");
					SwingUtilities.updateComponentTreeUI(frameEbill);
				} catch (IOException er) {
					JOptionPane.showMessageDialog(null, "Error! ");
					
				}
				
				lblCurrentUsageCost.setText(Integer.toString(Elecricity.currentUsage((int)el.getTotalUnits())));
				lblCurrentUsageUnits.setText(Double.toString(el.getTotalUnits()));
				avgDailyUsage.setText(Double.toString((el.getTotalUnits()/el.getTotalDays())));
				lblCurrentUsageRemainingUnits.setText(Double.toString(Double.parseDouble(monTgtUnits.getText()) - el.getTotalUnits()));		
				double expUnits = Double.parseDouble(avgDailyUsage.getText()) * 30;
				monExpectedUnits.setText(Double.toString(expUnits));
				monExpectedUnitCost.setText(Integer.toString(Elecricity.currentUsage((int)expUnits)));
				savingCost.setText(Integer.toString(Integer.parseInt(monTgtCost.getText()) - Integer.parseInt(monExpectedUnitCost.getText())));
				
				
			}
		});
		
				
		btnNewButton.setBounds(284, 123, 70, 25);
		btnNewButton.setFont(new Font("Arial", Font.BOLD, 14));

		
		frameEbill.getContentPane().add(lblReadingEntry_1);
		frameEbill.getContentPane().add(lblNewLabel);
		frameEbill.getContentPane().add(lblReport);
		frameEbill.getContentPane().add(btnNewButton);
		frameEbill.getContentPane().add(lblReadingEntry);
		frameEbill.getContentPane().add(ebLableMonthlyTargetCost);
		frameEbill.getContentPane().add(ebLableMonthlyTargetUnits);
		frameEbill.getContentPane().add(lblUsageUptoCurrent);
		frameEbill.getContentPane().add(lblRemainingUnits);
		frameEbill.getContentPane().add(txtrAdvice);
		frameEbill.getContentPane().add(lblXcCostrs);
		frameEbill.getContentPane().add(UnitReadingEntryMeter);
		frameEbill.getContentPane().add(UnitReadingEntryTime);
		frameEbill.getContentPane().add(UnitReadingEntryTimeInput);
		frameEbill.getContentPane().add(UnitReadingEntryMeterInput);
		frameEbill.getContentPane().add(lblCurrentUsageCost);
		frameEbill.getContentPane().add(lblCurrentUsageUnits);
		frameEbill.getContentPane().add(lblCurrentUsageRemainingUnits);
		
		
		JLabel lblDate = new JLabel("Date");
		lblDate.setFont(new Font("Arial", Font.PLAIN, 16));
		lblDate.setBounds(60, 215, 41, 19);
		frameEbill.getContentPane().add(lblDate);
		
		JLabel lblNewLabel_1_2_2 = new JLabel("Meater Reading");
		lblNewLabel_1_2_2.setFont(new Font("Arial", Font.PLAIN, 16));
		lblNewLabel_1_2_2.setBounds(186, 216, 117, 17);
		frameEbill.getContentPane().add(lblNewLabel_1_2_2);
		
		JLabel lblAdvice = new JLabel("Advice");
		lblAdvice.setFont(new Font("Arial", Font.PLAIN, 16));
		lblAdvice.setBounds(395, 155, 147, 19);
		frameEbill.getContentPane().add(lblAdvice);
		
		if (Integer.parseInt(savingCost.getText()) > 0) {
			txtrAdvice.setText("You are doing good job on saving electricity");
		}else {
			txtrAdvice.setText("You are not doing good job on saving electricity");
		}
		

	}
}
