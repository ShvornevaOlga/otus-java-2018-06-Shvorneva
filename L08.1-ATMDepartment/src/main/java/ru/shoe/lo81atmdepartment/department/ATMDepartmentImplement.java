package ru.shoe.lo81atmdepartment.department;

import org.slf4j.LoggerFactory;
import ru.shoe.lo81atmdepartment.atm.ATM;
import ru.shoe.lo81atmdepartment.atm.memento.ATMOriginator;

import java.util.List;

public class ATMDepartmentImplement implements ATMDepartment {
    private List<ATM> atmList;
    private ATMOriginator originator;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ATMDepartmentImplement.class);

    public ATMDepartmentImplement(List<ATM> atmList, ATMOriginator originator) {
        this.atmList = atmList;
        this.originator = originator;
    }

    @Override
    public long getSum() {
        long sum = 0;
        for (ATM atm : atmList) {
            sum += atm.getAmount();
        }
        return sum;
    }

    @Override
    public void encashment() {
        for (ATM atm : atmList) {
            logger.info("Ячейки до инкассации" + atm.lookCells());
            atm.setBanknotes(originator.restoreState(atm));
            logger.info("Ячейки после инкассации" + atm.lookCells());
        }
    }
}
