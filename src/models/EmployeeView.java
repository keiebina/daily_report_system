package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "employeeView")
@NamedQueries({
    @NamedQuery(
            name = "getAllEmployeeViews",
            query = "SELECT ev FROM EmployeeView AS ev ORDER BY ev.employee_id DESC"
            ),
    @NamedQuery(
            name = "deleteAllEmployeeViews",
            query = "DELETE FROM EmployeeView AS ev"
            ),
})
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "Reset_idEmployeeViews",
            query = "TRUNCATE TABLE EmployeeView ", //実行できたが怪しい
            resultClass = EmployeeView.class
            )
})
@Entity
public class EmployeeView {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "employee_id", nullable = false)
    private Integer employee_id;

    @Column(name = "employee_code", nullable = false, unique = true)
    private String employee_code;

    @Column(name = "empoyee_name", nullable = false)
    private String employee_name;

    @Column(name = "employee_delete_flag", nullable = false)
    private Integer employee_delete_flag;

    @Column(name = "follow_flag", nullable = false)
    private Integer follow_flag;

    //セッターとゲッター
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Integer employee_id) {
        this.employee_id = employee_id;
    }

    public String getEmployee_code() {
        return employee_code;
    }

    public void setEmployee_code(String employee_code) {
        this.employee_code = employee_code;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }

    public Integer getEmployee_delete_flag() {
        return employee_delete_flag;
    }

    public void setEmployee_delete_flag(Integer employee_delete_flag) {
        this.employee_delete_flag = employee_delete_flag;
    }

    public Integer getFollow_flag() {
        return follow_flag;
    }

    public void setFollow_flag(Integer follow_flag) {
        this.follow_flag = follow_flag;
    }

}
