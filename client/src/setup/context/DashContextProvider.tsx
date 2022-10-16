import {useState} from "react";
import {DashboardContext} from "./Context";
import {DashboardStateProps, Props} from "../../interface/interface";

const {Provider} = DashboardContext


export const DashboardContextProvider = ({children}: Props) => {
    const [action, setAction] = useState<DashboardStateProps>({
        displayName: 'dashboard',
        show: false
    })

    const getAction = () => {
      return action
    }

    const setActionProp = (props: DashboardStateProps) => {

    }
    
    const setActionType = (actionName: string) => {
        setAction({
            ...action,
            displayName: actionName,
        })
    }

    return (
        <Provider value={{
           getAction,
            setActionProp,
           setActionType
        }}>
            {children}
        </Provider>
    )
}