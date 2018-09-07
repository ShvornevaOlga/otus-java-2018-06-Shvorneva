package ru.shoe.lo71atm;

import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

class ATMimplement implements ATM {
    private Map<Banknote, Integer> banknotes;
    private Bank bank;
    private static org.slf4j.Logger logger = LoggerFactory.getLogger(ATMimplement.class);

    //==================================================================================================================
    //========================constructors==========================================================================
    //==================================================================================================================
    ATMimplement() {
        banknotes = new HashMap<>();
        for (Banknote banknote : Banknote.values()) {
            banknotes.put(banknote, 0);
        }
    }

    //==================================================================================================================
    //=======================getters, setters==========================================================================
    //==================================================================================================================
    void setBank(Bank bank) {
        this.bank = bank;
    }

    void setBanknotes(Map<Banknote, Integer> cash) {
        for (Banknote banknote : cash.keySet()) {
            banknotes.put(banknote, cash.get(banknote));
        }
    }

    long getAmount() {
        long amount = 0;
        for (Banknote banknote : banknotes.keySet()) {
            amount += banknote.nominal * banknotes.get(banknote);
        }
        return amount;
    }

    //==================================================================================================================
    //===========================methods============================================================================
    //==================================================================================================================
    @Override
    public Map<Banknote, Integer> lookBanknotes() {
        Map<Banknote, Integer> cashCopy = new HashMap<>();
        for (Banknote banknote : banknotes.keySet()) {
            cashCopy.put(banknote, banknotes.get(banknote));
        }
        return cashCopy;
    }

    @Override
    public void putMoneyToATM(Client client, Map<Banknote, Integer> putBanknotes) {
        client.putMoney(putBanknotes);
        long sum = 0;
        logger.info("Купюры в банкомате до приема:");
        for (Banknote banknote : banknotes.keySet()) {
            logger.info(banknote.getNominal() + "руб: " + banknotes.get(banknote));
        }

        for (Banknote banknote : putBanknotes.keySet()) {
            int quantityPutBanknote = putBanknotes.get(banknote);
            banknotes.put(banknote, banknotes.get(banknote) + quantityPutBanknote);
            sum += banknote.getNominal() * quantityPutBanknote;
        }
        logger.info("Принаятые купюры:");
        for (Banknote banknote : putBanknotes.keySet()) {
            logger.info(banknote.getNominal() + "руб: " + putBanknotes.get(banknote));
        }
        logger.info("Купюры в банкомате после приема:");
        for (Banknote banknote : banknotes.keySet()) {
            logger.info(banknote.getNominal() + "руб: " + banknotes.get(banknote));
        }
        bank.depositBill(client.getId(), sum);
    }

    @Override
    public void getMoneyFromATM(Client client, long sum) throws ATMExeption {
        if (getAmount() >= sum) {
            if (bank.balance(client.getId()) >= sum) {
                Map<Banknote, Integer> cashToGet = new HashMap<>();
                long remainingSum = getRemainingSum(sum, cashToGet);
                if (remainingSum == 0) {
                    logger.info("Купюры в банкомате до выдачи:");
                    for (Banknote banknote : banknotes.keySet()) {
                        logger.info(banknote.getNominal() + "руб: " + banknotes.get(banknote));
                    }
                    for (Banknote banknote : cashToGet.keySet()) {
                        int quantityATMBanknote = banknotes.get(banknote);
                        int quantityPutBanknote = cashToGet.get(banknote);
                        banknotes.put(banknote, quantityATMBanknote - quantityPutBanknote);
                    }
                    bank.creditBill(client.getId(), sum);
                    client.getMoney(cashToGet);
                    logger.info("Выданные купюры:");
                    for (Banknote banknote : cashToGet.keySet()) {
                        logger.info(banknote.getNominal() + "руб: " + cashToGet.get(banknote));
                    }
                    logger.info("Купюры в банкомате после выдачи:");
                    for (Banknote banknote : banknotes.keySet()) {
                        logger.info(banknote.getNominal() + "руб: " + banknotes.get(banknote));
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
            if (needBanknotes <= banknotes.get(banknote)) {
                if (needBanknotes > 0) {
                    cashToGet.put(banknote, needBanknotes);
                    remainingSum = remainingSum % banknote.getNominal();
                }
            } else {
                needBanknotes = banknotes.get(banknote);
                if (needBanknotes > 0) {
                    cashToGet.put(banknote, needBanknotes);
                    remainingSum = remainingSum % banknote.getNominal();
                }
            }
        }
        return remainingSum;
    }

    @Override
    public void getAccountBalance(Client client) {
        long sum = bank.balance(client.getId());
        if (getAmount() >= sum) {
            logger.info("Купюры в банкомате до выдачи:");
            for (Banknote banknote : banknotes.keySet()) {
                logger.info(banknote.getNominal() + "руб: " + banknotes.get(banknote));
            }
            Map<Banknote, Integer> cashToGet = new HashMap<>();
            long remainingSum = getRemainingSum(sum, cashToGet);
            if (remainingSum > 0) sum -= remainingSum;
            for (Banknote banknote : cashToGet.keySet()) {
                int quantityATMBanknote = banknotes.get(banknote);
                int quantityPutBanknote = cashToGet.get(banknote);
                banknotes.put(banknote, quantityATMBanknote - quantityPutBanknote);
            }
            logger.info("Выданные купюры:");
            for (Banknote banknote : cashToGet.keySet()) {
                logger.info(banknote.getNominal() + "руб: " + cashToGet.get(banknote));
            }
            logger.info("Купюры в банкомате после выдачи:");
            for (Banknote banknote : banknotes.keySet()) {
                logger.info(banknote.getNominal() + "руб: " + banknotes.get(banknote));
            }
            bank.creditBill(client.getId(), sum);
            client.getMoney(cashToGet);
        } else System.out.println("Не достаточно средств вбанкомате");
    }

    private List<Banknote> sortNominal() {
        return Arrays.stream(Banknote.values())
                .sorted(Comparator.comparing(Banknote::getNominal).reversed()).collect(Collectors.toList());
    }


}
