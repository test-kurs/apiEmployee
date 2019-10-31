import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MainApp implements Runnable {

    /*
    Zadanie:
    1. Wykonać link w przeglądarce: http://dummy.restapiexample.com/api/v1/employees
    2. Otworzyć nowy projekt Mavenowy
    3. Skonfigurować oraz wrzucić na gita
    4. Napisać parser JSON-a który zrzuci listę pracowników i wyświetlić w konsoli oraz wrzucić na gita
    5. Utworzyć liste obiektów które zwraca powyższe api
    6. Utworzyć liste oraz wyświetlić pracowników których wiek jest większy niż 30
    7. Utworzyć liste oraz wyświetlić pracowników posortować rosnąco po pensji
    8. Utworzyć liste oraz wyświetlić pracowników posortować malejąco po wieku
    */

    @Override
    public void run() {
        try {
            String response = new HttpService().connect(Config.APP_URL);
            parseJson(response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Employee> parseJson(String json) {
        JSONArray jsonArrayEmployees = new JSONArray(json);
        List<Employee> employeesList = new ArrayList<>();

        for (int i = 0; i < jsonArrayEmployees.length(); i++) {
            JSONObject one = (JSONObject) jsonArrayEmployees.get(i);
            Employee employee = new Employee();
            employee.setId(Integer.parseInt(one.get("id").toString()));
            employee.setAge(Double.parseDouble(one.get("employee_age").toString()));
            employee.setName(one.get("employee_name").toString());
            employee.setSalary(Double.parseDouble(one.get("employee_salary").toString()));
            employeesList.add(employee);
        }

/*        System.out.println("Logs: ");
        System.out.println(employeesList);
        System.out.println(employeesList.size());
        System.out.println(jsonArrayEmployees.length());*/

        List<Employee> employeeListAgeUpper30 = employeesList.stream().filter(f -> f.getAge() > 30).collect(Collectors.toList());
        System.out.println(employeeListAgeUpper30);

        List<Employee> employeeListSortedBySalary = employeesList.stream().sorted(Comparator.comparingDouble(Employee::getSalary)).collect(Collectors.toList());
        System.out.println(employeeListSortedBySalary);

        List<Employee> employeeListSortedByAge = employeesList.stream().sorted(Comparator.comparingDouble(Employee::getAge).reversed()).collect(Collectors.toList());
        System.out.println(employeeListSortedByAge);

        return employeesList;
    }
}
