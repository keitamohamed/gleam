import {HomePage} from "../../component/page/home"
import {Admin} from "../../component/page/admin";
import {Student} from "../../component/page/student";
import {Teacher} from "../../component/page/teacher";
import {Login} from "../../component/page/login";

export const routePath = [
    {
        name: "index",
        path: "/",
        protected: false,
        showLink: false,
        component: HomePage
    },
    {
        name: "admin",
        path: "/admin",
        protected: true,
        showLink: false,
        component: Admin
    },
    {
        name: "teacher",
        path: "/teacher",
        protected: true,
        showLink: false,
        component: Teacher
    },
    {
        name: "student",
        path: "/student",
        protected: true,
        showLink: false,
        component: Student
    },
    {
        name: "login",
        path: "/login",
        protected: false,
        showLink: false,
        component: Login
    },

]