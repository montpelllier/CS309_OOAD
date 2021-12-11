package factoryMethod;

import ITStaff.*;

public class ArtDesignerFactory implements ITStaffFactoryInterface{
    @Override
    public ITStaff createITStaff() {
        return new ArtDesigner();
    }
}
