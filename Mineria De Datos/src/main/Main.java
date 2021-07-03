package main;

//import java.util.Random;

//import helpers.BD;
import visual.Vista;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Vista principal = new Vista();
		principal.visible(true);
		
		
	//	BD db = BD.getBDD();
		
		
		/*
		int estado;
		for(int i=1;i<=300;i++) {
		estado = 0;
		Random maximo = new Random(); //instance of random class
	    int limite = 4;
	    //generate random values from 0-3
	    int max = maximo.nextInt(limite)+2;
		//System.out.println("---------------------Venta:"+i+",Max:"+max);
			do{
				
				
				for(int j=1;j<=15;j++) {
					Random rand = new Random(); //instance of random class
				    int upperbound = 2;
				    //generate random values from 0-1
				    int int_random = rand.nextInt(upperbound);
				    if(int_random==1) {
				    	if(estado<max) {
				    		estado++;
				    		Random cantidad = new Random(); //instance of random class
				    	    int lim = 10;
				    	    //generate random values from 0-3
				    	    int cant = cantidad.nextInt(lim)+1;
					    	System.out.println("Insert into venta values ("+i+","+j+","+cant+ ")");
					    	Object[] ej = {i,j,cant};
							db.dbPrepareStatement("INSERT INTO venta(id_factura, idproducto, cantidad) VALUES (?,?,?)", ej);
					    }
				    }
				}
			}while(estado<2 || estado>max);
		}
		*/
	}

}
