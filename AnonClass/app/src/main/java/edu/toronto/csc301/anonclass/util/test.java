package edu.toronto.csc301.anonclass.util;

public class test {
    public static void main(String[] args) {
        User user = new User.RegisterUserBuilder().email("csc301212@test.com").pwd("1234564").
                firstName("fi").lastName("adfa").UTORid("daf2d").studentFlag(true).build();

        retMsg ret = PassingData.SignUp(user);
        System.out.println("signup: " + ret.getErrorCode());

        User user1 = new User.LoginUserBuilder().email("csc301212@test.com").pwd("1234564").build();
        retMsg ret2 = PassingData.LogIn(user1);
        System.out.println("login: " + ret2.getErrorCode());


        retMsg ret3 = PassingData.EnrolCourse("csc301212@test.com", 2);
        System.out.println("enroll: " + ret3.getErrorCode());
//        System.out.println(ret3.getUser().serialize());

        retMsg ret4 = PassingData.DisplayCourses("csc301212@test.com");
        System.out.println("display: " + ret4.getErrorCode());
        System.out.println(ret4.getUser().serialize());

    }
}
