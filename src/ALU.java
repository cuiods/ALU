
public class ALU {
	
	public static void main(String[] args){
		//System.out.println(integerRepresentation(-1+"", 8));
		//System.out.println(integerTrueValue("10000000"));
		//System.out.println(negation("10101010"));
		//System.out.println(leftShift("111101", 1));
		//System.out.println(rightAriShift("1101001", 2));
		//System.out.println(rightLogShift("1101001", 2));
		//System.out.println(fullAdder('1', '0', '0'));
		//System.out.println(claAdder("11111111", "00000001", '1'));
		//System.out.println(integerAddition("1100000000000011", "0100000100000001", '0', 18));
		//System.out.println(integerSubtraction("0000000000000111", "1000000000000011", 16));
		//System.out.println(integerMultiplication("1001", "1010", 4));
		//System.out.println(integerDivision("0111", "0011", 5));
		//System.out.println(floatRepresentation("-0.4375", 52, 11));
	}
	
	// 1
	public String calculation(String formula){
		return null;
	}

	// 2
	public static String integerRepresentation(String number, int length) {
		String integerRepresentation = "";
		int num = Integer.parseInt(number);
		int sign = num<0?1:0;
		if(sign == 1){num+=Math.pow(2, length);}
		for(int i = 0; i < length; i++){
			integerRepresentation=(num%2)+integerRepresentation;
			num = num/2;
		}
		return integerRepresentation;
	}

	// 3
	public static String floatRepresentation(String number, int sLength, int eLength){
		double num = Double.parseDouble(number);
		String floatRepre = num>=0?"0":"1";
		int zheng = Math.abs((int) num);
		double xiao = Math.abs(num - zheng);
		String temp1 = Integer.toBinaryString(zheng);
		String temp2 = "";
		String mantissa = "";
		for(int i = 0; i < (int)(Math.pow(2, eLength-1)-2+sLength); i++){
			if(2*xiao>=1){
				temp2+="1";
				xiao = 2.0*xiao -1;
			}else{
				temp2+="0";
				xiao = 2.0*xiao;
			}
		}
		int exponent = temp1.length()-1+ (int)(Math.pow(2, eLength-1)-1);
		if(temp1.equals("0")){
			for(int i = 1; i <= (int)(Math.pow(2, eLength-1)-2+sLength); i++){
				if(temp2.charAt(i-1)!='0'){
					if(i <= (int)(Math.pow(2, eLength-1)-2)){
						exponent -= i;
						mantissa = temp2.substring(i, i+sLength);
					}else{
						exponent = 0;
						mantissa = temp2.substring(i-1);
						while(mantissa.length()<sLength){
							mantissa+="0";
						}
					}
					break;
				}
				if(i==(int)(Math.pow(2, eLength-1)-2+sLength)){
					while(mantissa.length()<sLength){
						mantissa+="0";
					}
				}
			}
		}else{
			mantissa = temp1.substring(1);
//			System.out.println(mantissa);
			int i = 0;
			while(mantissa.length()<sLength){
				mantissa+=temp2.charAt(i);
				i++;
			}
			if(mantissa.length()>sLength){
				mantissa = temp1.substring(1,1+sLength);
			}
		}
		String expo = Integer.toBinaryString(exponent);
		while(expo.length()<eLength){
			expo = "0" + expo;
		}
//		System.out.println(expo);
//		System.out.println(mantissa);
//		System.out.println(temp2.substring(0,sLength));
		floatRepre+=(expo+mantissa);
	    
		return floatRepre;
	}

	// 4
	public static String ieee754(String number, int length) {
		if(length == 32){
			return floatRepresentation(number, 23, 8);
		}else if(length == 64){
			return floatRepresentation(number, 52, 11);
		}
		return null;
	}

	// 5
	public static String integerTrueValue(String operand){
		int value = 0;
		for(int i = operand.length()-1; i >=0 ; i--){
			value += Integer.parseInt(operand.charAt(i)+"")*Math.pow(2, operand.length()-1-i);
		}
		value -= Integer.parseInt(operand.charAt(0)+"")*Math.pow(2, operand.length());
		return value+"";
	}

	// 6
	public String floatTrueValue(String operand, int sLength, int eLength){
		String floatValue = "";
		boolean isNegative = false;
		if(operand.charAt(0)=='1'){
			isNegative = true;
			floatValue+="-";
		}
		String e = operand.substring(1, 1+eLength);
		String s = operand.substring(1+eLength);
		
		return floatValue;
	}

	// 7
	public String negation(String operand) {
		String negation = "";
		for(int i = 0; i < operand.length(); i++){
			if(operand.charAt(i)=='1'){
				negation+="0";
			}else{
				negation+="1";
			}
		}
		return negation;
	}

	// 8
	public static String leftShift(String operand, int n) {
		int length = operand.length();
		operand = operand.substring(n, length);
//		for(int i = 0; i < n; i++){
//			operand+="0";
//		}
		return operand;
	}

	// 9
	public static String rightAriShift(String operand, int n) {
		//operand = operand.substring(0, operand.length()-n);
		String ariAdd = operand.charAt(0)+"";
		for(int i = 0; i < n; i++){
			operand = ariAdd + operand;
		}
		return operand;
	}

	// 10
	public String rightLogShift(String operand, int n){
		//operand = operand.substring(0, operand.length()-n);
		for(int i = 0; i < n; i++){
			operand = "0" + operand;
		}
		return operand;
	}

	// 11
	public static String fullAdder(char x, char y, char c) {
		char s = xor(xor(x,y),c);
		char c1 = or(or(and(x,c),and(y,c)),and(x,y));
		return (s+"")+(c1+"");
	}
	
