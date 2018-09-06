import java.sql.*;

public class SQLServerDatabase{
   String uri = "jdbc:sqlserver://theodore.ist.rit.edu;databaseName=Jobs";
   String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
   String user = "330User";
   String password = "330Password";
   Connection conn = null;
   Boolean connected = false;
   
   public Boolean Connect(){
      try {
         Class.forName( driver );
      }
      catch(ClassNotFoundException cnfe ){
         conn = null;
         connected = false;
         return connected;
      }      
      try{
         conn = DriverManager.getConnection( uri, user, password );
         connected = true;
         return connected;
      }
      catch(SQLException sqle){
         conn = null;
         connected = false;
         return connected;
      }
      catch(Exception e){
         conn = null;
         connected = false;
         return connected;
      }
   }
   
   public Boolean Close(){
       try{
         conn.close();
         conn = null;
         connected = false;
         return connected;
      }
      catch(SQLException sqle){
         connected = true;
         return connected;
      }
      catch(Exception e){
         connected = true;
         return connected;
      }
   }
}