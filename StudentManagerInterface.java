
package za.ac.tut.bl;
import java.sql.SQLException;
import java.util.List;
import za.ac.tut.entity.Student;
/**
*
* @author MemaniV
*/
public interface StudentManagerInterface<T> {
 public boolean add(Student t, String addStudentSQL, String addModuleSQL) throws SQLException;
 public boolean delete(Integer id, String deleteSQL) throws SQLException;
 public T get(Integer id, String sql) throws SQLException;
 public List<T> getAll(String sql) throws SQLException;
 public boolean update(T t, String sql) throws SQLException;
}
