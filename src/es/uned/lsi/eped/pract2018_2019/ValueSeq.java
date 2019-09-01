package es.uned.lsi.eped.pract2018_2019;

import es.uned.lsi.eped.DataStructures.*;

public class ValueSeq extends Value {
	int length;
	private Queue<Character> data;
	
	/* Constructor: recibe un String con el valor numérico */
	public ValueSeq(String s) {
		data = new Queue<>();
		init(s);
		compact();
	}
	
	/* Método que transforma el valor numérico en un String */
	public String toString() {
		if (isZero()) return "0";
		char[] chars = new char[length];
		for (int i = length -1; i >= 0; i--) {
			chars[i] = (char)(data.getFirst() + '0');
			data.dequeue();
		}
		String s = new String(chars);
		init(s);
		return s;
	}

	
	/* Método que modifica el valor numérico llamante, sumándole el valor numérico parámetro */
	public void addValue(Value n) {
		//System.out.print(this.toString() + " + " + n.toString() + " = ");
		if (! ValueSeq.class.isInstance(n)) return;
		Queue<Character> result = new Queue<>();
		ValueSeq paramv = new ValueSeq(n.toString());
		length = 0;
		char auxchar;
		char left, right;
		boolean fCarry = false;
		while (fCarry || !(data.isEmpty() &&  paramv.data.isEmpty())) {
			auxchar = (char) ((fCarry) ? 1 : 0);
			left = this.extractFirst();
			right = paramv.extractFirst();
			auxchar += left + right;
			fCarry = (auxchar > 9);
			result.enqueue((char)(auxchar % 10));
			length++;
		}
		data = result;
		//System.out.println(this.toString());
	}

	/* Método que modifica el valor numérico llamante, restándole el valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico llamante */
	public void subValue(Value n) {
		//System.out.print("subValue: " + this.toString() + " - " + n.toString() + " = ");
		if (! ValueSeq.class.isInstance(n)) return;
		Queue<Character> result = new Queue<>();
		ValueSeq paramv = new ValueSeq(n.toString());
		length = 0;
		char left, right;
		boolean fCarry = false;
		while (!data.isEmpty() ||  !paramv.data.isEmpty() || fCarry) {
			left = this.extractFirst();
			right = paramv.extractFirst();
			if (fCarry) right++;
			fCarry = (right > left);
			result.enqueue((char)((10+left - right) % 10));
			length++;
		}
		data = result;
		compact();
		//System.out.println(this.toString());
	}

	/* Método que modifica el valor numérico llamante, restándolo del valor numérico parámetro */
	/* Sabemos que el mayor es el valor numérico parámetro */
	public void subFromValue(Value n) {
		//System.out.print("subFromValue: " + n.toString() + " - " + this.toString() + " = ");
		if (! ValueSeq.class.isInstance(n)) return;
		ValueSeq paramv = new ValueSeq(n.toString());
		Queue<Character> result = new Queue<>();
		length = 0;
		char left, right;
		boolean fCarry = false;
		while (!paramv.data.isEmpty() || !data.isEmpty() || fCarry) {
			right = this.extractFirst();
			left = paramv.extractFirst();
			if (fCarry) right++;
			fCarry = (right > left);
			result.enqueue((char)((10+left - right) % 10));
			length++;
		}
		data = result;
		compact();
		//System.out.println(this.toString());
	}

	/* Método que modifica el valor numérico llamante, multiplicándolo por el valor numérico parámetro */
	public void multValue(Value n) {
		//System.out.println(this.toString() + " * " + n.toString());
		ValueSeq left;
		ValueSeq right;
		if (this.length < ((ValueSeq) n).length ) {
			left = new ValueSeq(this.toString());
			right = new ValueSeq(n.toString());
		} else {
			left = new ValueSeq(n.toString());
			right = new ValueSeq(this.toString());
		}
		char current;
		String rightString = right.toString();
		ValueSeq result = new ValueSeq("");
		int magnitude = 0;
		while (!left.data.isEmpty()) {
			current = left.extractFirst();
			ValueSeq auxval = new ValueSeq(rightString);
			auxval.multByCharWithOffset(current, magnitude);
			result.addValue(auxval);
			magnitude++;
		}
		this.data = result.data;
		this.length = result.length;
		//System.out.println("resultado final de la multiplicacion: " + this.toString());
	}

	protected void multByCharWithOffset(char c, int offset){
		//System.out.println("Multiplicando " + toString() + " * " + (char)(c+'0') + " con offset " + offset);
		if(c==0) {
			init("");
			return;
		}
		Queue<Character> result = new Queue<>();
		for (int i = 0; i< offset; i++) {
			result.enqueue((char)0);
		}
		int newlength = offset;
		int carry = 0;
		char auxres;
		while(!data.isEmpty() || carry > 0) {
			char current = extractFirst();
			auxres = (char) (c * current + carry);
			carry = (char)(auxres / 10);
			result.enqueue((char) (auxres % 10 ));
			newlength++;
		}
		this.data = result;
		this.length = newlength;
		
	}
	
	/* Método que indica si el valor numérico llamante es mayor que el valor numérico parámetro */
	public boolean greater(Value n) {
		ValueSeq vs = (ValueSeq) n;
		this.compact();
		vs.compact();
		//System.out.print(toString() + " > " + n.toString() + " = " );
		if (length!=vs.length) {
			//System.out.println(length>vs.length);
			return length>vs.length;
		} else {
			//System.out.println((this.toString().charAt(0))>(vs.toString().charAt(0)));
			String left = this.toString();
			String right = n.toString();
			int i = 0;
			for (; i < left.length(); i++) {
				if (left.charAt(i)!= right.charAt(i)) break;
			}
			if (i==left.length()) return false;
			return (left.charAt(i))>(right.charAt(i));
		}
	}

	/* Método que indica si el valor numérico es cero */
	public boolean isZero() {
		return length == 0;
	}

	private void init(String s) {
		length = s.length();
		data.clear();
		for (int i = length - 1; i >= 0; i--) {
			data.enqueue((char)(s.charAt(i) - '0'));
		}		
	}
	private char extractFirst() {
		if (data.isEmpty()) return 0;
		char auxc = data.getFirst();
		data.dequeue();
		return auxc;
	}
	
	private void compact() {
		String s = this.toString();
		boolean fRequiresInit = false;
		int i = 0;
		for (; i < length; i++) {
			if (s.charAt(i) != '0') break;
			fRequiresInit = true;
		}
		if (fRequiresInit) {
			if (i< s.length()) init(s.substring(i));
			if (i==s.length()) init("");
		}
	}
}
