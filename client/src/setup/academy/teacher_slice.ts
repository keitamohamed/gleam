import {TeacherInitialState} from "../../interface/interface_a";
import {createSlice} from "@reduxjs/toolkit";

const reSetTeacher: TeacherInitialState["teacher"] = {
    id: 0,
    name: "",
    gender: "",
    phone: "",
    dob: ""
}

const initialState: TeacherInitialState = {
    address: [],
    auth: {
        authID: 0,
        email: "",
        password: ""
    },
    teacher: {
        dob: "",
        gender: "",
        id: 0,
        name: "",
        phone: ""
    },
    teachers: [],
    message: {},
    error: {},
}

const teacherSlice = createSlice({
    name: "teacher",
    initialState,
    reducers: {
        addTeacher: (state, action) => {
            const teacher = action.payload
            state.teacher[teacher.name as keyof Object] = teacher.value
        },
        loadTeacher: (state, action) => {
           state.teacher = action.payload
        },
        loadTeachers: (state, action) => {
            state.teachers = action.payload
        },
        initTeacher: (state, action) => {
            state.teacher = reSetTeacher
        },
        setErrorMessage: (state, action) => {
            state.error = action.payload
        },
        setMessage: (state, action) => {
            state.message = action.payload
        },
        reSetErrorMessage: (state) => {
            state.error = {}
        },
        reSetMessage: (state) => {
            state.message = {}
        }
    }
})

export const teacherAction = teacherSlice.actions;
export default teacherSlice