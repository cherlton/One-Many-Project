package za.ac.tut.entity;
import java.util.List;
/**
*
* @author MemaniV
*/
public class Student {
 private Integer id;
 private String name;
 private List<Module> modules;
 public Student() {
 }
 public Student(Integer id, String name, List<Module> modules) {
 this.id = id;
 this.name = name;
 this. modules = modules;
 }
 public Integer getId() {
 return id;
 }
 public void setId(Integer id) {
 this.id = id;
 }
9
 public String getName() {
 return name;
 }
 public void setName(String name) {
 this.name = name;
 }
 public List<Module> getModules() {
 return modules;
 }
 public void setModules(List<Module> modules) {
 this.modules = modules;
 }
 @Override
 public String toString() {
 return id + "," + name + "," + modules;
 }
}
