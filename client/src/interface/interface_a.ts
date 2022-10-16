
export interface TeacherInitialState {
    teacher: {
        id: number,
        name: string,
        gender: string,
        phone: string,
        dob: string
    },
    teachers: any[],
    auth: {
        authID: number,
        email: string
        password: string
    }
    address: any[],
    message: any,
    error: any
}