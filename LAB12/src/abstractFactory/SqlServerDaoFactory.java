package abstractFactory;

import dao.ComputerDao;
import dao.SqlServerComputerDao;
import dao.SqlServerStaffDao;
import dao.StaffDao;

public class SqlServerDaoFactory implements DaoFactory{
    @Override
    public StaffDao createStaffDao() {
        return new SqlServerStaffDao();
    }

    @Override
    public ComputerDao createComputerDao() {
        return new SqlServerComputerDao();
    }
}
