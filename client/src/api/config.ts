
export const getHeader = (token: string) => {
    return {
        Authorization: token ? `Bearer ${token}` : 'Bearer',
        'Content-Type': 'application/json;charset=UTF-8'
    }
}