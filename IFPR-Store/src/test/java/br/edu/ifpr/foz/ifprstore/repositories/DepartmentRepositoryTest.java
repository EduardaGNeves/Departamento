package br.edu.ifpr.foz.ifprstore.repositories;

import br.edu.ifpr.foz.ifprstore.models.Department;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

public class DepartmentRepositoryTest {
    @Test
    public void deveInserirUmRegistroNaTabelaDepartment(){

        DepartmentRepository repository = new DepartmentRepository();

        Department departmentFake = new Department();
        departmentFake.setName("Pet");


        Department department = repository.insert(departmentFake);

    }

    @Test
    public void deveAtualizarUmDepartamentoNaTabelaDepartment(){
        DepartmentRepository repository = new DepartmentRepository();

        repository.updateDepartment(1, "book");
    }

    @Test
    public void deveDeletarUmDepartamento(){
        DepartmentRepository repository = new DepartmentRepository();
        repository.delete(15);
    }

    @Test
    public void deveExibirUmListaDeDepartamentos(){
        DepartmentRepository repository = new DepartmentRepository();

        List<Department> departments = repository.getDepartments();

        for(Department department : departments){
            System.out.println(department);
        }
    }

    @Test
    public void deveRetornarUmDepartamentoPeloId(){
        DepartmentRepository repository = new DepartmentRepository();
        Department department = repository.getById(1);
        System.out.println(department);
        System.out.println("Departamento: ");
        System.out.println(department.getName());
    }

    @Test
    public void deveRetornarUmaListaPeloDepartmentId(){
        DepartmentRepository repository = new DepartmentRepository();
        List<Department> departmentsList = repository.findById(2);
        for(Department department : departmentsList){
            System.out.println(department);
            System.out.println("Departamento: ");
            System.out.println(department.getName());
        }
    }

}
