package com.greggahill.blondin.model;

import javax.persistence.*;

@Entity
public class Login {
    static final long RETRY_TIMEOUT = 300000L; // 5 minutes
    static final long LOCK_TIMEOUT = 1200000L; // 20 minutes

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    private String userName;
    private String password;
    private int failedAttemptCounter;
    private long failedAttemptStartTime;
    private boolean locked;
    private long lockStartTime;

    public Login() {
    }

    public Login(Long id) {
        this.id = id;
    }

    public Login(String userName, String password, int failedAttemptCounter, long failedAttemptStartTime,
                 boolean locked, long lockStartTime) {
        this.userName = userName;
        this.password = password;
        this.failedAttemptCounter = failedAttemptCounter;
        this.failedAttemptStartTime = failedAttemptStartTime;
        this.locked = locked;
        this.lockStartTime = lockStartTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setFailedAttemptCounter(int failedAttemptCounter) {
        this.failedAttemptCounter = failedAttemptCounter;
    }

    public int getFailedAttemptCounter() {
        return failedAttemptCounter;
    }

    public void setFailedAttemptStartTime(long failedAttemptStartTime) {
        this.failedAttemptStartTime = failedAttemptStartTime;
    }

    public long getFailedAttemptStartTime() {
        return failedAttemptStartTime;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public boolean getLocked() {
        return locked;
    }

    public void set(long lockedStartTime) {
        this.lockStartTime = lockedStartTime;
    }

    public long getLockStartTime() {
        return failedAttemptStartTime;
    }

    public void fail() {
        failedAttemptCounter++;
        // if 5 failed attempts within 5 minutes lock the Login
        if ((failedAttemptCounter > 5) && ((System.currentTimeMillis() - failedAttemptStartTime) < RETRY_TIMEOUT)) {
            locked = true;
        }
    }

    public boolean isLocked() {
        if (!locked) return false;

        if ((System.currentTimeMillis()-lockStartTime) > LOCK_TIMEOUT) {
            locked = false;
            return false;
        }

        return true;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Login user = (Login) o;

        return id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
