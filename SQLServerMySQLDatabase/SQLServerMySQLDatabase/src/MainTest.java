import java.util.*;

public class MainTest{
   public static void main(String [] args) throws DLException{
      MySQLDatabase mysql = new MySQLDatabase(); //instantiated mysql database object
      Equipment equipment = new Equipment(568, "Continent", "Passenger", 200); //instatiated equipment object with a valid equipID
      String sql = "SELECT * FROM Equipment WHERE EquipID = ?"; //prepared statement string
      ArrayList<String> values = new ArrayList<String>(); //arraylist of prepared statement values
      values.add(Integer.toString(568)); //added the equipment object's equipID to the arraylist
      if(mysql.Connect()){ //if connected to the database
         System.out.println("Successfully connected to the MySQL database!"); //display a message indicating success
         DLUser dlUser = new DLUser(mysql); //instantiated data layer user object
         if(dlUser.login("mjh4406", "Computernerd356") == true){ //if successful login
            System.out.println("Successfully logged in!"); //display a message indicating success
            String access = dlUser.getAccess(); //get the access data of the data layer user object
            BLEquipment blEquipment = new BLEquipment(); //instantiated business layer equipment object
            if(blEquipment.save(access) == false){ //if user access is general
               System.out.println("User access: " + access); //display a message indicating success
               equipment.fetch(sql, values); //fetch record with the equipment object's equipID
               //get the data of the equipment object////////////////////////////////////////////////////
               int equipID = equipment.getEquipID();
               String equipmentName = equipment.getEquipmentName();
               String equipmentDescription = equipment.getEquipmentDescription();
               int equipmentCapacity = equipment.getEquipmentCapacity();
               //////////////////////////////////////////////////////////////////
               //display the fetched record
               System.out.println("Fetched data for " + access + ": EquipID = " + equipID + ", EquipmentName = " + equipmentName + ", EquipmentDescription = " + equipmentDescription + ", EquipmentCapacity = " + equipmentCapacity);
            }else{
               System.out.println("User access: " + access); //display a message indicating failure
               System.out.println("Failed to fetch data for: " + access); //display a message indicating failure
            }
          }else{
            System.out.println("Failed to login!"); //display a message indicating failure
          }
         mysql.swap(894); //swap the equipment name of two equipment records
         System.out.println("Successfully swapped the names of equipment data!"); //display a message indicating success
         equipment.fetch(sql, values); //fetch record with the equipment object's equipID
         //get the data of the equipment object////////////////////////////////////////////////////
         int equipmentID = equipment.getEquipID();
         String equipName = equipment.getEquipmentName();
         String equipDescription = equipment.getEquipmentDescription();
         int equipCapacity = equipment.getEquipmentCapacity();
         //////////////////////////////////////////////////////////////////
         //display the updated record
         System.out.println("Updated data for " + equipmentID + ": EquipID = " + equipmentID + ", EquipmentName = " + equipName + ", EquipmentDescription = " + equipDescription + ", EquipmentCapacity = " + equipCapacity);
         Equipment equip = new Equipment(894, "Continent", "Passenger", 200); //instatiated equipment object with the tested equipID
         values.remove(Integer.toString(568)); //removed the previous equipment object's equipID from the arraylist
         values.add(Integer.toString(894)); //added the equipment object's equipID to the arraylist
         equip.fetch(sql, values); //fetch record with the equipment object's equipID
         //get the data of the equipment object////////////////////////////////////////////////////
         int id = equip.getEquipID();
         String name = equip.getEquipmentName();
         String description = equip.getEquipmentDescription();
         int capacity = equip.getEquipmentCapacity();
         //////////////////////////////////////////////////////////////////
         //display the updated record
         System.out.println("Updated data for " + id + ": EquipID = " + id + ", EquipmentName = " + name + ", EquipmentDescription = " + description + ", EquipmentCapacity = " + capacity);
         if(mysql.Close()){
            System.out.println("Failed to close the MySQL database!"); //display closed failure message
         }else{
            System.out.println("Successfully closed the MySQL database!"); //display closed message
         }
      }else{
          System.out.println("Failed to connect to the MySQL database!"); //display connected failure message
      }
   }
}
