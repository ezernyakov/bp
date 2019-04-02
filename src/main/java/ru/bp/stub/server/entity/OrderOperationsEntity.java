package ru.bp.stub.server.entity;

public class OrderOperationsEntity {

    private String type;
    private String sumOperation;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setSumOperation(String sumOperation) {
        this.sumOperation = sumOperation;
    }

    public String getSumOperation() {
        return sumOperation;
    }
}
