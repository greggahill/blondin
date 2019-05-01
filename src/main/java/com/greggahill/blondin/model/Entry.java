package com.greggahill.blondin.model;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Entry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private Date datetime;
    private int points;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false, updatable = false)
    private Member member;

    public Entry() {
    }


    public Entry(Date datetime, String reason, int points, Member member) throws Exception {
        this.datetime = datetime;
        this.reason = reason;
        this.points = points;
        this.member = member;
    }

    // put this here to support @Query in the repository.  seems like a hack tho
    /*
    public Long getMember_id() {
        return member.getId();
    }
    */

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getReason() {
        return reason;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Entry entry = (Entry) o;

        return id.equals(entry.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "Member{" +
                "id=" + id +
                ", datetime='" + datetime + '\'' +
                ", points='" + points + '\'' +
                ", member=" + member +
                '}';
    }
}
