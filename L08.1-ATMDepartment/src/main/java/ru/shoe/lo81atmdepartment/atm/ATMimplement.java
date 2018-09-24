package ru.shoe.lo81atmdepartment.atm;

import org.slf4j.LoggerFactory;
import ru.shoe.lo81atmdepartment.atm.memento.ATMMemento;
import ru.shoe.lo81atmdepartment.bank.Bank;
import ru.shoe.lo81atmdepartment.Banknote;
import ru.shoe.lo81atmdepartment.Client;

import java.util.*;
import java.util.stream.Collectors;

public class ATMimplement implements ATM {
    private List<Cell> banknotes;
    private Bank bank;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ATMimplement.class);

    //==================================================================================================================
    //========================constructors==========================================================================
    //==================================================================================================================
    public ATMimplement() {
        banknotes = new ArrayList<>();
    }

    //==================================================================================================================
    //=======================getters, setters==========================================================================
    //==================================================================================================================
    @Override
    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public void setBanknotes(List<Cell> cellList) {
        if (cellList != null) {
            banknotes.clear();
            for (Cell cell : cellList) {
                Cell cellATM = new Cell(cell.getBanknote(), cell.getCount());
                banknotes.add(cellATM);
            }
        }
    }

    @Override
    public long getAmount() {
        long amount = 0;
        for (Cell cell : banknotes) {
            amount += cell.getBanknote().getNominal() * cell.getCount();
        }
        return amount;
    }

    //==================================================================================================================
    //===========================methods============================================================================
    //==================================================================================================================
    @Override
    public void putMoneyToATM(Client client, Map<Banknote, Integer> putBanknotes) {
        client.putMoney(putBanknotes);
        long sum = 0;
        logger.info("Купюры в банкомате до приема:");
        for (Cell cell : banknotes) {
            logger.info(cell.getBanknote().getNominal() + "руб: " + cell.getCount());
        }

        for (Banknote banknote : putBanknotes.keySet()) {
            int quantityPutBanknote = putBanknotes.get(banknote);
            for (Cell cell : banknotes) {
                if (cell.getBanknote().equals(banknote)) {
                    cell.addBanknotesToCell(quantityPutBanknote);
                }
            }
            sum += banknote.getNominal() * quantityPutBanknote;
        }
        logger.info("Принаятые купюры:");
        for (Banknote banknote : putBanknotes.keySet()) {
            logger.info(banknote.getNominal() + "руб: " + putBanknotes.get(banknote));
        }
        logger.info("Купюры в банкомате после приема:");
        for (Cell cell : banknotes) {
            logger.info(cell.getBanknote().getNominal() + "руб: " + cell.getCount());
        }
        bank.depositBill(client, sum);
    }

    @Override
    public void getMoneyFromATM(Client client, long sum) throws ATMExeption {
        if (getAmount() >= sum) {
            if (bank.balance(client) >= sum) {
                Map<Banknote, Integer> cashToGet = new HashMap<>();
                long remainingSum = getRemainingSum(sum, cashToGet);
                if (remainingSum == 0) {
                    logger.info("Купюры в банкомате до выдачи:");
                    for (Cell cell : banknotes) {
                        logger.info(cell.getBanknote().getNominal() + "руб: " + cell.getCount());
                    }
                    for (Banknote banknote : cashToGet.keySet()) {
                        for (Cell cell : banknotes) {
                            if (cell.getBanknote().equals(banknote)) {
                                cell.removeBanknotesFromCell( cashToGet.get(banknote));
                            }
                        }
                    }
                    bank.creditBill(client, sum);
                    client.withdrawMoney(cashToGet);
                    logger.info("Выданные купюры:");
                    for (Banknote banknote : cashToGet.keySet()) {
                        logger.info(banknote.getNominal() + "руб: " + cashToGet.get(banknote));
                    }
                    logger.info("Купюры в банкомате после выдачи:");
                    for (Cell cell : banknotes) {
                        logger.info(cell.getBanknote().getNominal() + "руб: " + cell.getCount());
                    }
                } else {
                    throw new ATMExeption("Не корректная сумма");
                }
            } else throw new ATMExeption("Недостаточно средств");
        } else throw new ATMExeption("Не достаточно средств вбанкомате");
    }

    private long getRemainingSum(long sum, Map<Banknote, Integer> cashToGet) {
        long remainingSum = sum;
        List<Banknote> mas = sortNominal();
        for (Banknote banknote : mas) {
            int needBanknotes = (int) remainingSum / banknote.getNominal();
            for (Cell cell : banknotes) {
                if (cell.getBanknote().equals(banknote)) {
                    if (needBanknotes <= cell.getCount()) {
                        if (needBanknotes > 0) {
                            cashToGet.put(banknote, needBanknotes);
                            remainingSum = remainingSum % banknote.getNominal();
                        }
                    } else {
                        needBanknotes = cell.getCount();
                        if (needBanknotes > 0) {
                            cashToGet.put(banknote, needBanknotes);
                            remainingSum = remainingSum % banknote.getNominal();
                        }
                    }
                }
            }

        }
        return remainingSum;
    }

    private List<Banknote> sortNominal() {
        return Arrays.stream(Banknote.values())
                .sorted(Comparator.comparing(Banknote::getNominal).reversed()).collect(Collectors.toList());
    }

    @Override
    public void getAccountBalance(Client client) {
        long sum = bank.balance(client);
        if (getAmount() >= sum) {
            logger.info("Купюры в банкомате до выдачи:");
            for (Cell cell : banknotes) {
                logger.info(cell.getBanknote().getNominal() + "руб: " + cell.getCount());
            }
            Map<Banknote, Integer> cashToGet = new HashMap<>();
            long remainingSum = getRemainingSum(sum, cashToGet);
            if (remainingSum > 0) sum -= remainingSum;
            for (Banknote banknote : cashToGet.keySet()) {
                for (Cell cell : banknotes) {
                    if (cell.getBanknote().equals(banknote)) {
                        cell.removeBanknotesFromCell(cashToGet.get(banknote));
                    }
                }
            }
            logger.info("Выданные купюры:");
            for (Banknote banknote : cashToGet.keySet()) {
                logger.info(banknote.getNominal() + "руб: " + cashToGet.get(banknote));
            }
            logger.info("Купюры в банкомате после выдачи:");
            for (Cell cell : banknotes) {
                logger.info(cell.getBanknote().getNominal() + "руб: " + cell.getCount());
            }
            bank.creditBill(client, sum);
            client.withdrawMoney(cashToGet);
        } else System.out.println("Не достаточно средств вбанкомате");
    }

    @Override
    public Map<Banknote, Integer> lookBanknotes() {
        Map<Banknote, Integer> cashCopy = new HashMap<>();
        for (Cell cell : banknotes) {
            cashCopy.put(cell.getBanknote(), cell.getCount());
        }
        return cashCopy;
    }

    @Override
    public ATMMemento createAtmMemento() {
        return new ATMMemento(banknotes);
    }
}