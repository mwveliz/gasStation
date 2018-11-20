package mock;


import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.hamcrest.CoreMatchers.is;

import src.main.java.gasimplementation.GasImpl;

public class GasImplTest  {
	@Test
	public void test_aProductiveDayWithNoProblems() {	
		GasImpl obj = new GasImpl();
		double revenue = obj.initialize(310, 500, 50, 60, 0.5, 1, 1.5, 3, 210, 220, 1, 22);
		int nos = obj.getNumberOfSales();
		int nocNoGas = obj.getNumberOfCancellationsNoGas();
		int noc2ex = obj.getNumberOfCancellationsTooExpensive(); 
		System.out.println("*** RESULTS OF THE DAY ****");
		System.out.println("Revenue: "+ revenue);
		System.out.println("Number of Sales: "+ nos);
		System.out.println("Number of Cancelation No GAS: "+ nocNoGas);
		System.out.println("Number of Cancelation 2 xpensive: "+ noc2ex);		 
		assertTrue(revenue>0.0);
		assertTrue(nos>0);
		assertThat(nocNoGas, is(0));
		assertThat(noc2ex,is(0));

	}
	@Test
	public void test_notEnoughGas() {	
		GasImpl obj = new GasImpl();
		double revenue = obj.initialize(1, 3, 100, 999, 0.5, 3, 3, 4, 21, 22, 10, 50);
		int nos = obj.getNumberOfSales();
		int nocNoGas = obj.getNumberOfCancellationsNoGas();
		int noc2ex = obj.getNumberOfCancellationsTooExpensive(); 
		System.out.println("*** RESULTS OF THE DAY ****");
		System.out.println("Revenue: "+ revenue);
		System.out.println("Number of Sales: "+ nos);
		System.out.println("Number of Cancelation No GAS: "+ nocNoGas);
		System.out.println("Number of Cancelation 2 xpensive: "+ noc2ex);		 
		assertThat(noc2ex, is(0));
		assertTrue(nos<nocNoGas);
	}
	
	@Test
	public void test_gasWasTooExpensive() {	
		GasImpl obj = new GasImpl();
		double revenue = obj.initialize(100, 999, 10, 20,4, 3000, 0.5, 3, 210, 220, 1, 20);
		int nos = obj.getNumberOfSales();
		int nocNoGas = obj.getNumberOfCancellationsNoGas();
		int noc2ex = obj.getNumberOfCancellationsTooExpensive(); 
		System.out.println("*** RESULTS OF THE DAY ****");
		System.out.println("Revenue: "+ revenue);
		System.out.println("Number of Sales: "+ nos);
		System.out.println("Number of Cancelation No GAS: "+ nocNoGas);
		System.out.println("Number of Cancelation 2 xpensive: "+ noc2ex);
		assertTrue(nos<noc2ex);
	}
	
	@Test
	public void test_aVeryBadDay() {	
		GasImpl obj = new GasImpl();
		double revenue = obj.initialize(1, 50, 100, 900, 1, 3, 0.5, 3, 21, 22, 1, 50);
		int nos = obj.getNumberOfSales();
		int nocNoGas = obj.getNumberOfCancellationsNoGas();
		int noc2ex = obj.getNumberOfCancellationsTooExpensive(); 
		System.out.println("*** RESULTS OF THE DAY ****");
		System.out.println("Revenue: "+ revenue);
		System.out.println("Number of Sales: "+ nos);
		System.out.println("Number of Cancelation No GAS: "+ nocNoGas);
		System.out.println("Number of Cancelation 2 xpensive: "+ noc2ex);	
		assertTrue(nos< nocNoGas );
		assertTrue(nos< noc2ex );
		
	}
	
	@Test
	public void test_aRegularDay() {	
		GasImpl obj = new GasImpl();
		double revenue = obj.initialize(10, 99, 10, 99, 0.5, 3, 0.5, 3, 21, 22, 1, 50);
		int nos = obj.getNumberOfSales();
		int nocNoGas = obj.getNumberOfCancellationsNoGas();
		int noc2ex = obj.getNumberOfCancellationsTooExpensive(); 
		System.out.println("*** RESULTS OF THE DAY ****");
		System.out.println("Revenue: "+ revenue);
		System.out.println("Number of Sales: "+ nos);
		System.out.println("Number of Cancelation No GAS: "+ nocNoGas);
		System.out.println("Number of Cancelation 2 xpensive: "+ noc2ex);	
		assertTrue(revenue > 0.0);
		assertTrue(nos > 0);
		assertTrue(nocNoGas > 0);
		assertTrue(noc2ex > 0);		
	}	
}
	
	
	