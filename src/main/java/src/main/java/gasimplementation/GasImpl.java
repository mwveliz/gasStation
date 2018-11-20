
package src.main.java.gasimplementation;

import net.bigpoint.assessment.gasstation.GasPump;
import net.bigpoint.assessment.gasstation.GasStation;
import net.bigpoint.assessment.gasstation.GasType;
import net.bigpoint.assessment.gasstation.exceptions.GasTooExpensiveException;
import net.bigpoint.assessment.gasstation.exceptions.NotEnoughGasException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;


public class GasImpl implements GasStation  {
	
	// constructors

	 
	private Collection<GasPump> pumps = new ArrayList<GasPump>();
	private ConcurrentHashMap<GasPump,Boolean> busyGasPump = new  ConcurrentHashMap<GasPump, Boolean>();
	private double revenue;
	private int numberOfSales;
	private int numberOfCancellationsNoGas;
	private int numberOfCancellationsTooExpensive;
	HashMap<GasType, Double> gasTypePrice= new HashMap<GasType, Double>();
    


	//method for initialize
	public double initialize(int MINP, int MAXP, int MINC, int MAXC, 
			               double MIN_GAS_PRICE, double MAX_GAS_PRICE, 
			               double MIN_GAS_PAY, double MAX_GAS_PAY, 
			               double MIN_PUMP_TANK, double MAX_PUMP_TANK,
			               double MIN_CAR_TANK, double MAX_CAR_TANK
			               ){
	    int pumpsAmount = ThreadLocalRandom.current().nextInt(MINP, MAXP);
	    int customersAmount = ThreadLocalRandom.current().nextInt(MINC, MAXC);
        int i = 1;
        int c = 1;
        //setting prices for gasTypes
        this.setPrice(GasType.REGULAR, ThreadLocalRandom.current().nextDouble(MIN_GAS_PRICE, MAX_GAS_PRICE));
    	this.setPrice(GasType.DIESEL, ThreadLocalRandom.current().nextDouble(MIN_GAS_PRICE, MAX_GAS_PRICE));
    	this.setPrice(GasType.SUPER, ThreadLocalRandom.current().nextDouble(MIN_GAS_PRICE, MAX_GAS_PRICE));
    	
       
        while(i  < pumpsAmount){
        	GasType rgt = GasType.values()[new Random().nextInt(GasType.values().length)];
    		GasPump pump = new GasPump(rgt,  ThreadLocalRandom.current().nextDouble(MIN_PUMP_TANK, MAX_PUMP_TANK));
            i++;
            addGasPump(pump);
        }
        
        
        System.out.println("pumps Generated: ");
        System.out.println(pumpsAmount);
        System.out.println("customers Generated: ");
        System.out.println(customersAmount);
        
        while(c  < customersAmount){
        	GasType rgt = GasType.values()[new Random().nextInt(GasType.values().length)];
        	double rail =  ThreadLocalRandom.current().nextDouble(MIN_CAR_TANK, MAX_CAR_TANK);
        	double rmppl =  ThreadLocalRandom.current().nextDouble(MIN_GAS_PAY, MAX_GAS_PAY);
        	try {
                System.out.println("customer has entered the queue "
                		+ "and asked for " + rail +" liters of "+ rgt+ " wants to pay max"
                		+ rmppl + " per liter");
                System.out.println("**** customer will be identifed as "+ rgt+"-"+rail+"-" + rmppl);
				buyGas(rgt, rail, rmppl);
			} catch (NotEnoughGasException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GasTooExpensiveException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	c++;
            
        }
		return getRevenue();     
		}
	 
	//method for add a gas pump
		public void addGasPump(GasPump pump) {
			pumps.add(pump);
		}
		
		public Collection<GasPump> getGasPumps(){
			
			return pumps;
		}

        public double buyGas(GasType type, double amountInLiters , double maxPricePerLiter) throws NotEnoughGasException, GasTooExpensiveException {
        	
        	double pricePerLiter = this.getPrice(type);
        	
        	double total = amountInLiters*pricePerLiter;
        	try {
        		if (maxPricePerLiter < pricePerLiter) {
        			throw new GasTooExpensiveException ();
        		}
        		if (pricePerLiter == 0.0) {
            		throw new NotEnoughGasException();
            	}
        		 Optional<GasPump> pumpAvailable = pumps.stream()
        			     .filter(item -> item.getRemainingAmount() >= amountInLiters && item.getGasType()== type)
        			     .findFirst();
        		if (!pumpAvailable.isPresent() ) {
        			throw new NotEnoughGasException();
        		}
        		busyGasPump.put(pumpAvailable.get(), false);
    			System.out.println("this pump still has "+ pumpAvailable.get().getRemainingAmount()+" liters");
                System.out.println("waiting for " + amountInLiters +" liters of "+pumpAvailable.get().getGasType()+" ...");
                Thread th = new Thread(() -> pumpAvailable.get().pumpGas(amountInLiters));     	
                th.start();	
            	System.out.println("new Thread for customer " + th.getName());
            	th.join();
            	System.out.println("*** customer "+ type+"-"+amountInLiters+"-" + maxPricePerLiter + " is leaving the Gas Station now");
         		busyGasPump.put(pumpAvailable.get(), true);
         		numberOfSales++;
         		revenue += total;
         		System.out.println("Total to pay: "+ total);
        	}
        	catch (NotEnoughGasException e)
        	{
    			numberOfCancellationsNoGas++;
        		System.out.println("Not Enough gas of that type for customer " +type+"-"+amountInLiters+"-" + maxPricePerLiter );
        		
        	}
        	catch  (GasTooExpensiveException e) {
    			numberOfCancellationsTooExpensive++;
        		System.out.println("Gas is too expensive for you, sorry customer " +type+"-"+amountInLiters+"-" + maxPricePerLiter );
        	} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	return total;
        }
        
        public double getRevenue() {
        	return revenue;
        }

    	

        public int getNumberOfSales() {
        	return numberOfSales;
        }

    	

        public int getNumberOfCancellationsNoGas() {
        	return numberOfCancellationsNoGas;
        }

    	

        
    	public int getNumberOfCancellationsTooExpensive() {
    		return numberOfCancellationsTooExpensive;
    	}

    	
    	public double getPrice(GasType type) {
    		double gasPrice = 0.0;
    		Optional<GasPump> pump = pumps.stream()
   			     .filter(item -> item.getGasType()== type)
   			     .findFirst();
    		if (pump.isPresent()) {
    		  GasType gasType = pump.get().getGasType();
    		  gasPrice = gasTypePrice.get(gasType);
    		}
    		return gasPrice;
    	}

    	

    	public void setPrice(GasType type, double price) {
    		gasTypePrice.put(type, price);
    	}

		
	}