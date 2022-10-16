import {useTeacher} from "../../hook/useTeacher";
import {useEffect} from "react";

export const Profile = () => {
    const {findTeacherByID} = useTeacher()

    useEffect(() => {
        findTeacherByID(34493)
    }, [])
    return (
        <div>
            <h1>Teacher profile</h1>
        </div>
    )
}