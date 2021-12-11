package abstractFactory;

import dao.ComputerDao;
import dao.MysqlComputerDao;
import dao.MysqlStaffDao;
import dao.StaffDao;

public class MysqlDaoFactory implements DaoFactory{
    @Override
    public StaffDao createStaffDao() {
        return new MysqlStaffDao();
    }

    @Override
    public ComputerDao createComputerDao() {
        return new MysqlComputerDao();
    }
}
