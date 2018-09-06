public class BLEquipment{
   public boolean save(String Access){
      if(Access.equals("Editor") || Access.equals("Admin")){
         return true;
      }else{
         return false;
      }
   }
}