package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnectionFactory {
	
	//classe q sera Fabrica de conexoes 
	//exclusivamente vai ser invocado direto do banco de dados, apenas essa função
	// vai ser criado um método estático que vai criar uma conexão 
	
		public static Connection obterConexao() {
			
			try {
				return DriverManager.getConnection("jdbc:mysql://localhost/taking", "root", "Luciana@2023");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
				return null;
			}
						
		}
		
}
