import java.util.*;

public class DLUser{
   private MySQLDatabase mysql;
   private String UserName;
   private String Password;
   private String Name;
   private String Access;
   
   public DLUser(MySQLDatabase mysql){
      this.mysql = mysql;
   }
   
   public String getUserName(){
      return UserName;
   }
   
   public String getPassword(){
      return Password;
   }
   
   public String getName(){
      return Name;
   }
   
   public String getAccess(){
      return Access;
   }
   
   public void setUserName(String UserName){
      this.UserName = UserName;
   }
   
   public void setPassword(String Password){
      this.Password = Password;
   }

   public void setName(String Name){
      this.Name = Name;
   }

   public void setAccess(String Access){
      this.Access = Access;
   }
     
   public boolean login(String UserName, String Password){
      String sql = "SELECT * FROM User WHERE UserName = ?";
      ArrayList<String> values = new ArrayList<String>();
      values.add(UserName);
      try{
         ArrayList<ArrayList<String>> data = mysql.getData(sql, values);
         if(data.size() == 1){
            return false;
         }else{
            ArrayList<String> row = data.get(1);
            String userName = row.get(0);
            String password = row.get(1);
            String name = row.get(2);
            String access = row.get(3);
            if(Password.equals(password)){
               setUserName(userName);
               setPassword(password);
               setName(name);
               setAccess(access);
               return true;
            }
            return false;
         }
      }catch(DLException dle){
         return false;
      }
   }
}