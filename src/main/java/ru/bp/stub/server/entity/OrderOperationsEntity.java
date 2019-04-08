package ru.bp.stub.server.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Сущность, описывающая операции по счету
 */

@Entity
@Table(name = "OrdersOperations")
public class OrderOperationsEntity implements Serializable {

    @Column(name = "type")
    private String type;

    @Column(name = "sum")
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
