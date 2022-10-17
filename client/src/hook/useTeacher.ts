import {useAppDispatch} from "../setup/academy/useReduxHook";
import {GET_REQUEST} from "../api/Request";
import {ApiPath} from "../api/path";
import {teacherAction} from "../setup/academy/teacher_slice";
import {useEffect} from "react";

let isDataLoaded = false;

export const useTeacher = () => {
    const dispatch = useAppDispatch()

    const setSelectedTeacher = (teacher: any) => {
        dispatch(teacherAction.loadTeacher(teacher))
        dispatch(teacherAction.setAuth(teacher))
        dispatch(teacherAction.setAddresses(teacher))
    }

    const findTeacherByID = (id: number) => {
        findTeacher(id)
    }

    const setErrorMessage = (error: object) => {
        dispatch(teacherAction.setErrorMessage(error))
    }

    const findTeacher = (id: number) => {
        // @ts-ignore
        dispatch(GET_REQUEST(null, ApiPath.GET_TEACHER(id), setSelectedTeacher, setErrorMessage))
    }

    useEffect(() => {
        if (!isDataLoaded) {
            findTeacherByID(34493)
            isDataLoaded = true;
        }
    }, [])

    return {
        findTeacherByID
    }
}