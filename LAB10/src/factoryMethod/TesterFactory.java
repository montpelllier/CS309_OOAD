package factoryMethod;

import ITStaff.*;

public class TesterFactory implements ITStaffFactoryInterface{
    @Override
    public ITStaff createITStaff() {
        return new Tester();
    }
}
