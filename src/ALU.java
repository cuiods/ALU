import java.math.BigInteger;


public class ALU {
	
//	public static void main(String[] args){
//		//System.out.println(integerRepresentation(-1+"", 32));
//		//System.out.println(integerTrueValue("10000000"));
//		//System.out.println(negation("10101010"));
//		//System.out.println(leftShift("111101", 1));
//		//System.out.println(rightAriShift("1101001", 2));
//		//System.out.println(rightLogShift("1101001", 2));
//		//System.out.println(fullAdder('1', '0', '0'));
//		//01111100  00100010  00011110
//		//System.out.println(claAdder("01100011", "11111001", '0'));
//		//System.out.println(integerAddition("11111111", "10000000", '0', 8));
//		//System.out.println(integerSubtraction("0000000000000111", "0000000000000111", 16));
//		//System.out.println(integerMultiplication("1001", "1010", 4));
//		//System.out.println(integerDivision("1010", "0010", 4));
//		//System.out.println(floatRepresentation("0.5", 23, 8));
//		//System.out.print(floatTrueValue("10000000000000000000000000000001", 23, 8));
//		//System.out.println(floatMultiplication("10111111000000000000000000000000", "00111110111000000000000000000000", 23, 8));
//		//System.out.println(floatDivision("00111110111000000000000000000000", "10111111000000000000000000000000", 23, 8));
//		//System.out.println(floatAddition("00111111000000000000000000000000", "00111110111000000000000000000000", 23, 8, 5));
//		//System.out.println(floatSubtraction("00111111000000000000000000000000", "10111110111000000000000000000000", 23, 8, 5));
//		//System.out.println(calculation("1.5/(-1.5)="));
//	}
	
	// 1
	public String calculation(String formula){
		String result = "";
		int operation = 0;//0:+  1:-  2:*  3:/
		int cutIndex = 0;
		int type = 0;// 0:int 1:double
		String[] num = {"",""};
		for(int i = 0; i < formula.length(); i++){
			if(formula.charAt(i)=='+'){
				operation = 0;
				cutIndex = i;
				break;
			}
			if(formula.charAt(i)=='-'){
				if(i>0&&formula.charAt(i-1)!='('){
					operation = 1;
					cutIndex = i;
				}else{
					continue;
				}
				break;
			}
			if(formula.charAt(i)=='*'){
				operation = 2;
				cutIndex = i;
				break;
			}
			if(formula.charAt(i)=='/'){
				operation = 3;
				cutIndex = i;
				break;
			}
		}
		if(formula.contains(".")){
			type = 1;
		}
		num[0] = formula.substring(0, cutIndex);
		num[1] = formula.substring(cutIndex+1, formula.length()-1);
		for(int i = 0; i < 2; i++){
			for(int j = 0 ; j < num[i].length(); j++){
				if(num[i].charAt(j)=='('){
					num[i] = num[i].substring(1, num[i].length()-1);
				}
			}
		}
		switch(type){
		case 0:
			for(int i = 0; i < 2; i++){
				num[i] = integerRepresentation(num[i], 32);
			}
			switch(operation){
			case 0:result = integerAddition(num[0], num[1], '0', 32);
			result = result.substring(0, 32);break;
			case 1:result = integerSubtraction(num[0],num[1], 32);
			result = result.substring(0, 32);break;
			case 2:result = integerMultiplication(num[0], num[1], 32);
			result = result.substring(0, 64);break;
			case 3:result = integerDivision(num[0], num[1], 32);
			result = result.substring(0, 32);break;
			}
			result = integerTrueValue(result);
			break;
		case 1:
			for(int i = 0; i < 2; i++){
				num[i] = floatRepresentation(num[i], 23, 8);
				//System.out.println(num[i]); 
			}
			switch(operation){
			case 0:result = floatAddition(num[0], num[1], 23, 8, 0);
			result = result.substring(0, 32);break;
			case 1:result = floatSubtraction(num[0], num[1], 23, 8, 0);
			result = result.substring(0, 32);break;
			case 2:result = floatMultiplication(num[0], num[1], 23, 8);
			break;
			case 3:result = floatDivision(num[0], num[1], 23, 8);break;
			}
			result = floatTrueValue(result, 23, 8);
			break;
		}
		return result;
	}

