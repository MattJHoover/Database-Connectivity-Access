import java.util.*;

public class Equipment{

   private int EquipID;
   private String EquipmentName;
   private String EquipmentDescription;
   private int EquipmentCapacity;
   MySQLDatabase mysql = new MySQLDatabase();

   public Equipment(int EquipID, String EquipmentName, String EquipmentDescription, int EquipmentCapacity){
      this.EquipID = EquipID;
      this.EquipmentName = EquipmentName;
      this.EquipmentDescription = EquipmentDescription;
      this.EquipmentCapacity = EquipmentCapacity;
   }
   
   public int getEquipID(){
      return EquipID;
   }
      
   public String getEquipmentName(){
      return EquipmentName;
   }
   
   public String getEquipmentDescription(){
      return EquipmentDescription;
   }
   
   public int getEquipmentCapacity(){
      return EquipmentCapacity;
   }
   
   public void setEquipID(int EquipID){
      this.EquipID = EquipID;
   }
      
   public void setEquipmentName(String EquipmentName){
      this.EquipmentName = EquipmentName;
   }
   
   public void setEquipmentDescription(String EquipmentDescription){
      this.EquipmentDescription = EquipmentDescription;
   }
   
   public void setEquipmentCapacity(int EquipmentCapacity){
      this.EquipmentCapacity = EquipmentCapacity;
   }
   
   public void fetch(String sql, ArrayList<String> values) throws DLException{
      if(mysql.Connect()){ 
         ArrayList<ArrayList<String>> data = mysql.getData(sql, values);
         ArrayList row = data.get(1);
         String strID = (String) row.get(0);
         int id = Integer.parseInt(strID);
         String strName = (String) row.get(1);
         String strDescription = (String) row.get(2);
         String strCapacity = (String) row.get(3);
         int capacity = Integer.parseInt(strCapacity);
         this.EquipID = id;
         this.EquipmentName = strName;
         this.EquipmentDescription = strDescription;
         this.EquipmentCapacity = capacity;
         mysql.Close();
      }
   }
   
   public void put() throws DLException{
      if(mysql.Connect()){
         ArrayList<String> values = new ArrayList<String>();
         values.add(this.EquipmentName);
         values.add(this.EquipmentDescription);
         values.add(Integer.toString(this.EquipmentCapacity));
         values.add(Integer.toString(this.EquipID));
         String sql = "UPDATE Equipment SET EquipmentName = ?, EquipmentDescription = ?, EquipmentCapacity = ? WHERE EquipID = ?";
         mysql.setData(sql, values);
         mysql.Close();
      }
   }
      
   public void post() throws DLException{
      if(mysql.Connect()){
         ArrayList<String> values = new ArrayList<String>();
         values.add(Integer.toString(this.EquipID));
         values.add(this.EquipmentName);
         values.add(this.EquipmentDescription);
         values.add(Integer.toString(this.EquipmentCapacity));
         String sql = "INSERT INTO Equipment (EquipID, EquipmentName, EquipmentDescription, EquipmentCapacity) VALUES (?, ?, ?, ?)";
         mysql.setData(sql, values);
         mysql.Close();
      }
   }
   
   public void delete() throws DLException{
      if(mysql.Connect()){
         ArrayList<String> values = new ArrayList<String>();
         values.add(Integer.toString(this.EquipID));
         String sql = "DELETE FROM Equipment WHERE EquipID = ?";
         mysql.setData(sql, values);
         mysql.Close();
      }
   }
}