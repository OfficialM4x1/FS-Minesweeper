import java.sql.*;

public class DataBase {
   /**
    * This class exists for testing purposes only. Here we tried different test cases
    * @param args
    */
   public static void main( String args[] ) {
      

      Connection c = null;
      Statement stmt = null;                                    
                                                               
      try {
         Class.forName("org.sqlite.JDBC");
         c = DriverManager.getConnection("jdbc:sqlite:leaderboard.db");
         c.setAutoCommit(false);
         System.out.println("Opened database successfully");
   
         stmt = c.createStatement();
         ResultSet rs = stmt.executeQuery( "SELECT * FROM PLAYERS;" );
         
         while ( rs.next() ) {
            int id = rs.getInt("id");
            String  name = rs.getString("name");
            int time  = rs.getInt("time");
            
            System.out.println( "ID = " + id );
            System.out.println( "NAME = " + name );
            System.out.println( "TIME = " + time );
            System.out.println();
         }
         rs.close();
         stmt.close();
         c.close();
      } catch ( Exception e ) {
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
         System.exit(0);
      }
}
}
