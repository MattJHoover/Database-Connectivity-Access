import java.sql.*;

import java.util.*;

public class MySQLDatabase{
   String uri = "jdbc:mysql://127.0.0.1/travel?&useSSL=false";
   String driver = "com.mysql.jdbc.Driver";
   String user = "root";
   String password = "student";
   Connection conn = null;
   Boolean connected = false;
   ArrayList<String> exception = new ArrayList<String>();
   ArrayList<ArrayList<String>> exceptionList = new ArrayList<ArrayList<String>>();
  
   public Boolean Connect() throws DLException{
      try {
         Class.forName( driver );
      }
      catch(ClassNotFoundException cnfe ){
         conn = null;
         connected = false;
         exception.add("Could not find driver to connect to the MySQL database: " + cnfe.toString());
         exceptionList.add(exception);
         throw new DLException(cnfe, exceptionList);
      }      
      try{
         conn = DriverManager.getConnection( uri, user, password );
         connected = true;
         return connected;
      }
      catch(SQLException sqle ){
         conn = null;
         connected = false;
         exception.add("Failed to connect to the MySQL database: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public Boolean Close() throws DLException{
       try{
         conn.close();
         conn = null;
         connected = false;
         return connected;
      }
      catch(SQLException sqle ){
         connected = true;
         exception.add("Failed to close to the MySQL database: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public ArrayList<ArrayList<String>> getData(String SQL) throws DLException{
      ArrayList<ArrayList<String>> result = new ArrayList<>();
      try{
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(SQL);
         ResultSetMetaData rsmd = rs.getMetaData();
         int numFields = rsmd.getColumnCount();
         getData(stmnt, rs, rsmd, numFields, SQL, result);
         return result;
      }
      catch(SQLException sqle){
         exception.add("Failed to execute the MySQL query: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public boolean setData(String SQL) throws DLException{
      try{         
         Statement stmnt = conn.createStatement();
         stmnt.execute(SQL);
         return true;
      }
      catch(SQLException sqle){
         exception.add("Failed to execute the MySQL command: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public void descTable(String SQL) throws DLException{
      ArrayList<String> nameArray = new ArrayList<String>();
      try{
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(SQL);
         ResultSetMetaData rsmd = rs.getMetaData();
         int numFields = rsmd.getColumnCount();
         System.out.println();
         System.out.println("Result of the query: " + SQL.trim().replaceAll("\\s+", " "));
         System.out.println("Fields retrieved: " + numFields);
         System.out.printf("%-25s  %-25s%n", "Column Name", "Column Type");
         for(int i = 1; i <= numFields; i++){
            String fieldName = rsmd.getColumnName(i);
            String colType = rsmd.getColumnTypeName(i);
            System.out.printf("%-25s  %-25s%n", fieldName, colType);
         }
         System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
         for(int i = 1; i <= numFields; i++){
            String fieldName = rsmd.getColumnName(i);
            int colSize = rsmd.getPrecision(i);
            int colWidth = colSize + 25;
            if(i == numFields){
               System.out.printf("%-" + colWidth + "s%n", fieldName);
            }
            else{
               System.out.printf("%-" + colWidth + "s", fieldName);
            }
         }
         System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
         int rows = 0;
         while (rs.next()){
            for(int i = 1; i <= numFields; i++){
               int colSize = rsmd.getPrecision(i);
               int colWidth = colSize + 25;
               String colValue = rs.getString(i);
               if(i == numFields){
                  System.out.printf("%-" + colWidth + "s%n", colValue);
                  rows++;
               }
               else{
                  System.out.printf("%-" + colWidth + "s", colValue);
               }
            }
         }
         System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
         System.out.println(rows + " rows in set");
         System.out.println("+-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------+");
         System.out.println();

      }
      catch(SQLException sqle){
         exception.add("Failed to execute the MySQL query: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public void getColumnData(Statement stmt, ResultSet rs, ResultSetMetaData rsmd, int numCols, String SQL, ArrayList<String> col, ArrayList<ArrayList<String>> result) throws DLException{
      try{
         for(int i = 1; i <= numCols; i++){
            String colName = rsmd.getColumnName(i);
            col.add(colName);
         }
         result.add(col);
      }
      catch(SQLException sqle){
         exception.add("Failed to execute the MySQL query: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public void getData(Statement stmt, ResultSet rs, ResultSetMetaData rsmd, int numCols, String SQL, ArrayList<ArrayList<String>> result) throws DLException{
      try{
         while(rs.next()){
            ArrayList<String> row = new ArrayList<>(numCols);
            for(int i = 1; i <= numCols; i++){
               row.add(rs.getString(i));
            }
            result.add(row);
         }
      }
      catch(SQLException sqle){
         exception.add("Failed to execute the MySQL query: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public ArrayList<ArrayList<String>> getData(String SQL, Boolean value) throws DLException{
      ArrayList<ArrayList<String>> result = new ArrayList<>();
      ArrayList<String> col = new ArrayList<>();
      try{
         Statement stmnt = conn.createStatement();
         ResultSet rs = stmnt.executeQuery(SQL);
         ResultSetMetaData rsmd = rs.getMetaData();
         int numCols = rsmd.getColumnCount();
         if(value == true){
            getColumnData(stmnt, rs, rsmd, numCols, SQL, col, result);
            getData(stmnt, rs, rsmd, numCols, SQL, result);
            return result;
         }
         else{
            getData(stmnt, rs, rsmd, numCols, SQL, result);
            return result;
         }
      }
      catch(SQLException sqle){
         exception.add("Failed to execute the MySQL query: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public PreparedStatement prepare(String SQL, ArrayList<String> values) throws DLException{
      try{
         PreparedStatement stmt = conn.prepareStatement(SQL);
         for(int i = 0; i < values.size(); i++){
            stmt.setString(i+1, values.get(i));
         }
         return stmt;
      }
      catch(SQLException sqle){
         exception.add("Failed to execute prepared statement: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      } 
   } 
   
   public ArrayList<ArrayList<String>> getData(String SQL, ArrayList<String> values) throws DLException{
      try{
         PreparedStatement stmt = prepare(SQL, values);
         ResultSet rs = stmt.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         int numFields = rsmd.getColumnCount();
         ArrayList<String> row = new ArrayList<>();
         ArrayList<ArrayList<String>> result = new ArrayList<>();
         for(int i = 1; i <= numFields; i++){
            String colName = rsmd.getColumnName(i);
            row.add(colName);
         }
         result.add(row);
         while(rs.next()){
            ArrayList<String> rows = new ArrayList<>(numFields);
            for(int i = 1; i <= numFields; i++){
               rows.add(rs.getString(i));
            }
            result.add(rows);
         }
         return result;
      }
      catch(SQLException sqle){
         return null;
      }
   }
   
   public Boolean setData(String SQL, ArrayList<String> values) throws DLException{
      try{
         PreparedStatement stmt = prepare(SQL, values);
         stmt.executeUpdate();
         return true;
      }
      catch(SQLException sqle){
         //System.out.println(sqle.toString());
         return false;
      }
   } 
   
   public void executeStmt(String SQL, ArrayList<String> values) throws DLException{
      try{
         PreparedStatement stmt = prepare(SQL, values);
         int count = stmt.executeUpdate();
         if(count > 0){
            stmt.executeUpdate();
         }else{
            stmt.executeQuery();
         }
      }
      catch(SQLException sqle){
         exception.add("Failed to execute prepared statement: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public void startTrans() throws DLException{
      try{
         conn.setAutoCommit(false);
      }
      catch(SQLException sqle){
         exception.add("Failed to start transaction: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public void endTrans() throws DLException{
      try{
         conn.setAutoCommit(true);
      }
      catch(SQLException sqle){
         exception.add("Failed to end transaction: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public void rollbackTrans() throws DLException{
      try{
         conn.rollback();
      }
      catch(SQLException sqle){
         exception.add("Failed to rollback transaction: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
   
   public void swap(int value) throws DLException{
      try{
         startTrans(); //start transaction
         //select the equipment name of an equipment record with a valid equip id 
         String sql = "SELECT equipmentName FROM Equipment WHERE equipID = ?";
         ArrayList<String> values = new ArrayList<String>();
         values.add(Integer.toString(568));
         ArrayList<ArrayList<String>> data = getData(sql, values);
         ArrayList<String> row = data.get(1);
         String equipmentName = row.get(0);
         //select the equipment name of an equipment record with an equip id of value
         values.remove(Integer.toString(568));
         values.add(Integer.toString(value));
         ArrayList<ArrayList<String>> data2 = getData(sql, values);
         ArrayList<String> row2 = data2.get(1);
         String equipName = row2.get(0);
         //update the equipment name of an equipment record with an equip id of value
         values.remove(Integer.toString(value));
         String updateSQL = "UPDATE Equipment SET equipmentName = ? WHERE equipID = ?";
         values.add(equipmentName);
         values.add(Integer.toString(value));
         boolean update = setData(updateSQL, values); 
         //update the equipment name of an equipment record with a valid equip id
         values.remove(Integer.toString(value));
         values.remove(equipmentName);
         values.add(equipName);
         values.add(Integer.toString(568));
         boolean update2 = setData(updateSQL, values);
         if(update == true && update2 == true){
            conn.commit(); //update 2 records
         }
         else{
            rollbackTrans(); //rollback transaction
         }
         endTrans(); //end transaction
      }
      catch(DLException dle){
         exception.add("Failed to complete transaction: " + dle.toString());
         exceptionList.add(exception);
         throw new DLException(dle, exceptionList);
      }
      catch(SQLException sqle){
         exception.add("Failed to commit transaction: " + sqle.toString());
         exceptionList.add(exception);
         throw new DLException(sqle, exceptionList);
      }
   }
}