	public static char xor(char x,char y){
		if(x==y){
			return '0';
		}else{
			return '1';
		}
	}
	
	public static char and(char x, char y){
		if(x=='1'&&y=='1'){
			return '1';
		}else{
			return '0';
		}
	}
	
	public static char or(char x, char y){
		if(x=='0'&&y=='0'){
			return '0';
		}else{
			return '1';
		}
	}

	// 12
	public static String claAdder(String operand1, String operand2, char c) {
		String result = "";
		for(int i = 7; i >= 0; i--){
			String str = fullAdder(operand1.charAt(i), operand2.charAt(i), c);
			result = str.charAt(0)+result;
			c = str.charAt(1);
		}
		return result+(c+"");
	}

	// 13
	public static String integerAddition(String operand1, String operand2, char c, int length) {
		String result = "";
		for(int i = 0; i < length; i++){
			if(operand1.length()<length){
				operand1 = operand1.charAt(0)+operand1;
			}
			if(operand2.length()<length){
				operand2 = operand2.charAt(0)+operand2;
			}
		}
		for(int i = length/8-1; i >= 0; i--){
			String temp = "";
			if(i == 0){
				temp = claAdder(operand1.substring(i*8+length%8, i*8+8+length%8), operand2.substring(i*8+length%8, i*8+8+length%8), c);
			}else if(i == length/8-1){
				temp = claAdder(operand1.substring(i*8+length%8), operand2.substring(i*8+length%8), c);
			}else{
				temp = claAdder(operand1.substring(i*8+length%8, i*8+9+length%8), operand2.substring(i*8+length%8, i*8+9+length%8), c);
			}
			result = temp.substring(0, 8) + result;
			c = temp.charAt(8);
		}
		for(int i = length%8-1; i>=0; i--){
			String str = fullAdder(operand1.charAt(i), operand2.charAt(i), c);
			result = str.charAt(0)+result;
			c = str.charAt(1);
		}
		if(operand1.charAt(0)!=operand2.charAt(0)){
			c = '0';
		}else if(operand1.charAt(0)==result.charAt(0)){
			c = '0';
		}else{
			c = '1';
		}
		return result+c;
	}

	// 14
	public static String integerSubtraction(String operand1, String operand2, int length) {
		String neg = integerTrueValue(operand2);
		operand2 = integerRepresentation((-Integer.parseInt(neg))+"", length);
		return integerAddition(operand1, operand2, '0', length);
	}

	// 15
	public static String integerMultiplication(String operand1, String operand2, int length) {
		for(int i = 0; i < length; i++){
			if(operand1.length()<length){
				operand1 = operand1.charAt(0)+operand1;
			}
			if(operand2.length()<length){
				operand2 = operand2.charAt(0)+operand2;
			}
		}
		String product = "";
		for(int i = 0; i < length; i++){
			product+="0";
		}
		operand2 += "0";
		for(int i = 0; i < length; i++){
			String temp = "";
			if(operand2.charAt(length-i)=='0'&&operand2.charAt(length-i-1)=='1'){
				temp = integerSubtraction(product.substring(0, length), operand1, length);
				product = temp.substring(0, length) + product.substring(length);
			}
			if(operand2.charAt(length-i)=='1'&&operand2.charAt(length-i-1)=='0'){
				temp = integerAddition(product.substring(0, length), operand1, '0',length);
				product = temp.substring(0, length) + product.substring(length);
			}
			product = rightAriShift(product, 1);
		}
		return product;
	}

	// 16
	public static String integerDivision (String operand1, String operand2, int length) {
		for(int i = 0; i < length; i++){
			if(operand1.length()<length){
				operand1 = operand1.charAt(0)+operand1;
			}
			if(operand2.length()<length){
				operand2 = operand2.charAt(0)+operand2;
			}
		}
		boolean isSameSign = true;
		if(operand1.charAt(0)!=operand2.charAt(0)){
			isSameSign = false;
		}
		for(int i = 0; i < length; i++){
			operand1=operand1.charAt(0)+operand1;
		}
		for(int i = 0; i < length; i++){
			operand1 = leftShift(operand1, 1);
			char memorySign = operand1.charAt(0);
			String memoryStr = operand1;
			if(operand1.charAt(0)==operand2.charAt(0)){
				String temp = integerSubtraction(operand1.substring(0, length), operand2, length);
				operand1 = temp.substring(0, length) + operand1.substring(length);
			}else{
				String temp = integerAddition(operand1.substring(0, length), operand2, '0', length);
				operand1 = temp.substring(0, length) + operand1.substring(length);
			}
			if(memorySign == operand1.charAt(0)||Integer.parseInt(integerTrueValue(operand1.substring(0,length)))==0){
				operand1+="1";
			}else{
				operand1 = memoryStr;
				operand1+="0";
			}
		}
		if(!isSameSign){
			int quotientTemp = -Integer.parseInt(integerTrueValue(operand1.substring(length)));
			operand1 = operand1.substring(0,length)+integerRepresentation(quotientTemp+"", length);
		}
		operand1 = operand1.substring(length)+operand1.substring(0,length);
		return operand1;
	}

	// 17
	public String floatAddition(String operand1, String operand2, int sLength,
			int eLength, int gLength) {
		return null;
	}

	// 18
	public String floatSubtraction(String operand1, String operand2,
			int sLength, int eLength, int gLength) {
		return null;
	}

	// 19
	public String floatMultiplication(String operand1, String operand2, 
			int sLength, int eLength) {
		return null;
	}
	
	//20
	public String floatDivision(String operand1, String operand2, 
			int sLength, int eLength) {
		return null;
	}
}
