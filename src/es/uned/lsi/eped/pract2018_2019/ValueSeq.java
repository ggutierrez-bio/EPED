package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.*;

public class ValueSeq extends Value {
	int length;
	protected Queue<Character> data;
	
	/* Constructor: recibe un String con el valor numérico */
	public ValueSeq(String s) {
		init(s);
	}
	
	/* Método que transforma el valor numérico en un String */
	public String toString() {
		char[] chars = new char[length];
		//for (int i = length -1; i >= 0; i--) {
		for (int i = 0; i < length; i++) {
			chars[i] = (char)(data.getFirst() + '0');
			data.dequeue();
		}
		String s = new String(chars);
		init(s);
		return s;
	}

	
	/* Método que modifica el valor numérico llamante, sumándole el valor numérico parámetro */
	public void addValue(Value n) {
		if (! n.getClass().isInstance(ValueSeq.class) ) return; //TODO: throw exceptions and stuff
		Queue<Character> result = new Queue<>();
		Queue<Character> ndata = ((ValueSeq) n).data;
		length = 0;
		char auxchar;
		char left, right;
		boolean fCarry = false;
		while (fCarry || !(data.isEmpty() &&  ndata.isEmpty())) {
			auxchar = (char) ((fCarry) ? 1 : 0);
			left = (!data.isEmpty()) ? data.getFirst() : 0;
			right = (!ndata.isEmpty()) ? ndata.getFirst() : 0;
			auxchar += left + right;
			fCarry = (auxchar > 9);
			result.enqueue((char)(auxchar % 10));
			length++;
		}
		if (length==0) {
			result.enqueue((char)0);
			length ++;
		}
		data = result;
	}

	/* Método que modifica el valor numérico llamante, restándole el valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico llamante */
	public void subValue(Value n) {
	}

	/* Método que modifica el valor numérico llamante, restándolo del valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico parámetro */
	public void subFromValue(Value n) {
	}

	/* Método que modifica el valor numérico llamante, multiplicándolo por el valor numérico parámetro */
	public void multValue(Value n) {
	}

	/* Método que indica si el valor numérico llamante es mayor que el valor numérico parámetro */
	public boolean greater(Value n) {
		return false;
	}

	/* Método que indica si el valor numérico es cero */
	public boolean isZero() {
		return false;
	}

	private void init(String s) {
		length = s.length();
		data = new Queue<>();
		for (int i = 0; i < length; i++) {
			data.enqueue((char)(s.charAt(i) - '0'));
		}		
	}
}
