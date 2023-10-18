package ru.babushkina;

import java.util.List;

public interface EmployeeRepository {

    public Employee save(Employee emp);

    public List<Employee> all();

    public Employee get(int id);
}
