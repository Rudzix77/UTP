/**
 *
 *  @author Jankowski Michał S16842
 *
 */

package zad1;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.HashMap;
import java.util.Map;

public class Calc {

	private final Map<String, String> operation = new HashMap<String, String>() {{
		put("+", "add");
		put("-", "subtract");
		put("*", "multiply");
		put("/", "divide");
	}};

	String doCalc(String equation){
		String[] e = equation.split("\\s+");

		try{
			return this.getClass().getMethod(operation.get(e[1]), BigDecimal.class, BigDecimal.class).invoke(this, new BigDecimal(e[0]), new BigDecimal(e[2])).toString();
		}catch (Exception ex){
			return "Invalid command to calc";
		}
	}

	public BigDecimal add(BigDecimal a, BigDecimal b){
		return a.add(b, MathContext.DECIMAL32);
	}

	public BigDecimal subtract(BigDecimal a, BigDecimal b){
		return a.subtract(b, MathContext.DECIMAL32);
	}

	public BigDecimal multiply(BigDecimal a, BigDecimal b){
		return a.multiply(b, MathContext.DECIMAL32);
	}

	public BigDecimal divide(BigDecimal a, BigDecimal b){
		return a.divide(b, MathContext.DECIMAL32);
	}
}  
