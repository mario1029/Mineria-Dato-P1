package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class BD {
	private static BD base_datos= new BD();
	private Connection conn;
	private Statement stmt;
	private PreparedStatement pstmt;
	private ResultSet rs;
	private String driverDB = "org.postgresql.Driver";
	private String dbName = "supermarket";//users
	private String urlDB = "jdbc:postgresql://localhost:5432/"+this.dbName;
	private String userDB = "postgres";//
	private String passDB = "apwmwg.ga";//apwmwg.ga
	
	public BD() {
		try {
			Class.forName(driverDB);
			this.conn = DriverManager.getConnection(urlDB,userDB,passDB);
		}catch (ClassNotFoundException| SQLException e) {
			e.printStackTrace();  
		}
	}
	
	public static BD getBDD() {
		return base_datos;
	}
	
	public ResultSet getProducto(String query, ArrayList<Object[]> lista) {
		try {
			this.stmt = this.conn.createStatement();
			this.rs = this.stmt.executeQuery(query);
				while(rs.next()) {
					Object[] e= {rs.getInt(1),rs.getString(2),rs.getString(4)};
					lista.add(e);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					this.stmt.close();
					this.rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return rs;
		}
	
	public ResultSet getVenta(String query, ArrayList<ArrayList<Object[]>> lista, int index) {
		try {
			this.stmt = this.conn.createStatement();
			this.rs = this.stmt.executeQuery(query);
			ArrayList<Object[]> demo = new ArrayList<Object[]>();
				while(rs.next()) {
					Object[] e= {rs.getInt(1),rs.getInt(2),rs.getInt(3)};
					demo.add(e);
					
				}
			lista.add(demo);
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					this.stmt.close();
					this.rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return rs;
		}
	
	public ResultSet getFactura(String query, ArrayList<Object[]> lista) {
		try {
			this.stmt = this.conn.createStatement();
			this.rs = this.stmt.executeQuery(query);
				while(rs.next()) {
					Object[] e= {rs.getInt(1),rs.getInt(2),rs.getDate(3),rs.getString(4)};
					lista.add(e);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally{
				try {
					this.stmt.close();
					this.rs.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
			return rs;
		}
	
	
	public void dbPrepareStatement(String query, Object[] obj) {

		try {
			this.pstmt=this.conn.prepareStatement(query);
			for(int i=0;i<3;i++) {
				this.pstmt.setInt((i+1), (int)obj[i]);
			}
			this.pstmt.executeUpdate();	
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				this.pstmt.close();
			}catch (SQLException e) {
				e.printStackTrace();
			}
		}

}
	
	public void dbClose() {
		try {
			this.conn.close();
			System.out.println("Conexion cerrada");
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
