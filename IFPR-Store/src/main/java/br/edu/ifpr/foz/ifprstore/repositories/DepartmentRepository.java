package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.connection.ConnectionFactory;
import br.edu.ifpr.foz.ifprstore.exceptions.DatabaseException;
import br.edu.ifpr.foz.ifprstore.models.Department;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DepartmentRepository {

    private final Connection connection;

    public DepartmentRepository(){

        connection = ConnectionFactory.getConnection();
    }

    public List<Department> getDepartments(){
        List<Department> departments = new ArrayList<>();

        try{
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery("SELECT * FROM department limit 2 offset 3");

            while(result.next()){
                Department department = instatiateDepartment(result);
                departments.add(department);
            }
            result.close();
        }catch (SQLException e){
            throw new RuntimeException(e);
        }finally {
            ConnectionFactory.closeConnection();
        }
        return departments;
    }

    public Department insert(Department department) {

        String sql = "INSERT INTO department (Name) " +
                "VALUES(?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, department.getName());

            Integer rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {

                ResultSet id = statement.getGeneratedKeys();

                id.next();

                Integer Id = id.getInt(1);

                System.out.println("Rows inserted: " + rowsInserted);
                System.out.println("Id: " + Id);

                department.setId(Id);
            }
        } catch (Exception e) {
            throw new DatabaseException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }

        return department;
    }

    public void updateDepartment(Integer Id, String name) {
        String sql = "UPDATE department SET Name = ? WHERE Id = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setInt(2, Id);

            Integer rowsUpdated = statement.executeUpdate();

            if(rowsUpdated > 0){
                System.out.println("Rows updated: " + rowsUpdated);
            }
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }

    public void delete(Integer Id) {
        String sql = "DELETE FROM department WHERE id = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setInt(1, Id);
            Integer rowsDelete = statement.executeUpdate();

            if(rowsDelete > 0){
                System.out.println("Rows deleted: " + rowsDelete);
            }
        }catch (Exception e){
            throw new DatabaseException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
    }

    public Department getById(Integer Id) {
        Department department;

        String sql = "SELECT * FROM department WHERE Id = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Id);

            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                department = this.instatiateDepartment(resultSet);

            }else{
                throw new DatabaseException("Departamento nao encontrado");
            }
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }finally {
            ConnectionFactory.closeConnection();
        }
        return department;
    }

    public List<Department> findById(Integer Id) {
        List<Department> departmentsList = new ArrayList<>();

        Department department;

        String sql = "SELECT * FROM department WHERE Id = ?";

        try{
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, Id);

            ResultSet resultSet = statement.executeQuery();

            Map<Integer, Department> map = new HashMap<>();

            while (resultSet.next()){
                department = map.get(resultSet.getInt("Id"));

                if(department == null){
                    department = instatiateDepartment(resultSet);
                    map.put(Id,department);
                }
                department = this.instatiateDepartment(resultSet);
                departmentsList.add(department);
            }
        }catch (SQLException e){
            throw new DatabaseException(e.getMessage());
        }
        return departmentsList;
    }
    public Department instatiateDepartment(ResultSet resultSet, Department department) throws SQLException {
        department = new Department();

        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }

    public Department instatiateDepartment(ResultSet resultSet) throws SQLException {
        Department department = new Department();

        department.setId(resultSet.getInt("Id"));
        department.setName(resultSet.getString("Name"));
        return department;
    }
}
