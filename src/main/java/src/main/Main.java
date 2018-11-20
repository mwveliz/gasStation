
package src.main;


import src.main.java.gasimplementation.*;



public class Main  {
	public static final int MINP = 10; // minimum pumps
	public static final int MAXP = 99; // maximum pumps
	public static final int MINC = 10; // minimum customers
	public static final int MAXC = 99; // maximum customers
	public static final double MIN_GAS_PRICE = 0.5;
	public static final double MAX_GAS_PRICE = 3;
	public static final double MIN_GAS_PAY = 0.5; // min that customer wants to pay
	public static final double MAX_GAS_PAY = 3; // max that customer wants to pay
	public static final double MIN_PUMP_TANK = 21;
	public static final double MAX_PUMP_TANK = 22;
	public static final double MIN_CAR_TANK = 1;
	public static final double MAX_CAR_TANK = 50;
	
	
	
	public static void main(String [] args){
	GasImpl GI = new GasImpl();
	double revenue = GI.initialize(MINP, MAXP, MINC, MAXC, MIN_GAS_PRICE, MAX_GAS_PRICE,
			      MIN_GAS_PRICE, MAX_GAS_PRICE,
			      MIN_PUMP_TANK, MAX_PUMP_TANK, MIN_CAR_TANK, MAX_CAR_TANK);
	
	System.out.println("*** RESULTS OF THE DAY ****");
	System.out.println("Revenue: "+ revenue);
	System.out.println("Number of Sales: "+ GI.getNumberOfSales());
	System.out.println("Number of Cancelation No GAS: "+ GI.getNumberOfCancellationsNoGas());
	System.out.println("Number of Cancelation 2 xpensive: "+ GI.getNumberOfCancellationsTooExpensive());
	 
	
	
	}	 
}