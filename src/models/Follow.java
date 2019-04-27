package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name = "follow")
@NamedQueries({
    @NamedQuery( //あとで追加
            name = "getAllFollows",
            query = "SELECT f FROM Follow AS f"
            ),
    @NamedQuery(
            name = "getAllfollows",
            query = "SELECT f.followEmployee FROM Follow AS f"         //フォローされている社員情報をすべて取得
            ),
    @NamedQuery(
            name = "getFollows",
            query = "SELECT f.followEmployee FROM Follow AS f WHERE f.user = :user"  //ログインしている従業員にフォローされている社員情報をすべて取得
            ),
    @NamedQuery(
            name = "countFollows",
            query = "SELECT COUNT(f) FROM Follow AS f WHERE f.user = :user"         //ログインしている従業員にフォローされている社員数を取得
            ),
    @NamedQuery(
            name = "deleteFollow",
            query = "DELETE  FROM Follow AS f WHERE f.user = :user AND f.followEmployee = :followEmployee"     //フォローの解除
            ),

})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user", nullable = false)            //フォローする側の社員情報
    private Employee user;

    @ManyToOne
    @JoinColumn(name = "followEmployee", nullable = false)               //フォローされる側の社員情報
    private Employee followEmployee;

    //セッターとゲッター
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getUser() {
        return user;
    }

    public void setUser(Employee user) {
        this.user = user;
    }

    public Employee getFollowEmployee() {
        return followEmployee;
    }

    public void setFollowEmployee(Employee followEmployee) {
        this.followEmployee = followEmployee;
    }





}
