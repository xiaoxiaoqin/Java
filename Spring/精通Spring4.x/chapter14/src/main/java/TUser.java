// default package
// Generated 2017-10-13 16:07:10 by Hibernate Tools 5.0.6.Final


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TUser generated by hbm2java
 */
@Entity
@Table(name="t_user"
    ,catalog="sampledb"
)
public class TUser  implements java.io.Serializable {


     private Integer userId;
     private String userName;
     private String password;
     private Integer score;
     private String lastLogonTime;

    public TUser() {
    }

	
    public TUser(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }
    public TUser(String userName, String password, Integer score, String lastLogonTime) {
       this.userName = userName;
       this.password = password;
       this.score = score;
       this.lastLogonTime = lastLogonTime;
    }
   
     @Id @GeneratedValue(strategy=IDENTITY)

    
    @Column(name="user_id", unique=true, nullable=false)
    public Integer getUserId() {
        return this.userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    
    @Column(name="user_name", nullable=false, length=30)
    public String getUserName() {
        return this.userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }

    
    @Column(name="password", nullable=false, length=30)
    public String getPassword() {
        return this.password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }

    
    @Column(name="score")
    public Integer getScore() {
        return this.score;
    }
    
    public void setScore(Integer score) {
        this.score = score;
    }

    
    @Column(name="last_logon_time", length=16777215)
    public String getLastLogonTime() {
        return this.lastLogonTime;
    }
    
    public void setLastLogonTime(String lastLogonTime) {
        this.lastLogonTime = lastLogonTime;
    }




}


