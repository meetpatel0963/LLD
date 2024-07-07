package DesignPatterns;

public class ProxyPattern {
    /*
    * It is used when we want to prevent our data objects from getting exposed to the clients directly.
    * Proxy objects/components sit between clients and the actual objects/components.
    * The system may have multiple proxies chained together.
    * Use cases:
    *   1. Access Restriction: for instance schools/organizations use forward proxy to restrict access to
    *       a limited set of websites/online content.
    *   2. Caching: proxy can cache the data and return directly, reducing the load on services/database.
    *   3. Preprocessing & Postprocessing: proxy can be used for logging, publishing events, etc.
    *       before/after the request processing.
    *   4. Marshalling & Unmarshalling: proxy can be used to modify (add/remove some fields) the request object
    *       before/after the request processing.
    * */

    enum EmployeeType {
        ADMIN,
        USER
    }

    static class Employee {
        private int id;
        private String name;

        public Employee(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public int getId() {
            return id;
        }
    }

    interface EmployeeDAO {
        void create(EmployeeType employeeType, Employee employee) throws Exception;
        Employee get(EmployeeType employeeType, int id) throws Exception;
        void delete(EmployeeType employeeType, int id) throws Exception;
    }

    static class EmployeeDAOImpl implements EmployeeDAO {
        @Override
        public void create(EmployeeType employeeType, Employee employee) throws Exception {
            System.out.println("created employee with id: " + employee.getId());
        }

        @Override
        public void delete(EmployeeType employeeType, int id) throws Exception {
            System.out.println("deleted employee with id: " + id);
        }

        @Override
        public Employee get(EmployeeType employeeType, int id) throws Exception {
            return new Employee(id, "test");
        }
    }

    static class EmployeeDAOProxy implements EmployeeDAO {
        private final EmployeeDAO employeeDAO;

        public EmployeeDAOProxy() {
            employeeDAO = new EmployeeDAOImpl();
        }

        @Override
        public void create(EmployeeType employeeType, Employee employee) throws Exception {
            if (employeeType.equals(EmployeeType.ADMIN)) {
                employeeDAO.create(employeeType, employee);
            } else {
                throw new Exception("can't perform this operation");
            }
        }

        @Override
        public void delete(EmployeeType employeeType, int id) throws Exception {
            if (employeeType.equals(EmployeeType.ADMIN)) {
                employeeDAO.delete(employeeType, id);
            } else {
                throw new Exception("can't perform this operation");
            }
        }

        @Override
        public Employee get(EmployeeType employeeType, int id) throws Exception {
            return employeeDAO.get(employeeType, id);
        }
    }

    public static void main(String[] args) {
        try {
            EmployeeDAO employeeDAOProxy = new EmployeeDAOProxy();
            employeeDAOProxy.create(EmployeeType.ADMIN, new Employee(1, "test"));
            employeeDAOProxy.get(EmployeeType.USER, 1);
            employeeDAOProxy.create(EmployeeType.USER, new Employee(2, "test"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
