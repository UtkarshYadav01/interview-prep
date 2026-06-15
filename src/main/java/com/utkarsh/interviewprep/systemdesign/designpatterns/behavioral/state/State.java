package com.utkarsh.interviewprep.systemdesign.designpatterns.behavioral.state;

public class State {

    public static void main(String[] args) {

        VendingMachine machine = new VendingMachine(2, 10);

        machine.insertCoin(10);
        machine.selectItem();
        machine.dispense();

        System.out.println("------------------");

        machine.insertCoin(10);
        machine.selectItem();
        machine.dispense();

        System.out.println("------------------");

        machine.insertCoin(10); // sold out case
        machine.refill(3);

        System.out.println("------------------");

        machine.insertCoin(10);
        machine.selectItem();
        machine.dispense();
    }

    // ================= STATE INTERFACE =================

    interface VendingMachineState {

        VendingMachineState insertCoin(VendingMachine machine, int coin);

        VendingMachineState selectItem(VendingMachine machine);

        VendingMachineState dispense(VendingMachine machine);

        VendingMachineState returnCoin(VendingMachine machine);

        VendingMachineState refill(VendingMachine machine, int quantity);
    }

    // ================= CONTEXT =================

    static class VendingMachine {

        private VendingMachineState currentState;

        private int inventory;
        private final int price;
        private int currentBalance;

        private final VendingMachineState noCoinState;
        private final VendingMachineState hasCoinState;
        private final VendingMachineState dispenseState;
        private final VendingMachineState soldOutState;

        VendingMachine(int inventory, int price) {

            this.inventory = inventory;
            this.price = price;

            //Create state objects
            this.noCoinState = new NoCoinState();
            this.hasCoinState = new HasCoinState();
            this.dispenseState = new DispenseState();
            this.soldOutState = new SoldOutState();

            // Set initial state
            this.currentState = inventory > 0 ? noCoinState : soldOutState;
        }

        public void insertCoin(int coin) {
            currentState = currentState.insertCoin(this, coin);
        }

        public void selectItem() {
            currentState = currentState.selectItem(this);
        }

        public void dispense() {
            currentState = currentState.dispense(this);
        }

        public void returnCoin() {
            currentState = currentState.returnCoin(this);
        }

        public void refill(int quantity) {
            currentState = currentState.refill(this, quantity);
        }

        // getters/setters
        public int getInventory() {
            return inventory;
        }

        public void setInventory(int inventory) {
            this.inventory = inventory;
        }

        public int getPrice() {
            return price;
        }

        public int getCurrentBalance() {
            return currentBalance;
        }

        public void setCurrentBalance(int currentBalance) {
            this.currentBalance = currentBalance;
        }

        public VendingMachineState getNoCoinState() {
            return noCoinState;
        }

        public VendingMachineState getHasCoinState() {
            return hasCoinState;
        }

        public VendingMachineState getDispenseState() {
            return dispenseState;
        }

        public VendingMachineState getSoldOutState() {
            return soldOutState;
        }
    }

    // ================= NO COIN STATE =================

    static class NoCoinState implements VendingMachineState {

        @Override
        public VendingMachineState insertCoin(VendingMachine machine, int coin) {

            machine.setCurrentBalance(coin);
            System.out.println("Coin inserted: " + coin);

            return machine.getHasCoinState();
        }

        @Override
        public VendingMachineState selectItem(VendingMachine machine) {
            System.out.println("Insert coin first");
            return this;
        }

        @Override
        public VendingMachineState dispense(VendingMachine machine) {
            System.out.println("Insert coin first");
            return this;
        }

        @Override
        public VendingMachineState returnCoin(VendingMachine machine) {
            System.out.println("No coin to return");
            return this;
        }

        @Override
        public VendingMachineState refill(VendingMachine machine, int quantity) {

            machine.setInventory(machine.getInventory() + quantity);
            System.out.println("Refilled: " + quantity);

            return this;
        }
    }

    // ================= HAS COIN STATE =================

    static class HasCoinState implements VendingMachineState {

        @Override
        public VendingMachineState insertCoin(VendingMachine machine, int coin) {

            machine.setCurrentBalance(machine.getCurrentBalance() + coin);
            System.out.println("Additional coin inserted");

            return this;
        }

        @Override
        public VendingMachineState selectItem(VendingMachine machine) {

            if (machine.getCurrentBalance() < machine.getPrice()) {
                System.out.println("Insufficient balance");
                return this;
            }

            System.out.println("Item selected");
            return machine.getDispenseState();
        }

        @Override
        public VendingMachineState dispense(VendingMachine machine) {
            System.out.println("Select item first");
            return this;
        }

        @Override
        public VendingMachineState returnCoin(VendingMachine machine) {

            System.out.println("Returning coin: " + machine.getCurrentBalance());
            machine.setCurrentBalance(0);

            return machine.getNoCoinState();
        }

        @Override
        public VendingMachineState refill(VendingMachine machine, int quantity) {
            System.out.println("Cannot refill while transaction active");
            return this;
        }
    }

    // ================= DISPENSE STATE =================

    static class DispenseState implements VendingMachineState {

        @Override
        public VendingMachineState insertCoin(VendingMachine machine, int coin) {
            System.out.println("Currently dispensing");
            return this;
        }

        @Override
        public VendingMachineState selectItem(VendingMachine machine) {
            System.out.println("Already selected");
            return this;
        }

        @Override
        public VendingMachineState dispense(VendingMachine machine) {

            System.out.println("Dispensing item...");

            machine.setInventory(machine.getInventory() - 1);
            machine.setCurrentBalance(machine.getCurrentBalance() - machine.getPrice());

            if (machine.getInventory() == 0) {
                System.out.println("Machine is SOLD OUT");
                return machine.getSoldOutState();
            }

            return machine.getNoCoinState();
        }

        @Override
        public VendingMachineState returnCoin(VendingMachine machine) {
            System.out.println("Cannot return coin during dispense");
            return this;
        }

        @Override
        public VendingMachineState refill(VendingMachine machine, int quantity) {
            System.out.println("Cannot refill during dispense");
            return this;
        }
    }

    // ================= SOLD OUT STATE =================

    static class SoldOutState implements VendingMachineState {

        @Override
        public VendingMachineState insertCoin(VendingMachine machine, int coin) {
            System.out.println("Machine is sold out");
            return this;
        }

        @Override
        public VendingMachineState selectItem(VendingMachine machine) {
            System.out.println("Machine is sold out");
            return this;
        }

        @Override
        public VendingMachineState dispense(VendingMachine machine) {
            System.out.println("Machine is sold out");
            return this;
        }

        @Override
        public VendingMachineState returnCoin(VendingMachine machine) {
            System.out.println("No coin to return");
            return this;
        }

        @Override
        public VendingMachineState refill(VendingMachine machine, int quantity) {

            machine.setInventory(machine.getInventory() + quantity);
            System.out.println("Refilled with: " + quantity);

            return machine.getNoCoinState();
        }
    }
}