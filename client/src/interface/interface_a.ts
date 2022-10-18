
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
    addresses: any[],
    address: {},
    message: any,
    error: any
}