package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
            name = "getAllfollow_ids",
            query = "SELECT f.follow_id FROM Follow AS f"         //フォローされる側の社員IDをすべて取得
            ),
    @NamedQuery(
            name = "getFollow_ids",
            query = "SELECT f.follow_id FROM Follow AS f WHERE f.user_id = :user_id"         //フォローされる側の社員IDをすべて取得
            ),
    @NamedQuery(
            name = "countFollow_ids",
            query = "SELECT COUNT(f) FROM Follow AS f WHERE f.user_id = :user_id"         //フォローされる側の社員ID数を取得
            ),
    @NamedQuery(
            name = "deleteFollow_id",
            query = "DELETE  FROM Follow AS f WHERE f.user_id = :user_id AND f.follow_id = :follow_id"     //フォローの解除
            ),

})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @JoinColumn(name = "user_id", nullable = false)            //フォローする側の社員ID
    private Integer user_id;


    @Column(name = "follow_id", nullable = false)               //フォローされる側の社員ID
    private Integer follow_id;

    public Integer getId() {
        return id;
    }


    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getUser_id() {
        return user_id;
    }


    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }


    public Integer getFollow_id() {
        return follow_id;
    }


    public void setFollow_id(Integer follow_id) {
        this.follow_id = follow_id;
    }

}
