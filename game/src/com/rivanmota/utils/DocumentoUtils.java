package com.rivanmota.utils;

public class DocumentoUtils {

	private static final int CPF_LENGTH  = 11;
	private static final int CNPJ_LENGTH = 14;
	
	public static boolean isCPFValido(String strCpf) {
	    int iDigito1Aux = 0, iDigito2Aux = 0, iDigitoCPF;
	    int iDigito1 = 0, iDigito2 = 0, iRestoDivisao = 0;
	    String strDigitoVerificador, strDigitoResultado;
	 
	    if (strCpf.equals("00000000000") || strCpf.equals("11111111111") || strCpf.equals("22222222222") || 
	    		strCpf.equals("33333333333") || strCpf.equals("44444444444") || strCpf.equals("55555555555") || 
	    		strCpf.equals("66666666666") || strCpf.equals("77777777777") || strCpf.equals("88888888888") || 
	    		strCpf.equals("99999999999") || (strCpf.length() != CPF_LENGTH)) return(false);
	    
	    if (strCpf.equals("000.000.000-00") || strCpf.equals("111.111.111-11") || strCpf.equals("222.222.222-22") || 
	    		strCpf.equals("333.333.333-33") || strCpf.equals("444.444.444-44") || strCpf.equals("555.555.555-55") || 
	    		strCpf.equals("666.666.666-66") || strCpf.equals("777.777.777-77") || strCpf.equals("888.888.888-88") || 
	    		strCpf.equals("999.999.999-99") || (strCpf.length() != CPF_LENGTH)) return(false);
	    
	    if (! strCpf.substring(0,1).equals("")){
	        try{
	            strCpf = strCpf.replace('.',' ');
	            strCpf = strCpf.replace('-',' ');
	            strCpf = strCpf.replaceAll(" ","");
	            for (int iCont = 1; iCont < strCpf.length() -1; iCont++) {
	                iDigitoCPF = Integer.valueOf(strCpf.substring(iCont -1, iCont)).intValue();
	                iDigito1Aux = iDigito1Aux + (11 - iCont) * iDigitoCPF;
	                iDigito2Aux = iDigito2Aux + (12 - iCont) * iDigitoCPF;
	            }
	            iRestoDivisao = (iDigito1Aux % 11);
	            if (iRestoDivisao < 2) {
	                iDigito1 = 0;
	            } else {
	                iDigito1 = 11 - iRestoDivisao;
	            }
	            iDigito2Aux += 2 * iDigito1;
	            iRestoDivisao = (iDigito2Aux % 11);
	            if (iRestoDivisao < 2) {
	                iDigito2 = 0;
	            } else {
	                iDigito2 = 11 - iRestoDivisao;
	            }
	            strDigitoVerificador = strCpf.substring(strCpf.length()-2, strCpf.length());
	            strDigitoResultado = String.valueOf(iDigito1) + String.valueOf(iDigito2);
	            return strDigitoVerificador.equals(strDigitoResultado);
	        } catch (Exception e) {
	            return false;
	        }
	    } else {
	        return false;
	    }
	}
	
	public static boolean isCNPJValido(String strCNPJ) {
	    int iSoma = 0, iDigito;
	    char[] chCaracteresCNPJ;
	    String strCNPJ_Calculado;
	 
	    if (! strCNPJ.substring(0,1).equals("")){
	        try{
	            strCNPJ=strCNPJ.replace('.',' ');
	            strCNPJ=strCNPJ.replace('/',' ');
	            strCNPJ=strCNPJ.replace('-',' ');
	            strCNPJ=strCNPJ.replaceAll(" ","");
	            strCNPJ_Calculado = strCNPJ.substring(0,12);
	            if ( strCNPJ.length() != CNPJ_LENGTH ) return false;
	            chCaracteresCNPJ = strCNPJ.toCharArray();
	            for(int i = 0; i < 4; i++) {
	                if ((chCaracteresCNPJ[i]-48 >= 0) && (chCaracteresCNPJ[i]-48 <= 9)) {
	                    iSoma += (chCaracteresCNPJ[i] - 48 ) * (6 - (i + 1)) ;
	                }
	            }
	            for( int i = 0; i < 8; i++ ) {
	                if ((chCaracteresCNPJ[i+4]-48 >= 0) && (chCaracteresCNPJ[i+4]-48 <= 9)) {
	                    iSoma += (chCaracteresCNPJ[i+4] - 48 ) * (10 - (i + 1)) ;
	                }
	            }
	            iDigito = 11 - (iSoma % 11);
	            strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
	               /* Segunda parte */
	            iSoma = 0;
	            for (int i = 0; i < 5; i++) {
	                if ((chCaracteresCNPJ[i]-48 >= 0) && (chCaracteresCNPJ[i]-48 <= 9)) {
	                    iSoma += (chCaracteresCNPJ[i] - 48) * (7 - (i + 1)) ;
	                }
	            }
	            for (int i = 0; i < 8; i++) {
	                if ((chCaracteresCNPJ[i+5]-48 >= 0) && (chCaracteresCNPJ[i+5]-48 <= 9)) {
	                    iSoma += (chCaracteresCNPJ[i+5] - 48) * (10 - (i + 1)) ;
	                }
	            }
	            iDigito = 11 - (iSoma % 11);
	            strCNPJ_Calculado += ((iDigito == 10) || (iDigito == 11)) ? "0" : Integer.toString(iDigito);
	            return strCNPJ.equals(strCNPJ_Calculado);
	        } catch (Exception e) {
	            return false;
	        }
	    } else return false;
	}
	
	public static boolean isCPF_CNPJValido(String cpfCnpj) {
		
		cpfCnpj = StringUtils.removerPontosETracosEBarraDoCPF_CNPJ(cpfCnpj);
		
		switch (cpfCnpj.length()) {
			case CPF_LENGTH:
				return DocumentoUtils.isCPFValido(cpfCnpj);
				
			case CNPJ_LENGTH:
				return DocumentoUtils.isCNPJValido(cpfCnpj);
		}
		
		return false;
	}
}
