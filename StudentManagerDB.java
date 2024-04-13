
package za.ac.tut.bl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import za.ac.tut.entity.Module;
import za.ac.tut.entity.Student;
/**
*
* 
*/
public class StudentManagerDB implements StudentManagerInterface<Student> {
 private Connection connection;
 
 public StudentManagerDB(String dbURL, String username, String password) throws SQLException{
 connection = getConnection(dbURL, username, password);
 }
 
 private Connection getConnection(String dbURL, String username, String password) throws 
SQLException {
 Connection theConnection;
 theConnection = DriverManager.getConnection(dbURL, username, password);
 return theConnection;
 }
12
 @Override
 public boolean add(Student s, String addStudentSQL, String addModuleSQL) throws SQLException {
 PreparedStatement ps = connection.prepareStatement(addStudentSQL);
 ps.setInt(1, s.getId());
 ps.setString(2, s.getName());
 ps.executeUpdate();
 ps.close();
 List<Module> modules = s.getModules();
 
 for(Module module:modules)
 {
 PreparedStatement pstm = connection.prepareStatement(addModuleSQL);
 pstm.setInt(1, module.getId());
 pstm.setString(2, module.getName());
 pstm.setString(3, module.getCode());
 pstm.setInt(4, s.getId());
 pstm.executeUpdate();
 pstm.close();
 }
 return true;
 }
 @Override
 public boolean delete(Integer id, String deleteSQL) throws SQLException {
 PreparedStatement ps = connection.prepareStatement(deleteSQL); 
 ps.setInt(1, id); 
 ps.executeUpdate();
 ps.close(); 
 
 return true;
 }
13
 @Override
 public boolean update(Student t, String updateSQL) throws SQLException {
 PreparedStatement ps = connection.prepareStatement(updateSQL); 
 ps.setString(1, t.getName()); 
 ps.setInt(2, t.getId());
 
 ps.executeUpdate();
 ps.close(); 
 
 return true;
 }
 
 @Override
 public Student get(Integer id, String sql) throws SQLException {
 Student student = null;
 PreparedStatement ps = connection.prepareStatement(sql);
 ps.setInt(1, id);
 ResultSet rs = ps.executeQuery();
 
 ResultSetMetaData rsmd = rs.getMetaData();
 System.out.println("Number of columns returned: " + rsmd.getColumnCount());
 System.out.println("Row number: " + rs.getRow());
 
 List<Module> modules = new ArrayList<>();
 
 if(rs.next()){
 Integer studID = rs.getInt("ID");
 String name = rs.getString("NAME");
 
14
 do{
 Integer moduleID = rs.getInt("module_id");
 String moduleName = rs.getString("module_name");
 String moduleCode = rs.getString("code");
 
 Module module = new Module(moduleID, moduleName, moduleCode);
 modules.add(module);
 }while(rs.next());
 
 student = new Student(studID, name, modules);
 }
 ps.close();
 
 return student;
 }
 @Override
 public List<Student> getAll(String sql) throws SQLException {
 Integer previousID = 0, studID;
 int i = 0;
 String name;
 Module module;
 
 Student student = new Student();
 List<Student> students = new ArrayList<>();
 PreparedStatement ps = connection.prepareStatement(sql);
 ResultSet rs = ps.executeQuery();
 
15
 while(rs.next()){
 if(i == 0){
 studID = rs.getInt("ID");
 
 name = rs.getString("NAME"); 
 student.setId(studID);
 student.setName(name);
 
 module = createModule(rs);//new Module(moduleID, moduleName, moduleCode);
 List<Module> modules = student.getModules();
 modules.add(module);
 student.setModules(modules);
 i++;
 previousID = studID;
 } else {
 //read the current idea
 studID = rs.getInt("ID");
 
 if(studID.equals(previousID)) { 
 module = createModule(rs);//new Module(moduleID, moduleName, moduleCode);
 List<Module> modules = student.getModules();
 modules.add(module);
 student.setModules(modules);
 } else {
 students.add(student);
 
 name = rs.getString("NAME"); 
 student = new Student();
 student.setId(studID);
 student.setName(name);
 
16
 module = createModule(rs);//new Module(moduleID, moduleName, moduleCode);
 List<Module> modules = student.getModules();
 modules.add(module);
 student.setModules(modules);
 previousID = studID;
 }
 }
 }
 
 if(1 != 0){
 students.add(student);
 }
 
 ps.close();
 return students;
 }
 private Module createModule(ResultSet rs) throws SQLException { 
 Integer moduleID = rs.getInt("module_id");
 String moduleName = rs.getString("module_name");
 String moduleCode = rs.getString("code");
 Module module= new Module(moduleID, moduleName, moduleCode);
 
 return module;
 }
 
}