	// 2
	public String integerRepresentation(String number, int length) {
		String integerRepresentation = "";
		long num = Integer.parseInt(number);
		int sign = num<0?1:0;
		if(sign == 1){num+=Math.pow(2, length);}
		for(int i = 0; i < length; i++){
			integerRepresentation=(num%2)+integerRepresentation;
			num = num/2;
		}
		return integerRepresentation;
	}

	// 3
	public String floatRepresentation(String number, int sLength, int eLength){
		double num = Double.parseDouble(number);
		//judge sign
		String floatRepre = num>=0?"0":"1";
		if(number.charAt(0)=='-'){
			floatRepre = '1'+"";
		}
		//calculate zheng and xiao
		int zheng = Math.abs((int) num);
		double xiao = Math.abs(Math.abs(num) - zheng);
		//temp1:exponent; temp2:mantissa
		String temp1 = Integer.toBinaryString(zheng);
		String temp2 = "";
		String mantissa = "";
		// if num == 0; return
		if(num == 0){
			floatRepre = number.charAt(0)=='-'?'1'+"":'0'+"";
			for(int i = 0; i < eLength+sLength; i++){
				floatRepre+="0";
			}
			return floatRepre;
		}
		//calculate xiao
		for(int i = 0; i < (int)(Math.pow(2, eLength-1)-1+2*sLength); i++){
			if(2*xiao>=1){
				temp2+="1";
				xiao = 2.0*xiao -1;
			}else{
				temp2+="0";
				xiao = 2.0*xiao;
			}
		}
		//System.out.println(temp2);
		//calculate exponent
		temp2 = temp1 +temp2;
		int exponent = 0;
		if(zheng == 0){
			exponent = (int)(Math.pow(2, eLength-1)-1);
		}else{
			exponent = temp1.length()-1+ (int)(Math.pow(2, eLength-1)-1);
			//infinite
			if(temp1.length()-1 == (int)(Math.pow(2, eLength-1))){
				for(int i = 0; i < eLength; i++){floatRepre+="1";}
				for(int i = 0; i < sLength; i++){floatRepre+="0";}
				return floatRepre;
			}
		}
		//calculate mantissa
		if(temp1.equals("0")){
			for(int i = 2; i <= (int)(Math.pow(2, eLength-1)-1+sLength); i++){
				if(temp2.charAt(i-1)!='0'){
					if(i <= (int)(Math.pow(2, eLength-1)-1)){
						exponent -= i-1;
						mantissa = temp2.substring(i, i+sLength+1);
						if(mantissa.charAt(sLength)=='1'){
							mantissa = integerAddition("0"+mantissa, "01", '0', sLength+1).substring(1, 1+sLength);
						}else{
							mantissa = mantissa.substring(0, sLength);
						}
					}else{
						exponent = 0;
						mantissa = temp2.substring(i);
						if(mantissa.length()>sLength){
							if(mantissa.charAt(sLength)=='1'){
								mantissa = integerAddition("0"+mantissa, "01", '0', sLength+1).substring(1, 1+sLength);
							}else{
								mantissa = mantissa.substring(0, sLength);
							}
						}
						while(mantissa.length()<sLength){
							mantissa+="0";
						}
					}
					break;
				}
				if(i==(int)(Math.pow(2, eLength-1)-1+sLength)){
					while(mantissa.length()<sLength){
						mantissa+="0";
					}
				}
			}
		}else{
			mantissa = temp2.substring(1);
//			System.out.println(mantissa);
			int i = 0;
			while(mantissa.length()<sLength){
				mantissa+=temp2.charAt(i);
				i++;
			}
			if(mantissa.length()>sLength){
				mantissa = temp2.substring(1,1+sLength);
				if(temp2.charAt(1+sLength)=='1'){
					mantissa = integerAddition("0"+mantissa, "01", '0', sLength+1).substring(1, 1+sLength);
				}
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
		//System.out.println(floatRepre);
		return floatRepre;
	}

	// 4
	public String ieee754(String number, int length) {
		assert length==32||length==64;
		if(length == 32){
			return floatRepresentation(number, 23, 8);
		}else if(length == 64){
			return floatRepresentation(number, 52, 11);
		}
		return null;
	}

	// 5
	public String integerTrueValue(String operand){
		char cut = operand.charAt(0);
		for(int i = 0; i < operand.length(); i++){
			if(operand.charAt(i)!=cut){
				operand = operand.substring(i-1);
				break;
			}
		}
		long value = 0;
		for(int i = operand.length()-1; i >=0 ; i--){
			value += Integer.parseInt(operand.charAt(i)+"")*Math.pow(2, operand.length()-1-i);
		}
		value -= Integer.parseInt(operand.charAt(0)+"")*Math.pow(2, operand.length());
		return value+"";
	}

	// 6
	public String floatTrueValue(String operand, int sLength, int eLength){
		String sign = "";
		if(operand.charAt(0)=='1'){
			sign+="-";
		}	
		String e = operand.substring(1, 1+eLength);
		String s = operand.substring(1+eLength);
		if(Integer.valueOf(e, 2)==0&&Integer.valueOf(s,2)==0){
			return "0";
		}
		if(Integer.parseInt(integerTrueValue("0"+e))==(Math.pow(2, eLength)-1)){
			for(int i = 0; i < sLength; i++){
				if(s.charAt(i)=='1'){
					return "NaN";
				}
			}
			if(sign.equals("-")){
				return "-Inf";
			}else{
				return "+Inf";
			}
		}
		double value = 1.0;
		if(Integer.parseInt(integerTrueValue("0"+e))==0){
			value = 0;
			for(int i = 0; i < sLength; i++){
				value+=Math.pow(2, -(i+1))*Integer.parseInt(s.charAt(i)+"");
			}
			return sign+value*Math.pow(2, -Math.pow(2, eLength-1)-2);
		}
		for(int i = 0; i < sLength; i++){
			value+=Math.pow(2, -(i+1))*Integer.parseInt(s.charAt(i)+"");
		}
		return sign+value*Math.pow(2, Integer.parseInt(integerTrueValue("0"+e))-(Math.pow(2, eLength-1)-1));
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
	public String leftShift(String operand, int n) {
		int length = operand.length();
		operand = operand.substring(n, length);
//		for(int i = 0; i < n; i++){
//			operand+="0";
//		}
		return operand;
	}

	// 9
	public String rightAriShift(String operand, int n) {
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
	public String fullAdder(char x, char y, char c) {
		char s = xor(xor(x,y),c);
		char c1 = or(or(and(x,c),and(y,c)),and(x,y));
		return (s+"")+(c1+"");
	}
	
	public char xor(char x,char y){
		if(x==y){
			return '0';
		}else{
			return '1';
		}
	}
	
	public char and(char x, char y){
		if(x=='1'&&y=='1'){
			return '1';
		}else{
			return '0';
		}
	}
	
	public char or(char x, char y){
		if(x=='0'&&y=='0'){
			return '0';
		}else{
			return '1';
		}
	}

	// 12
	public String claAdder(String operand1, String operand2, char c) {
		String result = "";
//		for(int i = 7; i >= 0; i--){
//			String str = fullAdder(operand1.charAt(i), operand2.charAt(i), c);
//			result = str.charAt(0)+result;
//			c = str.charAt(1);
//		}
		char[] p = new char[8];
		char[] g = new char[8];
		char[] ci = new char[8];
		char[] results = new char[8];
		for(int i = 0; i < 8; i++){
			p[7-i] = or(operand1.charAt(i),operand2.charAt(i));
			g[7-i] = and(operand1.charAt(i),operand2.charAt(i));
		}
		//ci[0] = or(g[0],and(p[0],c));
		for(int i = 0; i < 8; i++){
			char temp1 = g[i];
			for(int j = 0; j <= i; j++){
				char temp2;
				if(j == 0){
					temp2 = c;
				}else{
					temp2 = g[j-1];
				}
				for(int k = i; k >= j; k--){
					temp2 = and(temp2,p[k]);
				}
				temp1 = or(temp1,temp2);
			}
			ci[i] = temp1;
		}
		for(int i = 0; i < 8; i++){
			if(i == 0){
				results[i] = fullAdder(operand1.charAt(7-i), operand2.charAt(7-i), c).charAt(0);
			}else{
				results[i] = fullAdder(operand1.charAt(7-i), operand2.charAt(7-i), ci[i-1]).charAt(0);
			}
		}
		for(int i = 0; i <8; i++){
			result = results[i]+result;
		}
		return result+(ci[7]+"");
	}

	// 13
	public String integerAddition(String operand1, String operand2, char c, int length) {
//		System.out.println(operand1);
//		System.out.println(operand2);
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
			//System.out.println(result);
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
	public String integerSubtraction(String operand1, String operand2, int length) {
		String neg = integerTrueValue(operand2);
		operand2 = integerRepresentation((-Integer.parseInt(neg))+"", length);
		return integerAddition(operand1, operand2, '0', length);
	}

	// 15
	public String integerMultiplication(String operand1, String operand2, int length) {
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
	public String integerDivision (String operand1, String operand2, int length) {
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
			boolean isZero = false;
			if(operand1.charAt(0)==operand2.charAt(0)){
				String temp = integerSubtraction(operand1.substring(0, length), operand2, length);
				operand1 = temp.substring(0, length) + operand1.substring(length);
			}else{
				String temp = integerAddition(operand1.substring(0, length), operand2, '0', length);
				operand1 = temp.substring(0, length) + operand1.substring(length);
			}
			if(memorySign == operand1.charAt(0)/*||(Integer.parseInt(integerTrueValue(operand1.substring(0,length)))==0)*/){
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
		if(floatTrueValue(operand1, sLength, eLength).equals("0")){
			return operand2;
		}
		if(floatTrueValue(operand2, sLength, eLength).equals("0")){
			return operand1;
		}
		String exponent1 = operand1.substring(1,1+eLength);
		String mantissa1 = operand1.substring(1+eLength);
		String exponent2 = operand2.substring(1,1+eLength);
		String mantissa2 = operand2.substring(1+eLength);
		if(Integer.parseInt(exponent1)!=0){
			mantissa1 = "1"+mantissa1;
		}else{
			mantissa1 = "0"+mantissa1;
		}
		if(Integer.parseInt(exponent2)!=0){
			mantissa2 = "1"+mantissa2;
		}else{
			mantissa2 = "0"+mantissa2;
		}

		if(Integer.parseInt(integerTrueValue("0"+exponent1))!=Integer.parseInt(integerTrueValue("0"+exponent2))){
			int distance = Math.abs(Integer.parseInt(integerTrueValue("0"+exponent1))-Integer.parseInt(integerTrueValue("0"+exponent2)));
			if(Integer.parseInt(integerTrueValue("0"+exponent1))>Integer.parseInt(integerTrueValue("0"+exponent2))){
				exponent2 = integerAddition("0"+exponent2,"0"+Integer.toBinaryString(distance), '0', eLength+1).substring(1, 1+eLength);
				mantissa2 = rightLogShift(mantissa2, distance);
			}else{
				exponent1 = integerAddition("0"+exponent1, "0"+Integer.toBinaryString(distance), '0', eLength+1).substring(1, 1+eLength);
				mantissa1 = rightLogShift(mantissa1, distance);
			}
		}
		sLength+=1;
		while(mantissa2.length()<sLength+gLength){
			mantissa2+="0";
		}
		while(mantissa1.length()<sLength+gLength){
			mantissa1+="0";
		}
		mantissa1 = mantissa1.substring(0, sLength+gLength);
		mantissa2 = mantissa2.substring(0, sLength+gLength);
		String exponent = exponent1;
		String mantissa = "";
		String sign = "";
		char yichu = '0';
		if(operand1.charAt(0)==operand2.charAt(0)){
			sign = operand1.charAt(0)+"";
			String temp = integerAddition("0"+mantissa1, "0"+mantissa2, '0', sLength+gLength+1);
			if(temp.charAt(0)=='1'){
				mantissa = temp.substring(1, sLength+1);
				String temp1 = integerAddition("0"+exponent, "01",'0',  eLength+1);
				exponent = temp1.substring(1, eLength+1);
				yichu = temp1.charAt(0);
			}else{
				yichu = '0';
				for(int i = 1; i < sLength+gLength+1; i++){
					if(temp.charAt(i)=='1'){
						mantissa = temp.substring(i+1);
						exponent = integerSubtraction("0"+exponent, "0"+Integer.toBinaryString(i-1),  eLength+1).substring(1, 1+eLength);
						if(mantissa.length()>sLength){
							mantissa = mantissa.substring(0, sLength);
						}else if(mantissa.length()<sLength){
							while(mantissa.length()<sLength){
								mantissa += "0";
							}
						}
						break;
					}	
				}
			}
		}else{
			if(operand1.charAt(0)=='0'&&operand2.charAt(0)=='1'){
				mantissa2 = complement(mantissa2);
			}else{
				mantissa1 = complement(mantissa1);		
			}
			String sum = "";
			char c = '0';
			for(int i = mantissa1.length()-1; i>=0; i--){
				String temp = fullAdder(mantissa1.charAt(i), mantissa2.charAt(i), c);
				sum = temp.charAt(0)+sum;
				c = temp.charAt(1);
				if(i == 0){
					if(c=='1'){
						mantissa = sum;
						sign = "0";
					}else{
						mantissa = complement(sum);
						sign = "1";
					}
				}
			}
			sLength-=1;
			for(int i = 0; i < mantissa.length(); i++){
				if(mantissa.charAt(i)=='1'){
					mantissa = mantissa.substring(i+1);
					String temp = integerSubtraction("0"+exponent, "0"+Integer.toBinaryString(i), eLength+1);
					exponent = temp.substring(1, 1+eLength);
					yichu = '0';
					break;
				}
				if(i == mantissa.length()-1){
					mantissa = "0";
					exponent = integerSubtraction(exponent, exponent, eLength);
					yichu = '0';
				}
			}
			while(mantissa.length()<sLength){
				mantissa+="0";
			}
			if(mantissa.length()>sLength){
				mantissa = mantissa.substring(0,sLength);
			}
		}
		return sign+exponent+mantissa+yichu;
	}
	
	public String complement(String s){
		int index = s.length();
		for(int i = s.length()-1; i>=0; i--){
			if(s.charAt(i)=='1'){
				index = i;
				break;
			}
		}
		return negation(s.substring(0, index))+s.substring(index);
	}

	// 18
	public String floatSubtraction(String operand1, String operand2,
			int sLength, int eLength, int gLength) {
		if(operand2.charAt(0)=='0'){
			operand2 = "1" + operand2.substring(1);
		}else{
			operand2 = "0" + operand2.substring(1);
		}
		return floatAddition(operand1, operand2, sLength, eLength, gLength);
	}

	// 19
	public String floatMultiplication(String operand1, String operand2, 
			int sLength, int eLength) {
		for(int i = 1; i < operand1.length(); i++){
			if(operand1.charAt(i)=='1'){
				break;
			}
			if(i == operand1.length() - 1){
				return "0";
			}
		}
		for(int i = 1; i < operand2.length(); i++){
			if(operand2.charAt(i)=='1'){
				break;
			}
			if(i == operand2.length() - 1){
				return "0";
			}
		}
		String exponent1 = operand1.substring(1,1+eLength);
		String mantissa1 = operand1.substring(1+eLength);
		String exponent2 = operand2.substring(1,1+eLength);
		String mantissa2 = operand2.substring(1+eLength);
		String temp1 = integerAddition("0"+exponent1, "0"+exponent2, '0', exponent1.length()+1);
		String exponent = integerSubtraction("0"+temp1.substring(0,exponent1.length()+1), integerRepresentation(""+(int)(Math.pow(2, eLength-1)-1), exponent1.length()+2), exponent1.length()+2).substring(2,2+eLength);
		//System.out.println(exponent);
		String mantissa="";
		if(Integer.parseInt(exponent)!=0){
			mantissa = integerMultiplication("01"+mantissa1, "01"+mantissa2, sLength+2);
		}else{
			mantissa = integerMultiplication("00"+mantissa1, "00"+mantissa2, sLength+2);
		}
		if(mantissa.charAt(2)=='1'){
			mantissa = mantissa.substring(3,4+sLength);
			if(mantissa.charAt(sLength)=='1'){
				mantissa = integerAddition("0"+mantissa, "01", '0', sLength+2).substring(1, 1+sLength);
			}else{
				mantissa = mantissa.substring(0, sLength);
			}
			exponent = integerAddition("0"+exponent, "01", '0', eLength+1).substring(1, 1+eLength);
		}else{
			for(int i = 3; i < sLength + 2; i++){
				if(mantissa.charAt(i)=='1'){
					mantissa = mantissa.substring(i+1,i+2+sLength);
					if(mantissa.charAt(sLength)=='1'){
						mantissa = integerAddition("0"+mantissa, "01", '0', sLength+2).substring(1, 1+sLength);
					}else{
						mantissa = mantissa.substring(0, sLength);
					}
					exponent = integerSubtraction("0"+exponent, integerRepresentation(i-3+"", eLength+1),  eLength+1).substring(1,1+eLength);
					break;
				}
			}
		}
		//11000010000001000000000000000000
		//System.out.println(xor(operand1.charAt(0), operand2.charAt(0))+""+exponent+mantissa);
		return xor(operand1.charAt(0), operand2.charAt(0))+""+exponent+mantissa;
	}
	
	//20
	public String floatDivision(String operand1, String operand2, 
			int sLength, int eLength) {
		for(int i = 1; i < operand1.length(); i++){
			if(operand1.charAt(i)=='1'){
				break;
			}
			if(i == operand1.length() - 1){
				return "0";
			}
		}
		for(int i = 1; i < operand2.length(); i++){
			if(operand2.charAt(i)=='1'){
				break;
			}
			if(i == operand2.length() - 1){
				return "Inf";
			}
		}
		String exponent1 = operand1.substring(1,1+eLength);
		String mantissa1 = operand1.substring(1+eLength);
		String exponent2 = operand2.substring(1,1+eLength);
		String mantissa2 = operand2.substring(1+eLength);
		String temp1 = integerAddition("0"+exponent1, integerRepresentation((int)(Math.pow(2, eLength-1)-1)+"",eLength+1), '0', eLength+1).substring(0, eLength+1);
		String exponent = integerSubtraction("0"+temp1, "00"+exponent2, eLength+2).substring(2, eLength+2);
		//System.out.println(exponent);
		for(int i = 0; i < sLength; i++){
			mantissa1+="0";
		}
		if(Integer.parseInt(exponent)!=0){
			mantissa1 = "01"+mantissa1;
			mantissa2 = "1"+mantissa2;
		}else{
			mantissa1 = "00"+mantissa1;
			mantissa2 = "0"+mantissa2;
		}
		//System.out.println(mantissa1);
		//System.out.println(mantissa2);
		for(int i = 0; i < sLength+1; i++){
			String remainder = "";
			boolean isEnough = false;
			mantissa1 = leftShift(mantissa1, 1);
			remainder = mantissa1.substring(0, sLength+1);
			for(int j = 0; j < sLength+1; j++){
                if(mantissa2.charAt(j)=='1'&&remainder.charAt(j)=='0'){
					break;
				}
				if(mantissa2.charAt(j)=='0'&&remainder.charAt(j)=='1'){
					isEnough = true;
					break;
				}
				if(j==sLength){
					isEnough = true;
				}
			}
			if(isEnough){
				remainder = integerSubtraction("0"+remainder, "0"+mantissa2, sLength+2).substring(1, sLength+2);
				mantissa1 = remainder+mantissa1.substring(sLength+1);
				mantissa1 += "1";
			}else{
				mantissa1 += "0";
			}
		}
		String mantissa = mantissa1.substring(sLength+1);
		for(int i = 0;i < sLength+1; i++ ){
			if(mantissa.charAt(i)=='1'){
				mantissa = mantissa.substring(i+1);
				exponent = integerSubtraction("0"+exponent, integerRepresentation(i+"", eLength+1),  eLength+1).substring(1,1+eLength);
				break;
			}
			if(i==sLength){
				mantissa = "0";
			}
		}
		while(mantissa.length()<sLength){
			mantissa+="0";
		}
		return xor(operand1.charAt(0), operand2.charAt(0))+""+exponent+mantissa;
	}
}
