package com.kangwon.a356.kangwonunivapp.dataprocess.database;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Attandance implements Comparable<Attandance> {
//////인증을 보낼때 사용함.
    public static String AUTH = "auth";



    public static String CHECK_DATE = "checkdate";
    public static String CHECK_TIME = "time";
    public static String STUDENT_ID = "student";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
    public static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
    private String student;
    private Date date;
    private Date time;


    public Attandance(String studentName, String date, String time) {
        this.student = studentName;
        try {
            this.date = dateFormat.parse(date);
            this.time = dateFormat.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public Date getDate() {
        return date;
    }

    public Date getTime() {
        return time;
    }

    public String getStudent() {
        return student;
    }

    @Override
    public String toString() {
        return student + " " + date + " " + time;
    }

    @Override
    public int compareTo(Attandance o) {
        int flag = this.date.compareTo(o.getDate());
        if (flag == 0) {
            this.time.compareTo(o.getTime());
        }
        return flag;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        if (this == obj) {
            System.out.println("Object Same");
            return true;
        }
        Attandance attd = (Attandance) obj;
        if (student.equals(attd.getStudent()))
            if (date.equals(attd.getDate())) {
                if (time.equals(attd.getTime()))
                    return true;
            }
        return false;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int hashCode = 1;
        hashCode = prime * hashCode + ((student == null) ? 0 : student.hashCode());
        hashCode = prime * hashCode + date.hashCode();
        hashCode = prime * hashCode + time.hashCode();
        return hashCode;
    }


}
