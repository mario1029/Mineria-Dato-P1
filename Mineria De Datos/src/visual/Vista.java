package visual;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;

import helpers.BD;

public class Vista {
	private JFrame cuadro;
	private JLabel etiqueta1, etiqueta2;
	private JTextField comando;
	private JPanel panel_general;
	private JButton send, clear;
	private JTextArea consola;
	private JScrollPane scroll;
	private ArrayList<Object[]> producto, facturas;
	private ArrayList<ArrayList<Object[]>> itemSetUno, itemSetDos, itemSetTres, itemSetCuatro, itemSetCinco;
	public Vista() {
		cuadro= new JFrame("Mineria de datos");
		cuadro.setBounds(100, 100, 520, 300);
		cuadro.setVisible(false);
		cuadro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		cuadro.setResizable(true);
		cuadro.setBackground(Color.black);
		
		panel_general = new JPanel();
		panel_general.setLayout(null);
		
		etiqueta1 = new JLabel("Consola:");
		etiqueta1.setBounds(10, 10, 150, 20);
		
		etiqueta2 = new JLabel("Soporte Minimo:");
		etiqueta2.setBounds(10, 220, 150, 25);
		
		comando = new JTextField();
		comando.setBounds(110, 220, 100, 30);
		
		send = new JButton("Iniciar");
		send.setBounds(420, 220, 80, 25);
		
		clear = new JButton("Clear");
		clear.setBounds(420, 190, 80, 25);
		
		consola = new JTextArea(50,20);
		consola.setBounds(70, 10, 350, 200);
		consola.setDisabledTextColor(Color.BLACK);
		consola.setEnabled(false);
		scroll = new JScrollPane(consola);
		scroll.setBounds(70, 10, 350, 200);
		helpConsole();
		
		BD db = BD.getBDD();
		
		send.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				consola.append("SM: "+comando.getText()+"\n");
			//	int sopMin = Integer.parseInt(comando.getText());
			double sopMin=0;
			try {
				sopMin = Double.parseDouble(comando.getText());
			}catch(Exception err){
				sopMin =0;
			}
			comando.setText("");
				
					//item-set1
					producto = new ArrayList<Object[]>();
					db.getProducto("SELECT * FROM producto", producto);
					consola.append("Productos:\n");
					
					for(int i=0;i<producto.size();i++) {
						consola.append("Producto#"+producto.get(i)[0]+":"+producto.get(i)[1]+","+producto.get(i)[2]+"\n");
					}
					
					facturas = new ArrayList<Object[]>();
					
					db.getFactura("SELECT * FROM factura", facturas);
					
					consola.append("\nTotal de facturas:"+facturas.size()+"\n\n");
					
					consola.append("------------------ItemSet1--------------------\n");
					itemSetUno = new ArrayList<ArrayList<Object[]>>();
					for(int i=0;i<producto.size();i++) {
						db.getVenta("SELECT * FROM venta WHERE idproducto ="+producto.get(i)[0], itemSetUno, i);
						consola.append(producto.get(i)[1]+"\nSoporte: "+(itemSetUno.get(i).size()*100.0/facturas.size())+"%\n\n");
					}
					
					consola.append("------------------ItemSet2--------------------\n");
					int total2 = 0;
					itemSetDos = new ArrayList<ArrayList<Object[]>>();
					for(int i=0;i<producto.size();i++) {
						for(int j=i+1;j<producto.size();j++) {
							//System.out.println("Valor["+i+"]"+"["+j+"]");
							db.getVenta("select * from venta where idproducto="+producto.get(i)[0]
									   +" AND id_factura in (SELECT id_factura FROM venta where idproducto="+producto.get(j)[0]+");",itemSetDos, total2);
							
							if((itemSetDos.get(total2).size()*100.0/facturas.size())>(sopMin*100)) {
								consola.append("{"+producto.get(i)[1]+"}"
											  +"{"+producto.get(j)[1]+"}:\n");
								consola.append("Soporte:"+(itemSetDos.get(total2).size()*100.0/facturas.size()+"%")+"\n\n");
								total2++;
							}
						}
					}
					
					consola.append("------------------ItemSet3--------------------\n");
					itemSetTres = new ArrayList<ArrayList<Object[]>>();
					int total3 = 0;
					for(int i=0;i<producto.size();i++) {
						for(int j=i+1;j<producto.size();j++) {
							for(int k=j+1;k<producto.size();k++) {
								//System.out.println("Valor["+i+"]"+"["+j+"]"+"["+k+"]");
								db.getVenta("select * from venta where idproducto="+producto.get(i)[0]
										   +" AND id_factura in (SELECT id_factura FROM venta where idproducto="+producto.get(j)[0]
										   +" AND id_factura in (SELECT id_factura FROM venta WHERE idproducto="+producto.get(k)[0]+" ));",itemSetTres, total3);
								
								if((itemSetTres.get(total3).size()*100.0/facturas.size())>(sopMin*100)) {
									consola.append("{"+producto.get(i)[1]+"}"
											  +"{"+producto.get(j)[1]+"}"
										      +"{"+producto.get(k)[1]+"}"
											  +":\n");
									consola.append("Soporte:"+(itemSetTres.get(total3).size()*100.0/facturas.size()+"%")+"\n\n");
									total3++;
								}
							}
						}
					}
					
					consola.append("------------------ItemSet4--------------------\n");
					itemSetCuatro = new ArrayList<ArrayList<Object[]>>();
					int total4 = 0;
					for(int i=0;i<producto.size();i++) {
						for(int j=i+1;j<producto.size();j++) {
							for(int k=j+1;k<producto.size();k++) {
								for(int z=k+1;z<producto.size();z++) {
									//System.out.println("Valor["+i+"]"+"["+j+"]"+"["+k+"]"+"["+z+"]");
									db.getVenta("select * from venta where idproducto="+producto.get(i)[0]
											+" AND id_factura in (SELECT id_factura FROM venta where idproducto="+producto.get(j)[0]
											+" AND id_factura in (SELECT id_factura FROM venta WHERE idproducto="+producto.get(k)[0]
											+" AND id_factura in (SELECT id_factura FROM venta WHERE idproducto="+producto.get(z)[0]+" )));",itemSetCuatro, total4);
									
									if((itemSetCuatro.get(total4).size()*100.0/facturas.size())>(sopMin*100)) {
										consola.append("{"+producto.get(i)[1]+"}"
												+"{"+producto.get(j)[1]+"}"
												+"{"+producto.get(k)[1]+"}"
												+"{"+producto.get(z)[1]+"}"
												+":\n");
										consola.append("Soporte:"+(itemSetCuatro.get(total4).size()*100.0/facturas.size()+"%")+"\n\n");
										total4++;
									}
								}
							}
						}
					}
					
					consola.append("------------------ItemSet5--------------------\n");
					itemSetCinco = new ArrayList<ArrayList<Object[]>>();
					int total5 = 0;
					for(int i=0;i<producto.size();i++) {
						for(int j=i+1;j<producto.size();j++) {
							for(int k=j+1;k<producto.size();k++) {
								for(int z=k+1;z<producto.size();z++) {
									for(int v=z+1;v<producto.size();v++) {
										
										//System.out.println("Valor["+total5+"]");
										db.getVenta("select * from venta where idproducto="+producto.get(i)[0]
												+" AND id_factura in (SELECT id_factura FROM venta where idproducto="+producto.get(j)[0]
												+" AND id_factura in (SELECT id_factura FROM venta WHERE idproducto="+producto.get(k)[0]
												+" AND id_factura in (SELECT id_factura FROM venta WHERE idproducto="+producto.get(z)[0]
												+" AND id_factura in (SELECT id_factura FROM venta WHERE idproducto="+producto.get(v)[0]+" ))));",itemSetCinco, total5);
										
										if((itemSetCinco.get(total5).size()*100.0/facturas.size())>(sopMin*100)) {
											consola.append("{"+producto.get(i)[1]+"}"
													+"{"+producto.get(j)[1]+"}"
													+"{"+producto.get(k)[1]+"}"
													+"{"+producto.get(z)[1]+"}"
													+"{"+producto.get(v)[1]+"}"
													+":\n");
											consola.append("Soporte:"+(itemSetCinco.get(total5).size()*100.0/facturas.size())+"%"+"\n\n");
											System.out.println("Valor["+i+"]"+"["+j+"]"+"["+k+"]"+"["+z+"]"+"["+v+"]:"+itemSetCinco.get(total5).size());
										}
										total5++;
									}
								}
							}
						}
					}
					
					int total=0;
					
					consola.append("------------------Confianza--------------------\n");
					consola.append("----------------Confianza X->{Y}--------------------\n");
					for(int i=0;i<producto.size();i++) {
						for(int j=i+1;j<producto.size();j++) {
							if((i!=j)) {
//							    confianza x->y = union x e y/ soporte de que ocurra X
								double confianza = itemSetDos.get(total).size()*1.0/ itemSetUno.get(i).size();
								if((confianza!=0) && (itemSetDos.get(total).size()*1.0/producto.size()>(sopMin))) {
									consola.append("C:"+producto.get(i)[1]+" -> "+producto.get(j)[1]+"\n");
									consola.append("Confianza:"+confianza*100+"%\n\n");
								}
							}
							total++;
						}
					}
			
			}
			
		});
		
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearConsole();
				helpConsole();
			}
			
		});
		
		/*
		direccion = new JTextField();
		direccion.setBounds(160, 50,300,25);
		
		direccion.addFocusListener(new FocusListener() {
		    @Override
		    public void focusGained(FocusEvent e) {
		        if (direccion.getText().equals("C:\\Users\\nombre\\Desktop\\ejemplo.txt")) {
		        	direccion.setText("");
		        	direccion.setForeground(Color.BLACK);
		        }
		    }
		    @Override
		    public void focusLost(FocusEvent e) {
		        if (direccion.getText().isEmpty()) {
		        	direccion.setForeground(Color.GRAY);
		        	direccion.setText("C:\\Users\\nombre\\Desktop\\ejemplo.txt");
		        }
		    }
		});*/
		
	
		
		/*encriptar = new JButton("Encriptar");
		encriptar.setBounds(150, 200, 120, 30);
		
		desencriptar = new JButton("Desencriptar");
		desencriptar.setBounds(270, 200, 120, 30);
		
		panel_general.add(encriptar);
		panel_general.add(desencriptar);*/
		panel_general.add(etiqueta1);
		panel_general.add(etiqueta2);
		panel_general.add(comando);
		panel_general.add(send);
		panel_general.add(clear);
		panel_general.add(scroll);
		/*panel_general.add(direccion);
		panel_general.add(salida);*/
		
		cuadro.add(panel_general);
	}
	
	
	
	public void visible(boolean estado) {
		cuadro.setVisible(estado);
	}
	
	private void helpConsole() {
		this.consola.append("Se realizara un analisis por medio del algoritmo a priori\n");
		this.consola.append("Presiona Iniciar para gerar los itemSet\n");
		this.consola.append("Nota: El soporteminimo de base es 0, si se cambia se \n");
		this.consola.append("eliminaran valores de los items sets inferiores \n");
		this.consola.append("El valor a ingresar del soporte minimo puede ir de 0 a 1 \n");
		this.consola.append("Nota: El soporte se presenta en porcentaje 0-100%\n");
		this.consola.append("Nota: Los casos que tenga 0% de probablidad o \n");
		this.consola.append("menos no apareceran\n");
	}
	
	private void clearConsole() {
		this.consola.setText("");
	}
	
}
